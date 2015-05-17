package com.m4.socialnetwork.model.controllers;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.m4.socialnetwork.model.javabeans.Post;
import com.m4.socialnetwork.model.javabeans.Privacy;

public abstract class PostCommand {
	public Post getbasePost(String postId) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Key postKey = KeyFactory.createKey("POST", Long.parseLong(postId));
		try {
			Entity postEntity = dataStore.get(postKey);
			Post p = new Post.PostBuilder()
					.setContent(postEntity.getProperty("content").toString())
					.setCreatorId(
							postEntity.getProperty("creatorId").toString())
					.setPostId(postEntity.getProperty("postId").toString())
					.setPrivacy((Privacy) postEntity.getProperty("privacy"))
					.setLikers(new PostController().getPostLikes(postId))
					.build();
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public abstract Post getPost(String postId);

}
