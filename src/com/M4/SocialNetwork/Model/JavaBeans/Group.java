package com.m4.socialnetwork.model.javabeans;

import java.util.ArrayList;

public class Group {
	private String Id;
	private String Name;
	private ArrayList<String> groupMembers;
	private String adminId;

	public Group(String id, String name, ArrayList<String> groupMembers,
			String adminId) {
		super();
		Id = id;
		Name = name;
		this.groupMembers = groupMembers;
		this.adminId = adminId;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public ArrayList<String> getGroupMembers() {
		return groupMembers;
	}

	public void setGroupMembers(ArrayList<String> groupMembers) {
		this.groupMembers = groupMembers;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

}
