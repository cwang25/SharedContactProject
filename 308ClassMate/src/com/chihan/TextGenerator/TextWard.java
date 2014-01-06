package com.chihan.TextGenerator;

import java.util.ArrayList;

public class TextWard {
	private ArrayList<Text> textlist;
	public TextWard(){
		textlist = new ArrayList<Text>();
	}
	
	public void addText(Text text){
		textlist.add(text);
	}
	
	public Text getTextAt(int index){
		return textlist.get(index);
	}
	
	public void removeText(int index){
		textlist.remove(index);
	}
	
	public void editText(int index, Text text){
		textlist.get(index).setTitle(text.getTitle());
		textlist.get(index).setContent(text.getContent());		
	}
	
	public ArrayList<String> getTitleList(){
		ArrayList<String> titles = new ArrayList<String>();
		for(int i = 0 ; i < textlist.size(); i++){
			titles.add(textlist.get(i).getTitle());
		}
		return titles;
	}
	public ArrayList<Text> save(){
		return textlist;
	}

	public void removeById(long id){
		for(int i = 0 ; i < textlist.size() ; i++){
			if(textlist.get(i).getId() == id){
				textlist.remove(i);
				break;
			}
		}
	}

}
