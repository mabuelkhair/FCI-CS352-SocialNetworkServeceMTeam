package com.m4.socialnetwork.model.javabeans;

import java.util.ArrayList;

import com.google.appengine.labs.repackaged.com.google.common.base.Pair;

public class MessageGroup {
	private String groupId;
	private String groupName;
	private String creatorId;
	private ArrayList<Message> messaged;
	private ArrayList<Pair<String, Boolean>> members;

	public MessageGroup(String groupId, String groupName, String creatorId,
			ArrayList<Message> messaged, ArrayList<Pair<String, Boolean>> members) {
		super();
		this.groupId = groupId;
		this.groupName = groupName;
		this.creatorId = creatorId;
		this.messaged = messaged;
		this.members = members;
	}

	/*public MessageGroup(String groupId, String groupName, String creatorId,
			ArrayList<Message> messaged, ArrayList<String> members) {
		super();
		this.groupId = groupId;
		this.groupName = groupName;
		this.creatorId = creatorId;
		this.messaged = messaged;
		this.members = members;
	}*/

	public ArrayList<Pair<String, Boolean>> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<Pair<String, Boolean>> members) {
		this.members = members;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public ArrayList<Message> getMessaged() {
		return messaged;
	}

	public void setMessaged(ArrayList<Message> messaged) {
		this.messaged = messaged;
	}

	
}
