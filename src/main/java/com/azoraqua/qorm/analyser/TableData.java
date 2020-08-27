package com.azoraqua.qorm.analyser;

public final class TableData implements Data {

    protected Class<?> type;
    protected int hash;
    protected String name;

    public TableData(Class<?> type, int hash, String name) {
        this.type = type;
        this.hash = hash;
        this.name = name;
    }

    // Only internal.
    protected TableData() {
    }

    public Class<?> getType() {
        return type;
    }

    public int getHash() {
        return hash;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "TableData { " +
            "type=" + type.getSimpleName() +
            ", hash=" + Integer.toHexString(hash) +
            ", name=" + name +
            " }";
    }
}
