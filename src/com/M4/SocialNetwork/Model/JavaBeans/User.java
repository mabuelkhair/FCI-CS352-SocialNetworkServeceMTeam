package com.m4.socialnetwork.model.javabeans;



public class User {
	
	private String id ;
	private String userName ;
	private String email ;
	private String password ;
	private boolean active ;
	

	


	public User(String id, String userName, String email, String password,
			boolean active) {
		super();
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.active = active;
	}
	
	
	
	public User(String userName, String email, String password, boolean active) {
		super();
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.active = active;
	}



	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}



	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", email=" + email
				+ ", password=" + password + ", active=" + active + "]";
	}
	
	
	
	
	
	

}
