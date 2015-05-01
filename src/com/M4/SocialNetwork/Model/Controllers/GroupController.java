package com.m4.socialnetwork.model.controllers;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.m4.socialnetwork.model.javabeans.Group;

public class GroupController { 
	 public void addMemberToGroup(String groupId , String memberId){ 
		   DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService() ;
		   Entity groupMembetEntity = new Entity("GROUP_MEMBER") ;
		   groupMembetEntity.setProperty("groupId", groupId);
		   groupMembetEntity.setProperty("memberId", memberId);
		   dataStore.put(groupMembetEntity) ;
	 }
	public Group createGroup(Group group){
		DatastoreService  dataStore = DatastoreServiceFactory.getDatastoreService() ; 
		Entity groupEntity = new Entity("GROUP") ;
		groupEntity.setProperty("adminId", group.getAdminId());
		groupEntity.setProperty("groupName", group.getName());
		dataStore.put(groupEntity) ;
		group.setId(String.valueOf(groupEntity.getKey().getId()));
		for(int i=0 ; i<group.getGroupMembers().size() ; i++){
			 addMemberToGroup(group.getId(), group.getGroupMembers().get(i));
		}
		return group ;
	}
	

}
