package com.m4.socialnetwork.services;

import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;
import com.m4.socialnetwork.model.controllers.NotificationController;
import com.m4.socialnetwork.model.controllers.UserController;
import com.m4.socialnetwork.model.javabeans.User;

@Path("/NotificationService")
public class Notification {
	@POST
	@Path("/GetNotification")
	public String getNotification(@FormParam("userEmail") String userEmail,
			@FormParam("password") String password,
			@FormParam("notificationType") String notificationType,
			@FormParam("notificationId") String notificationId,
			@FormParam("componentId") String componentId) {
		UserController controller = new UserController();
		NotificationController notificationController = new NotificationController();
		User user = controller.getUser(userEmail, password);
		if (user != null
				&& notificationController.notificationBelongToUser(
						notificationId, user.getId())) {
			return notificationController
					.getNotification(new com.m4.socialnetwork.model.javabeans.Notification(
							notificationType, user.getId(), notificationId,
							componentId));
		}
		return null;
	}

	
	@POST
	@Path("/GetAllNotifications")
	public String getUserNotifications(@FormParam("userEmail") String email,
			@FormParam("password") String password) {
		User user = new UserController().getUser(email, password);
		JSONObject jsonObject = new JSONObject();
		Gson gson = new Gson();
		if (user != null) {
			ArrayList<com.m4.socialnetwork.model.javabeans.Notification> notifications = new NotificationController()
					.getNotifications(user.getId());
			try {
				jsonObject.put("notifications", gson.toJson(notifications));
				jsonObject.put("status", "OK");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				//here
				jsonObject.put("Status", "faild");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jsonObject.toString();
	}

}
