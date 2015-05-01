package com.m4.socialnetwork.model.controllers;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.m4.socialnetwork.model.javabeans.Notification;

public abstract class NotificationCommand {
	public void setNotificationSeen(String notificationId){
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService() ;
	try {
		Entity e = 	dataStore.get(KeyFactory.createKey("NOTIFICATION", Long.parseLong(notificationId))) ;
		e.setProperty("seen", true); 
		dataStore.put(e) ;
	} catch (NumberFormatException | EntityNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	public abstract String getNotification(Notification notification) ;

}
