package com.infinity.isbbe.member.controller;

import com.infinity.isbbe.member.dto.MemberDTO;
import com.infinity.isbbe.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/member")
@Tag(name = "회원 정보API", description = "Member Info")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(summary = "회원 전체 조회", description = "회원을 전체 조회합니다.")
    @GetMapping("/list")
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        List<MemberDTO> memberList= memberService.getAllMember();
        return ResponseEntity.ok(memberList);
    }


}
