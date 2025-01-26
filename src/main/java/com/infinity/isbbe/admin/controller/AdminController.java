package com.infinity.isbbe.admin.controller;

import com.infinity.isbbe.admin.aggregate.ResponseAdmin;
import com.infinity.isbbe.admin.dto.AdminDTO;
import com.infinity.isbbe.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "관리자 등록", description = "신규 관리자를 등록합니다.")
    @PostMapping("/create")
    public ResponseEntity<String> createAdmin(@RequestBody ResponseAdmin request) {
        return adminService.createMember(request);
    }

    @Operation(summary = "관리자 수정", description = "기존 관리자를 수정합니다.")
    @PutMapping("/update/{adminCode}")
    public ResponseEntity<String> updateAdmin(@PathVariable int adminCode, @RequestBody ResponseAdmin request) {
        return adminService.updateAdmin(adminCode,request);
    }

}
