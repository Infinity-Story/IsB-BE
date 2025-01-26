package com.infinity.isbbe.notice.aggregate;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class RequestNotice {

    private int noticeCode;
    private String noticeTitle;
    private String noticeContent;
    private String noticeEnrollDate;
    private String noticeUpdateDate;
    private int adminCode;

}
