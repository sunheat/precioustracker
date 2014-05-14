package com.maxsoft.precioustracker.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;

import com.maxsoft.precioustracker.PreciousCategory;
import com.maxsoft.precioustracker.model.PreciousCategoryTable.PreciousCategoryEntry;
import com.maxsoft.precioustracker.model.PreciousItemTable.PreciousItemEntry;
import com.maxsoft.precioustracker.model.PreciousMoveTable.PreciousMoveEntry;

public class PreciousTrackerModel {

	public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final String PHOTO_DIR = "PreciousTrackerSnapshots";
	public static final int REQ_CODE_ADD_MOVE = 2336;

	public static final String INTENT_MSG_MODEL = "precioustracker.intent.model";
	public static final String INTENT_MSG_REFRESH_MOVE_LIST = "precioustracker.intent.refresh_move_list";
	public static final String INTENT_MSG_REFRESH_ITEM_LIST = "precioustracker.intent.refresh_item_list";
	public static final String INTENT_MSG_REFRESH_CATEGORY_LIST = "precioustracker.intent.refresh_category_list";
	public static final String IMG_FORMAT_STRING = "yyyyMMdd_HHmmss";
	public static final int LOADER_ID_MOVE_LIST = 6578;
	public static final long CREATE_NEW_ITEM_ID = -1;
	public static final long CREATE_NEW_CATEGORY_ID = -1;
	public static final int REQ_CODE_CREATE_ITEM = 2486;
	public static final int REQ_CODE_CREATE_CATEGORY = 2483;

	public static final String EXTRA_KEY_NEW_ITEM_ID = "precioustacker.extras.new_item_id";

	private static PreciousTrackerModel instance;

	private PreciousTrackerDbHelper dbHelper;
	private LocalBroadcastManager broadcastManager;

	protected PreciousTrackerModel(Context context) {
		if (dbHelper == null) {
			dbHelper = new PreciousTrackerDbHelper(context);
			broadcastManager = LocalBroadcastManager.getInstance(context);
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
			int nameIdx = c.getColumnIndex(PreciousItemEntry.COLUMN_NAME_ITEM_NAME);
			item.setName(c.getString(nameIdx));
			int locIdx = c.getColumnIndex(PreciousItemEntry.COLUMN_NAME_LOC);
			item.setLocation(c.getString(locIdx));
			int lastMovedIdx = c.getColumnIndex(PreciousItemEntry.COLUMN_NAME_LAST_MOVED);
			item.setLastMoved(c.getString(lastMovedIdx));
			int catNameIdx = c.getColumnIndex(PreciousCategoryEntry.COLUMN_NAME_NAME);
			item.setCategoryName(c.getString(catNameIdx));
			int catIdx = c.getColumnIndex(PreciousItemEntry.COLUMN_NAME_CATEGORY_ID);
			item.setCategoryId(c.getLong(catIdx));
			int dateCreatedIdx = c.getColumnIndex(PreciousItemEntry.COLUMN_NAME_DATE_CREATED);
			item.setDateCreated(new Date(c.getLong(dateCreatedIdx)));
			int photoIdx = c.getColumnIndex(PreciousItemEntry.COLUMN_NAME_PHOTO);
			item.setPhotoFilePath(c.getString(photoIdx));
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
			int itemNameIdx = c.getColumnIndex(PreciousItemEntry.COLUMN_NAME_ITEM_NAME);
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
		return insertSuccessful & updateSuccessful; // TODO change algorithm
	}

	public void broadcast(String intentMessage) {
		Intent intent = new Intent(intentMessage);
		broadcastManager.sendBroadcast(intent);
	}

	public void registerBroadcastReceiver(BroadcastReceiver receiver, String action) {
		IntentFilter filter = new IntentFilter(action);
		broadcastManager.registerReceiver(receiver, filter);
	}

	public List<PreciousCategory> getCategoryList() {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String[] columns = new String[] { PreciousCategoryEntry._ID, PreciousCategoryEntry.COLUMN_NAME_NAME };
		Cursor c = db.query(PreciousCategoryTable.TABLE_NAME, columns, null, null, null, null, null, null);
		List<PreciousCategory> result = new ArrayList<PreciousCategory>(c.getCount());
		while (c.moveToNext()) {
			PreciousCategory category = new PreciousCategory();
			int idIdx = c.getColumnIndex(PreciousCategoryEntry._ID);
			category.set_id(c.getLong(idIdx));
			int nameIdx = c.getColumnIndex(PreciousCategoryEntry.COLUMN_NAME_NAME);
			category.setName(c.getString(nameIdx));
			result.add(category);
		}
		return result;
	}

	public long insertNewCategory(PreciousCategory newCategory) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues categoryValues = new ContentValues();
		categoryValues.put(PreciousCategoryEntry.COLUMN_NAME_NAME, newCategory.getName());
		return db.insert(PreciousCategoryTable.TABLE_NAME, null, categoryValues);
	}

	public long insertNewItem(PreciousItem newItem) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(PreciousItemEntry.COLUMN_NAME_ITEM_NAME, newItem.getName());
		values.put(PreciousItemEntry.COLUMN_NAME_LOC, newItem.getLocation());
		values.put(PreciousItemEntry.COLUMN_NAME_CATEGORY_ID, newItem.getCategoryId());
		values.put(PreciousItemEntry.COLUMN_NAME_LAST_MOVED, new Date().getTime());
		return db.insert(PreciousItemTable.TABLE_NAME, null, values);
	}
}
