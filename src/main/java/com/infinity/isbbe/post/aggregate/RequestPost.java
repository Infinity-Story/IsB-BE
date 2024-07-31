package com.infinity.isbbe.post.aggregate;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class RequestPost {

    private int postCode;
    private String postTitle;
    private String postEnrollDate;
    private String postSubTitle;
    private String postContent;
    private int postReportCount;
    private int postViewCount;
    private int postLikeCount;
    private int postDislikeCount;
    private int postReplyCount;

    private int memberCode;
}
