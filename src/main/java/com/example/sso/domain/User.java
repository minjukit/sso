package com.example.sso.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String password;
    @Column(nullable = false)
    private String profileName;
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private UserRole role;

    // provider : google이 들어감
    private String provider;

    // providerId : 구굴 로그인 한 유저의 고유 ID가 들어감
    private String providerId;

    @Builder
    public User(String userId, String profileName, UserRole role){
        this.userId = userId;
        this.profileName = profileName;
        this.role = role;
        // 추가하셈
    }

}
