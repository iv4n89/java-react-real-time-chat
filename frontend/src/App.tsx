import React from 'react';
import './App.css'
import { useUser } from './hooks/useUser'
import { ChatRoom } from './components/Chat/ChatRoom';
import { UserProvider } from './context/UserContext';
import { ChatProvider } from './context/ChatContext';

function AppContent() {
  const {user, loading, error, joinChat} = useUser();
  const [isJoining, setIsJoining] = React.useState(false);

  React.useEffect(() => {
    const autojoin = async () => {
      setIsJoining(true);
      await joinChat();
      setIsJoining(false);
    };
    
    if (!user && !loading) {
      autojoin();
    }
  }, [user, loading, joinChat]);
  
  if (isJoining || loading) {
    return (
      <div className="app-loading">
        <h2>Uniéndose al chat...</h2>
        <div className="spinner"></div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="app-error">
        <h2>Error</h2>
        <p>{error}</p>
        <button onClick={joinChat}>Reintentar</button>
      </div>
    )
  }

  if (!user) {
    return (
      <div className="app-welcome">
        <h1>Bienvenido al Chat</h1>
        <button onClick={joinChat} className='join-button'>Unirse al Chat</button>
      </div>
    )
  }

  return <ChatRoom />;
}

function App() {
  return (
    <UserProvider>
      <ChatProvider>
        <AppContent />
      </ChatProvider>
    </UserProvider>
  )
}

export default App
