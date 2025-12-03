package com.example.instagram.controller;

import com.example.instagram.dto.request.CommentCreateRequest;
import com.example.instagram.dto.request.PostCreateRequest;
import com.example.instagram.dto.response.PostResponse;
import com.example.instagram.security.CustomUserDetails;
import com.example.instagram.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("postCreateRequest", new PostCreateRequest());
        return "post/form";
    }

    @PostMapping
    public String create(
            @Valid @ModelAttribute PostCreateRequest postCreateRequest,
            BindingResult bindingResult,
            @AuthenticationPrincipal CustomUserDetails userDetails
            ) {
            if (bindingResult.hasErrors()) {
                return "post/form";
            }

            postService.create(postCreateRequest,userDetails.getId() );

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String detail(
            @PathVariable Long id,
            Model model
    ) {
        PostResponse post = postService.getPost(id);
        model.addAttribute("post", post);
        model.addAttribute("commentRequest", new CommentCreateRequest() );
        return "post/detail";
    }


}
