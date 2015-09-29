package com.mongodash.model;

public class Breadcrumb {

	String title;
	String link;
	
	public Breadcrumb(String title, String link) {
		this.title = title;
		this.link = link;
	}
	
	public Breadcrumb(String title) {
		this.title = title;
	}	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
