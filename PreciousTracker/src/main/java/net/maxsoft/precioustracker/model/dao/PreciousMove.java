package net.maxsoft.precioustracker.model.dao;

import java.io.Serializable;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table PRECIOUS_MOVE.
 */
public class PreciousMove implements Serializable {

    private static final long serialVersionUID = 77347804304036176L;
    
    public static final String INTENT_MESSAGE = "intent.PreciousMove";
    
    private Long id;
    /** Not-null value. */
    private String from_where;
    /** Not-null value. */
    private String to_where;
    /** Not-null value. */
    private java.util.Date date;
    private String snapshot;
    private long item_id;

    public PreciousMove() {
    }

    public PreciousMove(Long id) {
        this.id = id;
    }

    public PreciousMove(Long id, String from_where, String to_where, java.util.Date date, String snapshot, long item_id) {
        this.id = id;
        this.from_where = from_where;
        this.to_where = to_where;
        this.date = date;
        this.snapshot = snapshot;
        this.item_id = item_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getFrom_where() {
        return from_where;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setFrom_where(String from_where) {
        this.from_where = from_where;
    }

    /** Not-null value. */
    public String getTo_where() {
        return to_where;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTo_where(String to_where) {
        this.to_where = to_where;
    }

    /** Not-null value. */
    public java.util.Date getDate() {
        return date;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public long getItem_id() {
        return item_id;
    }

    public void setItem_id(long item_id) {
        this.item_id = item_id;
    }

}
