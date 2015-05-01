package com.m4.socialnetwork.model.javabeans;

import com.m4.socialnetwork.model.controllers.PostCommand;

public  class Post {
	protected String postId;
	protected String content;
	protected String creatorId;
	protected Privacy privacy;
	protected int numOfLikers;

	public static class PostBuilder {
		protected String postId;
		protected String content;
		protected String creatorId;
		protected Privacy privacy;
		protected int numOfLikers;

		public PostBuilder setPost(Post post) {
			postId = post.postId;
			content = post.content;
			creatorId = post.creatorId;
			privacy = post.privacy;
			numOfLikers = post.numOfLikers;
			return this;
		}

		public PostBuilder setNumOfLikers(int numOfLikers) {
			this.numOfLikers = numOfLikers;
			return this;
		}

		public PostBuilder setPostId(String postId) {
			this.postId = postId;
			return this;
		}

		public PostBuilder setContent(String content) {
			this.content = content;
			return this;
		}

		public PostBuilder setCreatorId(String creatorId) {
			this.creatorId = creatorId;
			return this;
		}

		public PostBuilder setPrivacy(Privacy privacy) {
			this.privacy = privacy;
			return this;
		}

		public Post build() {
			return new Post(this);
		}

	}

	protected Post(PostBuilder builder) {
		postId = builder.postId;
		content = builder.content;
		creatorId = builder.creatorId;
		privacy = builder.privacy;
		numOfLikers = builder.numOfLikers;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public Privacy getPrivacy() {
		return privacy;
	}

	public void setPrivacy(Privacy privacy) {
		this.privacy = privacy;
	}

	public int getNumOfLikers() {
		return numOfLikers;
	}

	public void setNumOfLikers(int numOfLikers) {
		this.numOfLikers = numOfLikers;
	}

}
