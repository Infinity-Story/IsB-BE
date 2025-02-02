package com.infinity.isbbe.member.aggregate;

import com.infinity.isbbe.member.dto.MemberDTO;
import com.infinity.isbbe.member.etc.MEMBER_STATUS;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResponseMember {

    private int memberCode;
    private String memberName;
    private String memberId;
    private String memberPw;
    private String memberEmail;
    private String memberPhone;
    private String memberEnrollDate;
    private String memberUpdateDate;
    private MEMBER_STATUS memberStatus;

    public ResponseMember(Member member) {
        this.memberCode = member.getMemberCode();
        this.memberName = member.getMemberName();
        this.memberId = member.getMemberId();
        this.memberPw = member.getMemberPw();
        this.memberEmail = member.getMemberEmail();
        this.memberPhone = member.getMemberPhone();
        this.memberEnrollDate = member.getMemberEnrollDate();
        this.memberUpdateDate = member.getMemberUpdateDate();
        this.memberStatus = member.getMemberStatus();
    }

    public ResponseMember(MemberDTO memberDTO) {
        this.memberCode = memberDTO.getMemberCode();
        this.memberName = memberDTO.getMemberName();
        this.memberId = memberDTO.getMemberId();
        this.memberPw = memberDTO.getMemberPw();
        this.memberEmail = memberDTO.getMemberEmail();
        this.memberPhone = memberDTO.getMemberPhone();
        this.memberEnrollDate = memberDTO.getMemberEnrollDate();
        this.memberUpdateDate = memberDTO.getMemberUpdateDate();
        this.memberStatus = memberDTO.getMemberStatus();
    }
}
