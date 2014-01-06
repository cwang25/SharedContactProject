package com.chihan.TextGenerator;

public class Text {
	private String title;
	private String content;
	private long id;
	public Text(long id, String title, String content){
		this.id = id;
		this.title = title;
		this.content = content;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getContent(){
		return content;
	}
	 public void setTitle(String newtitle){
		 this.title = newtitle;
	 }
	 
	 public void setContent(String newcontent){
		 this.content = newcontent;
	 }
	 
	 public long getId(){
		 return id;
	 }
	
}
