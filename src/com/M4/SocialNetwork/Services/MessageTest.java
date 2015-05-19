package com.m4.socialnetwork.services;

import java.util.ArrayList;
import java.util.Date;

import junit.framework.Assert;

import org.testng.annotations.Test;

import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;
import com.m4.socialnetwork.model.controllers.MessageController;
import com.m4.socialnetwork.model.controllers.UserController;
import com.m4.socialnetwork.model.javabeans.Conversation;
import com.m4.socialnetwork.model.javabeans.MessageGroup;
import com.m4.socialnetwork.model.javabeans.User;

public class MessageTest {
Message a=new Message();
  @Test
  public void createGroupChat() {
	  Assert.assertEquals("", a.createGroupChat("", "friends", ""));

    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void getConversation() {
	  Conversation conversation = new MessageController()
		.getConversation("");
	  Assert.assertEquals(conversation, a.getConversation(""));

	  throw new RuntimeException("Test not implemented");
  }

  @Test
  public void getGroupMessage() {
	  MessageGroup group = new MessageController()
		.getConvesationGroup("");
	  Gson gson = new Gson();
	  Assert.assertEquals(gson.toJson(group), a.getGroupMessage(""));
	  
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void sendMessageConversation() {
	  UserController controller = new UserController();
		
	  User sender = controller.getUser("m", "m");
		
	  Date date = new MessageController().sendMessageConversation(
				sender.getId(), "", "Hi");
	  Assert.assertEquals("OK"+date, a.sendMessageConversation("m", "m", "", "Hi"));
	  
	  throw new RuntimeException("Test not implemented");
  }

  @Test
  public void sendMessageGroupChat() {
	  MessageController controller = new MessageController();
	  Date date = controller.sendMessageConversationGroup("","",
				"Hello");
	  Assert.assertEquals(date, a.sendMessageGroupChat("", "", "Hello"));
	  
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void userConversationIndividuals() {
	  
	ArrayList<Conversation> userConversations = new MessageController().getUserConversationIndividuals("") ;
	Gson gson = new Gson() ;
	Assert.assertEquals(gson.toJson(userConversations), a.userConversationIndividuals(""));
	throw new RuntimeException("Test not implemented");
  }
}
