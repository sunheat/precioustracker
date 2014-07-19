package net.maxsoft.precioustracker.model;

import java.util.Date;

import net.maxsoft.precioustracker.model.dao.DaoMaster;
import net.maxsoft.precioustracker.model.dao.DaoSession;
import net.maxsoft.precioustracker.model.dao.PreciousCategory;
import net.maxsoft.precioustracker.model.dao.PreciousCategoryDao;
import net.maxsoft.precioustracker.model.dao.PreciousItem;
import net.maxsoft.precioustracker.model.dao.PreciousItemDao;
import net.maxsoft.precioustracker.model.dao.PreciousMove;
import net.maxsoft.precioustracker.model.dao.PreciousMoveDao;
import net.maxsoft.precioustracker.model.dao.DaoMaster.DevOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PreciousTrackerDbHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "PreciousTracker.db";

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

    public static void generateSampleData(DevOpenHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        DaoMaster master = new DaoMaster(db);
        DaoSession session = master.newSession();
        
        // clear the database first
        session.deleteAll(PreciousMove.class);
        session.deleteAll(PreciousItem.class);
        session.deleteAll(PreciousCategory.class);
        
        // PreciousCategory
        PreciousCategory cat1 = new PreciousCategory(null, "Electronics");
        PreciousCategoryDao catDao = session.getPreciousCategoryDao();
        long cat1Id = catDao.insert(cat1);
        
        // PreciousItem
        PreciousItem item1 = new PreciousItem(null, "GP AA rechargable", "clock", new Date(), new Date(), null, cat1Id);
        PreciousItem item2 = new PreciousItem(null, "Sanyo AAA rechargable", "toy", new Date(), new Date(), null, cat1Id);
        PreciousItemDao itemDao = session.getPreciousItemDao();
        long item1Id = itemDao.insert(item1);
        long item2Id = itemDao.insert(item2);
        
        // PreciousMove
        PreciousMove move1 = new PreciousMove(null, "charger", "clock", new Date(), null, item1Id);
        PreciousMove move2 = new PreciousMove(null, "charger", "toy", new Date(), null, item2Id);
        PreciousMoveDao moveDao = session.getPreciousMoveDao();
        moveDao.insert(move1);
        moveDao.insert(move2);
        db.close();
    }


}
