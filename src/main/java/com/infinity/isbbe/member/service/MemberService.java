package com.infinity.isbbe.member.service;

import com.infinity.isbbe.member.dto.MemberDTO;

import java.util.List;

public interface MemberService {
    List<MemberDTO> getAllMember();

    List<MemberDTO> getMemberByCode(int memberCode);
}
