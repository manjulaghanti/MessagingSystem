package com.target.interview.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.target.interview.util.ApplicationConstant;

@Controller
public class NotificationType extends NotificationFactory {
	
	@Autowired
	EmailNotification emailNotification;
	@Override
	public Notification NotificationForm(String type) {
		// TODO Auto-generated method stub
		Notification notification = null;
		if(type.equals(ApplicationConstant.EMAIL)) {
			notification = emailNotification;
		}else if(type.equals(ApplicationConstant.SLACK)) {
			//code for slack notification
		}
		
		return notification;
	}

}
