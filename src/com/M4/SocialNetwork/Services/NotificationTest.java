package com.m4.socialnetwork.services;

import java.util.ArrayList;

import junit.framework.Assert;

import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.m4.socialnetwork.model.controllers.NotificationController;
import com.m4.socialnetwork.model.controllers.UserController;
import com.m4.socialnetwork.model.javabeans.User;

public class NotificationTest {
Notification a =new Notification();
  @Test
  public void getNotification() {
	  NotificationController notificationController = new NotificationController();
		UserController controller = new UserController();
		
	  User user = controller.getUser("m", "m");
		
	  Assert.assertEquals(notificationController
				.getNotification(new com.m4.socialnetwork.model.javabeans.Notification(
						"", user.getId(), "",
						"")), a.getNotification("m", "m", "", "", ""));
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void getUserNotifications() {
	  User user = new UserController().getUser("m", "m");
		Gson gson = new Gson();
		ArrayList<com.m4.socialnetwork.model.javabeans.Notification> notifications = new NotificationController()
		.getNotifications(user.getId());

	  Assert.assertEquals(gson.toJson(notifications)+"OK", a.getUserNotifications("m","m"));
		
	  throw new RuntimeException("Test not implemented");
  }
}
