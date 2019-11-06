package com.target.interview.controller;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.target.interview.config.ApplicationConfigReader;
import com.target.interview.dto.Notification;
import com.target.interview.dto.NotificationBean;
import com.target.interview.dto.NotificationFactory;
import com.target.interview.dto.NotificationType;
import com.target.interview.util.ApplicationConstant;



/**
 * Message Listener for RabbitMQ
 * @author manjula ghanti
 *
 */

@Service
public class MessageListener {

    private static final Logger log = LoggerFactory.getLogger(MessageListener.class);
    
    @Autowired
    ApplicationConfigReader applicationConfigReader;
    @Autowired
    NotificationFactory notificationFactory;
    @Autowired
    NotificationType notificationType;
    
   

    
    
    /**
     * Message listener for app1
     * @param NotificationBean object used for deserialization of message
     * @throws MessagingException 
     * @throws AddressException 
     */
    @RabbitListener(queues = "${app1.queue.name}")
    public void receiveMessageForApp1(final NotificationBean data) throws AddressException, MessagingException {
    	log.info("Received message: {} from app1 queue.", data);
    	
    	try {
    		log.info("Making REST call to the API");
    		 Notification notification=notificationFactory.NotificationForm(ApplicationConstant.EMAIL);
    		 notification.SendNotification(data);
    		//TODO: Code to make REST call
        	log.info("<< Exiting receiveMessageForApp1() after API call.");
    	} catch(HttpClientErrorException  ex) {
    		
    		if(ex.getStatusCode() == HttpStatus.NOT_FOUND) {
        		log.info("Delay...");
        		try {
    				Thread.sleep(ApplicationConstant.MESSAGE_RETRY_DELAY);
    			} catch (InterruptedException e) { }
    			
    			log.info("Throwing exception so that message will be requed in the queue.");
    			// Note: Typically Application specific exception should be thrown below
    			throw new RuntimeException();
    		} else {
    			throw new AmqpRejectAndDontRequeueException(ex); 
    		}
    		
    	} catch(Exception e) {
    		log.error("Internal server error occurred in API call. Bypassing message requeue {}", e);
    		throw new AmqpRejectAndDontRequeueException(e); 
    	}

    }
    
    
    /**
     * Message listener for app2
     * 
     */
    
    @RabbitListener(queues = "${app2.queue.name}")
    public void receiveMessageForApp2(final NotificationBean notificationBean) {
    	log.info("Received message: {} from app2 queue.", notificationBean);
    	
    	try {
    		log.info("Making REST call to the API");
    		 Notification notification=notificationFactory.NotificationForm(ApplicationConstant.SLACK);
    		 notification.SendNotification(notificationBean);
    		//TODO: Code to make REST call
        	log.info("<< Exiting receiveMessageCrawlCI() after API call.");
    	} catch(HttpClientErrorException  ex) {
    		
    		if(ex.getStatusCode() == HttpStatus.NOT_FOUND) {
        		log.info("Delay...");
        		try {
    				Thread.sleep(ApplicationConstant.MESSAGE_RETRY_DELAY);
    			} catch (InterruptedException e) { }
    			
    			log.info("Throwing exception so that message will be requed in the queue.");
    			// Note: Typically Application specific exception can be thrown below
    			throw new RuntimeException();
    		} else {
    			throw new AmqpRejectAndDontRequeueException(ex); 
    		}
    		
    	} catch(Exception e) {
    		log.error("Internal server error occurred in python server. Bypassing message requeue {}", e);
    		throw new AmqpRejectAndDontRequeueException(e); 
    	}

    }


}
