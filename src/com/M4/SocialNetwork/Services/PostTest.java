package com.m4.socialnetwork.services;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

import junit.framework.Assert;

import org.json.JSONObject;
import org.testng.annotations.Test;

import com.google.appengine.labs.repackaged.com.google.common.base.Pair;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.m4.socialnetwork.model.controllers.PostController;
import com.m4.socialnetwork.model.javabeans.PagePost;
import com.m4.socialnetwork.model.javabeans.Privacy;
import com.m4.socialnetwork.model.javabeans.UserPost;
import com.m4.socialnetwork.model.javabeans.PagePost.PagePostBuilder;

public class PostTest {
Post a=new Post();
  @Test
  public void SearchPostsByHashTag() {
	  ArrayList<com.m4.socialnetwork.model.javabeans.Post> posts = new PostController()
		.searchPostsByHashTag("#Egypt");
Gson gson = new Gson();
	  Assert.assertEquals(gson.toJson(posts),a.SearchPostsByHashTag("#Egypt"));
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void createPagePost() {
	  Gson gson = new Gson();
	  PagePost pagePost = ((PagePostBuilder) new PagePost.PagePostBuilder()
		.setCreatorId("").setContent("Welcome"))
		.setPageId("").build();
pagePost = new PostController().createPagePost(pagePost);
    Assert.assertEquals(gson.toJson(pagePost),a.createPagePost("", "", "Welcome", ""));
	  throw new RuntimeException("Test not implemented");
  }

  @Test
  public void createUserPost() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
	  Gson gson = new Gson();
		BufferedReader br;
		br = new BufferedReader(new StringReader(""));
		
	  Pair<String, String> privacyPair = gson.fromJson(br,
			new TypeToken<Pair<String, String>>() {
			}.getType());
	  Privacy privacyObject = ((Privacy) Class.forName(
				"com.m4.socialnetwork.model.controllers."
						+ privacyPair.first).newInstance())
				.createPrivacy(privacyPair.second);
	  UserPost userPost = (UserPost) (new UserPost.UserPostBuilder()
		.setReceiverId("").setCreatorId("")
		.setContent("Welcome Friends").setPrivacy(privacyObject).build());
userPost = new PostController().createUserPost(userPost);
	  Assert.assertEquals(gson.toJson(userPost),a.createUserPost("", "", "Welcome", ""));
		
	  throw new RuntimeException("Test not implemented");
  }

  @Test
  public void getUserPosts() {
	  ArrayList<com.m4.socialnetwork.model.javabeans.Post> posts = new PostController()
		.getUserTimline("");
Gson gson = new Gson();
	  Assert.assertEquals( gson.toJson(posts),a.getUserPosts(""));
		
	  throw new RuntimeException("Test not implemented");
  }

  @Test
  public void likePost() {
		
	  throw new RuntimeException("Test not implemented");
  }
}
