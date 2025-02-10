package com.infinity.isbbe.member.aggregate;

import com.infinity.isbbe.member.etc.MEMBER_STATUS;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class RequestMember {

    private int memberCode;
    private String memberName;
    private String memberId;
    private String memberPw;
    private String memberEmail;
    private String memberPhone;
    private String memberEnrollDate;
    private MEMBER_STATUS memberStatus;
    private String profileImage;
}
