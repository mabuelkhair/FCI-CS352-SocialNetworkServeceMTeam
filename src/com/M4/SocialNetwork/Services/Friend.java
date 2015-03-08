package com.M4.SocialNetwork.Services;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.json.simple.JSONObject;

import com.M4.SocialNetwork.Model.Controllers.UserController;

@Path("/FriendService")
public class Friend {

	@POST
	@Path("/SendFreindRequest")
	public String addFriend(@FormParam("senderEmail") String sender,
			@FormParam("senderPassword") String password,
			@FormParam("receptionEmail") String reception) {

		UserController userController = new UserController();
		JSONObject json = new JSONObject();
		if (userController.getUser(sender, password) != null
				&& userController.sendFriedRequest(sender, reception)) {
			json.put("Status", "OK");

		} else {
			json.put("Status", "failed");
		}

		return json.toString();
	}

	@POST
	@Path("/ConfirmFriendRequest")
	public String confirmFriend(
			@FormParam("receptionEmail") String receptionEmail,
			@FormParam("receptionPassword") String receptionPassword,
			@FormParam("senderEmail") String senderEmail) {
         
		System.out.println(receptionEmail + " " + receptionPassword + " " + senderEmail) ;
		UserController controller = new UserController();
		JSONObject json = new JSONObject();
		if (controller.getUser(receptionEmail, receptionPassword) != null
				&& controller.requestExistBefore(senderEmail, receptionEmail)
				&& controller.acceptFriendRequest(senderEmail,
						receptionEmail)) {
			json.put("Status", "OK");
		} else {
			json.put("Status", "failed");
		}

		return json.toString();
	}
}
