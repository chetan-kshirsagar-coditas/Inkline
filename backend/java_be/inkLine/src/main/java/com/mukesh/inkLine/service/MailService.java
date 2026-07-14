package com.mukesh.inkLine.service;

import com.mukesh.inkLine.exceptions.MailSenderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
    private final JavaMailSender javaMailSender;

    public void sendMail(String senderMail, String receiverMail, String subject, String message) throws MailSenderException{
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(senderMail);
            simpleMailMessage.setTo(receiverMail);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(message);

            javaMailSender.send(simpleMailMessage);
            log.info("Mail is successfully sent");
        } catch (Exception exception) {
            throw new MailSenderException("There is an error while sending from " + senderMail + " to " + receiverMail);
        }
    }
}
