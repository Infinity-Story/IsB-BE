package com.infinity.isbbe.member.service;

import com.infinity.isbbe.admin.repository.AdminRepository;
import com.infinity.isbbe.commonUser.MailService;
import com.infinity.isbbe.log.etc.LogStatus;
import com.infinity.isbbe.log.service.LogService;
import com.infinity.isbbe.member.aggregate.Member;
import com.infinity.isbbe.member.aggregate.RequestMember;
import com.infinity.isbbe.member.dto.MemberDTO;
import com.infinity.isbbe.member.etc.MEMBER_STATUS;
import com.infinity.isbbe.member.repository.MemberRepository;
import com.infinity.isbbe.security.PasswordEncoderUtil;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final LogService logService;
    private final AdminRepository adminRepository;
    private final MailService mailService;
    private final StringRedisTemplate redisTemplate;

    private static final int CODE_LENGTH = 6;

    public MemberServiceImpl(MemberRepository memberRepository, LogService logService, AdminRepository adminRepository, MailService mailService, StringRedisTemplate redisTemplate) {
        this.memberRepository = memberRepository;
        this.logService = logService;
        this.adminRepository = adminRepository;
        this.mailService = mailService;
        this.redisTemplate = redisTemplate;
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
    public MemberDTO getMemberByMemberId(String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
        return new MemberDTO(member);
    }

    @Override
    public String findMemberIdByEmail(String email) {
        Member member = memberRepository.findByMemberEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일로 등록된 회원이 없습니다."));
        return member.getMemberId(); // Member 엔티티에서 ID 필드 이름에 맞게 수정
    }

    @Override
    public String findMemberIdAndSendEmail(String memberEmail) {
        System.out.println("Before processing email: " + memberEmail);

        memberEmail = memberEmail.trim().toLowerCase();

        System.out.println("After processing email: " + memberEmail);

        System.out.println("Executing findByMemberEmail...");
        Optional<Member> memberOptional = memberRepository.findByMemberEmail(memberEmail);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            System.out.println("Found member: " + member.getMemberEmail() + ", ID: " + member.getMemberId());

            String memberId = member.getMemberId();
            String subject = "아이디 찾기 안내";
            String content = "안녕하세요, 요청하신 아이디는 다음과 같습니다: " + memberId;

            mailService.sendEmail(memberEmail, subject, content);
            return "이메일이 성공적으로 발송되었습니다.";
        } else {
            System.out.println("No member found with email: " + memberEmail);
            throw new IllegalArgumentException("입력하신 정보와 일치하는 회원이 존재하지 않습니다.");
        }
    }

    @Override
    public String sendVerificationCode(String memberEmail) {
        memberEmail = memberEmail.trim().toLowerCase();
        Optional<Member> memberOptional = memberRepository.findByMemberEmail(memberEmail);

        if (memberOptional.isEmpty()) {
            throw new IllegalArgumentException("입력하신 정보와 일치하는 회원이 존재하지 않습니다.");
        }

        // 6자리 랜덤 인증번호 생성
        String verificationCode = generateVerificationCode();

        // Redis에 인증번호 저장 (유효기간: 5분)
        redisTemplate.opsForValue().set("VERIFY_" + memberEmail, verificationCode, 5, TimeUnit.MINUTES);

        // 이메일 전송
        String subject = "아이디 찾기 인증번호 안내";
        String content = "인증번호: <b>" + verificationCode + "</b> (5분 내에 입력해주세요.)";

        mailService.sendEmail(memberEmail, subject, content);
        return "인증번호가 이메일로 전송되었습니다.";
    }

    private String generateVerificationCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(random.nextInt(10)); // 0~9 랜덤 숫자
        }
        return code.toString();
    }

    @Override
    public String verifyCodeAndReturnId(String memberEmail, String verificationCode) {
        memberEmail = memberEmail.trim().toLowerCase();

        // Redis에서 인증번호 가져오기
        String storedCode = redisTemplate.opsForValue().get("VERIFY_" + memberEmail);

        if (storedCode == null || !storedCode.equals(verificationCode)) {
            throw new IllegalArgumentException("인증번호가 일치하지 않거나 만료되었습니다.");
        }

        // 인증번호가 맞으면 회원 ID 반환
        Member member = memberRepository.findByMemberEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        return member.getMemberId();
    }

    @Override
    @Transactional
    public MemberDTO getMemberById(String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        return new MemberDTO(member);
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

        // 비밀번호 암호화 (null 체크 후 암호화 수행)
        String encodedPassword = (request.getMemberPw() != null && !request.getMemberPw().isEmpty())
                ? PasswordEncoderUtil.encodePassword(request.getMemberPw())
                : member.getMemberPw();

        // 필드별 null 체크 후 기존 값 유지
        member.setMemberPw(encodedPassword);
        member.setMemberName(request.getMemberName() != null ? request.getMemberName() : member.getMemberName());
        member.setMemberEmail(request.getMemberEmail() != null ? request.getMemberEmail() : member.getMemberEmail());
        member.setMemberId(request.getMemberId() != null ? request.getMemberId() : member.getMemberId());
        member.setMemberPhone(request.getMemberPhone() != null ? request.getMemberPhone() : member.getMemberPhone());
        member.setMemberUpdateDate(formattedDateTime);

        memberRepository.save(member);

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
