package com.nc13.moviemates.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_ADMIN("ROLE_ADMIN", "관리자"),
    ROLE_USER("ROLE_USER", "일반 사용자"),
    ROLE_NOT_REGISTERED("ROLE_NOT_REGISTERED", "회원가입 이전 사용자");

    private final String key;
    private final String title;

   /* @JsonValue
    public String getKey() {
        // JSON으로 직렬화될 때 "ROLE_USER" 대신 "USER" 형식으로 반환
        return key.replace("ROLE_", "");
    }

    @JsonCreator
    public static Role fromValue(String value) {
        // "USER" 또는 "ADMIN" 등의 값으로 Enum을 역직렬화
        String formattedValue = "ROLE_" + value.toUpperCase();
        for (Role role : Role.values()) {
            if (role.key.equals(formattedValue)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown enum type " + value);
    }*/
}
