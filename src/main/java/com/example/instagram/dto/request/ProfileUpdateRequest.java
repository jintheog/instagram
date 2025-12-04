package com.example.instagram.dto.request;



import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileUpdateRequest {

    @Size(max = 50, message = "이름은 50자 이내로 작성해주세요")
    private String name;

    @Size(max = 200, message = "자기소개는 200자 이내로 작성해주세요")
    private String bio;
}