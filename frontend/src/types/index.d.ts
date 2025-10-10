interface User {
    id: string;
    username: string;
    roomId: string;
    createdAt: string;
}

interface Room {
    roomId: string;
    name: string;
    currentUsers: number;
    maxUsers: number;
    status: 'OPEN' | 'FULL' | 'CLOSED';
}

interface Message {
    id: string;
    roomId: string;
    userId: string;
    username: string;
    content: string;
    timestamp: string;
    type: 'CHAT' | 'JOIN' | 'LEAVE' | 'SYSTEM';
}

interface WebSocketMessage {
    roomId: string;
    userId: string;
    username: string;
    content: string;
    timestamp: string;
    type: 'CHAT' | 'JOIN' | 'LEAVE' | 'SYSTEM';
}

interface UserJoinResponse {
    userId: string;
    username: string;
    roomId: string;
    roomName: string;
    timestamp: string;
}
