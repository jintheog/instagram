package com.example.instagram.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "posts")
@NoArgsConstructor
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "image_url")
    private String imageUrl;

    @Builder
    public Post(String content, User user, String imageUrl) {
        this.content = content;
        this.user = user;
        this.imageUrl = imageUrl;
    }


}
