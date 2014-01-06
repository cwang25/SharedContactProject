package com.chihan.LsshClassmate;

public class Contact {
	private long id;
	private String seatnumber;
	private String name;
	private String bday;
	private String phone;
	private String email;
	private boolean pick;
	//KEY_ROWID, KEY_SEATNUMBER, KEY_NAME, KEY_BDAY, KEY_PHONE, KEY_EMAIL, KEY_PICK
	public Contact(long i, String sn, String n, String bd, String p, String e,  String pick) {
		this.seatnumber = sn;
		this.name = n;
		this.bday = bd;
		this.phone = p;
		this.email = e;
		this.id = i;
		if(Integer.parseInt(pick) == 1){
			this.pick = true;
		} else {
			this.pick = false;
		}
	}
	
	public long getID(){
		return id;
	}
	
	public String getSeatNumber(){
		return seatnumber;
	}
	
	public String getName(){
		return name;
	}
	
	public long setID(long i){
		this.id = i;
		return id;
	}
	
	public String setName(String n){
		this.name = n;
		return name;
	}
	
	public String getBday(){
		return bday;
	}
	
	public String setBday(String d){
		this.bday = d;
		return bday;
	}
	public String getPhone(){
		return phone;
	}
	
	public String setPhone(String p){
		this.phone = p;
		return phone;
	}
	
	
	
	public String getEmail(){
		return email;
	}
	
	public String setEmail(String e){
		this.email = e;
		return email;
	}
	
	public boolean isPicked(){
		if(pick == true){
			return true;
		} else {
			return false;
		}
	}
	
	public void setPick(boolean p){
		if(p){
			pick = true;
		} else {
			pick = false;
		}
	}
	
	public void pick(){
		pick = true;
	}
	
	public void notPick(){
		pick = false;
	}
	
	public int getPhotoCode(){
		int seatnum = Integer.parseInt(seatnumber);
        switch(seatnum){
        case 1:
        	return R.drawable.s01 ;        	
        case 2:
        	return R.drawable.s02;        	
        case 3:
        	return R.drawable.s03;        	
        case 4:
        	return R.drawable.s04;        	
        case 5:
        	return R.drawable.s05;        	
        case 6:
        	return R.drawable.s06;        	
        case 7:
        	return R.drawable.s07;        	
        case 8:
        	return R.drawable.s08;        	
        case 9:
        	return R.drawable.s09;        	
        case 10:
        	return R.drawable.s10;        	
        case 11:
        	return R.drawable.s11;        	
        case 12:
        	return R.drawable.s12;        	
        case 13:
        	return R.drawable.s13;        	
        case 14:
        	return R.drawable.s14;        	
        case 15:
        	return R.drawable.s15;        	
        case 16:
        	return R.drawable.s16;        	
        case 17:
        	return R.drawable.s17;        	
        case 18:
        	return R.drawable.s18;        	
        case 19:
        	return R.drawable.s19;        	
        case 20:
        	return R.drawable.s20;        	
        case 21:
        	return R.drawable.s21;        	
        case 22:
        	return R.drawable.s22;        	
        case 23:
        	return R.drawable.s23;       	
        case 24:
        	return R.drawable.s24;       	
        case 25:
        	return R.drawable.s25;        	
        default:
        	return R.drawable.s22;     
        }
	}

}
