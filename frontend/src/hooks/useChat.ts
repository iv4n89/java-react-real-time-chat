import React, { useRef } from "react";
import { useChatContext } from "../context/ChatContext"
import { messageService } from "../services/messageService";

export const useChat = (roomId: string | null) => {
    const { setMessages } = useChatContext();
    const [loading, setLoading] = React.useState(false);
    const [error, setError] = React.useState<string | null>(null);

    // Stable reference to avoid infinite loop
    const setMessagesRef = useRef(setMessages);

    React.useEffect(() => {
        setMessagesRef.current = setMessages;
    }, [setMessages]);

    React.useEffect(() => {
        if (!roomId) return;

        const loadHistory = async () => {
            setLoading(true);
            setError(null);
            try {
                const history = await messageService.getRecent(roomId, 50);
                const wsMessages = history.map((msg: Message) => ({
                    roomId: msg.roomId,
                    userId: msg.userId,
                    username: msg.username,
                    content: msg.content,
                    timestamp: msg.timestamp,
                    type: msg.type,
                }));
                setMessagesRef.current(wsMessages);
            } catch (err) {
                const errorMessage = err instanceof Error ? err.message : 'Failed to load messages';
                setError(errorMessage);
                console.error("Error loading message history:", err);
            } finally {
                setLoading(false);
            }
        }

        loadHistory();
    }, [roomId]);

    const sendMessage = async (userId: string, username: string, content: string): Promise<boolean> => {
        if (!roomId) return false;

        try {
            await messageService.send({
                roomId,
                userId,
                username,
                content,
            });
            return true;
        } catch (err) {
            console.error("Error sending message:", err);
            return false;
        }
    };

    return {
        loading,
        error,
        sendMessage,
    }
}
