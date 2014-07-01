package net.maxsoft.precioustracker.model;

import android.provider.BaseColumns;

public class PreciousItemTable extends PreciousTrackerTables {

	static final String SQL_CREATE = "CREATE TABLE " + PreciousItemTable.TABLE_NAME + "(" + PreciousItemEntry._ID + " INTEGER PRIMARY KEY,"
	        + PreciousItemEntry.COLUMN_NAME_ITEM_NAME + TEXT_TYPE + COMMA_SEP + PreciousItemEntry.COLUMN_NAME_CATEGORY_ID + " REFERENCES "
	        + PreciousCategoryTable.TABLE_NAME + COMMA_SEP + PreciousItemEntry.COLUMN_NAME_LOC + TEXT_TYPE + COMMA_SEP + PreciousItemEntry.COLUMN_NAME_LAST_MOVED
	        + INT_TYPE + COMMA_SEP + PreciousItemEntry.COLUMN_NAME_DATE_CREATED + INT_TYPE + COMMA_SEP + PreciousItemEntry.COLUMN_NAME_PHOTO + TEXT_TYPE + ")";
	public static final String TABLE_NAME = "PreciousItem";

	public PreciousItemTable() {
		// empty constructor
	}

	public static abstract class PreciousItemEntry implements BaseColumns {
		public static final String COLUMN_NAME_CATEGORY_ID = "category_id";
		public static final String COLUMN_NAME_ITEM_NAME = "item_name";
		public static final String COLUMN_NAME_LOC = "location";
		public static final String COLUMN_NAME_LAST_MOVED = "last_Moved";
		public static final String COLUMN_NAME_DATE_CREATED = "date_created";
		public static final String COLUMN_NAME_PHOTO = "item_photo";
	}

}
