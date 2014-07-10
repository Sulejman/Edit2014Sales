package com.comtrade.edit2014sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class ArtikalDAO {
	private SQLiteDatabase database;
	private DatabaseHelper dbhelper;
	private String[] ARTIKAL_TABLE_COLUMNS = 
		{ dbhelper.KEY_ID,dbhelper.KEY_NAZIV, dbhelper.KEY_OPIS, dbhelper.KEY_CIJENA, dbhelper.KEY_BARKOD };
	
	public ArtikalDAO(Context context) {
		dbhelper = new DatabaseHelper(context);
	}

	public void open() throws SQLException {
		database = dbhelper.getWritableDatabase();
	}

	public void close() {
		dbhelper.close();
	}
	
	public Artikal addArtikal(Artikal artikal) throws SQLException {

		ContentValues values = new ContentValues();

		values.put(DatabaseHelper.KEY_NAZIV, artikal.getNaziv());
		values.put(DatabaseHelper.KEY_BARKOD, artikal.getBarkod());
		values.put(DatabaseHelper.KEY_OPIS, artikal.getOpis());
		values.put(DatabaseHelper.KEY_CIJENA, artikal.getCijena());
		
		long artiklId = database.insert(DatabaseHelper.TABLE_ARTIKLI, null, values);

		// now that the student is created return it ...
		Cursor cursor = database.query(DatabaseHelper.TABLE_ARTIKLI,
				ARTIKAL_TABLE_COLUMNS,"id = " + artiklId, null, null, null, null);
		
		/*Log.d("CIJENA:", String.valueOf(artikal.getCijena()));
		Log.d("BARKOD:", String.valueOf(artikal.getBarkod()));
		Log.d("NAZIV:",artikal.getNaziv());
		Log.d("OPIS:",artikal.getOpis());*/
		//Cursor cursor = null;
		
		/*try{
		cursor = database.rawQuery("INSERT INTO artikli (naziv,cijena,opis,barkod) VALUES " +
				"(" + artikal.getNaziv() + "," + artikal.getCijena() + "," + artikal.getOpis() + "," 
				+ artikal.getBarkod() + ")",null); 
		}
		catch(SQLiteException e){
			
		}*/
		
			cursor.moveToFirst();

			
		
		
		
		Artikal newComment = parseArtikal(cursor);
		cursor.close();
		return newComment;
	}

	public void deleteArtikal(Artikal comment) {
		long id = comment.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(DatabaseHelper.TABLE_ARTIKLI, DatabaseHelper.TABLE_ARTIKLI
				+ " = " + id, null);
	}

	public List getAllArtikal() throws SQLException {
		//List<HashMap<String,String>> artikli = new ArrayList<HashMap<String,String>>();
		List<Artikal> artikli = new ArrayList();
		//Cursor cursor = database.query(DatabaseHelper.TABLE_ARTIKLI,ARTIKAL_TABLE_COLUMNS, null, null, null, null, null);
		Cursor  cursor = database.rawQuery("select * from artikli",null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			/*HashMap<String, String> hm = new HashMap<String,String>();
			Artikal artikal = parseArtikal(cursor);
			hm.put("naziv","Naziv: " + artikal.getNaziv());
			hm.put("opis","Opis: " + artikal.getOpis());
			hm.put("barkod","Barcode: " + String.valueOf(artikal.getBarkod()));
			hm.put("cijena","Cijena: " + String.valueOf(artikal.getCijena()) + " KM");*/
			Artikal artikal = new Artikal();
	           // Take values from the DB
	           artikal.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_ID)));
	           artikal.setBarkod(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_BARKOD)));
	           artikal.setNaziv(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_NAZIV)));
	           artikal.setCijena(cursor.getFloat(3));
	           artikal.setOpis(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_OPIS)));
	 
	           // Add to the DB
	           artikli.add(artikal);
			cursor.moveToNext();
		}

		cursor.close();
		return artikli;
	}

	private Artikal parseArtikal(Cursor cursor) {
		Artikal artikal = new Artikal();
		artikal.setId((cursor.getInt(0)));
		artikal.setNaziv(cursor.getString(1));
		artikal.setOpis(cursor.getString(2));
		artikal.setCijena(cursor.getFloat(3));
		artikal.setBarkod(cursor.getInt(4));
		return artikal;
	}



}
