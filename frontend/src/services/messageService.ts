import { messageServiceApi } from "./api";

interface SendMessageRequest {
    roomId: string;
    userId: string;
    username: string;
    content: string;
};

export const messageService = {
    send: async (request: SendMessageRequest): Promise<Message> => {
        const response = await messageServiceApi.post<Message>(`/messages`, request);
        return response.data;
    },
    getHistory: async (roomId: string): Promise<Message[]> => {
        const response = await messageServiceApi.get<Message[]>(`/messages/room/${roomId}`);
        return response.data;
    },
    getRecent: async (roomId: string, limit = 50): Promise<Message[]> => {
        const response = await messageServiceApi.get<Message[]>(`/messages/room/${roomId}?limit=${limit}`);
        return response.data;
    }
}
