package com.chihan.TextGenerator;

import java.util.ArrayList;


public class TextGeneratorMain {
	private TextWard textlist;
	public TextGeneratorMain(){
		textlist = new TextWard();
		
	}
	
	public void addText(long id, String title, String content){
		Text temp = new Text(id, title, content);
		textlist.addText(temp);
	}
	
	public void addText(Text text){
		textlist.addText(text);
	}
	public Text getSelectedText(int index){
		return textlist.getTextAt(index);
	}
	
	public Text lookUp(String title){
		ArrayList<String> titles = textlist.getTitleList();
		if(titles.contains(title)){
			return textlist.getTextAt(titles.indexOf(title));
		} else {
			return null;
		}
	}
	
	public void remove(int index){
		textlist.removeText(index);
	}
	
	public void removeById(long id){
		textlist.removeById(id);
	}
	
	public String getSelectedContent(int index){
		return textlist.getTextAt(index).getContent();
	}
	
	public String getSelectedTitle(int index){
		return textlist.getTextAt(index).getTitle();
	}
	public int indexOf(String title){
		ArrayList<String> titles = getTitleslist();
		return titles.indexOf(title);
	}
	
	public ArrayList<String> getTitleslist(){
		return textlist.getTitleList();
	}
	

}
