package com.target.interview.controller;


import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.target.interview.config.ApplicationConfigReader;
import com.target.interview.dto.NotificationBean;
import com.target.interview.util.ApplicationConstant;



@RestController
@RequestMapping(path = "/notifyService")
public class NotificationService {

	private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

	private final RabbitTemplate rabbitTemplate;
	private ApplicationConfigReader applicationConfig;
	private MessageSender messageSender;

	public ApplicationConfigReader getApplicationConfig() {
		return applicationConfig;
	}

	@Autowired
	public void setApplicationConfig(ApplicationConfigReader applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

	@Autowired
	public NotificationService(final RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public MessageSender getMessageSender() {
		return messageSender;
	}

	@Autowired
	public void setMessageSender(MessageSender messageSender) {
		this.messageSender = messageSender;
	}


	@RequestMapping(path = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> sendMessage(@PathParam(value = ApplicationConstant.SYSTEM) String source, @RequestBody NotificationBean messagebean) {
		String exchange = null;
		String routingKey =null;
		
		if(source.equals(ApplicationConstant.SYSTEM)) {
			exchange = getApplicationConfig().getApp1Exchange();
			routingKey = getApplicationConfig().getApp1RoutingKey();
		}else {
				exchange = getApplicationConfig().getApp2Exchange();
			    routingKey = getApplicationConfig().getApp2RoutingKey();
		}

		/* Sending to Message Queue */
		try {
			messageSender.sendMessage(rabbitTemplate, exchange, routingKey, messagebean);
			return new ResponseEntity<String>(ApplicationConstant.IN_QUEUE, HttpStatus.OK);
			
		} catch (Exception ex) {
			log.error("Exception occurred while sending message to the queue. Exception= {}", ex);
			return new ResponseEntity(ApplicationConstant.MESSAGE_QUEUE_SEND_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	


	
}
