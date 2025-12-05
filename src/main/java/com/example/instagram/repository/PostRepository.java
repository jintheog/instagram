package com.example.instagram.repository;

import com.example.instagram.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @EntityGraph(attributePaths = {"user"})
    List<Post> findAllByOrderByCreatedAtDesc();

    @EntityGraph(attributePaths = {"user"})
    List<Post> findByUserIdOrderByCreatedAtDesc(Long userId);

    long countByUserId(Long userId);

    @EntityGraph(attributePaths = "user")
    Optional<Post> findById(Long id);

    // 피드 조회
    @Query("SELECT p FROM Post p JOIN FETCH p.user WHERE p.user.id IN :userIds ORDER BY p.createdAt DESC")
    Slice<Post> findFeedPostsByUSerIds(@Param("userIds") List<Long> userIds, Pageable pageable);


    // 전체 게시물 조회 (페이징) (인피니티 스크롤 구현을 위해서 새롭게 만듬)
    @Query("SELECT p FROM Post p JOIN FETCH p.user ORDER BY p.createdAt")
    Slice<Post> findAllWithUserPaging(Pageable pageable);

    @Query("SELECT p FROM Post p JOIN FETCH p.user WHERE p.content LIKE %:keyword% ORDER BY p.createdAt")
    Slice<Post> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);


}

