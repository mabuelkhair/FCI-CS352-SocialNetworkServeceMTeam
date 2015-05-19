package com.m4.socialnetwork.services;

import junit.framework.Assert;

import org.testng.annotations.Test;

public class FriendTest {
Friend a=new Friend();
  @Test
  public void addFriend() {
	  Assert.assertEquals("OK", a.addFriend("m","m", "a"));
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void confirmFriend() {
	  Assert.assertEquals("OK", a.confirmFriend("a", "a", "m"));
	      
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void getUserFriends() {
	  Assert.assertEquals("m", a.getUserFriends("m"));
	    
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void searchFriend() {
	  Assert.assertEquals("Exist", a.searchFriend("m"));
	  
    throw new RuntimeException("Test not implemented");
  }
}
