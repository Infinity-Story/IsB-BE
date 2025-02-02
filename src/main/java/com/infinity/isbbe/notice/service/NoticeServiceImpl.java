package com.infinity.isbbe.notice.service;

import com.infinity.isbbe.admin.aggregate.Admin;
import com.infinity.isbbe.admin.repository.AdminRepository;
import com.infinity.isbbe.log.etc.LogStatus;
import com.infinity.isbbe.log.service.LogService;
import com.infinity.isbbe.notice.aggregate.Notice;
import com.infinity.isbbe.notice.aggregate.RequestNotice;
import com.infinity.isbbe.notice.dto.NoticeDTO;
import com.infinity.isbbe.notice.repository.NoticeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {


    private final NoticeRepository noticeRepository;
    private final AdminRepository adminRepository;
    private final LogService logService;

    public NoticeServiceImpl(NoticeRepository noticeRepository, AdminRepository adminRepository, LogService logService) {
        this.noticeRepository = noticeRepository;
        this.adminRepository = adminRepository;
        this.logService = logService;
    }

    @Override
    public List<NoticeDTO> getAllNotice() {
        List<Notice> noticeList = noticeRepository.findAll();
        List<NoticeDTO> noticeDTOList = new ArrayList<>();

        noticeList.forEach(notice -> noticeDTOList.add(new NoticeDTO(notice)));
        return noticeDTOList;
    }

    @Override
    public List<NoticeDTO> getNoticeByCode(int noticeCode) {
        List<Notice> noticeList = noticeRepository.findByNoticeCode(noticeCode);
        List<NoticeDTO> noticeDTOS = new ArrayList<>();
        noticeList.forEach(notice -> noticeDTOS.add(new NoticeDTO(notice)));
        return noticeDTOS;
    }

    @Override
    @Transactional
    public ResponseEntity<String> createNotice(RequestNotice request) {
        Notice notice = new Notice();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        List<Admin> adminList = adminRepository.findByAdminCode(request.getAdminCode());

        if (adminList == null || adminList.isEmpty()) {
            return ResponseEntity.badRequest().body("해당 관리자가 존재하지 않습니다.");
        }

        Admin admin = adminList.get(0);
        notice.setAdmin(admin);


        notice.setNoticeTitle(request.getNoticeTitle());
        notice.setNoticeContent(request.getNoticeContent());
        notice.setNoticeEnrollDate(formattedDateTime);

        Notice savedNotice = noticeRepository.save(notice);

        logService.saveLog("root", LogStatus.등록, savedNotice.getNoticeTitle(), "Notice");

        return ResponseEntity.ok("공지사항 등록 완료!");
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateNotice(int noticeCode, RequestNotice request) {
        Notice notice = noticeRepository.findById(noticeCode)
                .orElseThrow(() -> new EntityNotFoundException("해당 공지사항이 존재하지 않습니다."));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        List<Admin> adminList = adminRepository.findByAdminCode(request.getAdminCode());

        if (adminList == null || adminList.isEmpty()) {
            return ResponseEntity.badRequest().body("해당 관리자가 존재하지 않습니다.");
        }
        Admin admin = adminList.get(0);
        notice.setAdmin(admin);

        notice.setNoticeTitle(request.getNoticeTitle());
        notice.setNoticeContent(request.getNoticeContent());
        notice.setNoticeUpdateDate(formattedDateTime);

        Notice updatedNotice = noticeRepository.save(notice);

        logService.saveLog("root", LogStatus.수정, updatedNotice.getNoticeTitle(), "Notice");

        return  ResponseEntity.ok("공지사항 수정 완료!");
    }
}
