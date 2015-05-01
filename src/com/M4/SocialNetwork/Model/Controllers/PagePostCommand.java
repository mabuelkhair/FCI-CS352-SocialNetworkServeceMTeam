package com.m4.socialnetwork.model.controllers;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.m4.socialnetwork.model.javabeans.PagePost;
import com.m4.socialnetwork.model.javabeans.Post;

public class PagePostCommand extends PostCommand {
	@Override
	public PagePost getPost(String postId) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Post basePost = getbasePost(postId);
		Filter postIdFilter = new FilterPredicate("postId",
				FilterOperator.EQUAL, postId);
		Query pageQuery = new Query("PAGE_POST").setFilter(postIdFilter);
		PreparedQuery pq = dataStore.prepare(pageQuery);
		Entity pagePostEntity = pq.asSingleEntity();
		PagePost pagePost = new PagePost.PagePostBuilder().setPost(basePost)
				.setPageId(pagePostEntity.getProperty("pageId").toString())
				.build();
		return pagePost ;
	}

}
