package com.maxsoft.precioustracker.model;

import android.provider.BaseColumns;

public class PreciousCategoryTable extends PreciousTrackerTables {

	public static final String TABLE_NAME = "PreciousCategory";

	public PreciousCategoryTable() {
		// empty constructor
	}

	public static abstract class PreciousCategoryEntry implements BaseColumns {
		public static final String COLUMN_NAME_NAME = "cat_name";
	}

	public static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + "(" + PreciousCategoryEntry._ID + " INTEGER PRIMARY KEY,"
			+ PreciousCategoryEntry.COLUMN_NAME_NAME + TEXT_TYPE + ")";

}
