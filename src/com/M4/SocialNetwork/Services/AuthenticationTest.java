package com.m4.socialnetwork.services;

import junit.framework.Assert;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;   
import org.testng.annotations.Test;   
import org.testng.annotations.AfterTest;
public class AuthenticationTest {
Authentication a =new Authentication();
  @Test
  public void login() {
	  Assert.assertEquals("m", a.login("m", "m"));
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void registration() {
	  Assert.assertEquals("a", a.registration("a", "a", "a"));
	    
	  throw new RuntimeException("Test not implemented");
  }
}
