package com.example.instagram.controller;

import com.example.instagram.dto.request.CommentRequest;
import com.example.instagram.dto.request.PostCreateRequest;
import com.example.instagram.dto.response.CommentResponse;
import com.example.instagram.dto.response.PostResponse;
import com.example.instagram.entity.Post;
import com.example.instagram.security.CustomUserDetails;
import com.example.instagram.service.CommentService;
import com.example.instagram.service.LikeService;
import com.example.instagram.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final LikeService likeService;

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("postCreateRequest", new PostCreateRequest());
        model.addAttribute("isEdit", false);
        return "post/form";
    }

    @PostMapping
    public String create(
            @Valid @ModelAttribute PostCreateRequest postCreateRequest,
            BindingResult bindingResult,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(value = "image", required = false)MultipartFile image
            ) {
            if (bindingResult.hasErrors()) {
                return "post/form";
            }

            postService.create(postCreateRequest, image, userDetails.getId() );

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String detail(
            @PathVariable Long id,
            Model model,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        PostResponse post = postService.getPost(id);

        List<CommentResponse> comments = commentService.getComments(id);

        if(userDetails != null) {
            boolean isOwner = post.getUserId().equals(userDetails.getId());
            model.addAttribute("isOwner", isOwner);
        }

        model.addAttribute("post", post);
        model.addAttribute("commentRequest", new CommentRequest());
        model.addAttribute("comments", comments);
        model.addAttribute("liked", likeService.isLiked(id, userDetails.getId()));
        model.addAttribute("likeCount", likeService.getLikeCount(id));
        return "post/detail";
    }

    @PostMapping("/{postId}/comments")
    public String createComment(
            @PathVariable Long postId,
            @Valid @ModelAttribute CommentRequest commentRequest,
            BindingResult bindingResult,
            Model model,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        if(bindingResult.hasErrors()) {
            PostResponse post = postService.getPost(postId);
            List<CommentResponse> comments = commentService.getComments(postId);

            model.addAttribute("post", post);
            model.addAttribute("commentRequest", commentRequest);
            model.addAttribute("comments", comments);

            return "post/detail";
        }

        commentService.create(postId, commentRequest, userDetails.getId());

        return "redirect:/posts/" + postId;
    }

    @PostMapping("{id}/like")
    public String toggleLike(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        likeService.toggleLike(id, userDetails.getId());
        return "redirect:/posts/" + id;
    }

    @GetMapping("/{id}/edit")
    public String edit(
        @PathVariable Long id, Model model
    ) {
        PostResponse post = postService.getPost(id);

        PostCreateRequest postCreateRequest = new PostCreateRequest();
        postCreateRequest.setContent(post.getContent());

        model.addAttribute("postCreateRequest", postCreateRequest);
        model.addAttribute("isEdit", true);
        model.addAttribute("post", post);
        return "post/form";
    }

    @PostMapping("/{id}/edit")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute PostCreateRequest updatedPostRequest,
            BindingResult bindingResult,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model,
            @RequestParam(value = "postImage", required = false)MultipartFile postImage
    ) {
        if(bindingResult.hasErrors()){
            PostResponse post = postService.getPost(id);
            model.addAttribute("post", post);
            return "post/form";
        }

        postService.updatePost(id, updatedPostRequest, postImage);

        return "redirect:/posts/"+id;

    }

    @PostMapping("/{id}/delete")
    public String delete(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        postService.deletePost(id);

        return "redirect:/users/"+userDetails.getUsername();
    }

}
