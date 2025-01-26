package com.infinity.isbbe.notice.aggregate;

import com.infinity.isbbe.notice.dto.NoticeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseNotice {

    private int noticeCode;
    private String noticeTitle;
    private String noticeContent;
    private String noticeEnrollDate;
    private String noticeUpdateDate;

    private int adminCode;
    private String getAdminName;

    public ResponseNotice(Notice notice) {
        this.noticeCode = notice.getNoticeCode();
        this.noticeTitle = notice.getNoticeTitle();
        this.noticeContent = notice.getNoticeContent();
        this.noticeEnrollDate = notice.getNoticeEnrollDate();
        this.noticeUpdateDate = notice.getNoticeUpdateDate();

        this.adminCode = notice.getAdmin().getAdminCode();
        this.getAdminName = notice.getAdmin().getAdminName();
    }

    public ResponseNotice(NoticeDTO noticeDTO) {
        this.noticeCode = noticeDTO.getNoticeCode();
        this.noticeTitle = noticeDTO.getNoticeTitle();
        this.noticeContent = noticeDTO.getNoticeContent();
        this.noticeEnrollDate = noticeDTO.getNoticeEnrollDate();
        this.noticeUpdateDate = noticeDTO.getNoticeUpdateDate();

        this.adminCode = noticeDTO.getAdminCode();
        this.getAdminName = noticeDTO.getAdminName();
    }
}
