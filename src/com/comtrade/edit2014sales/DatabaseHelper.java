package com.comtrade.edit2014sales;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "artikal.db";
 
    // Contacts table name
    public static final String TABLE_ARTIKLI = "artikli";
 
    // Contacts Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_NAZIV = "naziv";
    public static final String KEY_CIJENA = "cijena";
    public static final String KEY_OPIS = "opis";
    public static final String KEY_BARKOD = "barkod";
 
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ARTIKAL_TABLE = "CREATE TABLE " + TABLE_ARTIKLI + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAZIV + " TEXT,"
                + KEY_OPIS + " TEXT, " + KEY_CIJENA + " REAL, " + KEY_BARKOD + " INTEGER" + ")";
        db.execSQL(CREATE_ARTIKAL_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTIKLI);
 
        // Create tables again
        onCreate(db);
    }
}