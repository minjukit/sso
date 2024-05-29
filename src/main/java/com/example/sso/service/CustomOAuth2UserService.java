package com.example.sso.service;

import com.example.sso.Repository.CustomUserDetails;
import com.example.sso.Repository.UserRepository;
import com.example.sso.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1. OAuth2 로그인 유저 정보를 가져옴
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes : {}", oAuth2User.getAttributes());

        // 2. provider : naver, google
        String provider = userRequest.getClientRegistration().getRegistrationId();
        log.info("provider : {}", provider);

        // 3. 필요한 정보를 provider에 따라 다르게 mapping
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(provider, oAuth2User.getAttributes());
        log.info("oAuth2UserInfo : {}", oAuth2UserInfo.toString());

        // 4. oAuth2UserInfo가 저장되어 있는지 유저 정보 확인
        //    없으면 DB 저장 후 해당 유저를 저장
        //    있으면 해당 유저를 저장
        User user = userRepository.findByProviderAndPassword(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getPassword())
                .orElseGet(() -> userRepository.save(oAuth2UserInfo.toEntity()));
        log.info("user : {}", user.toString());

        // 5. UserDetails와 OAuth2User를 다중 상속한 CustomUserDetails
        return new CustomUserDetails(user, oAuth2User.getAttributes());
    }
}