package com.target.interview.util;

public class ApplicationConstant {

	public static final String SUCCESS = "SUCCESS";
	public static final String ERROR = "ERROR";

	public static final String IN_PROGRESS = "INPROGRESS";
	public static final String IN_QUEUE = "REQUEST_IN_QUEUE";
	public static final String FAILED = "FAILED";

	/* Error codes Starts */
	public static final String MESSAGE_QUEUE_SEND_ERROR = "MESSAGE_QUEUE_SEND_ERROR";
	public static final String MESSAGE_QUEUE_RECEIVE_ERROR = "MESSAGE_QUEUE_RECEIVE_ERROR";
	
	/* Constants */
	public static final int MESSAGE_RETRY_DELAY=5000;
	public static final String EMAIL = "EMAIL";
	public static final String SLACK = "SLACK";
	
	public static final String TARGET = "target";
	public static final String SYSTEM = "system";
	public static final String USER_DISABLED="USER_DISABLED";
	public static final String INVALID_CREDENTIALS="INVALID_CREDENTIALS";
	
}
