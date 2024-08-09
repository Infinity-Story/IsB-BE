package com.infinity.isbbe.admin.service;

import com.infinity.isbbe.admin.aggregate.Admin;
import com.infinity.isbbe.admin.dto.AdminDTO;
import com.infinity.isbbe.admin.repository.AdminRepository;
import com.infinity.isbbe.log.service.LogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
