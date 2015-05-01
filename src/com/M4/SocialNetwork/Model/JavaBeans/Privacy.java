package com.m4.socialnetwork.model.javabeans;

public abstract class Privacy {
	protected String type;
	protected String postId;

	// public abstract void insertUsers(String postId) ;
	public Privacy(String _postId) {
		postId = _postId;
	}

	public abstract void notifyUsers();
	public abstract Privacy createPrivacy(String privacy) ;

	public abstract boolean isAllowed(String userId);

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

}
