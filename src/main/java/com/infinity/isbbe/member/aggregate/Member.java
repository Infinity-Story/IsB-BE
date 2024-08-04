package com.infinity.isbbe.member.aggregate;

import com.infinity.isbbe.member.etc.MEMBER_STATUS;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_code")
    private int memberCode;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "member_pw")
    private String memberPw;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "member_enroll_date")
    private String memberEnrollDate;

    @Column(name = "member_update_date")
    private String memberUpdateDate;

    @Column(name = "member_email")
    private String memberEmail;

    @Column(name = "member_phone")
    private String memberPhone;

    @Column(name = "member_status")
    @Enumerated(EnumType.STRING)
    private MEMBER_STATUS memberStatus;
}
