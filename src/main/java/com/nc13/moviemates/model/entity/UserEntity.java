package com.nc13.moviemates.model.entity;

import com.nc13.moviemates.model.domain.UserModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table (name="users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private UserModel.Role role;

    public enum Role {
        AUDIENCE, ADMIN
    }
}