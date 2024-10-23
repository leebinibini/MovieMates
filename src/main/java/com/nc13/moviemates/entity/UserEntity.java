package com.nc13.moviemates.entity;

import com.nc13.moviemates.component.model.UserModel;
import com.nc13.moviemates.enums.Provider;
import jakarta.persistence.*;
import lombok.*;
import com.nc13.moviemates.enums.Role;


import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table (name="users")
public class UserEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private Role role;
    private String tel;
    private String gender;
    private String profileImageUrl;
    private Provider provider;

}
