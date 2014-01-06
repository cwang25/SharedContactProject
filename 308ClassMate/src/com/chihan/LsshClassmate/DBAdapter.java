package com.chihan.LsshClassmate;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	public static final String KEY_ROWID = "id";
	//column
	public static final String KEY_SEATNUMBER = "seatnumber";
	public static final String KEY_NAME = "name";
	public static final String KEY_BDAY = "bday";
	public static final String KEY_PHONE = "phone";
	public static final String KEY_EMAIL	= "email";
	public static final String KEY_PICK = "pick";
	
	public static final String KEY_TITLE = "title";
	public static final String KEY_CONTENT = "content";
	
	private static final String TAG = "DBAdapter";
	
	private static final String DATABASE_NAME = "ClassMateDB";
	private static final String DATABASE_CONTACTS= "contacts";
	private static final String DATABASE_TEXTS= "texts";
	private static final int DATABASE_VERSION = 1;
	
	
	private static final String CONTACTS_CREATE = 
			"create table if not exists contacts (id integer primary key autoincrement, "
			+ "seatnumber VARCHAR not null, name VARCHAR, bday VARCHAR, phone VARCHAR, email VARCHAR, pick VARCHAR);";
	private static final String DATABASE_CREATE = 
			"create table if not exists texts (id integer primary key autoincrement, "
			+ "title VARCHAR not null, content VARCHAR );";
	
	private static String [] contacts = new String [25];
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	private static String filename = "contacts.txt";
	
	public DBAdapter(Context ctx){
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper{
		DatabaseHelper(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			readFile(filename, context);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			try{
				db.execSQL(DATABASE_CREATE);
				db.execSQL(CONTACTS_CREATE);
				insertRecordsToTexts(db, "最近想辦同學會", "各位同學們, 最近各位有沒有空要不要辦個同學會?");
				insertRecordsToTexts(db, "最近來辦個聚餐", "最近各位同學們有沒有空來辦個聚餐?");
				insertRecordsToTexts(db, "一起去郊遊", "各位同學們, 最近我想要揪團一起出去玩!!有沒有人有空一起初由呢?");
				for (int i = 0 ; i < contacts.length ; i++){
					if(contacts[i] == null){
						break;
					}
					String tline = contacts[i];
					String seatnum;
					String name;
					String bday;
					String phone;
					String email;
					Scanner s = new Scanner(tline);
					seatnum = s.next();
					if(s.hasNext()){
						name = s.next();
					} else {
						name = "n/a";
					}
					
					if(s.hasNext()){
						bday = s.next();
					} else {
						bday = "n/a";
					}
					
					if(s.hasNext()){
						phone = s.next();
					} else {
						phone = "-1";
					}
					
					if(s.hasNext()){
						email = s.next();
					} else {
						email = "n/a";
					}
					
					insertRecordsToContacts(db, seatnum, name, bday, phone, email, "1");
					
					s.close();
				}
				
				//need to modify to read the text file.
			}catch(SQLException e){
				e.printStackTrace();
			}
			
			
		}
		public long insertRecordsToTexts(SQLiteDatabase db, String title, String content){
			ContentValues initialValues = new ContentValues();
			initialValues.put(KEY_TITLE, title);
			initialValues.put(KEY_CONTENT, content);
			return db.insert(DATABASE_TEXTS, null, initialValues);
		}
		
		public long insertRecordsToContacts(SQLiteDatabase db, String seatnumber, String name, String bday, String phone, String email, String pick){
			ContentValues initialValues = new ContentValues();
			initialValues.put(KEY_SEATNUMBER, seatnumber);
			initialValues.put(KEY_NAME, name);
			initialValues.put(KEY_BDAY, bday);
			initialValues.put(KEY_PHONE, phone);
			initialValues.put(KEY_EMAIL, email);
			initialValues.put(KEY_PICK, pick);
			return db.insert(DATABASE_CONTACTS, null, initialValues);
		}
		
		public void readFile(String filename, Context c){
			AssetManager am = c.getAssets();
			try {
				Scanner console = new Scanner(am.open(filename));
				int i = 0;
				while(console.hasNextLine()){
					String line = console.nextLine();
					contacts[i] = line;
					i++;
				}
				
				console.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			Log.w(TAG, "Upgrading database from version "+ oldVersion + " to "+ newVersion+", which will destroy all old data.");
			db.execSQL("DROP TABLE IF EXISTS texts");
			onCreate(db);
		}
	}
	
	public DBAdapter open() throws SQLException{
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		DBHelper.close();
	}
	public long insertRecordsToTexts(String title, String content){
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TITLE, title);
		initialValues.put(KEY_CONTENT, content);
		return db.insert(DATABASE_TEXTS, null, initialValues);
	}
	
	public long insertRecordsToContacts(SQLiteDatabase db, String seatnumber, String name, String bday, String phone, String email, String pick){
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_SEATNUMBER, seatnumber);
		initialValues.put(KEY_NAME, name);
		initialValues.put(KEY_BDAY, bday);
		initialValues.put(KEY_PHONE, phone);
		initialValues.put(KEY_EMAIL, email);
		initialValues.put(KEY_PICK, pick);
		return db.insert(DATABASE_CONTACTS, null, initialValues);
	}
	
	public boolean deleteRecordFromTexts(long rowId){
		return db.delete(DATABASE_TEXTS, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	public boolean deleteRecordFromContacts(long rowId){
		return db.delete(DATABASE_CONTACTS, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	// 08/08
	public Cursor getAllRecordsFromTexts(){
		db = DBHelper.getReadableDatabase();
		return db.query(DATABASE_TEXTS, new String[] {KEY_ROWID, KEY_TITLE, KEY_CONTENT}, null, null, null, null, null);
	}
	
	public Cursor getAllRecordsFromContacts(){
		db = DBHelper.getReadableDatabase();
		return db.query(DATABASE_CONTACTS, new String[] {KEY_ROWID, KEY_SEATNUMBER, KEY_NAME, KEY_BDAY, KEY_PHONE, KEY_EMAIL, KEY_PICK}, null, null, null, null, null);
	}
	// here
	public Cursor getRecordFromTexts(long rowId) throws SQLException {
		Cursor mCursor = db.query(true, DATABASE_TEXTS, new String[] {KEY_ROWID, KEY_TITLE, KEY_CONTENT}, KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if(mCursor != null){
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	public Cursor getRecordFromContacts(long rowId) throws SQLException {
		Cursor mCursor = db.query(true, DATABASE_CONTACTS, new String[] {KEY_ROWID, KEY_SEATNUMBER, KEY_NAME, KEY_BDAY, KEY_PHONE, KEY_EMAIL, KEY_PICK}, KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if(mCursor != null){
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	public boolean updateRecordTexts(long rowId, String title, String content){
		ContentValues args = new ContentValues();
		args.put(KEY_TITLE, title);
		args.put(KEY_CONTENT, content);
		return db.update(DATABASE_TEXTS, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	public boolean updateRecordContacts(long rowId, String seatnumber, String name, String bday, String phone, String email, String pick){
		ContentValues args = new ContentValues();
		args.put(KEY_SEATNUMBER, seatnumber);
		args.put(KEY_NAME, name);
		args.put(KEY_BDAY, bday);
		args.put(KEY_PHONE, phone);
		args.put(KEY_EMAIL, email);
		args.put(KEY_PICK, pick);
		return db.update(DATABASE_CONTACTS, args, KEY_ROWID + "=" + rowId, null) > 0;
	}

}
