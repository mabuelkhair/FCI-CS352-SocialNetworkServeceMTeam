package com.m4.socialnetwork.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;
import com.m4.socialnetwork.model.controllers.MessageController;
import com.m4.socialnetwork.model.controllers.UserController;
import com.m4.socialnetwork.model.javabeans.Conversation;
import com.m4.socialnetwork.model.javabeans.MessageGroup;
import com.m4.socialnetwork.model.javabeans.User;

@Path("/MessageService")
public class Message {

	@POST
	@Path("/SendMessageConversation")
	public String sendMessageConversation(
			@FormParam("senderEmail") String senderEmail,
			@FormParam("senderPassword") String senderPassword,
			@FormParam("receiverId") String receiverId,
			@FormParam("message") String message) {
		UserController controller = new UserController();
		User sender = controller.getUser(senderEmail, senderPassword);
		User receiver = controller.getUserById(receiverId);
		JSONObject jsonObject = new JSONObject();
		if (sender != null && receiver != null) {
			Date date = new MessageController().sendMessageConversation(
					sender.getId(), receiverId, message);
			try {
				jsonObject.put("Status", "OK");
				jsonObject.put("date", date);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				jsonObject.put("Status", "faild");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return jsonObject.toString();
	}

	@POST
	@Path("/CreateGroupChat")
	public String createGroupChat(@FormParam("creatorId") String creatorId,
			@FormParam("groupChatName") String groupChatName,
			@FormParam("userId") String usersId) {
		String delims = "[;]+";
		ArrayList<String> users = new ArrayList<String>(Arrays.asList(usersId
				.split(delims)));
		MessageController controller = new MessageController();
		String groupId = controller.createConversaionGroup(creatorId,
				groupChatName, users);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("groupId", groupId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject.toString();
	}

	@POST
	@Path("/SendMessageGroupChat")
	public String sendMessageGroupChat(@FormParam("groupId") String groupId,
			@FormParam("senderId") String senderId,
			@FormParam("message") String message) {

		MessageController controller = new MessageController();
		Date date = controller.sendMessageConversationGroup(groupId, senderId,
				message);

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("date", date);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject.toString();
	}

	@POST
	@Path("/GetConversation")
	public String getConversation(
			@FormParam("conversationId") String conversationId) {
		Conversation conversation = new MessageController()
				.getConversation(conversationId);
		JSONObject jsonObject = new JSONObject();
		Gson gson = new Gson();
		try {
			jsonObject.put("Status", "OK");
			jsonObject.put("conversation", gson.toJson(conversation));
			return jsonObject.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@POST
	@Path("/GetGroupMessage")
	public String getGroupMessage(@FormParam("messageGroupId") String groupId) {

		MessageGroup group = new MessageController()
				.getConvesationGroup(groupId);
		JSONObject jsonObject = new JSONObject();
		Gson gson = new Gson();
		try {
			jsonObject.put("messageGroup", gson.toJson(group));
			return jsonObject.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@POST
	@Path("/UserConversationsIndividuals")
	public String userConversationIndividuals(@FormParam("userId") String userId){
		ArrayList<Conversation> userConversations = new MessageController().getUserConversationIndividuals(userId) ;
		JSONObject jsonObject = new JSONObject() ;
		Gson gson = new Gson() ;
		try {
			jsonObject.put("Status", "OK");
			jsonObject.put("userConversations", gson.toJson(userConversations)) ;
			return jsonObject.toString() ;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null ;
	}

}
