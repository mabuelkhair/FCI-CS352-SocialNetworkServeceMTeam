package com.m4.socialnetwork.model.javabeans;

public class FriendRequest {
	private String senderEmail;
	private String receiverEmail;
	private boolean confirm;

	public FriendRequest(String senderEmail, String receiverEmail,
			boolean confirm) {
		super();
		this.senderEmail = senderEmail;
		this.receiverEmail = receiverEmail;
		this.confirm = confirm;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getReceiverEmail() {
		return receiverEmail;
	}

	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}

	public boolean isConfirm() {
		return confirm;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}

}
