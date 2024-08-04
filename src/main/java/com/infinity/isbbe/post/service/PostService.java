package com.infinity.isbbe.post.service;

import com.infinity.isbbe.post.aggregate.RequestPost;
import com.infinity.isbbe.post.dto.PostDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {
    List<PostDTO> getAllPost();

    List<PostDTO> getPostByCode(int postCode);

    ResponseEntity<String> createPost(RequestPost request);

    ResponseEntity<String> updatePost(int postCode, RequestPost request);

    ResponseEntity<String> updatePostBlind(int postCode);
}
