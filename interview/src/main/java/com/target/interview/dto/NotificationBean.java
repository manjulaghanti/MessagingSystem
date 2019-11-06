package com.target.interview.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;



public final class NotificationBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@JsonProperty("from")
    private String from;
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@JsonProperty("to")
    private String to;
	
	@JsonProperty("subject")
	private String subject;

    // Default constructor is needed to de-serialize JSON
    public NotificationBean() {
    }

    public NotificationBean(String from, String to,  String subject) {
        this.from = from;
        this.to = to;
        this.subject = subject;
    }

	@Override
	public String toString() {
		return "NotificationBean [from=" + from + ", to=" + to + ", subject=" + subject + "]";
	}

}
