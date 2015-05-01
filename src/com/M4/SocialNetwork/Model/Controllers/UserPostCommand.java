package com.m4.socialnetwork.model.controllers;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.m4.socialnetwork.model.javabeans.Post;
import com.m4.socialnetwork.model.javabeans.UserPost;

public class UserPostCommand extends PostCommand {

	@Override
	public UserPost getPost(String postId) {
		Post basePost = getbasePost(postId);
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Filter postIdFilter = new FilterPredicate("postId",
				FilterOperator.EQUAL, postId);
		Query userPostQuery = new Query("USER_POST").setFilter(postIdFilter);
		PreparedQuery pq = dataStore.prepare(userPostQuery);
		Entity userPostEntity = pq.asSingleEntity();
		UserPost post = new UserPost.UserPostBuilder()
				.setPost(basePost)
				.setReceiverId(
						String.valueOf(userPostEntity.getProperty("receiverId")))
				.build();

		return post;
	}

}
