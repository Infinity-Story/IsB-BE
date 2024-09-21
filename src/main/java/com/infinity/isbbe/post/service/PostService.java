package com.infinity.isbbe.post.service;

import com.infinity.isbbe.post.aggregate.RequestPost;
import com.infinity.isbbe.post.aggregate.ResponsePost;
import com.infinity.isbbe.post.dto.PostDTO;
import com.infinity.isbbe.post.etc.POST_CATEGORY;
import com.infinity.isbbe.post.etc.POST_STATUS;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {
    List<PostDTO> getAllPost();

    List<PostDTO> getPostByCode(int postCode);

    ResponseEntity<String> createPost(RequestPost request);

    ResponseEntity<String> updatePost(int postCode, RequestPost request);

    ResponseEntity<String> updatePostBlind(int postCode);

    ResponseEntity<String> updatePostDelete(int postCode);

    ResponseEntity<String> updatePostOn(int postCode);

    List<ResponsePost> getPostMemberList(int memberCode);

    List<PostDTO> getPostByStatus(POST_STATUS postStatus);

    List<PostDTO> getPostByCategory(POST_CATEGORY postCategory);
}
