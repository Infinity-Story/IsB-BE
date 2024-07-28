package com.infinity.isbbe.member.service;

import com.infinity.isbbe.member.aggregate.Member;
import com.infinity.isbbe.member.dto.MemberDTO;
import com.infinity.isbbe.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public List<MemberDTO> getAllMember() {
        List<Member> memberList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();

        memberList.forEach(member -> memberDTOList.add(new MemberDTO(member)));
        return memberDTOList;ss
    }
}
