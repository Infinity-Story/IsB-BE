package com.infinity.isbbe.reply.dto;

import com.infinity.isbbe.reply.aggregate.Reply;
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
    private int replyReportCount;
    private int replyLikeCount;
    private int replyDislikeCount;


    private int memberCode;
    private String memberName;
    private String memberId;

    private int postCode;

    public ReplyDTO(Reply reply) {
        this.replyCode = reply.getReplyCode();
        this.replyContent = reply.getReplyContent();
        this.replyEnrollDate = reply.getReplyEnrollDate();
        this.replyReportCount = reply.getReplyReportCount();
        this.replyLikeCount = reply.getReplyLikeCount();
        this.replyDislikeCount = reply.getReplyDislikeCount();

        this.memberCode = reply.getMember().getMemberCode();
        this.memberName = reply.getMember().getMemberName();
        this.memberId = reply.getMember().getMemberId();

        this.postCode = reply.getPost().getPostCode();
    }
}
