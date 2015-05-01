package com.m4.socialnetwork.model.javabeans;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;
import com.m4.socialnetwork.model.javabeans.child.childBuilder;

class parent{
	int x ;
	public static class ParentBuilder{
		int x ;

		

		public ParentBuilder setX(int x) {
			this.x = x;
			return this ;
		}
		public ParentBuilder setBase(parent p){
			x = p.x ;
			return this ;
		}
		public parent build(){
			return new parent(this) ;
		}
		
	}
	
	protected parent(ParentBuilder builder ){
		 x = builder.x ;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
}

class child extends parent{
	   int y ; 
	   public static class childBuilder extends parent.ParentBuilder{
		   int y ;
		   public childBuilder setY(int y){
			   this.y = y ;
			   return this ;
		   }
		   public childBuilder setBase(parent p){
			    super.setBase(p) ;
			   return this ;
		   }
		   
		   public child build() {
			   return new child(this) ;
		}
		   
	   }
	   
	   protected child(childBuilder builder){
		   super(builder) ;
		   y = builder.y ;
	   }
	   
}

public class testing {
	public static void main(String[] args) {
	/*	test1 t1 = new test1(2, new test2(3)) ;
     JSONObject jsonObject = new JSONObject(); 
     Gson gson = new Gson() ;
     
     try {
		jsonObject.put("test",gson.toJson(t1)) ;
		System.out.println(jsonObject.toString());
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     
	*/
		/*
		parent p = new parent.ParentBuilder().setX(2).build() ;
		child c =  new child.childBuilder().setBase(p).setY(5).build() ;
		System.out.println(c.getX()+" " +c.y);
	 */
		
		
	}
	

}
