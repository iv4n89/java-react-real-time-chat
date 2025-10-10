import { userServiceApi } from "./api"

export const userService = {
    join: async (): Promise<UserJoinResponse> => {
        const response = await userServiceApi.post<UserJoinResponse>('/users/join');
        return response.data;
    },
    getUser: async (userId: string): Promise<User> => {
        const response = await userServiceApi.get<User>(`/users/${userId}`);
        return response.data;
    },
    disconnect: async (userId: string): Promise<void> => {
        await userServiceApi.delete(`/users/${userId}`);
    }
}
