package com.infinity.isbbe.notice.service;

import com.infinity.isbbe.admin.repository.AdminRepository;
import com.infinity.isbbe.log.service.LogService;
import com.infinity.isbbe.notice.aggregate.Notice;
import com.infinity.isbbe.notice.dto.NoticeDTO;
import com.infinity.isbbe.notice.repository.NoticeRepository;
import org.springframework.stereotype.Service;

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
}
