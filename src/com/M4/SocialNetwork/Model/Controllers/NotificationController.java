package com.m4.socialnetwork.model.controllers;

import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;
import com.m4.socialnetwork.model.javabeans.Notification;
import com.m4.socialnetwork.model.controllers.*;

public class NotificationController {

	public void putNotification(String recieverId, String componentId,
			String notificationType) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Entity notification = new Entity("NOTIFICATION");
		notification.setProperty("componentId", componentId);
		notification.setProperty("recieverId", recieverId);
		notification.setProperty("notificationType", notificationType);
		notification.setProperty("seen", false);
		dataStore.put(notification);
	}

	public ArrayList<Notification> getNotifications(String recieverId) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Filter filterRecieverId = new FilterPredicate("recieverId",
				FilterOperator.EQUAL, recieverId);
		Filter filterSeen = new FilterPredicate("seen", FilterOperator.EQUAL,
				false);
		Filter recieverAndSeen = CompositeFilterOperator.and(filterRecieverId,
				filterSeen);
		Query q = new Query("NOTIFICATION").setFilter(recieverAndSeen);
		PreparedQuery pq = dataStore.prepare(q);
		ArrayList<Notification> receiverNotification = new ArrayList<Notification>();
		for (Entity notifiction : pq.asIterable()) {
			receiverNotification.add(new Notification(notifiction.getProperty(
					"notificationType").toString(), notifiction.getProperty(
					"recieverId").toString(), String.valueOf(notifiction
					.getKey().getId()), notifiction.getProperty("componentId").toString()));
		}
		// JSONObject jsonObject = new JSONObject() ;
		// Gson gson = new Gson() ;
		//
		// try {
		// jsonObject.put("receiverNotification",
		// gson.toJson(receiverNotification)) ;
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		return receiverNotification;
	}

	public String getNotification(Notification notification) {

		try {
			NotificationCommand command = (NotificationCommand) Class.forName("com.m4.socialnetwork.model.controllers."+
					notification.getNotificationType()).newInstance();
			return command.getNotification(notification);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean notificationBelongToUser(String notficationId ,String userId){
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService() ;
		try {
			Key k = KeyFactory.createKey("NOTIFICATION", Long.parseLong(notficationId)) ;
			Entity e =dataStore.get(k) ;
		   if(e.getProperty("recieverId").toString().equals(userId)){
				 return true ;
			}
		} catch (NumberFormatException | EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false ;
	}
}
