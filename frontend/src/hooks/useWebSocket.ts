import React, { useRef } from "react";
import { WebSocketService } from "../services/websocket";
import { useChatContext } from "../context/ChatContext";
import { websocketUrl } from "../services/api";

export const useWebSocket = (roomId: string | null, userId: string | null) => {
  const wsService = useRef<WebSocketService | null>(null);
  const { addMessage, setConnected } = useChatContext();
  const [error, setError] = React.useState<string | null>(null);

  // Stable references to avoid effect re-runs
  const addMessageRef = useRef(addMessage);
  const setConnectedRef = useRef(setConnected);

  React.useEffect(() => {
    addMessageRef.current = addMessage;
    setConnectedRef.current = setConnected;
  }, [addMessage, setConnected]);

  React.useEffect(() => {
    if (!roomId || !userId) {
      console.log('useWebSocket: Missing roomId or userId', { roomId, userId });
      return;
    }

    console.log('useWebSocket: Connecting to WebSocket...', { roomId, userId, websocketUrl });
    wsService.current = new WebSocketService(websocketUrl);

    wsService.current.connect(
      () => {
        console.log('useWebSocket: Connected successfully!');
        setConnectedRef.current(true);
        setError(null);
        if (wsService.current) {
          wsService.current.subscribe(roomId, (message: WebSocketMessage) => {
            console.log('useWebSocket: Received message:', message);
            addMessageRef.current(message);
          });
        }
      },
      (error) => {
        console.error('useWebSocket: Connection error:', error);
        setConnectedRef.current(false);
        setError("WebSocket error: " + (error as Error).message);
      }
    );

    return () => {
      console.log('useWebSocket: Cleaning up connection');
      if (wsService.current) {
        wsService.current.disconnect();
        setConnectedRef.current(false);
      }
    };
  }, [roomId, userId]);

  const sendMessage = (content: string, username: string) => {
    if (!wsService.current || !roomId || !userId) {
      console.error(
        "Cannot send message: WebSocket not connected or missing room/user ID"
      );
      return;
    }

    const message: WebSocketMessage = {
      roomId,
      userId,
      username,
      content,
      timestamp: new Date().toISOString(),
      type: "CHAT",
    };

    wsService.current.sendMessage(message);
  };

  return {
    sendMessage,
    error,
  };
};
