package com.maxsoft.precioustracker.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.maxsoft.precioustracker.model.PreciousCategoryTable.PreciousCategoryEntry;
import com.maxsoft.precioustracker.model.PreciousItemTable.PreciousItemEntry;
import com.maxsoft.precioustracker.model.PreciousMoveTable.PreciousMoveEntry;

public class PreciousTrackerModel {

	public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final String PHOTO_DIR = "PreciousTrackerSnapshots";

	private PreciousTrackerDbHelper dbHelper;
	private static PreciousTrackerModel instance;

	public static final String INTENT_MSG_MODEL = "precioustracker.intent.model";

	protected PreciousTrackerModel(Context context) {
		if (dbHelper == null) {
			dbHelper = new PreciousTrackerDbHelper(context);
		}
	}

	public static PreciousTrackerModel getInstance(Context context) {
		if (instance == null) {
			instance = new PreciousTrackerModel(context);
		}
		return instance;
	}

	public List<PreciousItem> getItemList() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery(PreciousTrackerDbHelper.ITEM_QUERY, null);
		int size = c.getCount();
		List<PreciousItem> results = new ArrayList<PreciousItem>(size);
		while (c.moveToNext()) {
			PreciousItem item = new PreciousItem();
			int idIdx = c.getColumnIndex(PreciousItemEntry._ID);
			item._id = c.getLong(idIdx);
			int nameIdx = c.getColumnIndex(PreciousItemEntry.COLUMN_NAME_NAME);
			item.setName(c.getString(nameIdx));
			int locIdx = c.getColumnIndex(PreciousItemEntry.COLUMN_NAME_LOC);
			item.setLocation(c.getString(locIdx));
			int dateIdx = c.getColumnIndex(PreciousItemEntry.COLUMN_NAME_DATETIME);
			item.setLastMoved(c.getString(dateIdx));
			int catIdx = c.getColumnIndex(PreciousCategoryEntry.COLUMN_NAME_NAME);
			item.setCategory(c.getString(catIdx));
			results.add(item);
		}
		return results;
	}

	public List<PreciousMove> getRecentMoves() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery(PreciousTrackerDbHelper.MOVE_QUERY, null);
		int size = c.getCount();
		List<PreciousMove> results = new ArrayList<PreciousMove>(size);
		while (c.moveToNext()) {
			PreciousMove listItem = new PreciousMove();
			int idIdx = c.getColumnIndex(PreciousMoveEntry._ID);
			listItem._id = c.getLong(idIdx);
			int itemIdIdx = c.getColumnIndex(PreciousMoveEntry.COLUMN_NAME_ITEM_ID);
			listItem.itemId = c.getLong(itemIdIdx);
			int itemNameIdx = c.getColumnIndex(PreciousItemEntry.COLUMN_NAME_NAME);
			listItem.itemName = c.getString(itemNameIdx);
			int dateMovedIndx = c.getColumnIndex(PreciousMoveEntry.COLUMN_NAME_DATE);
			long dateMoved = c.getLong(dateMovedIndx);
			listItem.dateMoved = new Date(dateMoved);
			int fromWhereIdx = c.getColumnIndex(PreciousMoveEntry.COLUMN_NAME_FROM);
			listItem.fromWhere = c.getString(fromWhereIdx);
			int locationIdx = c.getColumnIndex(PreciousMoveEntry.COLUMN_NAME_TO);
			listItem.toWhere = c.getString(locationIdx);
			int snapshotIdx = c.getColumnIndex(PreciousMoveEntry.COLUMN_SNAPSHOT);
			listItem.snapshot = c.getString(snapshotIdx);
			results.add(listItem);
		}
		return results;
	}

	public long insertNewMove(PreciousMove newMove) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues moveValues = new ContentValues();
		moveValues.put(PreciousMoveEntry.COLUMN_NAME_ITEM_ID, newMove.getItemId());
		moveValues.put(PreciousMoveEntry.COLUMN_NAME_DATE, newMove.getDateMoved().getTime());
		moveValues.put(PreciousMoveEntry.COLUMN_NAME_FROM, newMove.getFromWhere());
		moveValues.put(PreciousMoveEntry.COLUMN_NAME_TO, newMove.getToWhere());
		moveValues.put(PreciousMoveEntry.COLUMN_SNAPSHOT, newMove.getSnapshot());
		long insertSuccessful = db.insert(PreciousMoveTable.TABLE_NAME, null, moveValues);

		db = dbHelper.getWritableDatabase();
		ContentValues itemValues = new ContentValues();
		itemValues.put(PreciousItemEntry.COLUMN_NAME_LOC, newMove.toWhere);
		String whereClause = PreciousItemEntry._ID + "=?";
		String[] whereArgs = { String.valueOf(newMove.getItemId()) };
		long updateSuccessful = db.update(PreciousItemTable.TABLE_NAME, itemValues, whereClause, whereArgs);
		return insertSuccessful & updateSuccessful;
	}

}
