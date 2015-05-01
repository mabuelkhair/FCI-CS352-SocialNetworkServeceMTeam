package com.m4.socialnetwork.model.controllers;

import java.util.ArrayList;
import java.util.List;

import com.m4.socialnetwork.model.javabeans.*;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class UserController {

	public Boolean insertUser(User user) {
		if (getUser(user.getEmail())!=null) {
			return false;
		}

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Entity employee = new Entity("USER");
		employee.setProperty("userName", user.getUserName());
		employee.setProperty("email", user.getEmail());
		employee.setProperty("password", user.getPassword());
		employee.setProperty("active", false);
		datastore.put(employee);
		user.setId(String.valueOf(employee.getKey().getId()));
		return true;
	}

	public User getUser(String email, String password) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Filter filterName = new FilterPredicate("email", FilterOperator.EQUAL,
				email);
		Filter filterPass = new FilterPredicate("password",
				FilterOperator.EQUAL, password);
		Filter NameAndPass = CompositeFilterOperator
				.and(filterName, filterPass);
		Query q = new Query("USER").setFilter(NameAndPass);
		PreparedQuery ps = datastore.prepare(q);
		Entity result = ps.asSingleEntity();

		if (result != null) {
			result.setProperty("active", true);
			datastore.put(result);
			return new User(String.valueOf(result.getKey().getId()), result.getProperty(
					"userName").toString(), result.getProperty("email")
					.toString(), result.getProperty("password").toString() , (Boolean)result.getProperty("active"));
		}

		return null;
	}

	public User getUser(String email) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Filter filterName = new FilterPredicate("email", FilterOperator.EQUAL,
				email);
		Query q = new Query("USER").setFilter(filterName);
		PreparedQuery ps = datastore.prepare(q);
		Entity result = ps.asSingleEntity();
		if (result != null) {
			return new User(String.valueOf(result.getKey().getId()), result.getProperty(
					"userName").toString(), result.getProperty("email")
					.toString(), "Private",
					(Boolean) result.getProperty("active"));
		}
		return null;
	}
	
	public User getUserById(String userId){
     DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService() ;
     Key k = KeyFactory.createKey("USER", Long.parseLong(userId)) ;
    Entity e;
	try {
		e = dataStore.get(k);
		 if(e!=null){
			   	return new User(userId, e.getProperty("userName").toString(), e.getProperty("email").toString(), "private", (Boolean)true);
			   }
	} catch (EntityNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
  
		
		return null; 
	}

	public boolean requestExistBefore(String senderEmail, String receptionEmail) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Filter filterSender = new FilterPredicate("senderEmail",
				FilterOperator.EQUAL, senderEmail);
		Filter filterReception = new FilterPredicate("receptionEmail",
				FilterOperator.EQUAL, receptionEmail);
		Filter senderAndReception = CompositeFilterOperator.and(filterSender,
				filterSender);
		Query q = new Query("FRIEND").setFilter(senderAndReception);
		PreparedQuery pq = dataStore.prepare(q);
		Entity e = pq.asSingleEntity();
		if (e != null) {
			System.out.println("Mawgoda");
			return true;
		}
		return false;
	}

	public boolean sendFriedRequest(String senderEmail, String receptionEmail) {
             User senderExist = getUser(senderEmail) ;
             User receptionExist = getUser(receptionEmail) ;
		if ( senderExist!=null&& receptionExist!=null
				&& !requestExistBefore(senderEmail, receptionEmail)) {
			DatastoreService datastoreService = DatastoreServiceFactory
					.getDatastoreService();
			Entity friendRequest = new Entity("FRIEND");
			friendRequest.setProperty("senderEmail", senderEmail);
			friendRequest.setProperty("receptionEmail", receptionEmail);
			friendRequest.setProperty("confirm", false);
			datastoreService.put(friendRequest);
			// for simplicity 
			new NotificationController().putNotification(receptionExist.getId(), String.valueOf(friendRequest.getKey().getId()), "NotificationSendFriendRequestCommand");
			return true;
		}

		return false;
	}

	public boolean acceptFriendRequest(String senderEmail, String receptionEmail) {
		User senderExist = getUser(senderEmail) ;
        User receptionExist = getUser(receptionEmail) ;
		if (senderExist !=null && receptionExist!=null
				&& !senderEmail.equals(receptionEmail)) {
			DatastoreService datastoreService = DatastoreServiceFactory
					.getDatastoreService();
			Filter senderFilter = new FilterPredicate("senderEmail",
					FilterOperator.EQUAL, senderEmail);
			Filter receptionFilter = new FilterPredicate("receptionEmail",
					FilterOperator.EQUAL, receptionEmail);
			Filter senderAndReception = CompositeFilterOperator.and(
					senderFilter, receptionFilter);
			Query q = new Query("FRIEND").setFilter(senderAndReception);
			PreparedQuery pq = datastoreService.prepare(q);

			Entity result = pq.asSingleEntity();
			if (result != null) {
				result.setProperty("confirm", true);
				datastoreService.put(result);
				new NotificationController().putNotification(senderExist.getId(), String.valueOf(result.getKey().getId()), "NotificationAcceptFriendRequestCommand");
				return true;
			}
		}
		return false;
	}

	public void logout(String email) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Filter filterEmail = new FilterPredicate("email", FilterOperator.EQUAL,
				email);
		Query q = new Query("USER").setFilter(filterEmail);
		PreparedQuery pq = dataStore.prepare(q);
		Entity userEmail = pq.asSingleEntity();
		userEmail.setProperty("active", false);
		dataStore.put(userEmail);
	}
	
	
	public ArrayList<User> getUserFriends(String userEmail , String kind , String filter){
		ArrayList<User> users = new ArrayList<User>() ;
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService() ;
		Filter userFilter = new FilterPredicate(kind, FilterOperator.EQUAL, userEmail);
		Filter userConfirm = new FilterPredicate("confirm", FilterOperator.EQUAL, "1") ;
		Filter userAndConfirm = CompositeFilterOperator.and(userFilter , userConfirm) ;
		Query q = new Query("FRIEND").setFilter(userAndConfirm);
		PreparedQuery pq = dataStore.prepare(q) ;
		for(Entity e : pq.asIterable()){
			users.add(getUser(e.getProperty(filter).toString()));
		}
		return users ;
	}
	public ArrayList<User> getAllUserFriends(String userEmail){
		ArrayList<User> users = getUserFriends(userEmail, "senderEmail" , "receptionEmail") ;
		 users.addAll(getUserFriends(userEmail, "receptionEmail", "senderEmail" )) ;
		return users ;
	}

}
