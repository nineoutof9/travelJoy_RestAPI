package com.ict.traveljoy.smtp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MailRepository mailRepository;
    private final JavaMailSender javaMailSender;
    
    @Value("${spring.mail.username}")
    private String senderEmail;

    // 랜덤으로 숫자 생성
    public String createNumber() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 5; i++) { // 인증 코드 5자리
            key.append(random.nextInt(10)); // 숫자 5자리로
        }
        return key.toString();
    }

    public MimeMessage createMail(String mail, String number) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom(senderEmail);
        message.setRecipients(MimeMessage.RecipientType.TO, mail);
        message.setSubject("TravelJoy 이메일 인증");
        String body = "";
        body += "<h3>인증 번호입니다.</h3>";
        body += "<h1>" + number + "</h1>";
        message.setText(body, "UTF-8", "html");

        return message;
    }

    // 메일 발송
    public String sendSimpleMessage(String sendEmail) throws MessagingException {
        String number = createNumber(); // 랜덤 인증번호 생성

        MimeMessage message = createMail(sendEmail, number); // 메일 생성
        try {
            javaMailSender.send(message); // 메일 발송
        } catch (MailException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("메일 발송 중 오류가 발생했습니다.");
        }

        return number; // 생성된 인증번호 반환
    }
    
    public MailDTO findByEmail(String email) {
        return mailRepository.findByEmail(email)
            .map(MailDTO::toDTO) 
            .orElse(null); 
    }

    
    public MailDTO saveMailAuth(MailDTO mailDTO) {
        Mail mail = mailDTO.toEntity();
        
        LocalDate today = LocalDate.now();
        
        // 날짜 비교 로직
        if (mail.getLastTryDate() != null && mail.getLastTryDate().toLocalDate().isEqual(today)) {
        	mail.setTodayTryCount(mail.getTodayTryCount() + 1);
        } else {
            mail.setTodayTryCount(1); // 새로운 날이면 1로 초기화
        }
        
        mail.setLastTryDate(LocalDateTime.now());
        return MailDTO.toDTO(mailRepository.save(mail));
    }
    
}