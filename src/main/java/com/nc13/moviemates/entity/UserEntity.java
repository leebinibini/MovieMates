package com.nc13.moviemates.entity;

import com.nc13.moviemates.component.model.UserModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    private String fName;
    private String lName;
    private String email;
    private String password;
    private String nickname;
    private UserModel.Role role;
    private int tel;
    private String gender;


    public enum Role {
        AUDIENCE, ADMIN
    }
}
