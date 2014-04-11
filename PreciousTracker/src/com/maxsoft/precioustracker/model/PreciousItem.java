package com.maxsoft.precioustracker.model;

import java.io.Serializable;

public class PreciousItem implements Serializable {

	public static final String INTENT_MESSAGE = "intent.item";

	private static final long serialVersionUID = 0;
	long _id;
	private String name;
	private String category;
	private String location;
	private String lastMoved;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLastMoved() {
		return lastMoved;
	}

	public void setLastMoved(String lastMoved) {
		this.lastMoved = lastMoved;
	}

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}

	@Override
	public String toString() {
		return name;
	}

}
