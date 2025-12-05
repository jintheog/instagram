package com.example.instagram.service;

import com.example.instagram.dto.request.ProfileUpdateRequest;
import com.example.instagram.dto.request.SignUpRequest;
import com.example.instagram.dto.response.ProfileResponse;
import com.example.instagram.dto.response.UserResponse;
import com.example.instagram.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    User register(SignUpRequest signUpRequest);

    boolean existsByUsername(String username);

    User findById(Long userId);

    ProfileResponse getProfile(String username);

    User findByUsername(String username);

    UserResponse getUserById(Long userId);

    void updateProfile(Long userId, ProfileUpdateRequest profileUpdateRequest, MultipartFile profileImg);

    // 사용자 검색
    List<UserResponse> searchUsers(String keyword);
}
