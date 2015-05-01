package com.m4.socialnetwork.services;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.json.JSONObject;

import com.m4.socialnetwork.model.controllers.UserController;
import com.m4.socialnetwork.model.javabeans.User;

@Path("/FriendService")
public class Friend {

	@POST
	@Path("/SendFreindRequest")
	public String addFriend(@FormParam("senderEmail") String sender,
			@FormParam("senderPassword") String password,
			@FormParam("receptionEmail") String reception) {

		UserController userController = new UserController();
		JSONObject json = new JSONObject();
		try{
		if (userController.getUser(sender, password) != null
				&& userController.sendFriedRequest(sender, reception)) {
			json.put("Status", "OK");

		} else {
			json.put("Status", "failed");
		}
		}catch(Exception e){} ;

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
		try{
		if (controller.getUser(receptionEmail, receptionPassword) != null
				&& controller.requestExistBefore(senderEmail, receptionEmail)
				&& controller.acceptFriendRequest(senderEmail,
						receptionEmail)) {
			json.put("Status", "OK");
		} else {
			json.put("Status", "failed");
		}
		}catch(Exception e){}
		return json.toString();
	} 
	
	@GET
	@Path("/SearchFriend/{email}")
	public String searchFriend(@PathParam("email") String email){
		System.out.println(email);
		UserController controller = new UserController() ;
		JSONObject json = new JSONObject() ;
		try{
		if(controller.getUser(email)!=null){
			json.put("Status", "Exist") ;
		}else{
			json.put("Status", "Not Exist") ;
		}
		}catch(Exception e){} 
		
		return json.toString() ;
	}
	@POST
	@Path("/test")
	public String getUserId(@FormParam("ID") String id){
		
		User user = new UserController().getUserById(id) ;
		
		if(user!=null){
			return user.toString() ;
		}
		return id ;
		
	}
	
}
