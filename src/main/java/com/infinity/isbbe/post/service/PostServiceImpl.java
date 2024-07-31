package com.infinity.isbbe.post.service;

import com.infinity.isbbe.post.aggregate.Post;
import com.infinity.isbbe.post.dto.PostDTO;
import com.infinity.isbbe.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @Override
    public List<PostDTO> getAllPost() {
        List<Post> postList = postRepository.findAll();
        List<PostDTO> postDTOList = new ArrayList<>();

        postList.forEach(post -> postDTOList.add(new PostDTO(post)));
        return postDTOList;
    }

    @Override
    public List<PostDTO> getPostByCode(int postCode) {
        List<Post> postList = postRepository.findByPostCode(postCode);
        List<PostDTO> postDTOS = new ArrayList<>();
        postList.forEach(post -> postDTOS.add(new PostDTO(post)));
        return postDTOS;
    }
}
