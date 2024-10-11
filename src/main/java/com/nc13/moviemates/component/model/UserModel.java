package com.nc13.moviemates.component.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Data
public class UserModel {
    private Long id;
    private String fName;
    private String lName;
    private String email;
    private String password;
    private String nickname;
    private Role role;
    private int tel;
    private String gender;

    public enum Role {
        ADMIN, USER,
    }

}
