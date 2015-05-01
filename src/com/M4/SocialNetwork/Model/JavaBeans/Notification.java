package com.m4.socialnetwork.model.javabeans;

public class Notification {
	private String notificationType;
	private String receiverId;
	private String notificationId;
	private String componentId ;
	

	
	public Notification(String notificationType, String receiverId,
			String notificationId, String componentId) {
		super();
		this.notificationType = notificationType;
		this.receiverId = receiverId;
		this.notificationId = notificationId;
		this.componentId = componentId;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

}
