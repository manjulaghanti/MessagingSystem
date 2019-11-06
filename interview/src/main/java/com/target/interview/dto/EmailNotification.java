package com.target.interview.dto;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;

import com.target.interview.util.ApplicationConstant;
@Controller
public class EmailNotification implements Notification {

	@Autowired
    private JavaMailSender javaMailSender;
	@Override
	public String SendNotification(NotificationBean messageDtls) {
		// TODO Auto-generated method stub
		try {
		MimeMessage  message = javaMailSender.createMimeMessage();
	 	message.setFrom(new InternetAddress(messageDtls.getFrom()));
        message.setRecipients(Message.RecipientType.TO, 
            InternetAddress.parse(messageDtls.getTo()));
        message.setSubject(messageDtls.getSubject());
        message.setContent(messageDtls.getSubject(), "text/html");

	 	 javaMailSender.send(message);
	 	 
		}catch(Exception e) {
			e.getStackTrace();
		}
		return ApplicationConstant.SUCCESS;

	}

}
