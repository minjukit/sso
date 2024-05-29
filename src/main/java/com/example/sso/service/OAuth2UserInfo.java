package com.example.sso.service;

import com.example.sso.domain.User;
import com.example.sso.domain.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;


@Builder
@Getter
@ToString
public class OAuth2UserInfo {

        private String providerId;
        private String password;
        private String email;
        private String profilename;
        private String provider;

        public static OAuth2UserInfo of(String provider, Map<String, Object> attributes) {
            switch (provider) {
                case "google":
                    return ofGoogle(attributes);
                case "naver":
                    return ofNaver(attributes);
                default:
                    throw new RuntimeException();
            }
        }

        private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
            return OAuth2UserInfo.builder()
                    .provider("google")
                    .providerId("google_" + (String) attributes.get("sub"))
                    .password((String) attributes.get("sub"))
                    .profilename((String) attributes.get("name")) // name 맞는지 확인하셈
                    .email((String) attributes.get("email"))
                    .build();
        }

        private static OAuth2UserInfo ofNaver(Map<String, Object> attributes) {
            return OAuth2UserInfo.builder()
                    .provider("naver")
                    .providerId("naver_" + (String) ((Map) attributes.get("response")).get("id"))
                    .password((String) ((Map) attributes.get("response")).get("id"))
                    .profilename((String) ((Map) attributes.get("response")).get("name")) // name 맞는지 확인하셈
                    .email((String) attributes.get("email"))
                    .build();
        }

        public User toEntity() {
            return User.builder()
                    .providerId(providerId)
                    .password(password)
                    .provider(provider)
                    .profileName(profilename)
                    .email(email)
                    .role(UserRole.USER)
                    .build();
        }


}
