import { Client, type IMessage, type StompConfig } from "@stomp/stompjs";
import SockJS from "sockjs-client";

export class WebSocketService {
    private client: Client | null = null;
    private connected: boolean = false;
    private url: string;

    constructor(url: string) {
        this.url = url;
    }

    connect(onConnect: () => void, onError: (error: unknown) => void): void {
        const config: StompConfig = {
            webSocketFactory: () => new SockJS(this.url),
            debug: (str: string) => {
                console.log('STOMP: ' + str);
            },
            reconnectDelay: 5000,
            heartbeatIncoming: 4000,
            heartbeatOutgoing: 4000,
            onConnect: () => {
                this.connected = true;
                console.log('WebSocket connected');
                onConnect();
            },
            onStompError: (frame: unknown) => {
                console.error('STOMP error' + frame);
                onError(frame);
            },
            onWebSocketError: (error: unknown) => {
                console.error('WebSocket error', error);
                onError(error);
            }
        };

        this.client = new Client(config);
        this.client.activate();
    }

    subscribe(roomId: string, onMessage: (message: WebSocketMessage) => void): void {
        if (!this.client || !this.connected) {
            console.error('WebSocket is not connected');
            return;
        }

        this.client.subscribe(`/topic/room/${roomId}`, (message: IMessage) => {
            const parsedMessage: WebSocketMessage = JSON.parse(message.body);
            onMessage(parsedMessage);
        });

        console.log(`Subscribed to room ${roomId}`);
    }

    sendMessage(message: WebSocketMessage): void {
        if (!this.client || !this.connected) {
            console.error('WebSocket is not connected');
            return;
        }

        this.client.publish({
            destination: `/app/chat.send`,
            body: JSON.stringify(message),
        });
    }

    disconnect(): void {
        if (this.client) {
            this.client.deactivate();
            this.connected = false;
            console.log('WebSocket disconnected');
        }
    }

    isConnected(): boolean {
        return this.connected;
    }
}
