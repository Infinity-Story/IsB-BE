package com.infinity.isbbe.member.dto;

import com.infinity.isbbe.member.aggregate.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {

    private int memberCode;
    private String memberId;
    private String memberPw;
    private String memberName;
    private String memberEnrollDate;
    private String memberEmail;
    private String memberPhone;

    public MemberDTO(Member member) {
        this.memberCode = member.getMemberCode();
        this.memberId = member.getMemberId();
        this.memberPw = member.getMemberPw();
        this.memberName = member.getMemberName();
        this.memberEnrollDate = member.getMemberEnrollDate();
        this.memberEmail = member.getMemberEmail();
        this.memberPhone = member.getMemberPhone();
    }
}
