package com.nc13.moviemates.component.model;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.nc13.moviemates.enums.Provider;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import com.nc13.moviemates.enums.Role;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {
    private Long id;
    private String fName;
    private String lName;
    private String email;
    private String password;
    private String nickname;
    private Role role;
    private String tel;
    private String gender;
    private String profileImageUrl;
    private Provider provider;

}
