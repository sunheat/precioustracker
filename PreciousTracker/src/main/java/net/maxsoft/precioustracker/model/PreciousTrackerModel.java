package net.maxsoft.precioustracker.model;

import static net.maxsoft.precioustracker.model.PreciousTrackerDbHelper.DATABASE_NAME;
import static net.maxsoft.precioustracker.model.PreciousTrackerDbHelper.generateSampleData;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.maxsoft.precioustracker.model.dao.*;
import net.maxsoft.precioustracker.model.dao.DaoMaster.DevOpenHelper;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.query.LazyList;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;

public class PreciousTrackerModel {

    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static final String PHOTO_DIR = "PreciousTrackerSnapshots";
    public static final int REQ_CODE_ADD_MOVE = 2336;

    public static final String INTENT_MSG_MODEL = "precioustracker.intent.model";
    public static final String INTENT_MSG_REFRESH_MOVE_LIST = "precioustracker.intent.refresh_move_list";
    public static final String INTENT_MSG_REFRESH_ITEM_LIST = "precioustracker.intent.refresh_item_list";
    public static final String INTENT_MSG_REFRESH_CATEGORY_LIST = "precioustracker.intent.refresh_category_list";
    public static final String INTENT_MSG_QUICK_ADD_ID = "precioustracker.intent.quickAddId";

    public static final String IMG_FORMAT_STRING = "yyyyMMdd_HHmmss";
    public static final int LOADER_ID_MOVE_LIST = 6578;
    public static final long CREATE_NEW_ITEM_ID = -1;
    public static final long CREATE_NEW_CATEGORY_ID = -1;
    public static final int REQ_CODE_CREATE_ITEM = 2486;
    public static final int REQ_CODE_CREATE_CATEGORY = 2483;

    public static final String EXTRA_KEY_NEW_ITEM_ID = "precioustacker.extras.new_item_id";
    public static final String EXTRA_PHOTO_FILE_PATH = "showPhotoIntent";
    public static final String EXTRA_KEY_NEW_CATEGORY_ID = "precioustacker.extras.new_category_id";

    private static PreciousTrackerModel instance;

    private DevOpenHelper dbHelper;
    private LocalBroadcastManager broadcastManager;

    protected PreciousTrackerModel(Context context) {
        if (dbHelper == null) {
            dbHelper = new DaoMaster.DevOpenHelper(context, DATABASE_NAME, null);
            generateSampleData(dbHelper);
            broadcastManager = LocalBroadcastManager.getInstance(context);
        }
    }

    public static PreciousTrackerModel getInstance(Context context) {
        if (instance == null) {
            instance = new PreciousTrackerModel(context);
        }
        return instance;
    }

    /**
     * Returns a lazy-loading list of {@link PreciousItem} objects. The list
     * MUST be closed after use.
     * 
     * @return a {@link LazyList} for accessing {@link PreciousItem} objects
     */
    public LazyList<PreciousItem> getItemList() {
        LazyList<PreciousItem> itemList = getAllRecords(PreciousItem.class);
        return itemList;
    }

    /**
     * Returns a lazy-loading list of {@link PreciousMove} objects. The list
     * MUST be closed after use.
     * 
     * @return a {@link LazyList} for accessing {@link PreciousMove} objects
     */
    public LazyList<PreciousMove> getRecentMoves() {
        LazyList<PreciousMove> moveList = getAllRecords(PreciousMove.class);
        return moveList;
    }

    /**
     * Returns a lazy-loading list of {@link PreciousCategory} objects. The list
     * MUST be closed after use.
     * 
     * @return a {@link LazyList} for accessing {@link PreciousCategory} objects
     */
    public LazyList<PreciousCategory> getCategoryList() {
        LazyList<PreciousCategory> catList = getAllRecords(PreciousCategory.class);
        return catList;
    }

    private <T> LazyList<T> getAllRecords(Class<T> type) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        DaoMaster dm = new DaoMaster(db);
        DaoSession session = dm.newSession();
        QueryBuilder<T> qb = session.queryBuilder(type);
        LazyList<T> lazyList = qb.listLazy();
        return lazyList;
    }

    public long insertNewCategory(PreciousCategory newCategory) {
        return insertNewRecord(newCategory);
    }

    public long insertNewItem(PreciousItem newItem) {
        return insertNewRecord(newItem);
    }

    public long insertNewMove(PreciousMove newMove) {
        return insertNewRecord(newMove);
    }

    public long insertNewQuickAdd(PreciousQuickAdd quickAdd) {
        return insertNewRecord(quickAdd);
    }

    @SuppressWarnings("unchecked")
    private <T> long insertNewRecord(T dataObject) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        DaoMaster dm = new DaoMaster(db);
        DaoSession session = dm.newSession();
        AbstractDao<T, Long> dao = (AbstractDao<T, Long>) session.getDao(dataObject.getClass());
        return dao.insert(dataObject);
    }

    public void broadcast(String intentMessage) {
        Intent intent = new Intent(intentMessage);
        broadcastManager.sendBroadcast(intent);
    }

    public void registerBroadcastReceiver(BroadcastReceiver receiver, String action) {
        IntentFilter filter = new IntentFilter(action);
        broadcastManager.registerReceiver(receiver, filter);
    }

    /**
     * Gets the snapshot picture file path.
     * 
     * @return the file path of the snapshot taken.
     */
    @SuppressLint("SimpleDateFormat")
    public String getOutputMediaFilePath() {
        // checks whether external storage is mounted
        String sdState = Environment.getExternalStorageState();
        if (sdState.equals(Environment.MEDIA_MOUNTED)) {
            // if mounted, use external storage for storing snapshots
            File mediaDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    PreciousTrackerModel.PHOTO_DIR);
            // checks to see if the directory exists for this app
            if (!mediaDir.exists()) {
                boolean s = mediaDir.mkdirs();
                if (!s) {
                    Log.e(PreciousTrackerModel.PHOTO_DIR, "failed to create dir");
                    return null;
                }
            }
            DateFormat formatter = new SimpleDateFormat(PreciousTrackerModel.IMG_FORMAT_STRING);
            String timeStamp = formatter.format(new Date());
            File mediaFile = new File(mediaDir.getPath() + File.separator + timeStamp + ".jpg");
            return mediaFile.getPath();
        } else {
            // else, use internal storage
            // TODO location of snapshot using internal storage
            return null;
        }
    }

    public PreciousCategory getCategory(Long catId) {
        WhereCondition condition = PreciousCategoryDao.Properties.Id.eq(catId);
        List<PreciousCategory> list = getRecord(PreciousCategory.class, condition);
        return list.get(0);
    }

    private <T> List<T> getRecord(Class<T> clz, WhereCondition condition) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        DaoMaster master = new DaoMaster(db);
        DaoSession session = master.newSession();
        QueryBuilder<T> qb = session.queryBuilder(clz);
        Query<T> query = qb.where(condition).build();
        return query.list();
    }

}
