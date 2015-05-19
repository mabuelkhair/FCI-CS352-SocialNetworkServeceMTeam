package com.m4.socialnetwork.services;

import junit.framework.Assert;

import org.json.JSONObject;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.m4.socialnetwork.model.controllers.PageController;

public class PageTest {
Page a=new Page();
  @Test
  public void createPage() {
	  com.m4.socialnetwork.model.javabeans.Page page = new com.m4.socialnetwork.model.javabeans.Page(
				"Social Network","");
		page = new PageController().createPage(page);
		Gson gson = new Gson();
	  Assert.assertEquals(gson.toJson(page), a.createPage("", "Social Network"));
		
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void likePage() {
	 //there's no test on a void functions	
	  throw new RuntimeException("Test not implemented");
  }
}
