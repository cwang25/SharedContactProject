package com.chihan.LsshClassmate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.auth.AuthenticationException;

import com.google.gdata.client.spreadsheet.*;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;

public class SpreadSheetReader {
	private final URL SPREADSHEET_FEED_URL;
	ArrayList <String> contactList;
	Contact [] contactListFromCloud;
	public final int CLASS_SIZE = 25;

	public SpreadSheetReader() throws AuthenticationException, MalformedURLException, IOException, ServiceException  {
		contactList = new ArrayList<String>();
		SpreadsheetService service = new SpreadsheetService("v1");
		    // TODO: Authorize the service object for a specific user (see other sections)

		    // Define the URL to request.  This should never change.
//		    URL SPREADSHEET_FEED_URL = new URL(
//		        "https://spreadsheets.google.com/feeds/spreadsheets/private/full");
//			SPREADSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/worksheets/0Aok9wErsV-wmdGNPV1owYjhMQlYzX0hlbXppZ0dWYUE/private/full");
			SPREADSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/cells/0Aok9wErsV-wmdGNPV1owYjhMQlYzX0hlbXppZ0dWYUE/1/public/basic");


		    
			ListFeed listFeed = service.getFeed(SPREADSHEET_FEED_URL, ListFeed.class);
//			String t = "";
		    // Iterate through each row, printing its cell values.
		    for (ListEntry row : listFeed.getEntries()) {
		      // Print the first column's cell value
//		      t = t + row.getTitle().getPlainText() + "\t";
		      String temp = row.getPlainTextContent();
		      contactList.add(temp);
		    }
		    contactListFromCloud = new Contact[CLASS_SIZE];
		    int pos = 0;
		    for(int i = 0 ; i < CLASS_SIZE ; i ++){
		    	int seatnum = i + 1;
		    	contactListFromCloud[i] = getContactFromCloudAt(seatnum);
		    }
			
	}
	public Contact getContactFromCloudAt(int seatnum){
		try{
			int pos = (seatnum - 1) * 4;
			String sn = Integer.toString(seatnum);
			//long i, String sn, String n, String bd, String p, String e,  String pick
			String name = contactList.get(pos);
			String bd = contactList.get(pos + 1);
			String p = contactList.get(pos + 2);
			String e = contactList.get(pos + 3);
			long i = 0;
			Contact c = new Contact(i, Integer.toString(seatnum) , name, bd, p, e, "1");
			return c;
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public Contact[] getContactsListFromCloud(){
		return contactListFromCloud;
	}
	
	public ArrayList<String> getcontactListArrayList(){
		return contactList;
	}
	
	public String [] getcontactList(){
		String [] cL = new String[contactList.size()];
		for(int i = 0 ; i < cL.length ; i ++){
			cL[i] = contactList.get(i);
		}
		
		return cL;
	}

}
