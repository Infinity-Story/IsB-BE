package com.infinity.isbbe.admin.aggregate;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class RequestAdmin {

    private int adminCode;
    private String adminName;
    private String adminId;
    private String adminPw;
    private String adminEmail;
    private String adminPhone;
    private String adminEnrollDate;
    private int adminRole;
}
