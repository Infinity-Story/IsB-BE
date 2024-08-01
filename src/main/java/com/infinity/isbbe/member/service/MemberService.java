package com.infinity.isbbe.member.service;

import com.infinity.isbbe.member.aggregate.RequestMember;
import com.infinity.isbbe.member.dto.MemberDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MemberService {
    List<MemberDTO> getAllMember();

    List<MemberDTO> getMemberByCode(int memberCode);

    ResponseEntity<String> createMember(RequestMember request);

    ResponseEntity<String> updateMember(int memberCode, RequestMember request);
}
