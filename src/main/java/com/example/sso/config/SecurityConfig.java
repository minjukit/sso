package com.example.sso.config;

import com.example.sso.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 활성화, 필터체인 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .oauth2Login(oauth2Login ->
                // oauth2Login.loginPage("/login/oauth2") // 로그인 페이지 기본 설정
                oauth2Login.userInfoEndpoint(userInfoEndpoint ->
                        userInfoEndpoint.userService(customOAuth2UserService))
        );
        return http.build();
    }

}