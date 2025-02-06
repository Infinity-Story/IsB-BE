package com.infinity.isbbe.member.service;

import com.infinity.isbbe.admin.repository.AdminRepository;
import com.infinity.isbbe.log.etc.LogStatus;
import com.infinity.isbbe.log.service.LogService;
import com.infinity.isbbe.member.aggregate.Member;
import com.infinity.isbbe.member.aggregate.RequestMember;
import com.infinity.isbbe.member.dto.MemberDTO;
import com.infinity.isbbe.member.etc.MEMBER_STATUS;
import com.infinity.isbbe.member.repository.MemberRepository;
import com.infinity.isbbe.security.PasswordEncoderUtil;
import jakarta.persistence.EntityNotFoundException;
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
    private final LogService logService;
    private final AdminRepository adminRepository;

    public MemberServiceImpl(MemberRepository memberRepository, LogService logService, AdminRepository adminRepository) {
        this.memberRepository = memberRepository;
        this.logService = logService;
        this.adminRepository = adminRepository;
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
        // adminId 중복 체크
        if (adminRepository.existsByAdminId(request.getMemberId())) {
            throw new IllegalArgumentException("Id already exist");
        }

        // memberId 중복 체크
        if (memberRepository.existsByMemberId(request.getMemberId())) {
            throw new IllegalArgumentException("Id already exist");
        }

        Member member = new Member();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        String encodedPassword = PasswordEncoderUtil.encodePassword(request.getMemberPw());

        member.setMemberName(request.getMemberName());
        member.setMemberEmail(request.getMemberEmail());
        member.setMemberPw(encodedPassword);
        member.setMemberId(request.getMemberId());
        member.setMemberPhone(request.getMemberPhone());
        member.setMemberEnrollDate(formattedDateTime);
        member.setMemberStatus(MEMBER_STATUS.활성화);

        Member savedMember = memberRepository.save(member);

        logService.saveLog("root", LogStatus.등록, savedMember.getMemberName(), "Member");

        return ResponseEntity.ok("신규 회원 등록 완료");
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateMember(int memberCode, RequestMember request) {
        Member member = memberRepository.findById(memberCode)
                .orElseThrow(() -> new EntityNotFoundException("해당 회원이 존재하지 않습니다."));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        member.setMemberName(request.getMemberName());
        member.setMemberEmail(request.getMemberEmail());
        member.setMemberPw(request.getMemberPw());
        member.setMemberId(request.getMemberId());
        member.setMemberPhone(request.getMemberPhone());
        member.setMemberUpdateDate(formattedDateTime);

        Member updatedMember = memberRepository.save(member);

        logService.saveLog("root", LogStatus.수정, updatedMember.getMemberName(), "Member");

        return ResponseEntity.ok("회원 수정 완료");
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateMemberOff(int memberCode) {
        Member member = memberRepository.findById(memberCode).orElseThrow(()-> new EntityNotFoundException("해당 회원이 존재하지 않습니다."));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        member.setMemberStatus(MEMBER_STATUS.비활성화);
        member.setMemberUpdateDate(formattedDateTime);

        Member updatedMember = memberRepository.save(member);

        logService.saveLog("root",LogStatus.수정, updatedMember.getMemberName(), "Member");
        return ResponseEntity.ok("회원상태 비활성화로 수정 완료");
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateMemberSleep(int memberCode) {
        Member member = memberRepository.findById(memberCode).orElseThrow(()-> new EntityNotFoundException("해당 회원이 존재하지 않습니다."));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        member.setMemberStatus(MEMBER_STATUS.휴면);
        member.setMemberUpdateDate(formattedDateTime);

        Member updatedMember = memberRepository.save(member);

        logService.saveLog("root",LogStatus.수정, updatedMember.getMemberName(), "Member");
        return ResponseEntity.ok("회원상태 휴면으로 수정 완료");
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateMemberStop(int memberCode) {
        Member member = memberRepository.findById(memberCode).orElseThrow(()-> new EntityNotFoundException("해당 회원이 존재하지 않습니다."));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        member.setMemberStatus(MEMBER_STATUS.제재);
        member.setMemberUpdateDate(formattedDateTime);

        Member updatedMember = memberRepository.save(member);

        logService.saveLog("root",LogStatus.수정, updatedMember.getMemberName(), "Member");
        return ResponseEntity.ok("회원상태 제재로 수정 완료");
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateMemberOn(int memberCode) {
        Member member = memberRepository.findById(memberCode).orElseThrow(()-> new EntityNotFoundException("해당 회원이 존재하지 않습니다."));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        member.setMemberStatus(MEMBER_STATUS.활성화);
        member.setMemberUpdateDate(formattedDateTime);

        Member updatedMember = memberRepository.save(member);

        logService.saveLog("root",LogStatus.수정, updatedMember.getMemberName(), "Member");
        return ResponseEntity.ok("회원상태 활성화로 수정 완료");
    }

    @Override
    public ResponseEntity<String> updateMemberOut(int memberCode) {
        Member member = memberRepository.findById(memberCode).orElseThrow(()-> new EntityNotFoundException("해당 회원이 존재하지 않습니다."));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);

        member.setMemberStatus(MEMBER_STATUS.탈퇴처리);
        member.setMemberUpdateDate(formattedDateTime);

        Member updatedMember = memberRepository.save(member);

        logService.saveLog("root",LogStatus.수정, updatedMember.getMemberName(), "Member");
        return ResponseEntity.ok("회원상태 탈퇴처리로 수정 완료");
    }

    @Override
    public boolean checkIdExist(String memberId) {
        return memberRepository.existsByMemberId(memberId);
    }

    @Override
    @Transactional
    public List<MemberDTO> getMemberByStatus(MEMBER_STATUS memberStatus) {
        List<Member> memberList = memberRepository.findAllByMemberStatus(memberStatus);
        List<MemberDTO> memberDTOs = new ArrayList<>();
        memberList.forEach(member -> memberDTOs.add(new MemberDTO(member)));
        return memberDTOs;
    }

}
