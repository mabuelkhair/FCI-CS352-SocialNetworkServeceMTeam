package com.m4.socialnetwork.model.controllers;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.labs.repackaged.com.google.common.base.Pair;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;
import com.m4.socialnetwork.model.javabeans.FriendRequest;
import com.m4.socialnetwork.model.javabeans.Notification;
import com.m4.socialnetwork.model.javabeans.User;

public class NotificationSendFriendRequestCommand extends NotificationCommand{

	@Override
	public String getNotification(Notification notification) {
		 DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService() ;
		 try {
			Entity e = dataStore.get(KeyFactory.createKey("FRIEND", Long.parseLong(notification.getComponentId())));
			FriendRequest friendRequest = new FriendRequest(e.getProperty("senderEmail").toString(), e.getProperty("receptionEmail").toString(), (Boolean)e.getProperty("confirm"));
			Pair<String, FriendRequest> NotificationString = new Pair<String, FriendRequest>("SendFriendRequest" , friendRequest) ;
			JSONObject jsonObject = new JSONObject() ;
			Gson gson = new Gson() ;
			
			try {
				jsonObject.put("notification", gson.toJson(NotificationString)) ;
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			setNotificationSeen(notification.getNotificationId());
			return jsonObject.toString() ;
		
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	
	

}
