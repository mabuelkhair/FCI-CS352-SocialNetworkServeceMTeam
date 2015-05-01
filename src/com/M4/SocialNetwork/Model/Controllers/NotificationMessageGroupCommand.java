package com.m4.socialnetwork.model.controllers;

import com.google.appengine.labs.repackaged.com.google.common.base.Pair;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;
import com.m4.socialnetwork.model.javabeans.Conversation;
import com.m4.socialnetwork.model.javabeans.MessageGroup;
import com.m4.socialnetwork.model.javabeans.Notification;

public class NotificationMessageGroupCommand extends NotificationCommand{

	@Override
	public String getNotification(Notification notification) {
  MessageController controller = new MessageController() ;
   MessageGroup group = controller.getConvesationGroup(notification.getComponentId()) ;
   JSONObject jsonObject = new JSONObject () ;
	Gson gson = new Gson();
	Pair<String, MessageGroup> conversationString = new Pair<String, MessageGroup>(
			"ConversationGroup", group);
	
	try {
		jsonObject.put("notification", gson.toJson(conversationString));
		setNotificationSeen(notification.getNotificationId());
		return jsonObject.toString() ;
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		return null;
	}

	
}
