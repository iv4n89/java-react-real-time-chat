/* eslint-disable react-refresh/only-export-components */
import React, { createContext } from "react";

interface ChatContextType {
    messages: WebSocketMessage[];
    addMessage: (message: WebSocketMessage) => void;
    setMessages: (messages: WebSocketMessage[]) => void;
    clearMessages: () => void;
    connected: boolean;
    setConnected: (connected: boolean) => void;
}

const ChatContext = createContext<ChatContextType | undefined>(undefined);

interface ChatProviderProps {
    children: React.ReactNode;
}

export const ChatProvider = ({children}: ChatProviderProps) => {
    const [messages, setMessagesState] = React.useState<WebSocketMessage[]>([]);
    const [connected, setConnected] = React.useState<boolean>(false);

    const addMessage = (message: WebSocketMessage) => {
        setMessagesState((prev) => {
            // Check for duplicates based on userId + content + timestamp
            const isDuplicate = prev.some(
                (msg) =>
                    msg.userId === message.userId &&
                    msg.content === message.content &&
                    msg.timestamp === message.timestamp
            );

            if (isDuplicate) {
                console.log('Duplicate message detected, skipping:', message.content);
                return prev;
            }

            return [...prev, message];
        });
    };

    const setMessages = (newMessages: WebSocketMessage[]) => {
        setMessagesState(newMessages);
    };

    const clearMessages = () => {
        setMessagesState([]);
    };

    const value: ChatContextType = {
        messages,
        addMessage,
        setMessages,
        clearMessages,
        connected,
        setConnected,
    };

    return <ChatContext.Provider value={value}>{children}</ChatContext.Provider>;
};

export const useChatContext = () => {
    const context = React.useContext(ChatContext);
    if (context === undefined) {
        throw new Error("useChatContext must be used within a ChatProvider");
    }

    return context;
}
