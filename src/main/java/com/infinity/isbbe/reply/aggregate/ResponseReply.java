package com.infinity.isbbe.reply.aggregate;

import com.infinity.isbbe.reply.dto.ReplyDTO;
import com.infinity.isbbe.reply.etc.REPLY_STATUS;
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
    private String replyUpdateDate;
    private int replyReportCount;
    private int replyLikeCount;
    private int replyDislikeCount;
    private REPLY_STATUS replyStatus;

    private int memberCode;
    private String getMemberName;
    private String getMemberId;

    private int postCode;

    public ResponseReply(Reply reply) {
        this.replyCode = reply.getReplyCode();
        this.replyContent = reply.getReplyContent();
        this.replyEnrollDate = reply.getReplyEnrollDate();
        this.replyUpdateDate = reply.getReplyUpdateDate();
        this.replyReportCount = reply.getReplyReportCount();
        this.replyLikeCount = reply.getReplyLikeCount();
        this.replyDislikeCount = reply.getReplyDislikeCount();
        this.replyStatus = reply.getReplyStatus();

        this.memberCode = reply.getMember().getMemberCode();
        this.getMemberName = reply.getMember().getMemberName();
        this.getMemberId = reply.getMember().getMemberId();

        this.postCode = reply.getPost().getPostCode();
    }

    public ResponseReply(ReplyDTO replyDTO) {
        this.replyCode = replyDTO.getReplyCode();
        this.replyContent = replyDTO.getReplyContent();
        this.replyEnrollDate = replyDTO.getReplyEnrollDate();
        this.replyUpdateDate = replyDTO.getReplyUpdateDate();
        this.replyReportCount = replyDTO.getReplyReportCount();
        this.replyLikeCount = replyDTO.getReplyLikeCount();
        this.replyDislikeCount = replyDTO.getReplyDislikeCount();
        this.replyStatus = replyDTO.getReplyStatus();

        this.memberCode = replyDTO.getMemberCode();
        this.getMemberName = replyDTO.getMemberName();
        this.getMemberId = replyDTO.getMemberId();

        this.postCode = replyDTO.getPostCode();
    }
}
