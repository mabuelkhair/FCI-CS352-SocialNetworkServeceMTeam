package com.M4.SocialNetwork.Services;

import com.M4.SocialNetwork.Model.Controllers.UserController;
import com.M4.SocialNetwork.Model.JavaBeans.User;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.simple.JSONObject;

@Path("/AuthenticationService")
@Produces("text/html")
public class Authentication {

    @POST
	@Path("/Signup")
	public String registration(@FormParam("userName") String userName,
			@FormParam("email") String email, @FormParam("password") String password) {
		  
		User user = new User(userName, email, password) ;
		UserController userController = new UserController() ;
		JSONObject jason = new JSONObject();
		if(userController.insertUser(user)){
		jason.put("Status", "OK") ;
		}else{
			jason.put("Status", "Failed") ;
		}
		return jason.toString();
	}
    
    @POST
    @Path("/Login") 
    public String login(@FormParam("email") String email , @FormParam ("password") String password){
    	UserController userController = new UserController() ;
    	User user = userController.getUser(email, password) ;
    	JSONObject jason = new JSONObject() ;
    	if(user==null){
    		jason.put("Status", "failed") ;
    	}else{
    		jason.put("id", user.getId()) ;
    		jason.put("userName", user.getUserName()) ;
    		jason.put("email", user.getEmail()) ;
    		jason.put("password", user.getPassword()) ;
    		
    	}
    	
    	return jason.toString() ;
    }
	
	

}
