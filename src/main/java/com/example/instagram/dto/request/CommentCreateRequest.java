package com.example.instagram.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateRequest {
    @NotBlank(message = "댓글을 입력 해 주세요")
    @Size(max = 500, message = "댓글은 500자 이내로 작성해주세요")
    private String content;
    
}
