package com.infinity.isbbe.commonUser;

import com.infinity.isbbe.config.MailConfig;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailConfig mailConfig;

    // 메일 발송 메서드
    public void sendEmail(String to, String subject, String content) {
        try {
            // 지정된 메일 서비스에 맞는 메일 발송 설정을 가져옴
            JavaMailSender mailSender = mailConfig.getMailSender();

            // 메일 메시지 구성
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8"); // 'true'는 HTML 형식 지원

            // 수신자, 제목, 내용 설정
            helper.setFrom("isb2025@naver.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // HTML 형식으로 내용 전달

            // 메일 발송
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 중 오류가 발생했습니다.", e);
        }
    }
}

