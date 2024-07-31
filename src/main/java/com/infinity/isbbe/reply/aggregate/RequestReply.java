package com.infinity.isbbe.reply.aggregate;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class RequestReply {

    private int replyCode;
    private String replyContent;
    private String replyEnrollDate;
    private int replyReportCount;
    private int replyLikeCount;
    private int replyDislikeCount;

    private int memberCode;
    private int postCode;
}
