package com.infinity.isbbe.commonUser;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender defaultMailSender; // 기본 SMTP (Gmail)
    private final Environment env; // application.yml에서 설정값 가져오기

    public void sendEmail(String toEmail, String subject, String content) {
        JavaMailSender mailSender = getMailSenderForDomain(toEmail);

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content);
            helper.setFrom(getSenderEmailForDomain(toEmail)); // 발신자 이메일 설정

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 실패", e);
        }
    }

    private JavaMailSender getMailSenderForDomain(String email) {
        if (email.endsWith("@naver.com")) {
            return createNaverMailSender();
        } else {
            return defaultMailSender; // 기본적으로 Gmail 사용
        }
    }

    private JavaMailSender createNaverMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(env.getProperty("spring.mail-naver.host"));
        mailSender.setPort(Integer.parseInt(env.getProperty("spring.mail-naver.port")));
        mailSender.setUsername(env.getProperty("spring.mail-naver.username"));
        mailSender.setPassword(env.getProperty("spring.mail-naver.password"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }

    private String getSenderEmailForDomain(String email) {
        if (email.endsWith("@naver.com")) {
            return env.getProperty("spring.mail-naver.username");
        }
        return env.getProperty("spring.mail.username"); // 기본적으로 Gmail
    }
}