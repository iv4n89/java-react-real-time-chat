import { useChatContext } from "../../context/ChatContext";
import { useUserContext } from "../../context/UserContext";

export const UserList = () => {
  const { connected } = useChatContext();
  const { user } = useUserContext();

  return (
    <div className="user-list">
      <h3>Usuarios Conectados</h3>
      <div className="connection-status">
        <span
          className={`status-indicator ${
            connected ? "connected" : "disconnected"
          }`}
        ></span>
        <span>{connected ? "Conectado" : "Desconectado"}</span>
      </div>
      {user && (
        <div className="user-item">
          <span className="user-name">{user.username}</span>
          <span className="user-badge">Tú</span>
        </div>
      )}
    </div>
  );
};
