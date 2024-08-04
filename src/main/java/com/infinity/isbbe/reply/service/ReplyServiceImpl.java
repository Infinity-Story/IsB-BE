package com.infinity.isbbe.reply.service;

import com.infinity.isbbe.log.etc.LogStatus;
import com.infinity.isbbe.log.service.LogService;
import com.infinity.isbbe.member.aggregate.Member;
import com.infinity.isbbe.member.repository.MemberRepository;
import com.infinity.isbbe.post.aggregate.Post;
import com.infinity.isbbe.post.repository.PostRepository;
import com.infinity.isbbe.reply.aggregate.Reply;
import com.infinity.isbbe.reply.aggregate.RequestReply;
import com.infinity.isbbe.reply.dto.ReplyDTO;
import com.infinity.isbbe.reply.etc.REPLY_STATUS;
import com.infinity.isbbe.reply.repository.ReplyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final LogService logService;
    private final PostRepository postRepository;

    public ReplyServiceImpl(ReplyRepository replyRepository, MemberRepository memberRepository, LogService logService, PostRepository postRepository) {
        this.replyRepository = replyRepository;
        this.memberRepository = memberRepository;
        this.logService = logService;
        this.postRepository = postRepository;
    }

    @Override
    public List<ReplyDTO> getAllReply() {
        List<Reply> replyList = replyRepository.findAll();
        List<ReplyDTO> replyDTOList = new ArrayList<>();

        replyList.forEach(reply -> replyDTOList.add(new ReplyDTO(reply)));
        return replyDTOList;
    }

    @Override
    public List<ReplyDTO> getReplyByCode(int replyCode) {
        List<Reply> replyList = replyRepository.findByReplyCode(replyCode);
        List<ReplyDTO> replyDTOS = new ArrayList<>();
        replyList.forEach(reply -> replyDTOS.add(new ReplyDTO(reply)));
        return replyDTOS;
    }

    @Override
    @Transactional
    public ResponseEntity<String> createReply(RequestReply request) {
        Reply reply = new Reply();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        List<Member> memberList = memberRepository.findByMemberCode(request.getMemberCode());

        if (memberList == null || memberList.isEmpty()) {
            return ResponseEntity.badRequest().body("해당 회원이 존재하지 않습니다.");
        }

        List<Post> postList = postRepository.findByPostCode(request.getPostCode());

        if (postList == null || postList.isEmpty()) {
            return ResponseEntity.badRequest().body("해당 게시물이 존재하지 않습니다.");
        }

        Member member = memberList.get(0);
        Post post = postList.get(0);
        reply.setMember(member);
        reply.setPost(post);

        reply.setReplyContent(request.getReplyContent());
        reply.setReplyEnrollDate(formattedDateTime);
        reply.setReplyReportCount(request.getReplyReportCount());
        reply.setReplyLikeCount(request.getReplyLikeCount());
        reply.setReplyDislikeCount(request.getReplyDislikeCount());

        Reply savedReply = replyRepository.save(reply);

        logService.saveLog("root", LogStatus.등록, savedReply.getReplyContent(), "Reply");

        return ResponseEntity.ok("댓글 작성 완료!");
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateReply(int replyCode, RequestReply request) {
        Reply reply = replyRepository.findById(replyCode)
                .orElseThrow(() -> new EntityNotFoundException("해당 댓글이 존재하지 않습니다."));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        List<Member> memberList = memberRepository.findByMemberCode(request.getMemberCode());
        if (memberList == null || memberList.isEmpty()) {
            return ResponseEntity.badRequest().body("해당 회원이 존재하지 않습니다.");
        }

        List<Post> postList = postRepository.findByPostCode(request.getPostCode());
        if (postList == null || postList.isEmpty()) {
            return ResponseEntity.badRequest().body("해당 게시물이 존재하지 않습니다.");
        }

        Member member = memberList.get(0);
        Post post = postList.get(0);
        reply.setMember(member);
        reply.setPost(post);

        reply.setReplyContent(request.getReplyContent());
        reply.setReplyUpdateDate(formattedDateTime);
        reply.setReplyReportCount(request.getReplyReportCount());
        reply.setReplyLikeCount(request.getReplyLikeCount());
        reply.setReplyDislikeCount(request.getReplyDislikeCount());

        Reply updatedReply = replyRepository.save(reply);

        logService.saveLog("root", LogStatus.수정, updatedReply.getReplyContent(), "Reply");

        return ResponseEntity.ok("댓글 수정 완료!");
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateReplyBlind(int replyCode) {
        Reply reply = replyRepository.findById(replyCode).orElseThrow(()-> new EntityNotFoundException("해당 댓글이 존재하지 않습니다."));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        reply.setReplyStatus(REPLY_STATUS.블라인드);
        reply.setReplyUpdateDate(formattedDateTime);

        Reply updatedReply = replyRepository.save(reply);

        logService.saveLog("root", LogStatus.수정, updatedReply.getReplyContent(), "Reply");
        return ResponseEntity.ok("댓글상태 블라인드로 수정 완료");
    }
}
