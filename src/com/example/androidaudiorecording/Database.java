package com.example.androidaudiorecording;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {
	
	public static String KEY_FNAME="FirstName";
	public static String KEY_LNAME="LastName";
	public static String KEY_MBNO="MobileNo";
	public static String KEY_EMAIL="EmailId";
	
	public static String DB_NAME="SignupDetails";
	public static String DB_TABLE="UserDetails"; 
	public static int DB_VERSION=4;
	private static String DB_CREATE="create table UserDetails (FirstName text,"+"LastName text,"+"MobileNo  text,"+"EmailId text);";
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase Sb;
	
	 public Database(Context ctx) {
		// TODO Auto-generated constructor stub
		 this.context=ctx;
		 DBHelper=new DatabaseHelper(context);
	}
	 private static class DatabaseHelper extends SQLiteOpenHelper
	 {

		public DatabaseHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase Sb) {
			// TODO Auto-generated method stub
			Sb.execSQL(DB_CREATE);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
		 
	 }
	 public Database open() throws SQLException
	 {
		 Sb=DBHelper.getWritableDatabase();
		 return this;
		 
	 } 
	 
	 public void close()
	 {
		 DBHelper.close();
	 }
	
     public long InsertTitles(String FirstName,String LastName,String MobileNo,String EmailId)
	 {
    	 ContentValues cv= new ContentValues();
    	 cv.put(KEY_FNAME,FirstName);
    	 cv.put(KEY_LNAME,LastName);
    	 cv.put(KEY_MBNO,MobileNo);
    	 cv.put(KEY_EMAIL,EmailId);
    	 
    	return Sb.insert(DB_TABLE, null, cv);
		 
	 }
     
     
     
     public Cursor getAllTitles(){
    		return Sb.query(DB_TABLE, new String[]{KEY_FNAME,KEY_LNAME,KEY_MBNO,KEY_EMAIL},null, null, null, null, null);
    		 
    	 }
    	 //---retrieves a particular title---
    	 public Cursor getTitle(long rowId){
    		// return Sb.query(DB_TABLE, columns, selection, selectionArgs, groupBy, having, orderBy)
    		 Cursor cc=Sb.query(true,DB_TABLE, new String[]{KEY_FNAME,KEY_LNAME,KEY_MBNO,KEY_EMAIL},KEY_FNAME+"="+rowId,null,null,null,null,null);
    		 if(cc!=null){
    			 cc.moveToFirst();
    		 }
    		return cc;
    	 }
    	 //---updates a title---
    	 public boolean updateTitles(String FirstName,String LastName,String MobileNo,String EmailId){
    		 ContentValues args=new ContentValues();
    		 args.put(KEY_FNAME,FirstName);
    		 args.put(KEY_LNAME,LastName);
    		 args.put(KEY_MBNO,MobileNo);
    		 args.put(KEY_EMAIL,EmailId);
    		
    		return Sb.update(DB_TABLE,args, KEY_FNAME,null)>0;
    		 
    	 }
    	

}
