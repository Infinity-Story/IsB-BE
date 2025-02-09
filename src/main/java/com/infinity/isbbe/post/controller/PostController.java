package com.infinity.isbbe.post.controller;

import com.infinity.isbbe.post.aggregate.RequestPost;
import com.infinity.isbbe.post.aggregate.ResponsePost;
import com.infinity.isbbe.post.dto.PostDTO;
import com.infinity.isbbe.post.etc.POST_CATEGORY;
import com.infinity.isbbe.post.etc.POST_STATUS;
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

    @Operation(summary = "게시물 수정", description = "기존 게시물을 수정합니다.")
    @PutMapping("/update/{postCode}")
    public ResponseEntity<String> updatePost(@PathVariable int postCode, @RequestBody RequestPost request) {
        return postService.updatePost(postCode, request);
    }

    @Operation(summary = "게시물 상태 수정", description = "게시물 상태를 블라인드로 수정합니다.")
    @PutMapping("/update/blind/{postCode}")
    public ResponseEntity<String> updatePostBlind(@PathVariable int postCode) {
        return postService.updatePostBlind(postCode);
    }

    @Operation(summary = "게시물 상태 수정", description = "게시물 상태를 삭제처리로 수정합니다.")
    @PutMapping("/update/delete/{postCode}")
    public ResponseEntity<String> updatePostDelete(@PathVariable int postCode) {
        return postService.updatePostDelete(postCode);
    }

    @Operation(summary = "게시물 상태 수정", description = "게시물 상태를 활성화로 수정합니다.")
    @PutMapping("/update/on/{postCode}")
    public ResponseEntity<String> updatePostOn(@PathVariable int postCode) {
        return postService.updatePostOn(postCode);
    }

    @Operation(summary = "회원의 게시물 조회", description = "특정 회원이 게시한 게시물을 조회합니다.")
    @GetMapping("/member/{memberCode}")
    public ResponseEntity<List<ResponsePost>> getPostMemberList(@PathVariable int memberCode) {
        return ResponseEntity.ok(postService.getPostMemberList(memberCode));
    }

    @Operation(summary = "게시물 상태별 조회", description = "게시물 상태를 기준으로 게시물을 조회합니다.")
    @GetMapping("/detail/postStatus/{postStatus}")
    public ResponseEntity<List<ResponsePost>> getPostByStatus(@PathVariable("postStatus") POST_STATUS postStatus) {
        List<PostDTO> postDTOS = postService.getPostByStatus(postStatus);
        List<ResponsePost> responsePost = new ArrayList<>();
        postDTOS.forEach(postDTO -> {
            responsePost.add(new ResponsePost(postDTO));
        });
        return ResponseEntity.ok(responsePost);
    }

    @Operation(summary = "게시물 카테고리별 조회", description = "게시물 카테고리를 기준으로 게시물을 조회합니다.")
    @GetMapping("/detail/postCategory/{postCategory}")
    public ResponseEntity<List<ResponsePost>> getPostByCategory(@PathVariable("postCategory") POST_CATEGORY postCategory) {
        List<PostDTO> postDTOS = postService.getPostByCategory(postCategory);
        List<ResponsePost> responsePost = new ArrayList<>();
        postDTOS.forEach(postDTO -> {
            responsePost.add(new ResponsePost(postDTO));
        });
        return ResponseEntity.ok(responsePost);
    }


}
