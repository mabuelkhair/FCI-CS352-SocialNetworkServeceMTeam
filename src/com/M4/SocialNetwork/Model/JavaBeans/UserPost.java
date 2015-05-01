package com.m4.socialnetwork.model.javabeans;

import com.m4.socialnetwork.model.javabeans.Post.PostBuilder;

public class UserPost extends Post{

	private String receiverId ;
    public static class UserPostBuilder extends Post.PostBuilder{
    	  private String receiverId ;

		public UserPostBuilder setReceiverId(String receiverId) {
			this.receiverId = receiverId;
			return this ;
		}
		public UserPostBuilder setPost(Post post){
			super.setPost(post) ;
			return this; 
		}
		
    	  public UserPost build(){
    		  return new UserPost(this) ;
    	  }
    }
    
    
    protected UserPost(PostBuilder builder) {
    	super(builder);
    	receiverId = ((UserPostBuilder)builder).receiverId ;
    	// TODO Auto-generated constructor stub
    }

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
    
}
