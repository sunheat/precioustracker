package net.maxsoft.precioustracker.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import net.maxsoft.precioustracker.model.dao.*;

import java.util.Date;

import static net.maxsoft.precioustracker.model.dao.DaoMaster.OpenHelper;

public class PreciousTrackerDbHelper extends OpenHelper {

    public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "PreciousTracker.db";

	public PreciousTrackerDbHelper(Context context) {
        super(context, DATABASE_NAME, null);
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
		// no logic here
	}

    public void generateSampleData() {
        SQLiteDatabase db = getWritableDatabase();
        DaoMaster master = new DaoMaster(db);
        DaoSession session = master.newSession();

        // clear the database first
        session.deleteAll(PreciousMove.class);
        session.deleteAll(PreciousItem.class);
        session.deleteAll(PreciousCategory.class);
        session.deleteAll(PreciousQuickAdd.class);

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

        // PreciousQuickAdd
        PreciousQuickAdd quickAdd = new PreciousQuickAdd(null, new Date(), "", "", "", false, null);
        PreciousQuickAddDao quickAddDao = session.getPreciousQuickAddDao();
        quickAddDao.insert(quickAdd);

        db.close();
    }


}
