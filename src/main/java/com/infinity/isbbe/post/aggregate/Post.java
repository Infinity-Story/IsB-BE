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
    private Integer postTitle;

    @Column(name = "post_enroll_date")
    private Integer postEnrollDate;

    @Column(name = "post_sub_title")
    private Integer postSubTitle;

    @Column(name = "post_content")
    private Integer postContent;

    @Column(name = "post_report_count")
    private Integer postReportCount;

    @Column(name = "post_view_count")
    private Integer postViewCount;

    @Column(name = "post_like_count")
    private Integer postLikeCount;

    @Column(name = "post_dislike_count")
    private Integer postDislikeCount;

    @Column(name = "post_reply_count")
    private Integer postReplyCount;

    @ManyToOne
    @JoinColumn(name = "member_code")
    private Member member;
}
