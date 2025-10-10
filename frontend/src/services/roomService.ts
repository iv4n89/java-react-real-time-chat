import { roomServiceApi } from './api';
import type { Room } from '../types';

export const roomService = {
  // Obtener sala por ID de usuario
  getUserRoom: async (userId: string): Promise<Room> => {
    const response = await roomServiceApi.get<Room>(`/rooms/user/${userId}`);
    return response.data;
  },

  // Obtener sala por ID de sala
  getRoom: async (roomId: string): Promise<Room> => {
    const response = await roomServiceApi.get<Room>(`/rooms/${roomId}`);
    return response.data;
  },

  // Obtener todas las salas
  getAllRooms: async (): Promise<Room[]> => {
    const response = await roomServiceApi.get<Room[]>('/rooms');
    return response.data;
  },

  // Obtener salas disponibles
  getAvailableRooms: async (): Promise<Room[]> => {
    const response = await roomServiceApi.get<Room[]>('/rooms/available');
    return response.data;
  },
};
