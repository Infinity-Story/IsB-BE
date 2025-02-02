package com.infinity.isbbe.post.aggregate;

import com.infinity.isbbe.post.etc.POST_CATEGORY;
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
    private POST_CATEGORY postCategory;

    private int memberCode;
}
