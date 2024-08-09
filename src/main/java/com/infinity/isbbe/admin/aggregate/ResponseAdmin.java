package com.infinity.isbbe.admin.aggregate;

import com.infinity.isbbe.admin.dto.AdminDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResponseAdmin {

    private int adminCode;
    private String adminName;
    private String adminId;
    private String adminPw;
    private String adminEmail;
    private String adminPhone;
    private String adminEnrollDate;
    private String adminUpdateDate;
    private int adminRole;

    public ResponseAdmin(Admin admin) {
        this.adminCode = admin.getAdminCode();
        this.adminName = admin.getAdminName();
        this.adminId = admin.getAdminId();
        this.adminPw = admin.getAdminPw();
        this.adminEmail = admin.getAdminEmail();
        this.adminPhone = admin.getAdminPhone();
        this.adminEnrollDate = admin.getAdminEnrollDate();
        this.adminUpdateDate = admin.getAdminUpdateDate();
        this.adminRole = admin.getAdminRole();
    }

    public ResponseAdmin(AdminDTO adminDTO) {
        this.adminCode = adminDTO.getAdminCode();
        this.adminName = adminDTO.getAdminName();
        this.adminId = adminDTO.getAdminId();
        this.adminPw = adminDTO.getAdminPw();
        this.adminEmail = adminDTO.getAdminEmail();
        this.adminPhone = adminDTO.getAdminPhone();
        this.adminEnrollDate = adminDTO.getAdminEnrollDate();
        this.adminUpdateDate = adminDTO.getAdminUpdateDate();
        this.adminRole = adminDTO.getAdminRole();
    }
}
