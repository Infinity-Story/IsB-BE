package com.infinity.isbbe.admin.aggregate;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_code")
    private int adminCode;

    @Column(name = "admin_id")
    private String adminId;

    @Column(name = "admin_pw")
    private String adminPw;

    @Column(name = "admin_name")
    private String adminName;

    @Column(name = "admin_enroll_date")
    private String adminEnrollDate;

    @Column(name = "admin_update_date")
    private String adminUpdateDate;

    @Column(name = "admin_email")
    private String adminEmail;

    @Column(name = "admin_phone")
    private String adminPhone;

    @Column(name = "admin_role")
    private int adminRole;
}
