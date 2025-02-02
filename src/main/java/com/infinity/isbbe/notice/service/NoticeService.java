package com.infinity.isbbe.notice.service;

import com.infinity.isbbe.notice.aggregate.RequestNotice;
import com.infinity.isbbe.notice.dto.NoticeDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NoticeService {
    List<NoticeDTO> getAllNotice();

    List<NoticeDTO> getNoticeByCode(int noticeCode);

    ResponseEntity<String> createNotice(RequestNotice request);

    ResponseEntity<String> updateNotice(int noticeCode, RequestNotice request);
}
