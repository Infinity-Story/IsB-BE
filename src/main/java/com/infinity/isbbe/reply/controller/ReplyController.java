package com.infinity.isbbe.reply.controller;

import com.infinity.isbbe.reply.dto.ReplyDTO;
import com.infinity.isbbe.reply.service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reply")
@Tag(name = "댓글 정보 API", description = "Reply Info")
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @Operation(summary = "댓글 전체 조회", description = "게시물에 작성된 댓글을 전체 조회합니다.")
    @GetMapping("/list")
    public ResponseEntity<List<ReplyDTO>> getAllReply() {
        List<ReplyDTO> replyList = replyService.getAllReply();
        return ResponseEntity.ok(replyList);
    }
}
