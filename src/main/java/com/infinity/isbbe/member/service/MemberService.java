package com.infinity.isbbe.member.service;

import com.infinity.isbbe.member.aggregate.RequestMember;
import com.infinity.isbbe.member.dto.MemberDTO;
import com.infinity.isbbe.member.etc.MEMBER_STATUS;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MemberService {
    List<MemberDTO> getAllMember();

    List<MemberDTO> getMemberByCode(int memberCode);

    ResponseEntity<String> createMember(RequestMember request);

    ResponseEntity<String> updateMember(int memberCode, RequestMember request);

    ResponseEntity<String> updateMemberOff(int memberCode);

    ResponseEntity<String> updateMemberSleep(int memberCode);

    ResponseEntity<String> updateMemberStop(int memberCode);

    ResponseEntity<String> updateMemberOn(int memberCode);

    List<MemberDTO> getMemberByStatus(MEMBER_STATUS memberStatus);

    ResponseEntity<String> updateMemberOut(int memberCode);

    boolean checkIdExist(String memberId);
}
