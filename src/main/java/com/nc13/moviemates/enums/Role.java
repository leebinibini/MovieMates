package com.nc13.moviemates.enums;

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
}
