package com.m4.socialnetwork.services;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.m4.socialnetwork.model.controllers.PageController;

@Path("/PageService")
@Produces("text/html")
public class Page {
	@POST
	@Path("/createPage")
	public String createPage(@FormParam("creatorId") String creatorId,
			@FormParam("pageName") String pageName) {
		com.m4.socialnetwork.model.javabeans.Page page = new com.m4.socialnetwork.model.javabeans.Page(
				pageName, creatorId);
		page = new PageController().createPage(page);
		JSONObject jsonObject = new JSONObject();
		Gson gson = new Gson();

		try {
			jsonObject.put("page", gson.toJson(page));
			return jsonObject.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@POST
	@Path("/LikePage")
	public void likePage(@FormParam("pageId") String pageId,
			@FormParam("userId") String userId) {
		new PageController().likePage(pageId, userId);
	}
}
