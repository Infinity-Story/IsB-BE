package com.infinity.isbbe.reply.service;

import com.infinity.isbbe.reply.aggregate.RequestReply;
import com.infinity.isbbe.reply.dto.ReplyDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReplyService {
    List<ReplyDTO> getAllReply();

    List<ReplyDTO> getReplyByCode(int replyCode);

    ResponseEntity<String> createReply(RequestReply request);
}
