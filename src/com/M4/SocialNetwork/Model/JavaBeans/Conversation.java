package com.m4.socialnetwork.model.javabeans;

import java.util.ArrayList;

import com.google.appengine.labs.repackaged.com.google.common.base.Pair;

public class Conversation {
	private Pair<String, Boolean> userOne;
	private Pair<String, Boolean> userTwo;
	private ArrayList<Message> messages;
	
   
public Conversation(Pair<String, Boolean> userOne, Pair<String, Boolean> userTwo,
			ArrayList<Message> messages) {
		this.userOne = userOne;
		this.userTwo = userTwo;
		this.messages = messages;
	}

//	public Conversation(String userOne, String userTwo,
//			ArrayList<Message> messages) {
//		super();
//		this.userOne = userOne;
//		this.userTwo = userTwo;
//		this.messages = messages;
//	}

	public Pair<String, Boolean> getUserOne() {
		return userOne;
	}

	public void setUserOne(Pair<String, Boolean> userOne) {
		this.userOne = userOne;
	}

	public Pair<String, Boolean> getUserTwo() {
		return userTwo;
	}

	public void setUserTwo(Pair<String, Boolean> userTwo) {
		this.userTwo = userTwo;
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}

	
}
