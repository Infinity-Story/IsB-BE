package com.infinity.isbbe.admin.service;

import com.infinity.isbbe.admin.aggregate.Admin;
import com.infinity.isbbe.admin.aggregate.ResponseAdmin;
import com.infinity.isbbe.admin.dto.AdminDTO;
import com.infinity.isbbe.admin.repository.AdminRepository;
import com.infinity.isbbe.log.etc.LogStatus;
import com.infinity.isbbe.log.service.LogService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final LogService logService;

    public AdminServiceImpl(AdminRepository adminRepository, LogService logService) {
        this.adminRepository = adminRepository;
        this.logService = logService;
    }

    @Override
    @Transactional
    public List<AdminDTO> getAllAdmin() {
        List<Admin> adminList = adminRepository.findAll();
        List<AdminDTO> adminDTOList = new ArrayList<>();

        adminList.forEach(admin -> adminDTOList.add(new AdminDTO(admin)));
        return adminDTOList;
    }

    @Override
    public List<AdminDTO> getAdminByCode(int adminCode) {
        List<Admin> adminList = adminRepository.findByAdminCode(adminCode);
        List<AdminDTO> adminDTOList = new ArrayList<>();
        adminList.forEach(admin -> adminDTOList.add(new AdminDTO(admin)));
        return adminDTOList;
    }

    @Override
    public ResponseEntity<String> createMember(ResponseAdmin request) {
        Admin admin = new Admin();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        admin.setAdminCode(request.getAdminCode());
        admin.setAdminId(request.getAdminId());
        admin.setAdminPw(request.getAdminPw());
        admin.setAdminName(request.getAdminName());
        admin.setAdminEnrollDate(formattedDateTime);
        admin.setAdminEmail(request.getAdminEmail());
        admin.setAdminPhone(request.getAdminPhone());
        admin.setAdminRole(request.getAdminRole());

        Admin savedAdmin = adminRepository.save(admin);

        logService.saveLog("root", LogStatus.등록, savedAdmin.getAdminName(), "Admin");

        return ResponseEntity.ok("신규 관리자 등록 완료");
    }
}
