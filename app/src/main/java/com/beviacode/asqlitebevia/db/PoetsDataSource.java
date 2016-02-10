package com.beviacode.asqlitebevia.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.beviacode.asqlitebevia.model.Poets;
import com.beviacode.asqlitebevia.utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class PoetsDataSource {
	
	public static final String LOGTAG = "PERSONAL";
	/**
	 * Next, I'll declare the instances of the Database Open Helper and the Database.
	 */

	SQLiteOpenHelper dbhelper;
	SQLiteDatabase  database;	
	/**
	 * to retrive the data from the db we'll need a string array:
	 */
	private static final String [] allColumns = {
		
		PoetsDBOpenHelper.COLUMN_ID,
		PoetsDBOpenHelper.COLUMN_NAME,
		PoetsDBOpenHelper.COLUMN_LASTNAME ,
		PoetsDBOpenHelper.COLUMN_AGE,
		PoetsDBOpenHelper.COLUMN_IMAGE,
		PoetsDBOpenHelper.COLUMN_PHOTO
	};
	/**
	 * I'll instantiate the Database Open Helper class when the data source is instantiated. 
	 * So I'll add code to the PoetsDataSource constructor method.
	 */
	public PoetsDataSource(Context context) {
		dbhelper = new PoetsDBOpenHelper(context);
		
	}
	/**
	 * Create and/or open a database that will be used for reading and writing. 
	 * The first time this is called, the database will be opened and onCreate, 
	 * onUpgrade and/or onOpen will be called. Once opened successfully, the database 
	 * is cached, so you can call this method every time you need to write to the database. 
	 * @return 
	 */
	public PoetsDataSource open() throws SQLException {
		Log.i(LOGTAG, "Database opened");
		database = dbhelper.getWritableDatabase();
		return this;
	}
	/**
	 * Make sure to call close when you no longer need the database.
	 */
	public void close() throws SQLException {
		Log.i(LOGTAG, "Database closed");
		dbhelper.close();
		
	}
	/**
	 * Inserting data into the db, for this you must first implement your model, which we'll call "Poets"...
	 */
	public Poets create (Poets personal) //returns and instance of the Poets class!
	{
		ContentValues values = new ContentValues(); //instantiated with the class construction method
		//the ContentValues class implements the MAP interface!
		//values.put(key, value);
		values.put(PoetsDBOpenHelper.COLUMN_NAME, personal.getFistName());
		values.put(PoetsDBOpenHelper.COLUMN_LASTNAME, personal.getLastName());
		values.put(PoetsDBOpenHelper.COLUMN_AGE, personal.getAge());
		values.put(PoetsDBOpenHelper.COLUMN_IMAGE, personal.getImage());
		values.put(PoetsDBOpenHelper.COLUMN_PHOTO, Utility.getBytes(personal.getBmp()));
		
		//now lets insert the row into the database:
		long insertid = database.insert(PoetsDBOpenHelper.TABLE_PERSONAL, null, values);
		//when the row is created I receive back the COLUMN_ID " INTEGER PRIMARY KEY AUTOINCREMENT, " value!! 
		//so now, I will assign it back to the personal object
		personal.setId(insertid);
		return personal;
		//now you must set the data, so back to the LoadList!
	}
	//I'll create a list of personal objects to retrive all of my columns and rows from the db:
	public List<Poets> findAll() {

			List<Poets> personals = new ArrayList<Poets>();
		
		//next we'll query the db:
		Cursor cursor = database.query(PoetsDBOpenHelper.TABLE_PERSONAL, allColumns,
				null, null, null, null, null);
		
		Log.i(LOGTAG, "return" + cursor.getCount() + " rows");
		
		//this methods needs to return a list of personal objects, so we need to loop through the cursor...one row at a time!!
		//remember that in a result the cursor stars before the first row...so we'll have to make sure it is greater than 0!
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {   //now retrive one row at a time!
				Poets personal = new Poets(); //instantiating Poets class!
				personal.setId(cursor.getLong(cursor.getColumnIndex(PoetsDBOpenHelper.COLUMN_ID)));
				personal.setFistName(cursor.getString(cursor.getColumnIndex(PoetsDBOpenHelper.COLUMN_NAME)));
				personal.setLastName(cursor.getString(cursor.getColumnIndex(PoetsDBOpenHelper.COLUMN_LASTNAME)));
				personal.setImage(cursor.getString(cursor.getColumnIndex(PoetsDBOpenHelper.COLUMN_IMAGE)));
				personal.setAge(cursor.getInt(cursor.getColumnIndex(PoetsDBOpenHelper.COLUMN_AGE)));

				// this lines are used for the IMAGE!
				byte[] blob = cursor.getBlob(cursor.getColumnIndex(PoetsDBOpenHelper.COLUMN_PHOTO));
			    personal.setBmp(Utility.getPhoto(blob));
				
				personals.add(personal); //added to my list!
			}
		}
		return personals;  	//now you need to get this list from the main activity!
	}
}
