package com.example.instagram.service;

import com.example.instagram.dto.request.SignUpRequest;
import com.example.instagram.dto.response.ProfileResponse;
import com.example.instagram.entity.User;

public interface UserService {

    User register(SignUpRequest signUpRequest);

    boolean existsByUsername(String username);

    User findById(Long userId);

    ProfileResponse getProfile(String username);

    User findByUsername(String username);
}
