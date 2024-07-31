package com.infinity.isbbe.member.controller;

import com.infinity.isbbe.member.aggregate.RequestMember;
import com.infinity.isbbe.member.aggregate.ResponseMember;
import com.infinity.isbbe.member.dto.MemberDTO;
import com.infinity.isbbe.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/member")
@Tag(name = "회원 정보 API", description = "Member Info")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(summary = "회원 전체 조회", description = "회원을 전체 조회합니다.")
    @GetMapping("/list")
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        List<MemberDTO> memberList = memberService.getAllMember();
        return ResponseEntity.ok(memberList);
    }

    @Operation(summary = "회원 코드로 회원 조회", description = "특정 회원을 조회합니다.")
    @GetMapping("/detail/{memberCode}")
    public ResponseEntity<List<ResponseMember>> getMemberByMemberCode(@PathVariable int memberCode) {
        List<MemberDTO> memberDTOS = memberService.getMemberByCode(memberCode);
        List<ResponseMember> responseMember = new ArrayList<>();
        memberDTOS.forEach(memberDTO -> {
            responseMember.add(new ResponseMember(memberDTO));
        });
        return ResponseEntity.ok(responseMember);
    }

    @Operation(summary = "회원 등록", description = "신규 회원을 등록합니다.")
    @GetMapping("/create")
    public ResponseEntity<String> createMember(@RequestBody RequestMember request) {
        return memberService.createMember(request);
    }

}
