package com.azoraqua.qorm.analyser;

@SuppressWarnings("unused")
public final class TableData implements Data {

    private final Class<?> type;
    private final int hash;
    private final String name;

    public TableData(Class<?> type, int hash, String name) {
        this.type = type;
        this.hash = hash;
        this.name = name;
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
