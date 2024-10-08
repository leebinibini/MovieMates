package com.nc13.moviemates.component.model;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserModel {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private Role role;

    public enum Role {
        ADMIN,
        USER,

        @JsonEnumDefaultValue
        UNKNOWN;
    }

}
