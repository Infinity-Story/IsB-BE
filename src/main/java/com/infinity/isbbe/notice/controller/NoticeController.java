package com.infinity.isbbe.notice.controller;

import com.infinity.isbbe.notice.aggregate.RequestNotice;
import com.infinity.isbbe.notice.aggregate.ResponseNotice;
import com.infinity.isbbe.notice.dto.NoticeDTO;
import com.infinity.isbbe.notice.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Operation(summary = "공지사항 코드로 공지사항 조회", description = "특정 공지사항을 조회합니다.")
    @GetMapping("/detail/{noticeCode}")
    public ResponseEntity<List<ResponseNotice>> getNoticeByNoticeCode(@PathVariable int noticeCode) {
        List<NoticeDTO> noticeDTOS = noticeService.getNoticeByCode(noticeCode);
        List<ResponseNotice> responseNotice = new ArrayList<>();
        noticeDTOS.forEach(noticeDTO -> {
            responseNotice.add(new ResponseNotice(noticeDTO));
        });
        return ResponseEntity.ok(responseNotice);
    }

    @Operation(summary = "공지사항 등록", description = "신규 공지사항을 등록합니다.")
    @PostMapping("/create")
    public ResponseEntity<String> createNotice(@RequestBody RequestNotice request) {
        return noticeService.createNotice(request);
    }

    @Operation(summary = "공지사항 수정", description = "기존 공지사항을 수정합니다.")
    @PutMapping("/update/{noticeCode}")
    public ResponseEntity<String> updateNotice(@PathVariable int noticeCode, @RequestBody RequestNotice request) {
        return noticeService.updateNotice(noticeCode, request);
    }
}
