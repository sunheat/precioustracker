package net.maxsoft.precioustracker.model;

import android.provider.BaseColumns;

public final class PreciousMoveTable extends PreciousTrackerTables {

	public static final String TABLE_NAME = "PreciousMove";

	static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + "(" + PreciousMoveEntry._ID + " INTEGER PRIMARY KEY,"
			+ PreciousMoveEntry.COLUMN_NAME_ITEM_ID + TEXT_TYPE + " REFERENCES " + PreciousItemTable.TABLE_NAME + COMMA_SEP + PreciousItemTable.TABLE_NAME
			+ COMMA_SEP + PreciousMoveEntry.COLUMN_NAME_DATE + INT_TYPE + COMMA_SEP + PreciousMoveEntry.COLUMN_NAME_FROM + TEXT_TYPE + COMMA_SEP
			+ PreciousMoveEntry.COLUMN_NAME_TO + TEXT_TYPE + COMMA_SEP + PreciousMoveEntry.COLUMN_SNAPSHOT + TEXT_TYPE + ")";

	public PreciousMoveTable() {
		// empty constructor
	}

	public static abstract class PreciousMoveEntry implements BaseColumns {
		public static final String COLUMN_NAME_ITEM_ID = "item_id";
		public static final String COLUMN_NAME_DATE = "date";
		public static final String COLUMN_NAME_FROM = "from_where";
		public static final String COLUMN_NAME_TO = "to_where";
		public static final String COLUMN_SNAPSHOT = "snapshot";
	}

}
