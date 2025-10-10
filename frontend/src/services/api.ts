import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost';

export const userServiceApi = axios.create({
    baseURL: `${API_BASE_URL}:8081/api`,
    headers: {
        'Content-Type': 'application/json'
    },
});

export const roomServiceApi = axios.create({
    baseURL: `${API_BASE_URL}:8082/api`,
    headers: {
        'Content-Type': 'application/json'
    },
});

export const messageServiceApi = axios.create({
    baseURL: `${API_BASE_URL}:8083/api`,
    headers: {
        'Content-Type': 'application/json'
    },
});

export const websocketUrl = `${API_BASE_URL}:8080/ws`;
