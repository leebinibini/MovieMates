package com.nc13.moviemates.config;

import com.nc13.moviemates.absent.UserPrincipal;
import com.nc13.moviemates.entity.UserEntity;
import com.nc13.moviemates.util.PrincipalOauth2UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import com.nc13.moviemates.serviceImpl.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final PrincipalOauth2UserService principalOauth2UserService; // Inject your custom OAuth2 user service

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info(" ##### 시큐리티 필터 체인 진입 ##### ");
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/images/**", "/js/**", "/lib/**","/favicon.ico", "/admin/**", "/less/**", "/profile/**", "/vendors/**", "/style.css", "/fonts/**").permitAll()
                        .requestMatchers(
                                "/",
                                "/api/admin/**",
                                "/api/images/**",
                                "/api/chart/**",
                                "/api/movie/**",
                                "/api/payments/**",
                                "/api/poster/**",
                                "/api/reservation/**",
                                "/api/review/**",
                                "/api/schedule/**",
                                "/api/seat/**",
                                "/api/theater/**",
                                "/api/user/**",
                                "/api/crawl/**",
                                "/api/wish/**",
                                "/api/user/login",
                                "/api/user/register"
                        ).permitAll()
                        .requestMatchers("/**").authenticated()
                        .requestMatchers("/api/user/**").hasRole("USER")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/api/user/login")  // Custom login page URL
                        .successHandler((request, response, authentication) -> {
                            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
                            UserEntity userEntity = userPrincipal.getUser();
                            // 사용자 정보를 세션에 저장
                            HttpSession session = request.getSession();
                            session.setAttribute("loginUser", userEntity);
                            log.info("오어스 로그인 세션 ID: {}", session.getId());
                            log.info("세션에 저장된 사용자 이메일: {}", userEntity.getEmail());
                            log.info("세션에 저장된 사용자 닉네임: {}", userEntity.getNickname());


                            // 로그인한 사용자 정보 가져오기
                            String role = authentication.getAuthorities().stream()
                                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                                    .findFirst()
                                    .orElse("");
                            System.out.println("role 출력!!!" + role);

                            if ("ROLE_ADMIN".equals(role)) {
                                response.sendRedirect("/api/admin");  // 관리자 로그인 페이지로 리다이렉트
                            } else {
                                response.sendRedirect("/");  // 일반 사용자는 메인 페이지로 리다이렉트
                            }
                        })
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(principalOauth2UserService)  // Set your custom OAuth2 user service
                        )
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")

                )
                .userDetailsService(userDetailsServiceImpl)
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/403.html")
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}