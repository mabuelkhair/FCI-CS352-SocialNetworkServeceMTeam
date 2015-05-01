package com.m4.socialnetwork.model.javabeans;

import java.util.Date;

public class Message {
	private String id;
	private String content;
	private String senderId;
	private Date sendDate;

	public Message(String id, String content, String senderId, Date sendDate) {
		super();
		this.id = id;
		this.content = content;
		this.senderId = senderId;
		this.sendDate = sendDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

}
