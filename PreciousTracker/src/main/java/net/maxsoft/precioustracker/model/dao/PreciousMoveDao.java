package net.maxsoft.precioustracker.model.dao;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import net.maxsoft.precioustracker.model.dao.PreciousMove;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table PRECIOUS_MOVE.
*/
public class PreciousMoveDao extends AbstractDao<PreciousMove, Long> {

    public static final String TABLENAME = "PRECIOUS_MOVE";

    /**
     * Properties of entity PreciousMove.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property From_where = new Property(1, String.class, "from_where", false, "FROM_WHERE");
        public final static Property To_where = new Property(2, String.class, "to_where", false, "TO_WHERE");
        public final static Property Date = new Property(3, java.util.Date.class, "date", false, "DATE");
        public final static Property Snapshot = new Property(4, String.class, "snapshot", false, "SNAPSHOT");
        public final static Property Item_id = new Property(5, long.class, "item_id", false, "ITEM_ID");
    };

    private Query<PreciousMove> preciousItem_PreciousMoveListQuery;

    public PreciousMoveDao(DaoConfig config) {
        super(config);
    }
    
    public PreciousMoveDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'PRECIOUS_MOVE' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'FROM_WHERE' TEXT NOT NULL ," + // 1: from_where
                "'TO_WHERE' TEXT NOT NULL ," + // 2: to_where
                "'DATE' INTEGER NOT NULL ," + // 3: date
                "'SNAPSHOT' TEXT," + // 4: snapshot
                "'ITEM_ID' INTEGER NOT NULL );"); // 5: item_id
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'PRECIOUS_MOVE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, PreciousMove entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getFrom_where());
        stmt.bindString(3, entity.getTo_where());
        stmt.bindLong(4, entity.getDate().getTime());
 
        String snapshot = entity.getSnapshot();
        if (snapshot != null) {
            stmt.bindString(5, snapshot);
        }
        stmt.bindLong(6, entity.getItem_id());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public PreciousMove readEntity(Cursor cursor, int offset) {
        PreciousMove entity = new PreciousMove( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // from_where
            cursor.getString(offset + 2), // to_where
            new java.util.Date(cursor.getLong(offset + 3)), // date
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // snapshot
            cursor.getLong(offset + 5) // item_id
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, PreciousMove entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFrom_where(cursor.getString(offset + 1));
        entity.setTo_where(cursor.getString(offset + 2));
        entity.setDate(new java.util.Date(cursor.getLong(offset + 3)));
        entity.setSnapshot(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setItem_id(cursor.getLong(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(PreciousMove entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(PreciousMove entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "preciousMoveList" to-many relationship of PreciousItem. */
    public List<PreciousMove> _queryPreciousItem_PreciousMoveList(long item_id) {
        synchronized (this) {
            if (preciousItem_PreciousMoveListQuery == null) {
                QueryBuilder<PreciousMove> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Item_id.eq(null));
                preciousItem_PreciousMoveListQuery = queryBuilder.build();
            }
        }
        Query<PreciousMove> query = preciousItem_PreciousMoveListQuery.forCurrentThread();
        query.setParameter(0, item_id);
        return query.list();
    }

}
