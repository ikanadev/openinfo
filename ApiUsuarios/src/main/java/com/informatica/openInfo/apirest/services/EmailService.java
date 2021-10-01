package com.informatica.openInfo.apirest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService{
	
	@Autowired
	private JavaMailSender sender;

	public void sendEmail(String from, String to, String subject, String content)  {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setFrom(from);
        email.setTo(to);
        email.setSubject(subject);
        email.setText(content);
        sender.send(email);
	}
	
	public void mandaEmail(String to, String subject, String content)  {
		SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(content);
        sender.send(email);
	}
	
//	public void sendEmailContacto(String from, String to, String subject, String content)  {
//		MimeM email = new SimpleMailMessage();
//        email.setTo(to);
//        email.setSubject(subject);
//        email.setText(content);
//        sender.send(email);
//	}
	
}
