package com.infinity.isbbe.reply.dto;

import com.infinity.isbbe.reply.aggregate.Reply;
import com.infinity.isbbe.reply.etc.REPLY_STATUS;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyDTO {

    private int replyCode;
    private String replyContent;
    private String replyEnrollDate;
    private String replyUpdateDate;
    private int replyReportCount;
    private int replyLikeCount;
    private int replyDislikeCount;
    private REPLY_STATUS replyStatus;


    private int memberCode;
    private String memberName;
    private String memberId;

    private int postCode;

    public ReplyDTO(Reply reply) {
        this.replyCode = reply.getReplyCode();
        this.replyContent = reply.getReplyContent();
        this.replyEnrollDate = reply.getReplyEnrollDate();
        this.replyUpdateDate = reply.getReplyUpdateDate();
        this.replyReportCount = reply.getReplyReportCount();
        this.replyLikeCount = reply.getReplyLikeCount();
        this.replyDislikeCount = reply.getReplyDislikeCount();
        this.replyStatus = reply.getReplyStatus();

        this.memberCode = reply.getMember().getMemberCode();
        this.memberName = reply.getMember().getMemberName();
        this.memberId = reply.getMember().getMemberId();

        this.postCode = reply.getPost().getPostCode();
    }
}
