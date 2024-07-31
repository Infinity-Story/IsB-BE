package com.infinity.isbbe.member.aggregate;

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

}
