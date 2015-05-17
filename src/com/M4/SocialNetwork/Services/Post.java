package com.m4.socialnetwork.services;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

import javax.crypto.spec.PSource;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.search.query.QueryParser.comparable_return;
import com.google.appengine.labs.repackaged.com.google.common.base.Pair;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.m4.socialnetwork.model.controllers.PostController;
import com.m4.socialnetwork.model.javabeans.PagePost;
import com.m4.socialnetwork.model.javabeans.PagePost.PagePostBuilder;
import com.m4.socialnetwork.model.javabeans.Privacy;
import com.m4.socialnetwork.model.javabeans.UserPost;

@Path("/PostService")
@Produces("text/html")
public class Post {
	@POST
	@Path("/CreatePagePost")
	public String createPagePost(@FormParam("pageId") String pageId,
			@FormParam("creatorId") String creatorId,
			@FormParam("postContent") String postContent,
			@FormParam("privacy") String privacy) {
		Gson gson = new Gson();
		BufferedReader br;
		br = new BufferedReader(new StringReader(privacy));
		Pair<String, String> privacyPair = gson.fromJson(br,
				new TypeToken<Pair<String, String>>() {
				}.getType());

		try {
			Privacy privacyObject = ((Privacy) Class.forName(
					"com.m4.socialnetwork.model.controllers."
							+ privacyPair.first).newInstance())
					.createPrivacy(privacyPair.second);
			PagePost pagePost = ((PagePostBuilder) new PagePost.PagePostBuilder()
					.setCreatorId(creatorId).setContent(postContent))
					.setPageId(pageId).build();
			pagePost = new PostController().createPagePost(pagePost);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("pagePost", gson.toJson(pagePost));
			return jsonObject.toString();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@POST
	@Path("/CreateUserPost")
	public String createUserPost(@FormParam("receiverId") String receiverId,
			@FormParam("creatorId") String creatorId,
			@FormParam("postContent") String postContent,
			@FormParam("privacy") String privacy) {
		Gson gson = new Gson();
		BufferedReader br;
		br = new BufferedReader(new StringReader(privacy));
		Pair<String, String> privacyPair = gson.fromJson(br,
				new TypeToken<Pair<String, String>>() {
				}.getType());
		try {
			Privacy privacyObject = ((Privacy) Class.forName(
					"com.m4.socialnetwork.model.controllers."
							+ privacyPair.first).newInstance())
					.createPrivacy(privacyPair.second);
			UserPost userPost = (UserPost) (new UserPost.UserPostBuilder()
					.setReceiverId(receiverId).setCreatorId(creatorId)
					.setContent(postContent).setPrivacy(privacyObject).build());
			userPost = new PostController().createUserPost(userPost);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("userPost", gson.toJson(userPost));
			return jsonObject.toString();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@POST
	@Path("/GetUserPosts")
	public String getUserPosts(@FormParam("userId") String userId) {
		ArrayList<com.m4.socialnetwork.model.javabeans.Post> posts = new PostController()
				.getUserTimline(userId);
		JSONObject jsonObject = new JSONObject();
		Gson gson = new Gson();
		try {
			jsonObject.put("timeline", gson.toJson(posts));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@POST
	@Path("/LikePost")
	public void likePost(@FormParam("userId") String userId,
			@FormParam("postId") String postId) {
		new PostController().likePost(postId, userId);
	}

	@POST
	@Path("/SearchPostsByHashTag")
	public String SearchPostsByHashTag(@FormParam("hashTag") String hashTag) {
		ArrayList<com.m4.socialnetwork.model.javabeans.Post> posts = new PostController()
				.searchPostsByHashTag(hashTag);
		JSONObject jsonObject = new JSONObject();
		Gson gson = new Gson();
		try {
			jsonObject.put("hashTag", gson.toJson(posts));
			return jsonObject.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
