package com.infinity.isbbe.reply.service;

import com.infinity.isbbe.reply.aggregate.RequestReply;
import com.infinity.isbbe.reply.aggregate.ResponseReply;
import com.infinity.isbbe.reply.dto.ReplyDTO;
import com.infinity.isbbe.reply.etc.REPLY_STATUS;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReplyService {
    List<ReplyDTO> getAllReply();

    List<ReplyDTO> getReplyByCode(int replyCode);

    ResponseEntity<String> createReply(RequestReply request);

    ResponseEntity<String> updateReply(int replyCode, RequestReply request);

    ResponseEntity<String> updateReplyBlind(int replyCode);

    ResponseEntity<String> updateReplyDelete(int replyCode);

    ResponseEntity<String> updateReplyOn(int replyCode);

    List<ResponseReply> getReplyMemberList(int memberCode);

    List<ReplyDTO> getReplyByStatus(REPLY_STATUS replyStatus);
}
