package com.infinity.isbbe.admin.dto;

import com.infinity.isbbe.admin.aggregate.Admin;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDTO {

    private int adminCode;
    private String adminName;
    private String adminId;
    private String adminPw;
    private String adminEmail;
    private String adminPhone;
    private String adminEnrollDate;
    private String adminUpdateDate;
    private int adminRole;

    public AdminDTO(Admin admin) {
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
}
