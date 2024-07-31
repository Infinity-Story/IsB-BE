package com.infinity.isbbe.reply.aggregate;

import com.infinity.isbbe.reply.dto.ReplyDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResponseReply {

    private int replyCode;
    private String replyContent;
    private String replyEnrollDate;
    private int replyReportCount;
    private int replyLikeCount;
    private int replyDislikeCount;

    private int memberCode;
    private String getMemberName;
    private String getMemberId;

    private int postCode;

    public ResponseReply(Reply reply) {
        this.replyCode = reply.getReplyCode();
        this.replyContent = reply.getReplyContent();
        this.replyEnrollDate = reply.getReplyEnrollDate();
        this.replyReportCount = reply.getReplyReportCount();
        this.replyLikeCount = reply.getReplyLikeCount();
        this.replyDislikeCount = reply.getReplyDislikeCount();

        this.memberCode = reply.getMember().getMemberCode();
        this.getMemberName = reply.getMember().getMemberName();
        this.getMemberId = reply.getMember().getMemberId();

        this.postCode = reply.getPost().getPostCode();
    }

    public ResponseReply(ReplyDTO replyDTO) {
        this.replyCode = replyDTO.getReplyCode();
        this.replyContent = replyDTO.getReplyContent();
        this.replyEnrollDate = replyDTO.getReplyEnrollDate();
        this.replyReportCount = replyDTO.getReplyReportCount();
        this.replyLikeCount = replyDTO.getReplyLikeCount();
        this.replyDislikeCount = replyDTO.getReplyDislikeCount();

        this.memberCode = replyDTO.getMemberCode();
        this.getMemberName = replyDTO.getMemberName();
        this.getMemberId = replyDTO.getMemberId();

        this.postCode = replyDTO.getPostCode();
    }
}
