package com.infinity.isbbe.commonUser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final Map<String, String> verificationCodes = new HashMap<>(); // 인증번호 저장용 (여기선 임시로 Map 사용)

    @PostMapping("/send-email")
    public ResponseEntity<?> sendVerificationEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // 이메일이 유효한지 확인 (예시로 간단히 이메일 형식만 체크)
        if (!isValidEmail(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "유효하지 않은 이메일 주소입니다."));
        }

        // 인증번호 생성
        String verificationCode = generateVerificationCode();

        // 인증번호 저장 (여기서는 Map 사용, 실제로는 Redis 등을 사용하는 게 좋음)
        verificationCodes.put(email, verificationCode);

        // 이메일 발송
        emailService.sendEmail(email, "아이디 찾기 인증번호", "인증번호: " + verificationCode);

        return ResponseEntity.ok(Map.of("message", "인증번호가 전송되었습니다."));
    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyVerificationCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String inputCode = request.get("code");

        // 저장된 인증번호와 비교
        String savedCode = verificationCodes.get(email);
        if (savedCode != null && savedCode.equals(inputCode)) {
            // 인증번호가 일치하면 아이디 반환
            String userId = findUserIdByEmail(email); // 이메일로 아이디 찾기
            return ResponseEntity.ok(Map.of("message", "인증 성공", "userId", userId));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "인증번호가 일치하지 않습니다."));
        }
    }

    // 이메일로 아이디 찾기 (예시: 데이터베이스에서 사용자 조회)
    private String findUserIdByEmail(String email) {
        // 실제 DB에서 이메일로 사용자 아이디를 찾아서 반환하는 로직
        return "foundUserId"; // 예시로 반환
    }

    private String generateVerificationCode() {
        return String.valueOf(new Random().nextInt(900000) + 100000); // 6자리 인증번호 생성
    }

    // 이메일 형식 유효성 검사
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}