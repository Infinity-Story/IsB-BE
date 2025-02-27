package com.infinity.isbbe.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
@Configuration
@EnableConfigurationProperties(MailConfig.class)
@ConfigurationProperties(prefix = "spring.mail")
public class MailConfig {

    private String host;
    private int port;
    private String username;
    private String password;

    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        if (host == null || username == null || password == null) {
            throw new IllegalArgumentException("Naver mail configuration is missing");
        }

        System.out.println("Naver mail configuration - Username: " + username);
        System.out.println("Naver mail configuration - Password: " + password);

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        // TLS 설정
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}



