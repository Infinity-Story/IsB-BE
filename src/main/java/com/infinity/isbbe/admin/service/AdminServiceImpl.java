package com.infinity.isbbe.admin.service;

import com.infinity.isbbe.admin.aggregate.Admin;
import com.infinity.isbbe.admin.aggregate.ResponseAdmin;
import com.infinity.isbbe.admin.dto.AdminDTO;
import com.infinity.isbbe.admin.repository.AdminRepository;
import com.infinity.isbbe.log.etc.LogStatus;
import com.infinity.isbbe.log.service.LogService;
import com.infinity.isbbe.member.repository.MemberRepository;
import com.infinity.isbbe.security.PasswordEncoderUtil;
import jakarta.persistence.EntityNotFoundException;
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
    private final MemberRepository memberRepository;

    public AdminServiceImpl(AdminRepository adminRepository, LogService logService, MemberRepository memberRepository) {
        this.adminRepository = adminRepository;
        this.logService = logService;
        this.memberRepository = memberRepository;
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
    public ResponseEntity<String> createAdmin(ResponseAdmin request) {
        // adminId 중복 체크
        if (adminRepository.existsByAdminId(request.getAdminId())) {
            throw new IllegalArgumentException("Id already exist");
        }

        // memberId 중복 체크
        if (memberRepository.existsByMemberId(request.getAdminId())) {
            throw new IllegalArgumentException("Id already exist");
        }

        // 새로운 관리자 등록 로직
        Admin admin = new Admin();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        // 비밀번호 인코딩
        String encodedPassword = PasswordEncoderUtil.encodePassword(request.getAdminPw());

        admin.setAdminCode(request.getAdminCode());
        admin.setAdminId(request.getAdminId());
        admin.setAdminPw(encodedPassword); // 인코딩된 비밀번호 저장
        admin.setAdminName(request.getAdminName());
        admin.setAdminEnrollDate(formattedDateTime);
        admin.setAdminEmail(request.getAdminEmail());
        admin.setAdminPhone(request.getAdminPhone());
        admin.setAdminRole(request.getAdminRole());

        Admin savedAdmin = adminRepository.save(admin);

        logService.saveLog("root", LogStatus.등록, savedAdmin.getAdminName(), "Admin");

        return ResponseEntity.ok("신규 관리자 등록 완료");
    }

    @Override
    public ResponseEntity<String> updateAdmin(int adminCode, ResponseAdmin request) {
        Admin admin = adminRepository.findById(adminCode)
                .orElseThrow(()-> new EntityNotFoundException("해당 관리자가 존재하지 않습니다."));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        admin.setAdminId(request.getAdminId());
        admin.setAdminPw(request.getAdminPw());
        admin.setAdminName(request.getAdminName());
        admin.setAdminUpdateDate(formattedDateTime);
        admin.setAdminEmail(request.getAdminEmail());
        admin.setAdminPhone(request.getAdminPhone());
        admin.setAdminRole(request.getAdminRole());

        Admin updatedAdmin = adminRepository.save(admin);

        logService.saveLog("root", LogStatus.수정, updatedAdmin.getAdminName(), "Admin");

        return ResponseEntity.ok("관리자 수정 완료");
    }
}
