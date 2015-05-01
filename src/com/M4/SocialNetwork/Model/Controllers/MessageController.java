package com.m4.socialnetwork.model.controllers;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.labs.repackaged.com.google.common.base.Pair;
import com.m4.socialnetwork.model.javabeans.Conversation;
import com.m4.socialnetwork.model.javabeans.Message;
import com.m4.socialnetwork.model.javabeans.MessageGroup;

public class MessageController {

	public String conversationExist(String userId1, String userId2) {

		Filter filterId1 = new FilterPredicate("userOne", FilterOperator.EQUAL,
				userId1);
		Filter filterId2 = new FilterPredicate("userTwo", FilterOperator.EQUAL,
				userId2);

		Filter filterId1andId2 = CompositeFilterOperator.and(filterId1,
				filterId2);

		Query q = new Query("CONVERSATION").setFilter(filterId1andId2);

		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		PreparedQuery p = dataStore.prepare(q);
		Entity result = p.asSingleEntity();

		if (result != null) {
			return String.valueOf(result.getKey().getId());
		}

		return "-1";
	}

	public void CreateConverationSeenTable(String conversationId,
			String userOneId, String userTwoId) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Entity conversationSeen = new Entity("CONVERSATION_SEEN");
		conversationSeen.setProperty("conversationId", conversationId);
		conversationSeen.setProperty("userId", userOneId);
		conversationSeen.setProperty("seen", false);
		dataStore.put(conversationSeen);
		conversationSeen = new Entity("CONVERSATION_SEEN");
		conversationSeen.setProperty("conversationId", conversationId);
		conversationSeen.setProperty("userId", userTwoId);
		conversationSeen.setProperty("seen", false);
		dataStore.put(conversationSeen);
	}

	public String createConversation(String userId1, String userId2) {

		String check1 = conversationExist(userId1, userId2);
		String check2 = conversationExist(userId2, userId1);

		if (!check1.equals("-1")) {
			return check1;
		}
		if (!check2.equals("-1")) {
			return check2;
		}
		Entity conversation = new Entity("CONVERSATION");
		conversation.setProperty("userOne", userId1);
		conversation.setProperty("userTwo", userId2);
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		dataStore.put(conversation);
		String conversationId = String.valueOf(conversation.getKey().getId());
		CreateConverationSeenTable(conversationId, userId1, userId2);

		return conversationId;
	}

	public void setConversationSeen(String conversationId, String userId , boolean seen) {
		Filter userFilter = new FilterPredicate("userId", FilterOperator.EQUAL,
				userId);
		Filter conversationFilter = new FilterPredicate("conversationId",
				FilterOperator.EQUAL, conversationId);
		Filter userAndConversation = CompositeFilterOperator.and(userFilter,
				conversationFilter);
		Query query = new Query("CONVERSATION_SEEN")
				.setFilter(userAndConversation);
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		PreparedQuery pq = dataStore.prepare(query);
		Entity e = pq.asSingleEntity();
		if(e!=null){
		e.setProperty("seen", seen);
		dataStore.put(e);
		}

	}

	public Date sendMessageConversation(
			String senderId, String recieverId, String message) {
		String conversationId = createConversation(senderId, recieverId) ;
		System.out.println(conversationId);
        Entity conversationReply = new Entity("CONVERSATION_REPLY");
		conversationReply.setProperty("conversationId", conversationId);
		conversationReply.setProperty("senderId", senderId);
		conversationReply.setProperty("message", message);
		Date date = new Date() ;
		conversationReply.setProperty("sendDate", date);
		setConversationSeen(conversationId, senderId , true);
		setConversationSeen(conversationId, recieverId, false);
		new NotificationController().putNotification(recieverId,
				conversationId, "NotificationMessageIndividualCommand");
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();

		dataStore.put(conversationReply);

		return date;
	}

	public ArrayList<Pair<String, Boolean>> getUsersOfConversationIndividuals(
			String conversationId) {

		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Filter filteConversation = new FilterPredicate("conversationId",
				FilterOperator.EQUAL, conversationId);
		Query qConversation = new Query("CONVERSATION_SEEN")
				.setFilter(filteConversation);
		PreparedQuery pq = dataStore.prepare(qConversation);
		ArrayList<Pair<String, Boolean>> users = new ArrayList<Pair<String, Boolean>>();
		for (Entity conversation : pq.asIterable()) {
			users.add(new Pair<String, Boolean>(conversation.getProperty(
					"userId").toString(), (Boolean) conversation
					.getProperty("seen")));
		}
		return users;
	}

	public Conversation getConversation(String conversationId) {
		ArrayList<Pair<String, Boolean>> users = getUsersOfConversationIndividuals(conversationId);
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Filter conversationFilter = new FilterPredicate("conversationId",
				FilterOperator.EQUAL, conversationId);
		Query q = new Query("CONVERSATION_REPLY").setFilter(conversationFilter);
		PreparedQuery pq = dataStore.prepare(q);
		ArrayList<Message> messages = new ArrayList<Message>();
		for (Entity conversation : pq.asIterable()) {
			messages.add(new Message(String.valueOf(conversation.getKey().getId()),
					conversation.getProperty("message").toString(),
					conversation.getProperty("senderId").toString(),
					(Date) conversation.getProperty("sendDate")));
		}

		return new Conversation(users.get(0), users.get(1), messages);
	}

	public String insertConversationGroup(String creatorId, String chatName) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();

		Entity conversationGroup = new Entity("CONVERSATION_GROUP_CREATE");
		conversationGroup.setProperty("creatorId", creatorId);
		conversationGroup.setProperty("chatName", chatName);
		dataStore.put(conversationGroup);
		return conversationGroup.getKey().toString();
	}

	public String createConversaionGroup(String creatorId, String chatName,
			ArrayList<String> membersId) {
		String groupId = insertConversationGroup(creatorId, chatName);
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Entity conversationGroupFriends = new Entity(
				"CONVERSATION_GROUP_FRIENDS");
		conversationGroupFriends.setProperty("groupId", groupId);
		NotificationController notificationController = new NotificationController() ;
		for (int i = 0; i < membersId.size(); i++) {
			conversationGroupFriends.setProperty("memberId", membersId.get(i));
			conversationGroupFriends.setProperty("chatSeen", false);
			notificationController.putNotification(membersId.get(i), groupId, "NotificationMessageGroupCommand");
			dataStore.put(conversationGroupFriends);
		}
		return groupId;
	}

	public ArrayList<Pair<String,Boolean>> getUsersOfGroup(String groupId) {
		ArrayList<Pair<String, Boolean>> users = new ArrayList<Pair<String , Boolean>>();
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Filter filterGroupId = new FilterPredicate("grouoId",
				FilterOperator.EQUAL, groupId);
		Query usersId = new Query("CONVERSATION_GROUP_FRIENDS")
				.setFilter(filterGroupId);
		PreparedQuery pq = dataStore.prepare(usersId);
		for (Entity user : pq.asIterable()) {
			users.add(new Pair<String, Boolean>(user.getProperty("memberId").toString(), (Boolean)user.getProperty("chatSeen")));
		}
		return users;
	}

	public Date insertMessageConversationGroup(String groupId, String senderId,
			String message) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Entity conversationEntity = new Entity("CONVERSATION_GROUP_REPLY");
		conversationEntity.setProperty("groupId", groupId);
		conversationEntity.setProperty("senderId", senderId);
		conversationEntity.setProperty("message", message);
		Date date = new Date() ;
		conversationEntity.setProperty("sendDate", date);
		dataStore.put(conversationEntity);
		return date;
	}

	public void setChatSeenFalse(String groupId, String senderId) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Filter filterGroup = new FilterPredicate("groupId",
				FilterOperator.EQUAL, groupId);
		Query groupNotificationAndChat = new Query("CONVERSATION_GROUP_FRIENDS")
				.setFilter(filterGroup);
		PreparedQuery pq = dataStore.prepare(groupNotificationAndChat);
		for (Entity notification : pq.asIterable()) {
			if (senderId
					.equals(notification.getProperty("memberId").toString())) {
				// notification.setProperty("notificationSeen", false);
				notification.setProperty("chatSeen", false);
			} else {
				// notification.setProperty("notificationSeen", true);
				notification.setProperty("chatSeen", true);
			}
			dataStore.put(notification);
		}
	}

	public Date sendMessageConversationGroup(String groupId, String senderId,
			String message) {
		Date messageDate = insertMessageConversationGroup(groupId, senderId,
				message);
		setChatSeenFalse(groupId, senderId);
		ArrayList<Pair<String , Boolean>> usersGroup = getUsersOfGroup(groupId);
		NotificationController notificationController = new NotificationController();
		for (int i = 0; i < usersGroup.size(); i++) {
			notificationController.putNotification(usersGroup.get(i).first, groupId,
					"NotificationMessageGroupCommand");
		}
		return messageDate;
	}

	// public void seenNotificationChat(long userId, long groupId) {
	// DatastoreService dataStore = DatastoreServiceFactory
	// .getDatastoreService();
	// Filter filterUserId = new FilterPredicate("memberId",
	// FilterOperator.EQUAL, userId);
	// Filter filterGroupId = new FilterPredicate("groupId",
	// FilterOperator.EQUAL, groupId);
	// Filter userAndGroup = CompositeFilterOperator.and(filterUserId,
	// filterGroupId);
	// Query conversationGroupFriends = new Query("CONVERSATION_GROUP_FRIENDS")
	// .setFilter(userAndGroup);
	// PreparedQuery pq = dataStore.prepare(conversationGroupFriends);
	// Entity e = pq.asSingleEntity();
	// if (e != null) {
	// e.setProperty("notificationSeen", true);
	// dataStore.put(e);
	// }
	// }

	public void seenChat(String userId, String groupId) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Filter filterUserId = new FilterPredicate("memberId",
				FilterOperator.EQUAL, userId);
		Filter filterGroupId = new FilterPredicate("groupId",
				FilterOperator.EQUAL, groupId);
		Filter userAndGroup = CompositeFilterOperator.and(filterUserId,
				filterGroupId);
		Query conversationGroupFriends = new Query("CONVERSATION_GROUP_FRIENDS")
				.setFilter(userAndGroup);
		PreparedQuery pq = dataStore.prepare(conversationGroupFriends);
		Entity e = pq.asSingleEntity();
		if (e != null) {
			e.setProperty("chatSeen", true);
			dataStore.put(e);
		}
	}

	public ArrayList<Message> groupMessages(String groupId) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Filter filterGroup = new FilterPredicate("groupId",
				FilterOperator.EQUAL, groupId);
		Query message = new Query("CONVERSATION_GROUP_REPLY")
				.setFilter(filterGroup);
		PreparedQuery pq = dataStore.prepare(message);
		ArrayList<Message> messages = new ArrayList<Message>();
		for (Entity m : pq.asIterable()) {
			messages.add(new Message(m.getKey().toString(), String.valueOf(m
					.getProperty("message")), String.valueOf(m
					.getProperty("senderId")), (Date) m.getProperty("sendDate")));
		}
		return messages;
	}

	public MessageGroup getConvesationGroup(String groupId) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
//		Filter filteGroup = new FilterPredicate("ID", FilterOperator.EQUAL,
//				groupId);
//		Query q = new Query("CONVERSATION_GROUP_CREATE").setFilter(filteGroup);
//		PreparedQuery pq = dataStore.prepare(q);
		Entity e;
		try {
			e = dataStore.get(KeyFactory.createKey("CONVERSATION_GROUP_CREATE", Long.parseLong(groupId)));
			return new MessageGroup(groupId, String.valueOf(e
					.getProperty("chatName")), String.valueOf(e
					.getProperty("creatorId")), groupMessages(groupId),
					getUsersOfGroup(groupId));
		} catch (NumberFormatException | EntityNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
     
		return null ;
		
	}

}
