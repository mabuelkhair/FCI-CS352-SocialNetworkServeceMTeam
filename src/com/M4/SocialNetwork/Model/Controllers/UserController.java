package com.M4.SocialNetwork.Model.Controllers;

import java.util.List;

import com.M4.SocialNetwork.Model.JavaBeans.*;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class UserController {

	public Boolean insertUser(User user) {
		if (emailExist(user.getEmail())) {
			return false;
		}

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Entity employee = new Entity("USER");
		employee.setProperty("userName", user.getUserName());
		employee.setProperty("email", user.getEmail());
		employee.setProperty("password", user.getPassword());
		datastore.put(employee);
		user.setId(employee.getKey().toString());
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
			return new User(result.getKey().toString(), result.getProperty(
					"userName").toString(), result.getProperty("email")
					.toString(), result.getProperty("password").toString());
		}

		return null;
	}

	public boolean emailExist(String email) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Filter filterName = new FilterPredicate("email", FilterOperator.EQUAL,
				email);
		Query q = new Query("USER").setFilter(filterName);
		PreparedQuery ps = datastore.prepare(q);
		Entity result = ps.asSingleEntity();

		if (result != null) {
			return true;
		}

		return false;
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

		if (emailExist(senderEmail) && emailExist(receptionEmail)
				&& !senderEmail.equals(receptionEmail)
				&& !requestExistBefore(senderEmail, receptionEmail)) {
			DatastoreService datastoreService = DatastoreServiceFactory
					.getDatastoreService();
			Entity friendRequest = new Entity("FRIEND");
			friendRequest.setProperty("senderEmail", senderEmail);
			friendRequest.setProperty("receptionEmail", receptionEmail);
			friendRequest.setProperty("confirm", "0");
			datastoreService.put(friendRequest);
			return true;
		}

		return false;
	}

	public boolean acceptFriendRequest(String senderEmail,
			String receptionEmail) {

		if (emailExist(senderEmail) && emailExist(receptionEmail)
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
			PreparedQuery pq = datastoreService.prepare(q) ;
			
			Entity result = pq.asSingleEntity();
			if(result!=null){
				result.setProperty("confirm", "1");
				datastoreService.put(result) ;
				return true ;
			}
			

		}

		return false;
	}

}
