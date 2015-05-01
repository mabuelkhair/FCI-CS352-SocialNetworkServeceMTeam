package com.m4.socialnetwork.model.javabeans;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.m4.socialnetwork.model.controllers.UserController;

public class Private extends Privacy {
	public Private(String postId) {
		super(postId);
		type = "Private";
	}

	@Override
	public boolean isAllowed(String userId) {
		ArrayList<User> users = new UserController()
				.getAllUserFriends(new UserController().getUserById(userId)
						.getEmail());
		for (int i = 0; i < users.size(); i++) {
			if (userId.equals(users.get(i).getId())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void notifyUsers() {
		// No thing to Do
	}

	@Override
	public Private createPrivacy(String privacy) {
		Gson  gson = new Gson() ;
		BufferedReader br = new BufferedReader(new StringReader(privacy)) ;
	    Private privacyObject = gson.fromJson(br, new TypeToken<Private>(){}.getType()) ;
		return privacyObject ;
	}

	/*
	 * @Override public void insertUsers(String postId) {}
	 */

}
