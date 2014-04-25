package com.maxsoft.precioustracker.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.maxsoft.precioustracker.model.PreciousCategoryTable.PreciousCategoryEntry;
import com.maxsoft.precioustracker.model.PreciousItemTable.PreciousItemEntry;
import com.maxsoft.precioustracker.model.PreciousMoveTable.PreciousMoveEntry;

public class PreciousTrackerDbHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "PreciousTracker.db";
	public static final String MOVE_QUERY = "SELECT move." + PreciousMoveEntry._ID + "," + PreciousMoveEntry.COLUMN_NAME_DATE + ","
	        + PreciousMoveEntry.COLUMN_NAME_FROM + "," + PreciousMoveEntry.COLUMN_NAME_TO + "," + PreciousMoveEntry.COLUMN_NAME_ITEM_ID + ","
	        + PreciousMoveEntry.COLUMN_SNAPSHOT + "," + PreciousItemEntry.COLUMN_NAME_NAME + " FROM " + PreciousMoveTable.TABLE_NAME + " as move JOIN "
	        + PreciousItemTable.TABLE_NAME + " as item ON item." + PreciousItemEntry._ID + "=move." + PreciousMoveEntry.COLUMN_NAME_ITEM_ID;
	public static final String ITEM_QUERY = "SELECT item." + PreciousItemEntry._ID + ",item." + PreciousItemEntry.COLUMN_NAME_NAME + ",item."
	        + PreciousItemEntry.COLUMN_NAME_LOC + ",item." + PreciousItemEntry.COLUMN_NAME_DATETIME + ",cat." + PreciousCategoryEntry.COLUMN_NAME_NAME
	        + ",item." + PreciousItemEntry.COLUMN_NAME_CATEGORY_ID + " FROM " + PreciousItemTable.TABLE_NAME + " as item JOIN "
	        + PreciousCategoryTable.TABLE_NAME + " as cat ON cat." + PreciousCategoryEntry._ID + " =item." + PreciousItemEntry.COLUMN_NAME_CATEGORY_ID;

	public PreciousTrackerDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// db.execSQL(PreciousActivityTable.SQL_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
		// no logic here
	}

	public void onOpen(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + PreciousMoveTable.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + PreciousItemTable.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + PreciousCategoryTable.TABLE_NAME);

		db.execSQL(PreciousCategoryTable.SQL_CREATE);
		db.execSQL(PreciousItemTable.SQL_CREATE);
		db.execSQL(PreciousMoveTable.SQL_CREATE);

		ContentValues catValues = new ContentValues();
		catValues.put(PreciousCategoryEntry.COLUMN_NAME_NAME, "Electronics");
		long catId = db.insert(PreciousCategoryTable.TABLE_NAME, null, catValues);

		ContentValues itemValues = new ContentValues();
		itemValues.put(PreciousItemEntry.COLUMN_NAME_NAME, "GP AA rechargable");
		itemValues.put(PreciousItemEntry.COLUMN_NAME_CATEGORY_ID, catId);
		itemValues.put(PreciousItemEntry.COLUMN_NAME_LOC, "clock");
		itemValues.put(PreciousItemEntry.COLUMN_NAME_DATETIME, System.currentTimeMillis());
		long itemId = db.insert(PreciousItemTable.TABLE_NAME, null, itemValues);

		ContentValues itemValues2 = new ContentValues();
		itemValues2.put(PreciousItemEntry.COLUMN_NAME_NAME, "Sanyo AAA rechargable");
		itemValues2.put(PreciousItemEntry.COLUMN_NAME_CATEGORY_ID, catId);
		itemValues2.put(PreciousItemEntry.COLUMN_NAME_LOC, "toy");
		itemValues2.put(PreciousItemEntry.COLUMN_NAME_DATETIME, System.currentTimeMillis());
		long itemId2 = db.insert(PreciousItemTable.TABLE_NAME, null, itemValues2);

		ContentValues moveValues = new ContentValues();
		moveValues.put(PreciousMoveEntry.COLUMN_NAME_ITEM_ID, itemId);
		moveValues.put(PreciousMoveEntry.COLUMN_NAME_DATE, System.currentTimeMillis() - 3600 * 24);
		moveValues.put(PreciousMoveEntry.COLUMN_NAME_FROM, "charger");
		moveValues.put(PreciousMoveEntry.COLUMN_NAME_TO, "clock");
		db.insert(PreciousMoveTable.TABLE_NAME, null, moveValues);

		ContentValues moveValues2 = new ContentValues();
		moveValues2.put(PreciousMoveEntry.COLUMN_NAME_ITEM_ID, itemId2);
		moveValues2.put(PreciousMoveEntry.COLUMN_NAME_DATE, System.currentTimeMillis() - 3600 * 48);
		moveValues2.put(PreciousMoveEntry.COLUMN_NAME_FROM, "charger");
		moveValues2.put(PreciousMoveEntry.COLUMN_NAME_TO, "toy");
		db.insert(PreciousMoveTable.TABLE_NAME, null, moveValues2);
	}

}
