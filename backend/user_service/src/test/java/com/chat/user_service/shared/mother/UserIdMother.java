package com.chat.user_service.shared.mother;

import com.chat.user_service.domain.model.UserId;

public class UserIdMother {
    public static UserId random() {
        return UserId.generate();
    }

    public static UserId of(String uuid) {
        return UserId.of(uuid);
    }
}
