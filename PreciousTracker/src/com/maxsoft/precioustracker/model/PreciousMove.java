package com.maxsoft.precioustracker.model;

import java.io.Serializable;
import java.util.Date;

public class PreciousMove implements Serializable {

    public static final String INTENT_MESSAGE = "intent.move";

    private static final long serialVersionUID = 0;
    long _id;
    long itemId;
    String itemName;
    String toWhere;
    Date dateMoved;
    String fromWhere;
    String snapshot;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getToWhere() {
        return toWhere;
    }

    public void setToWhere(String toWhere) {
        this.toWhere = toWhere;
    }

    public Date getDateMoved() {
        return dateMoved;
    }

    public void setDateMoved(Date dateMoved) {
        this.dateMoved = dateMoved;
    }

    public String getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(String fromWhere) {
        this.fromWhere = fromWhere;
    }

    /**
     * Gets the snapshot image file path.
     * 
     * @return the file path of the snapshot image or null if no snapshot
     */
    public String getSnapshot() {
        return snapshot;
    }

    /**
     * Sets the snapshot image file path.
     * 
     * @param snapshot
     *            the file path of the snapshot image file
     */
    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getItemId() {
        return itemId;
    }

}
