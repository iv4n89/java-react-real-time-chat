import React from "react";
import { useChatContext } from "../../context/ChatContext"
import { useUserContext } from "../../context/UserContext";

export const MessageList = () => {
    const {messages} = useChatContext();
    const {user} = useUserContext();
    const messagesEndRef = React.useRef<HTMLDivElement>(null);

    React.useEffect(() => {
        messagesEndRef.current?.scrollIntoView({behavior: "smooth"});
    }, [messages]);

    const formatTimestamp = (timestamp: string) => {
        try {
            // Handle different timestamp formats
            const date = new Date(timestamp);
            if (isNaN(date.getTime())) {
                return 'Ahora';
            }
            return date.toLocaleTimeString('es-ES', {
                hour: '2-digit',
                minute: '2-digit',
            });
        } catch {
            return 'Ahora';
        }
    };

    return (
        <div className="message-list">
            {
                messages.map((message, index) => {
                    const isOwnMessage = message.userId === user?.userId;
                    const isSystemMessage = message.type !== 'CHAT';

                    if (isSystemMessage) {
                        return (
                            <div key={index} className="message system-message">
                                <span className="message-content">{message.content}</span>
                            </div>
                        )
                    }

                    return (
                        <div
                            key={index}
                            className={`message ${isOwnMessage ? 'own-message' : 'other-message'}`}
                        >
                            <div className="message-header">
                                <span className="message-username">{message.username}</span>
                                <span className="message-timestamp">
                                    {formatTimestamp(message.timestamp)}
                                </span>
                            </div>
                            <div className="message-content">{message.content}</div>
                        </div>
                    );
                })
            }
            <div ref={messagesEndRef} />
        </div>
    )
}
