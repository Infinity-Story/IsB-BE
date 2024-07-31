package com.infinity.isbbe.reply.service;

import com.infinity.isbbe.reply.aggregate.Reply;
import com.infinity.isbbe.reply.dto.ReplyDTO;
import com.infinity.isbbe.reply.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    public ReplyServiceImpl(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
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
}
