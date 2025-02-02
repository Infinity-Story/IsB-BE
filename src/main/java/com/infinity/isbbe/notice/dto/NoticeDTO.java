package com.infinity.isbbe.notice.dto;

import com.infinity.isbbe.notice.aggregate.Notice;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class NoticeDTO {

    private int noticeCode;
    private String noticeTitle;
    private String noticeContent;
    private String noticeEnrollDate;
    private String noticeUpdateDate;

    private int adminCode;
    private  String adminName;

    public NoticeDTO(Notice notice) {
        this.noticeCode = notice.getNoticeCode();
        this.noticeTitle = notice.getNoticeTitle();
        this.noticeContent = notice.getNoticeContent();
        this.noticeEnrollDate = notice.getNoticeEnrollDate();
        this.noticeUpdateDate = notice.getNoticeUpdateDate();

        this.adminCode = notice.getAdmin().getAdminCode();
        this.adminName = notice.getAdmin().getAdminName();
    }
}
