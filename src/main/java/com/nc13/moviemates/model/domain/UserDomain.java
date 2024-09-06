package com.nc13.moviemates.model.domain;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDomain {
    private Long id;
    private String email;
    private String password;
    private String nickname;
}
