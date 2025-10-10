import React, { type FormEvent, type KeyboardEvent } from "react"
import { useUserContext } from "../../context/UserContext";
import { useChat } from "../../hooks/useChat";
import { useWebSocket } from "../../hooks/useWebSocket";

export const MessageInput = () => {
    const [content, setContent] = React.useState("");
    const {user} =useUserContext();
    const {sendMessage: sendToBackend} = useChat(user?.roomId || "");
    const {sendMessage: sendToWebsocket} = useWebSocket(user?.roomId || null, user?.userId || null);

    const handleSubmit = async (e: FormEvent) => {
        e.preventDefault();
        if (!content.trim() || !user) return;

        const success = await sendToBackend(user.userId, user.username, content);

        if (success) {
            setContent("");
        }
    };

    const handleKeyPress = (e: KeyboardEvent<HTMLInputElement>) => {
        if (e.key === 'Enter' && !e.shiftKey) {
            e.preventDefault();
            handleSubmit(e);
        }
    };

    return (
        <form className="message-input-form" onSubmit={handleSubmit}>
            <input
                type="text"
                className="message-input"
                placeholder="Escribe un mensaje..."
                value={content}
                onChange={e => setContent(e.target.value)}
                onKeyDown={handleKeyPress}
                disabled={!user}
            />
            <button type="submit" className="send-button" disabled={!content.trim() || !user}>
                Enviar
            </button>
        </form>
    )
};
