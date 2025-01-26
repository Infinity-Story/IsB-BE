package com.infinity.isbbe.notice.controller;

import com.infinity.isbbe.notice.dto.NoticeDTO;
import com.infinity.isbbe.notice.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notice")
@Tag(name = "공지사항 정보 API", description = "Notice Info")
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Operation(summary = "공지사항 전체 조회", description = "공지사항을 전체 조회합니다.")
    @GetMapping("/list")
    public ResponseEntity<List<NoticeDTO>> getAllNotices() {
        List<NoticeDTO> noticeList = noticeService.getAllNotice();
        return ResponseEntity.ok(noticeList);
    }
}
