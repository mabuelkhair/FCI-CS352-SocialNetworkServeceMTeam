package com.m4.socialnetwork.model.javabeans;

import java.io.BufferedReader;
import java.io.StringReader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Public extends Privacy {
	public Public(String postId) {
		super(postId);
		type = "Public";
	}

	/*
	 * @Override public void insertUsers(String postId) {}
	 */

	@Override
	public boolean isAllowed(String userId) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void notifyUsers() {
		// no thing to do
	}

	@Override
	public Public createPrivacy(String privacy) {
		Gson gson = new Gson();
		BufferedReader br = new BufferedReader(new StringReader(privacy));
		Public privacyObject = gson.fromJson(br, new TypeToken<Public>() {
		}.getType());
		return privacyObject;
	}

}
