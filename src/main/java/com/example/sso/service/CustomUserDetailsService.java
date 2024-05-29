package com.example.sso.service;

import com.example.sso.Repository.CustomUserDetails;
import com.example.sso.Repository.UserRepository;
import com.example.sso.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    //email로 할건지..? 유저네임
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(); // 지워
       // User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(""));
        return new CustomUserDetails(user);
    }
}
