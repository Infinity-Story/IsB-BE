package com.infinity.isbbe.reply.controller;

import com.infinity.isbbe.reply.aggregate.RequestReply;
import com.infinity.isbbe.reply.aggregate.ResponseReply;
import com.infinity.isbbe.reply.dto.ReplyDTO;
import com.infinity.isbbe.reply.service.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Operation(summary = "댓글 코드로 댓글 조회", description = "특정 댓글을 조회합니다.")
    @GetMapping("/detail/{replyCode}")
    public ResponseEntity<List<ResponseReply>> getReplyByReplyCode(@PathVariable int replyCode) {
        List<ReplyDTO> replyDTOS = replyService.getReplyByCode(replyCode);
        List<ResponseReply> responseReply = new ArrayList<>();
        replyDTOS.forEach(replyDTO -> {
            responseReply.add(new ResponseReply(replyDTO));
        });
        return ResponseEntity.ok(responseReply);
    }

    @Operation(summary = "댓글 등록", description = "신규 댓글을 등록합니다.")
    @PostMapping("/create")
    public ResponseEntity<String> createReply(@RequestBody RequestReply request) {
        return replyService.createReply(request);
    }
}