package com.infinity.isbbe.post.controller;

import com.infinity.isbbe.post.aggregate.RequestPost;
import com.infinity.isbbe.post.aggregate.ResponsePost;
import com.infinity.isbbe.post.dto.PostDTO;
import com.infinity.isbbe.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Operation(summary = "게시물 코드로 게시물 조회", description = "특정 게시물을 조회합니다.")
    @GetMapping("/detail/{postCode}")
    public ResponseEntity<List<ResponsePost>> getPostByPostCode(@PathVariable int postCode) {
        List<PostDTO> postDTOS = postService.getPostByCode(postCode);
        List<ResponsePost> responsePost = new ArrayList<>();
        postDTOS.forEach(postDTO -> {
            responsePost.add(new ResponsePost(postDTO));
        });
        return ResponseEntity.ok(responsePost);
    }

    @Operation(summary = "게시물 등록", description = "신규 게시물을 등록합니다.")
    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestBody RequestPost request) {
        return postService.createPost(request);
    }
}
