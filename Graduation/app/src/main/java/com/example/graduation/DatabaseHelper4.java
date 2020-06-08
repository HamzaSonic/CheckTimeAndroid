package com.example.graduation;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by User on 2/28/2017.
 */

public class DatabaseHelper4 extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "user_tabel";
    private static final String COL1 = "ID";
    private static final String COL2 = "Name";
    private static final String COL3 = "Email";
    private static final String COL4 = "Phone";
    private static final String COL5 = "Age";
    private static final String COL6 = "Gender";


    public DatabaseHelper4(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 +" TEXT ," + COL3 +" TEXT ,"+ COL4 + " Text ,"+ COL5 +" text , "+ COL6 +" TEXT )";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String Name,String Email ,String Phone,String age,String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, Name);
        contentValues.put(COL3,Email);
        contentValues.put(COL4,Phone);
        contentValues.put(COL5,age);
        contentValues.put(COL6,gender);

        //Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);

        long dbresult = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (dbresult == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns all the data from database
     * @return
     */
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Returns only the ID that matches the name passed in
     * @param name
     * @return
     */
    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Updates the name field
     * @param newName
     * @param id
     * @param oldName
     */
     public void updateName(String newName, String id, String oldName){
     SQLiteDatabase db = this.getWritableDatabase();
     String query = "UPDATE " + TABLE_NAME + " SET '" + id +
     "' = '" + newName+"'" ;
    // Log.d(TAG, "updateName: query: " + query);
   //  Log.d(TAG, "updateName: Setting name to " + newName);
     db.execSQL(query);
     }


    public void deleteName(){
    SQLiteDatabase db = this.getWritableDatabase();
    String query = "DELETE  FROM " + TABLE_NAME ;


    db.execSQL(query);
    }

}

