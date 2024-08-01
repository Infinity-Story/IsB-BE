package com.infinity.isbbe.post.service;

import com.infinity.isbbe.log.etc.LogStatus;
import com.infinity.isbbe.log.service.LogService;
import com.infinity.isbbe.member.aggregate.Member;
import com.infinity.isbbe.member.repository.MemberRepository;
import com.infinity.isbbe.post.aggregate.Post;
import com.infinity.isbbe.post.aggregate.RequestPost;
import com.infinity.isbbe.post.dto.PostDTO;
import com.infinity.isbbe.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final LogService logService;

    public PostServiceImpl(PostRepository postRepository, MemberRepository memberRepository, LogService logService) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.logService = logService;
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

    @Override
    @Transactional
    public ResponseEntity<String> createPost(RequestPost request) {
        Post post = new Post();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        List<Member> memberList = memberRepository.findByMemberCode(request.getMemberCode());

        if (memberList == null || memberList.isEmpty()) {
            return ResponseEntity.badRequest().body("해당 회원이 존재하지 않습니다.");
        }

        Member member = memberList.get(0);
        post.setMember(member);

        post.setPostContent(request.getPostContent());
        post.setPostTitle(request.getPostTitle());
        post.setPostEnrollDate(formattedDateTime);
        post.setPostSubTitle(request.getPostSubTitle());
        post.setPostReportCount(request.getPostReportCount());
        post.setPostViewCount(request.getPostViewCount());
        post.setPostLikeCount(request.getPostLikeCount());
        post.setPostDislikeCount(request.getPostDislikeCount());
        post.setPostReplyCount(request.getPostReplyCount());

        Post savedPost = postRepository.save(post);

        logService.saveLog("root", LogStatus.등록, savedPost.getPostTitle(), "Post");

        return ResponseEntity.ok("게시물 등록 완료!");
    }

    @Override
    public ResponseEntity<String> updatePost(int postCode, RequestPost request) {
        Post post = postRepository.findById(postCode)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시물이 존재하지 않습니다."));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        List<Member> memberList = memberRepository.findByMemberCode(request.getMemberCode());

        if (memberList == null || memberList.isEmpty()) {
            return ResponseEntity.badRequest().body("해당 회원이 존재하지 않습니다.");
        }
        Member member = memberList.get(0);
        post.setMember(member);

        post.setPostContent(request.getPostContent());
        post.setPostTitle(request.getPostTitle());
        post.setPostUpdateDate(formattedDateTime);
        post.setPostSubTitle(request.getPostSubTitle());
        post.setPostReportCount(request.getPostReportCount());
        post.setPostViewCount(request.getPostViewCount());
        post.setPostLikeCount(request.getPostLikeCount());
        post.setPostDislikeCount(request.getPostDislikeCount());
        post.setPostReplyCount(request.getPostReplyCount());

        Post updatedPost = postRepository.save(post);

        logService.saveLog("root", LogStatus.수정, updatedPost.getPostTitle(), "Post");

        return ResponseEntity.ok("게시물 수정 완료!");
    }
}
