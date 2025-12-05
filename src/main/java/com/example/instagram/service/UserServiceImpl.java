package com.example.instagram.service;

import com.example.instagram.dto.request.ProfileUpdateRequest;
import com.example.instagram.dto.request.SignUpRequest;
import com.example.instagram.dto.response.ProfileResponse;
import com.example.instagram.dto.response.UserResponse;
import com.example.instagram.entity.Role;
import com.example.instagram.entity.User;
import com.example.instagram.repository.FollowRepository;
import com.example.instagram.repository.PostRepository;
import com.example.instagram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
//    private final PostService postService;
    private final PostRepository postRepository;
//    private final FollowService followService;
    private final FollowRepository followRepository;

    private final FileService fileService;

    @Override
    @Transactional
    public User register(SignUpRequest signUpRequest){
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .name(signUpRequest.getName())
                .role(Role.USER)
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .build();
        return userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow();
    }

    @Override
    public ProfileResponse getProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow();

        long postCount = postRepository.countByUserId(user.getId());
        long followerCount = followRepository.countByFollowingId(user.getId());
        long followingCount = followRepository.countByFollowerId(user.getId());

        return ProfileResponse.from(user, postCount, followerCount, followingCount);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow();
    }

    @Override
    public UserResponse getUserById(Long userId) {
        User user = findById(userId);
        return UserResponse.from(user);
    }

    @Override
    @Transactional
    public void updateProfile(Long userId, ProfileUpdateRequest profileUpdateRequest, MultipartFile profileImg) {
        User user = findById(userId);

        //프로필 이미지 처리
        if(profileImg != null && !profileImg.isEmpty()) {
            String savedFileName = fileService.saveFile(profileImg);
            String imageUrl = "/uploads/" + savedFileName;
            user.updateProfileImage(imageUrl);
        }
        user.updateProfile(profileUpdateRequest.getName(), profileUpdateRequest.getBio());
    }

    @Override
    public List<UserResponse> searchUsers(String keyword){
        return userRepository.searchByKeyword(keyword);
    }

}
