package com.m4.socialnetwork.model.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.jsp.ah.datastoreViewerFinal_jsp;

import com.google.api.server.spi.response.ForbiddenException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.m4.socialnetwork.model.javabeans.PagePost;
import com.m4.socialnetwork.model.javabeans.Post;
import com.m4.socialnetwork.model.javabeans.Privacy;
import com.m4.socialnetwork.model.javabeans.UserPost;
import com.m4.socialnetwork.model.javabeans.UserPost.UserPostBuilder;

public class PostController {
	public void likePost(String postId, String userId) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Entity postLikerEntity = new Entity("POST_LIKER");
		postLikerEntity.setProperty("postId", postId);
		postLikerEntity.setProperty("userId", userId);
		postLikerEntity.setProperty("deleted", false);
		dataStore.put(postLikerEntity);
	}

	public void unLikePost(String postId, String userId) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Filter postIdFilter = new FilterPredicate("postId",
				FilterOperator.EQUAL, postId);
		Filter userIdFilter = new FilterPredicate("userId",
				FilterOperator.EQUAL, userId);
		Filter postIdAndUserIdFilter = CompositeFilterOperator.and(
				postIdFilter, userIdFilter);
		Query postLikers = new Query("POST_LIKER")
				.setFilter(postIdAndUserIdFilter);
		PreparedQuery pq = dataStore.prepare(postLikers);
		Entity postLikeEntity = pq.asSingleEntity();
		if (postLikeEntity != null) {
			postLikeEntity.setProperty("deleted", true);
			dataStore.put(postLikeEntity);
		}
	}

	private void createPost(Post post, String postType) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Entity postEntity = new Entity("POST");
		postEntity.setProperty("creatorId", post.getCreatorId());
		postEntity.setProperty("privacy", post.getPrivacy());
		postEntity.setProperty("numOfLikers", post.getNumOfLikers());
		postEntity.setProperty("content", post.getContent());
		postEntity.setProperty("postType", postType);
		dataStore.put(postEntity);
		post.setPostId(String.valueOf(postEntity.getKey().getId()));
		post.getPrivacy().notifyUsers();
	}

	public PagePost createPagePost(PagePost pagePost) {
		createPost(pagePost, "PagePostCommand");
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Entity pagePostEntity = new Entity("PAGE_POST");
		pagePostEntity.setProperty("postId", pagePost.getPostId());
		pagePostEntity.setProperty("pageId", pagePost.getPageId());
		dataStore.put(pagePostEntity);
		return pagePost;
	}

	public UserPost createUserPost(UserPost post) {
		createPost(post, "UserPostCommand");
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Entity userPostEntity = new Entity("USER_POST");
		userPostEntity.setProperty("receiverId", post.getReceiverId());
		userPostEntity.setProperty("postId", post.getPostId());
		dataStore.put(userPostEntity);
		return post;
	}

	public Post getPost(String postId) {
		DatastoreService DataStore = DatastoreServiceFactory
				.getDatastoreService();
		Key postKey = KeyFactory.createKey("POST", postId);
		try {
			Entity postEntity = DataStore.get(postKey);
			Post post = ((PostCommand) Class.forName(
					"com.m4.socialnetwork.model.controllers."
							+ postEntity.getProperty("postType").toString())
					.newInstance()).getPost(postId);
			return post;
		} catch (EntityNotFoundException | InstantiationException
				| IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<Post> getPagePosts(String pageId) {
		ArrayList<Post> pagePosts = new ArrayList<Post>();
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Filter postFilter = new FilterPredicate("pageId", FilterOperator.EQUAL,
				pageId);
		Query postQuery = new Query("PAGE_POST").setFilter(postFilter);
		PreparedQuery pq = dataStore.prepare(postQuery);
		for (Entity posts : pq.asIterable()) {
			pagePosts.add(getPost(posts.getProperty("postId").toString()));
		}
		return null;
	}

	public ArrayList<Post> getUserProfilePosts(String userId) {
		ArrayList<Post> posts = new ArrayList<Post>();
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Query postQuery = new Query("POST");
		PreparedQuery pq = dataStore.prepare(postQuery);
		Set<String> postsId = new HashSet<String>();
		for (Entity post : pq.asIterable()) {
			if (post.getProperty("postType").toString()
					.equals("UserPostCommand")) {
				if (post.getProperty("creatorId").toString().equals(userId)) {
					postsId.add(String.valueOf(post.getKey().getId()));
					// posts.add(getPost(String.valueOf(post.getKey().getId())));
				} else if (((Privacy) post.getProperty("privacy"))
						.isAllowed(userId)) {
					// posts.add(getPost(String.valueOf(post.getKey().getId())));
					postsId.add(String.valueOf(post.getKey().getId()));
				}
			}
		}
		Filter postFilter = new FilterPredicate("receiverId",
				FilterOperator.EQUAL, userId);
		Query receiverQuery = new Query("USER_POST").setFilter(postFilter);
		PreparedQuery prepaeQueryReceiver = dataStore.prepare(receiverQuery);
		for (Entity post : prepaeQueryReceiver.asIterable()) {
			postsId.add(post.getProperty("postId").toString());
		}
		Iterator it = postsId.iterator();
		while (it.hasNext()) {
			posts.add(getPost((String) it.next()));
		}

		return posts;
	}
	public ArrayList<Post> getUserTimline(String userId){
		ArrayList<Post> posts = getUserProfilePosts(userId) ;
		ArrayList<String> pagesUserId = new PageController().getPagesOFUser(userId) ;
		for(int i=0 ; i<pagesUserId.size() ; i++){
			   posts.addAll(getPagePosts(pagesUserId.get(i))) ;
		}
		return posts ;
	}
	

}
