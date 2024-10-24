package com.nc13.moviemates.enums;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN", "관리자"),
    USER("ROLE_USER", "일반 사용자"),
    NOT_REGISTERED("ROLE_NOT_REGISTERED", "회원가입 이전 사용자");

    private final String key;
    private final String title;
}
