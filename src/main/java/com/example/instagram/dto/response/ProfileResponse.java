package com.example.instagram.dto.response;

import com.example.instagram.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileResponse {
    private Long id;
    private String username;
    private String name;
    private String bio;

    // 통계
    private long postCount;
    private long followerCount;
    private long followingCount;

    // Entity => DTO
    public static ProfileResponse from(User user) {
        return ProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .bio(user.getBio())
                .name(user.getName())
                .postCount(0)
                .followerCount(0)
                .followingCount(0)
                .build();
    }

}
