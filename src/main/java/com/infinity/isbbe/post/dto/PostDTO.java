package com.infinity.isbbe.post.dto;

import com.infinity.isbbe.post.aggregate.Post;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {

    private int postCode;
    private String postTitle;
    private String postSubTitle;
    private String postEnrollDate;
    private String postContent;
    private int postReportCount;
    private int postViewCount;
    private int postLikeCount;
    private int postDislikeCount;
    private int postReplyCount;
    private int memberCode;

    public PostDTO(Post post) {
        this.postCode = post.getPostCode();
        this.postTitle = post.getPostTitle();
        this.postSubTitle = post.getPostSubTitle();
        this.postEnrollDate = post.getPostEnrollDate();
        this.postContent = post.getPostContent();
        this.postReportCount = post.getPostReportCount();
        this.postViewCount = post.getPostViewCount();
        this.postLikeCount = post.getPostLikeCount();
        this.postDislikeCount = post.getPostDislikeCount();
        this.postReplyCount = post.getPostReplyCount();
        this.memberCode =post.getMember().getMemberCode();
    }
}
