package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender)
    {
        this.javaMailSender=javaMailSender;
    }

    @Async
    public void biletIptaliSendMail(String aliciEposta, Long biletId)
    {
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();

        String biletID = String.valueOf(biletId);

        simpleMailMessage.setSubject("Etix bilet iptali");
        simpleMailMessage.setTo(aliciEposta);
        simpleMailMessage.setText(biletID + " id sine sahip biletiniz iptal edilmiştir. İyi günler dileriz.");
        javaMailSender.send(simpleMailMessage);
    }

    @Async
    public void etkinlikSilSendMail(String aliciEposta, Long biletId)
    {
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();

        String biletID = String.valueOf(biletId);

        simpleMailMessage.setSubject("Etix bilet iptali");
        simpleMailMessage.setTo(aliciEposta);
        simpleMailMessage.setText(biletID + " id sine sahip biletiniz etkinlik iptal edildiği için iptal edilmiştir. İyi günler dileriz.");
        javaMailSender.send(simpleMailMessage);
    }
}
