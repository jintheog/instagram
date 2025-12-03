package com.example.instagram.service;

import com.example.instagram.entity.Follow;
import com.example.instagram.entity.User;
import com.example.instagram.repository.FollowRepository;
import com.example.instagram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

         // 자기 자신 팔로우 방지
        if(follower.getId().equals(following.getId())) {
          throw new RuntimeException("자기 자신은 팔로우 할 수 없습니다. ");
        }

        Optional<Follow> existingFollow = followRepository
                .findByFollowerIdAndFollowingId(follower.getId(), following.getId());
        // following toggle
        if (existingFollow.isPresent()) { //이미 팔로우중 이라면
            followRepository.delete(existingFollow.get());
        } else { //팔로우중이 아니라면
            Follow follow = Follow.builder()
                    .follower(follower)
                    .following(following)
                    .build();

            followRepository.save(follow);
        }
    }

    @Override
    public boolean isFollowing(Long followerId, Long followingId) {
        return followRepository.existsByFollowerIdAndFollowingId(
                followerId, followingId
        );
    }


    @Override
    public long countByFollowerId(Long followerId){
        return followRepository.countByFollowerId(followerId);
    }
    @Override
    public long countByFollowingId(Long followingId) {
       return followRepository.countByFollowingId(followingId);
    }


}
