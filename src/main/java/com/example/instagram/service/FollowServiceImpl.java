package com.example.instagram.service;

import com.example.instagram.entity.Follow;
import com.example.instagram.entity.User;
import com.example.instagram.repository.FollowRepository;
import com.example.instagram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class FollowServiceImpl implements FollowService {

    private final UserService userService;
    private final FollowRepository followRepository;

    @Override
    @Transactional
    public void toggleFollow(Long followerId, String followingUsername) {
         User follower = userService.findById(followerId);
         User following = userService.findByUsername(followingUsername);

         Follow follow = Follow.builder()
                         .follower(follower)
                        .following(following)
                        .build();

         followRepository.save(follow);
    }



}
