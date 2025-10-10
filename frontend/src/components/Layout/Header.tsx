import { useUserContext } from "../../context/UserContext"

export const Header = () => {
    const { user } = useUserContext();

    if (!user) return null;

    return (
        <header className="header">
            <div className="header-content">
                <h1>Chat App</h1>
                <div className="user-info">
                    <div className="username">
                        {user.username}
                    </div>
                    <div className="room-name">
                        {user.roomName}
                    </div>
                </div>
            </div>
        </header>
    )
}
