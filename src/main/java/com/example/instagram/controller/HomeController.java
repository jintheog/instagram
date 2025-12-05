package com.example.instagram.controller;

import com.example.instagram.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

//    @GetMapping("/")
//    public String home(Model model) {
////        List<PostResponse> posts = postService.getAllPosts();
//        List<PostResponse> posts = postService.getAllPostsWithStats();
//        model.addAttribute("posts", posts);
//        return "home";
//    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/explore")
    public String explore() {
        return "explore";
    }

    @GetMapping("/search")
    public String search() {
        return "search";
    }

}
