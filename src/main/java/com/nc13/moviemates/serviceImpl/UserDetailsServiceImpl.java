package com.nc13.moviemates.serviceImpl;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nc13.moviemates.entity.UserEntity;
import com.nc13.moviemates.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found with username:" + username);
        }
        return maptToUserDetails(user);
    }

    private UserDetails maptToUserDetails(UserEntity user) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        // UserDetails 객체를 생성하여 반환
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),       // 사용자 이름 또는 이메일 (인증에서 사용할 필드)
                user.getPassword(),    // 사용자 비밀번호 (암호화된 상태여야 함)
                authorities                  // 사용자 권한
        );
    }
}