package com.example.instagram.dto.response;

import com.example.instagram.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String bio;
    private String username;
    private String profileImageUrl;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .bio(user.getBio())
                .username(user.getUsername())
                .profileImageUrl(user.getProfileImageUrl())
                .build();

    }
}
