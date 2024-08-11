package com.infinity.isbbe.admin.service;

import com.infinity.isbbe.admin.aggregate.ResponseAdmin;
import com.infinity.isbbe.admin.dto.AdminDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
    List<AdminDTO> getAllAdmin();

    List<AdminDTO> getAdminByCode(int adminCode);

    ResponseEntity<String> createMember(ResponseAdmin request);
}
