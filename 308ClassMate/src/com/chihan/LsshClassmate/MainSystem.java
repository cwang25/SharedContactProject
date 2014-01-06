package com.chihan.LsshClassmate;

import java.util.ArrayList;



public class MainSystem {
	private ArrayList<Contact> contactList;
	private int currentPos;

	public MainSystem() {
		contactList = new ArrayList<Contact>();
	}
	
	public Contact addContact(Contact c){
		contactList.add(c);
		return c;
	}
	
	
	
	public Contact setContact(int pos, Contact c){
		contactList.set(pos, c);
		return c;
	}

	public int findPosByName(String n){
		Contact t = null;
		for(int i = 0 ; i < contactList.size() ; i++){
			t = contactList.get(i);
			if(t.getName().equals(n)){
				break;
			}
		}
		
		return contactList.indexOf(t);
	}
	
	public String[] getAllName(){
		String[] t = new String[contactList.size()];
		for(int i = 0 ; i < t.length ; i++){
			t[i] = contactList.get(i).getName();
		}
		
		return t;
	}
	
	public Contact getContact(int pos){
		return contactList.get(pos);
	}
	
	public ArrayList<Contact> getContactList(){
		return contactList;
	}
	
	public Contact [] getContactListArray(){
		Contact [] temp = new Contact[contactList.size()];
		for(int i = 0 ; i < contactList.size() ; i ++){
			temp[i] = contactList.get(i);
		}
		
		return temp;
	}
	
	public Contact setPickContactAt(int pos, boolean isPick){
		if(isPick == true){
			contactList.get(pos).pick();
		} else {
			contactList.get(pos).notPick();
		}
		return contactList.get(pos);
	}
	
	public ArrayList<Integer> getAllSeatNumber(){
		ArrayList<Integer> sn = new ArrayList<Integer>();
		for(int i = 0 ; i < contactList.size(); i++){
			sn.add(Integer.parseInt(contactList.get(i).getSeatNumber()));
		}
		return sn;
	}
	
	public Contact[] getAllPickedContacts(){
		ArrayList<Contact> allPickedC = new ArrayList<Contact>();
		Contact[] pickCA = null;
		for( int i = 0 ; i < contactList.size() ; i++ ){
			Contact t = contactList.get(i);
			if(t.isPicked()){
				allPickedC.add(t);
			}
		}
		int size = allPickedC.size();
		pickCA = new Contact[size];
		for(int i = 0 ; i < size ; i++){
			pickCA[i] = allPickedC.get(i);
		}
		return pickCA;
	}
	
	public int setCurrentPos(int pos){
		currentPos = pos;
		return currentPos;
	}
	
	public int getCurrentPos(){
		return currentPos;
	}
	
}
