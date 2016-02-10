package com.beviacode.asqlitebevia.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PoetsDBOpenHelper extends SQLiteOpenHelper {
	
	public static final String LOGTAG = "PERSONAL";

	private static final String DATABASE_NAME = "personal_one.db";
	private static final int DATABASE_VERSION = 1;

	public static final String TABLE_PERSONAL ="personal";
	//start adding columns:
	public static final String COLUMN_ID = "personalId";
	//Part of the model:
	public static final String COLUMN_NAME = "title";
	public static final String COLUMN_LASTNAME = "lastName";
	public static final String COLUMN_AGE = "age";
	public static final String COLUMN_IMAGE = "image";
	
	public static final String COLUMN_PHOTO = "photo";

	private static final String TABLE_CREATE = 
			"CREATE TABLE " + TABLE_PERSONAL + 
			" (" +
					COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					COLUMN_NAME + " TEXT, " +
					COLUMN_LASTNAME + " TEXT, " +
					COLUMN_AGE +  " NUMERIC, " +
					COLUMN_PHOTO + " BLOB, " +
					COLUMN_IMAGE + " TEXT " +					
			 ")";

	public PoetsDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}
	
	/**
	 * Each time I say to the Android SDK I want to get a connection to my database, 
	 * Android will determine whether the database exists or not. If it doesn't exist yet, 
	 * Android will call the onCreate method. If the database already exists, but I've indicated 
	 * through the database version value that I'm changing the version, that is that I've 
	 * incremented it, then the onUpgrade method will be called
	 */

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);		
		Log.i(LOGTAG, "Table has been created");
	}
	
	/**
	 * I'm just going to drop the existing table, the tours table, and then I'll recreate it. 
	 * So I'll move the cursor into the onCreate method. I'll call db.execSQL and I'll pass 
	 * in this explicit SQL Command, DROP TABLE IF EXISTS, and then I'll append to that the 
	 * name of the table, TABLE_PERSONAL. Then once I've dropped the table, in order to recreate it, 
	 * I'll simply call my onCreate method.  
	 */

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS" +  TABLE_PERSONAL);
		onCreate(db);
		
		Log.i(LOGTAG, "Database has been upgraded from " + 
				oldVersion + " to " + newVersion);

	}
	
	  // Deleting single contact
    public void Delete_Contact(int id) {
	SQLiteDatabase db = this.getWritableDatabase();
	db.delete(TABLE_PERSONAL, COLUMN_ID + " = ?",
		new String[] { String.valueOf(id) });
	db.close();
    }
}
