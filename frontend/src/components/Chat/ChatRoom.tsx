import { useUserContext } from "../../context/UserContext"
import { useChat } from "../../hooks/useChat";
import { useWebSocket } from "../../hooks/useWebSocket";
import { Header } from "../Layout/Header";
import { MessageInput } from "./MessageInput";
import { MessageList } from "./MessageList";
import { UserList } from "./UserList";

export const ChatRoom = () => {
    const {user} = useUserContext();

    console.log('ChatRoom: user data', user);
    console.log('ChatRoom: roomId', user?.roomId, 'userId', user?.userId);

    useWebSocket(user?.roomId || null, user?.userId || null);

    const {loading, error} = useChat(user?.roomId || null);

    if (!user) {
        return (
            <div className="loading-container">
                <p>Cargando...</p>
            </div>
        );
    }

    return (
        <div className="chat-container">
            <Header />
            <div className="chat-main">
                <div className="chat-messages">
                    {loading && <p className="loading-text">Cargando mensajes...</p>}
                    {error && <p className="error-text">Error: {error}</p>}
                    <MessageList />
                </div>
                <aside className="chat-sidebar">
                    <UserList />
                </aside>
            </div>
            <MessageInput />
        </div>
    )
}
