package com.m4.socialnetwork.model.javabeans;

public class PagePost extends Post {
	private String pageId;

	public static class PagePostBuilder extends Post.PostBuilder {
		String pageId;

		public PagePostBuilder setPageId(String pageId) {
			this.pageId = pageId;
			return this ;
		}
		public PagePostBuilder setPost(Post post){
			super.setPost(post);
			return this ;
		}

		public PagePost build() {
			return new PagePost(this);
		}
	}

	protected PagePost(PostBuilder builder) {
		super(builder);
		setPageId(((PagePostBuilder) builder).pageId);
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

}
