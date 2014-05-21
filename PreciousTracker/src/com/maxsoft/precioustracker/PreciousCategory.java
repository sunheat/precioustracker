package com.maxsoft.precioustracker;

import java.io.Serializable;

public class PreciousCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String INTENT_MESSAGE = "intent.category";
    private long _id;
    private String name;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

}
