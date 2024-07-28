package com.infinity.isbbe.reply.aggregate;

import com.infinity.isbbe.member.aggregate.Member;
import com.infinity.isbbe.post.aggregate.Post;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "reply")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_code")
    private int replyCode;

    @Column(name = "reply_content")
    private int replyContent;

    @Column(name = "reply_enroll_date")
    private int replyEnrollDate;

    @Column(name = "reply_report_count")
    private int replyReportCount;

    @Column(name = "reply_like_count")
    private int replyLikeCount;

    @Column(name = "reply_dislike_count")
    private int replyDislikeCount;

    @ManyToOne
    @JoinColumn(name = "member_code")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_code")
    private Post post;
}
