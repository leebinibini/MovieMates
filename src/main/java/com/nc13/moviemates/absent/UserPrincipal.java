package com.nc13.moviemates.absent;

import com.nc13.moviemates.entity.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
public class UserPrincipal implements UserDetails, OAuth2User {

    private UserEntity user;
    private String nameAttributeKey; // for OAuth
    private Map<String, Object> attributes; // for OAuth
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(UserEntity user) {
        this.user = user;
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getKey()));
    }


    public UserPrincipal(UserEntity user, Map<String, Object> attributes) {
        this.user = user;
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getKey()));
        this.attributes = attributes;

        // 구글 로그인 시 attributes에서 값 추출하여 UserEntity에 매핑
        /*if (attributes != null) {
            user.setNickname((String) attributes.get("name"));  // 구글에서는 'name' 키를 사용
            user.setEmail((String) attributes.get("email"));
            // 추가적인 프로필 이미지 URL이 있다면 설정 가능
            user.setProfileImageUrl((String) attributes.get("picture"));
        }*/
    }

    /**
     * OAuth2User method implements
     */
    @Override
    public String getName() {
        return user.getNickname();
    }

    /**
     * UserDetails method implements
     */
    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return String.valueOf(user.getId());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
