package com.m4.socialnetwork.model.controllers;

import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.m4.socialnetwork.model.javabeans.Page;

public class PageController {
	public void likePage(String pageId, String userId) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Entity pageLikerEntity = new Entity("PAGE_LIKERS");
		pageLikerEntity.setProperty("pageId", pageId);
		pageLikerEntity.setProperty("userId", userId);
		dataStore.put(pageLikerEntity);
	}

	public Page createPage(Page page) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Entity pageEntity = new Entity("PAGE");
		pageEntity.setProperty("pageName", page.getName());
		pageEntity.setProperty("creatorId", page.getCreatorId());
		dataStore.put(pageEntity);
		page.setId(String.valueOf(pageEntity.getKey().getId()));
		likePage(page.getId(), page.getCreatorId());
		return page;
	}

	public ArrayList<String> getPagesOFUser(String userId) {
		DatastoreService dataStore = DatastoreServiceFactory
				.getDatastoreService();
		Filter userFilter = new FilterPredicate("userId", FilterOperator.EQUAL,
				userId);
		Query pageQuery = new Query("PAGE_LIKERS").setFilter(userFilter);
		PreparedQuery pq = dataStore.prepare(pageQuery);
		ArrayList<String> pagesId = new ArrayList<String>();
		for (Entity page : pq.asIterable()) {
			pagesId.add(page.getProperty("pageId").toString());
		}
		return pagesId;
	}
	public ArrayList<String> getPageLikers(String pageId){
		ArrayList<String> pageLikers = new  ArrayList<String>() ;
      DatastoreService dataStore  = DatastoreServiceFactory.getDatastoreService() ; 
      Filter pageFilter = new FilterPredicate("pageId" ,FilterOperator.EQUAL , pageId) ;
       Query pageQuery = new Query("PAGE_LIKERS").setFilter(pageFilter)  ;
       PreparedQuery pq = dataStore.prepare(pageQuery) ;
       for(Entity pageLiker : pq.asIterable()){
    	    pageLikers.add(pageLiker.getProperty("userId").toString()) ;
       }
		return pageLikers ;
	}
    public Page getPage(String pageId){
    	DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService() ;
    	Key key = KeyFactory.createKey("PAGE", Long.parseLong(pageId)) ;
    	Entity page;
		try {
			page = dataStore.get(key);
			return new Page(pageId, page.getProperty("pageName").toString(), getPageLikers(pageId), page.getProperty("creatorId").toString()) ;
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null ;
    }
	
	public ArrayList<Page> getPagesByName(String pageName){
		ArrayList<Page> pages = new ArrayList<Page>() ;
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService() ;
		Filter pageFilter = new FilterPredicate("pageName" , FilterOperator.EQUAL , pageName) ;
		Query pageQuery = new Query("PAGE").setFilter(pageFilter) ;
		PreparedQuery pq = dataStore.prepare(pageQuery) ;
		for(Entity page : pq.asIterable()){
			pages.add(getPage(String.valueOf(page.getKey().getId()))) ;
		}
		return pages ;
	}

}
