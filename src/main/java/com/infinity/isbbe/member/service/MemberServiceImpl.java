package com.infinity.isbbe.member.service;

import com.infinity.isbbe.member.aggregate.Member;
import com.infinity.isbbe.member.aggregate.RequestMember;
import com.infinity.isbbe.member.dto.MemberDTO;
import com.infinity.isbbe.member.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        return memberDTOList;
    }

    @Override
    @Transactional
    public List<MemberDTO> getMemberByCode(int memberCode) {
        List<Member> memberList = memberRepository.findByMemberCode(memberCode);
        List<MemberDTO> memberDTOS = new ArrayList<>();
        memberList.forEach(member -> memberDTOS.add(new MemberDTO(member)));
        return memberDTOS;
    }

    @Override
    @Transactional
    public ResponseEntity<String> createMember(RequestMember request) {
        Member member = new Member();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        member.setMemberName(request.getMemberName());
        member.setMemberEmail(request.getMemberEmail());
        member.setMemberPw(request.getMemberPw());
        member.setMemberId(request.getMemberId());
        member.setMemberPhone(request.getMemberPhone());
        member.setMemberEnrollDate(formattedDateTime);

        Member savedMember = memberRepository.save(member);
    }
}
