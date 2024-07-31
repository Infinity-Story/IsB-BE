package com.infinity.isbbe.post.aggregate;

import com.infinity.isbbe.member.aggregate.Member;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_code")
    private int postCode;

    @Column(name = "post_title")
    private String postTitle;

    @Column(name = "post_enroll_date")
    private String postEnrollDate;

    @Column(name = "post_sub_title")
    private String postSubTitle;

    @Column(name = "post_content")
    private String postContent;

    @Column(name = "post_report_count")
    private int postReportCount;

    @Column(name = "post_view_count")
    private int postViewCount;

    @Column(name = "post_like_count")
    private int postLikeCount;

    @Column(name = "post_dislike_count")
    private int postDislikeCount;

    @Column(name = "post_reply_count")
    private int postReplyCount;

    @ManyToOne
    @JoinColumn(name = "member_code")
    private Member member;
}
