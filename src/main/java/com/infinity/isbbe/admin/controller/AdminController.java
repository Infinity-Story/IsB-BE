package com.infinity.isbbe.admin.controller;

import com.infinity.isbbe.admin.aggregate.ResponseAdmin;
import com.infinity.isbbe.admin.dto.AdminDTO;
import com.infinity.isbbe.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "관리자 정보 API", description = "Admin Info")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(summary = "관리자 전체 조회", description = "관리자를 전체 조회합니다.")
    @GetMapping("/list")
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        List<AdminDTO> adminList = adminService.getAllAdmin();
        return ResponseEntity.ok(adminList);
    }

    @Operation(summary = "관리자 코드로 관리자 조회", description = "특정 관리자를 조회합니다.")
    @GetMapping("/detail/{adminCode}")
    public ResponseEntity<List<ResponseAdmin>> getAdminByAdminCode(@PathVariable int adminCode) {
        List<AdminDTO> adminDTOS = adminService.getAdminByCode(adminCode);
        List<ResponseAdmin> responseAdmin = new ArrayList<>();
        adminDTOS.forEach(adminDTO -> {
            responseAdmin.add(new ResponseAdmin(adminDTO));
        });
        return ResponseEntity.ok(responseAdmin);
    }
}
