package com.infinity.isbbe.reply.repository;

import com.infinity.isbbe.reply.aggregate.Reply;
import com.infinity.isbbe.reply.etc.REPLY_STATUS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    List<Reply> findByReplyCode(int replyCode);

    List<Reply> findAllByMemberMemberCode(int memberCode);

    List<Reply> findAllByReplyStatus(REPLY_STATUS replyStatus);
}
