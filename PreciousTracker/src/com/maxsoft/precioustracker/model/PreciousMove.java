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

	public String getSnapshot() {
		return snapshot;
	}

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
