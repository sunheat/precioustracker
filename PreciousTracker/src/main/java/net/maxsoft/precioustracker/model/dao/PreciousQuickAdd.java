package net.maxsoft.precioustracker.model.dao;

import de.greenrobot.dao.DaoException;

import java.io.Serializable;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table PRECIOUS_QUICK_ADD.
 */
public class PreciousQuickAdd implements Serializable {

    private Long id;
    private java.util.Date dateTaken;
    private String photoFilePath;
    private String memoText;
    private String memoRecordingFilePath;
    private Boolean processed;
    private Long regardingItem;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient PreciousQuickAddDao myDao;

    private PreciousItem preciousItem;
    private Long preciousItem__resolvedKey;


    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public PreciousQuickAdd() {
    }

    public PreciousQuickAdd(Long id) {
        this.id = id;
    }

    public PreciousQuickAdd(Long id, java.util.Date dateTaken, String photoFilePath, String memoText, String memoRecordingFilePath, Boolean processed, Long regardingItem) {
        this.id = id;
        this.dateTaken = dateTaken;
        this.photoFilePath = photoFilePath;
        this.memoText = memoText;
        this.memoRecordingFilePath = memoRecordingFilePath;
        this.processed = processed;
        this.regardingItem = regardingItem;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPreciousQuickAddDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public java.util.Date getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(java.util.Date dateTaken) {
        this.dateTaken = dateTaken;
    }

    public String getPhotoFilePath() {
        return photoFilePath;
    }

    public void setPhotoFilePath(String photoFilePath) {
        this.photoFilePath = photoFilePath;
    }

    public String getMemoText() {
        return memoText;
    }

    public void setMemoText(String memoText) {
        this.memoText = memoText;
    }

    public String getMemoRecordingFilePath() {
        return memoRecordingFilePath;
    }

    public void setMemoRecordingFilePath(String memoRecordingFilePath) {
        this.memoRecordingFilePath = memoRecordingFilePath;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public Long getRegardingItem() {
        return regardingItem;
    }

    public void setRegardingItem(Long regardingItem) {
        this.regardingItem = regardingItem;
    }

    /** To-one relationship, resolved on first access. */
    public PreciousItem getPreciousItem() {
        Long __key = this.regardingItem;
        if (preciousItem__resolvedKey == null || !preciousItem__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PreciousItemDao targetDao = daoSession.getPreciousItemDao();
            PreciousItem preciousItemNew = targetDao.load(__key);
            synchronized (this) {
                preciousItem = preciousItemNew;
            	preciousItem__resolvedKey = __key;
            }
        }
        return preciousItem;
    }

    public void setPreciousItem(PreciousItem preciousItem) {
        synchronized (this) {
            this.preciousItem = preciousItem;
            regardingItem = preciousItem == null ? null : preciousItem.getId();
            preciousItem__resolvedKey = regardingItem;
        }
    }

    /** Convenient call for {@link de.greenrobot.dao.AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link de.greenrobot.dao.AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link de.greenrobot.dao.AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
