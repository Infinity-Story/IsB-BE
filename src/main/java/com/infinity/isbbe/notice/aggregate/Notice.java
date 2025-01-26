package com.infinity.isbbe.notice.aggregate;

import com.infinity.isbbe.admin.aggregate.Admin;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_code")
    private int noticeCode;

    @Column(name = "notice_title")
    private String noticeTitle;

    @Column(name = "notice_content")
    private String noticeContent;

    @Column(name = "notice_enroll_date")
    private String noticeEnrollDate;

    @Column(name = "notice_update_date")
    private String noticeUpdateDate;

    @ManyToOne
    @JoinColumn(name = "admin_code")
    private Admin admin;
}
