package com.infinity.isbbe.post.controller;

import com.infinity.isbbe.post.dto.PostDTO;
import com.infinity.isbbe.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/post")
@Tag(name = "게시물 정보 API", description = "Post Info")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "게시물 전체 조회", description = "게시물을 전체 조회합니다.")
    @GetMapping("/list")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> postList = postService.getAllPost();
        return ResponseEntity.ok(postList);
    }
}
