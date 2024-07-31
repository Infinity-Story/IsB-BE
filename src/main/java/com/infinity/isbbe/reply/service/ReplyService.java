package com.infinity.isbbe.reply.service;

import com.infinity.isbbe.reply.dto.ReplyDTO;

import java.util.List;

public interface ReplyService {
    List<ReplyDTO> getAllReply();

    List<ReplyDTO> getReplyByCode(int replyCode);
}
