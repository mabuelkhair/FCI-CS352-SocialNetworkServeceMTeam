package com.m4.socialnetwork.model.javabeans;

import java.util.ArrayList;

public class Page {
	private String id;
	private String name;
	private ArrayList<String> pageLikers;
	private String creatorId;

	public Page(String id, String name, ArrayList<String> pageLikers,
			String creatorId) {
		super();
		this.id = id;
		this.name = name;
		this.pageLikers = pageLikers;
		this.creatorId = creatorId;
	}
	

	public Page(String name, String creatorId) {
		super();
		this.name = name;
		this.creatorId = creatorId;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getPageLikers() {
		return pageLikers;
	}

	public void setPageLikers(ArrayList<String> pageLikers) {
		this.pageLikers = pageLikers;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

}
