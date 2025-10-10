import React from "react";
import { useUserContext } from "../context/UserContext"
import { userService } from "../services/userService";
import { roomService } from "../services/roomService";
import type { UserJoinResponse } from '../types';

export const useUser = () => {
    const {user, setUser} = useUserContext();
    const [loading, setLoading] = React.useState(false);
    const [error, setError] = React.useState<string | null>(null);

    const joinChat = async (): Promise<UserJoinResponse | null> => {
        setLoading(true);
        setError(null);

        try {
            // 1. Create user
            const userResponse = await userService.join();
            console.log('User created:', userResponse);

            // 2. Wait a bit for Kafka to process the event and assign room
            await new Promise(resolve => setTimeout(resolve, 1000));

            // 3. Get user's assigned room
            const room = await roomService.getUserRoom(userResponse.userId);
            console.log('Room assigned:', room);

            // 4. Create complete user object with room info
            const completeUser: UserJoinResponse = {
                ...userResponse,
                roomId: room.roomId,
                roomName: room.name,
            };

            setUser(completeUser);
            return completeUser;
        } catch (err) {
            const errorMessage = err instanceof Error ? err.message : 'Failed to join chat';
            setError(errorMessage);
            console.error("Error joining chat:", err);
            return null;
        } finally {
            setLoading(false);
        }
    };

    const disconnect = async () => {
        if (!user) return;

        try {
            await userService.disconnect(user.userId);
            setUser(null);
        } catch (err) {
            console.error("Error disconnecting user:", err);
        }
    }

    return {
        user,
        loading,
        error,
        joinChat,
        disconnect,
    }
}
