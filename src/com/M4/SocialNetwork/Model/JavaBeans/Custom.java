package com.m4.socialnetwork.model.javabeans;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.labs.repackaged.com.google.common.base.Pair;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.m4.socialnetwork.model.controllers.NotificationController;

public class Custom extends Privacy{
	private ArrayList<String> usersId ;
	
	public  Custom(String postId , ArrayList<String> usersId) {
		super(postId) ;
		type = "Custom" ;
		this.usersId = usersId ;
	}

/*	@Override
	public void insertUsers(String postId) {
		// moved to post Controller 
		 DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService() ;
		 for(int i=0 ; i<usersId.size() ; i++){
			 Entity postCustom = new Entity("POST_CUSTOM") ;
			 postCustom.setProperty("postId", postId);
			 postCustom.setProperty("userId", usersId.get(i));
			 dataStore.put(postCustom);
		 }
	}
	*/

	public ArrayList<String> getUsersId() {
		return usersId;
	}

	public void setUsersId(ArrayList<String> usersId) {
		this.usersId = usersId;
	}

	@Override
	public boolean isAllowed(String userId) {
		 for(int i=0 ; i<usersId.size() ; i++){
			  if(userId.equals(usersId.get(i))){
				  return true ;
			  }
		 }
 		return false;
	}

	@Override
	public void notifyUsers() {
     DatastoreService dataStore  =  DatastoreServiceFactory.getDatastoreService() ;
     NotificationController notificationController= new NotificationController() ;
     for(int i=0 ; i<usersId.size() ; i++){
    	   notificationController.putNotification(usersId.get(i), postId, "NotificationPostCommand");
     }
	}

	@Override
	public Custom createPrivacy(String privacy) {
		Gson  gson = new Gson() ;
  BufferedReader br = new BufferedReader(new StringReader(privacy)) ;
  Custom privacyObject = gson.fromJson(br, new TypeToken<Custom>(){}.getType()) ;
  
		return privacyObject;
	}
	

}
