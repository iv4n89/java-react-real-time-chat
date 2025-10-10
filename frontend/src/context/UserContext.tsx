/* eslint-disable react-refresh/only-export-components */
import React, { createContext } from "react";
import type { UserJoinResponse } from '../types';

interface UserContextType {
    user: UserJoinResponse | null;
    setUser: (user: UserJoinResponse | null) => void;
    isJoined: boolean;
}

const UserContext = createContext<UserContextType | undefined>(undefined);

interface UserProviderProps {
    children: React.ReactNode;
}

export const UserProvider = ({children}: UserProviderProps) => {
    const [user, setUser] = React.useState<UserJoinResponse | null>(null);
    
    const value: UserContextType = {
        user,
        setUser,
        isJoined: user !== null,
    };

    return <UserContext.Provider value={value}>{children}</UserContext.Provider>;
}

export const useUserContext = () => {
    const context = React.useContext(UserContext);
    if (context === undefined) {
        throw new Error("useUserContext must be used within a UserProvider");
    }

    return context;
}
