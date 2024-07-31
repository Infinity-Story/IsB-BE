package com.infinity.isbbe.post.aggregate;

import com.infinity.isbbe.post.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResponsePost {

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
    private String getMemberName;
    private String getMemberId;

    public ResponsePost(Post post) {
        this.postCode = post.getPostCode();
        this.postTitle = post.getPostTitle();
        this.postEnrollDate = post.getPostEnrollDate();
        this.postSubTitle = post.getPostSubTitle();
        this.postContent = post.getPostContent();
        this.postReportCount = post.getPostReportCount();
        this.postViewCount = post.getPostViewCount();
        this.postLikeCount = post.getPostLikeCount();
        this.postDislikeCount = post.getPostDislikeCount();
        this.postReplyCount = post.getPostReplyCount();

        this.memberCode = post.getMember().getMemberCode();
        this.getMemberName = post.getMember().getMemberName();
        this.getMemberId = post.getMember().getMemberId();
    }

    public ResponsePost(PostDTO postDTO) {
        this.postCode = postDTO.getPostCode();
        this.postTitle = postDTO.getPostTitle();
        this.postEnrollDate = postDTO.getPostEnrollDate();
        this.postSubTitle = postDTO.getPostSubTitle();
        this.postContent = postDTO.getPostContent();
        this.postReportCount = postDTO.getPostReportCount();
        this.postViewCount = postDTO.getPostViewCount();
        this.postLikeCount = postDTO.getPostLikeCount();
        this.postDislikeCount = postDTO.getPostDislikeCount();
        this.postReplyCount = postDTO.getPostReplyCount();

        this.memberCode = postDTO.getMemberCode();
        this.getMemberName = postDTO.getMemberName();
        this.getMemberId = postDTO.getMemberId();
    }
}