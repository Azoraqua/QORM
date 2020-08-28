package com.azoraqua.qorm.analyser;

import com.azoraqua.qorm.com.azoraqua.qorm.hasher.Hasher;

import java.sql.JDBCType;

@SuppressWarnings("unused")
public final class ColumnData implements Data {

    private final TableData parent;
    private final String name;
    private final JDBCType sqlType;
    private final boolean primary;
    private final boolean autoIncrement;
    private final boolean nullable;
    private final Class<? extends Hasher> hasher;
    private final Class<?> type;
    private final Object value;
    private final int length;

    public ColumnData(TableData parent, String name, JDBCType sqlType, boolean primary, boolean autoIncrement, boolean nullable, Class<? extends Hasher> hasher, Class<?> type, Object value, int length) {
        this.parent = parent;
        this.name = name;
        this.sqlType = sqlType;
        this.primary = primary;
        this.autoIncrement = autoIncrement;
        this.nullable = nullable;
        this.hasher = hasher;
        this.type = type;
        this.value = value;
        this.length = length;
    }

    public TableData getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public JDBCType getSqlType() {
        return sqlType;
    }

    public boolean isPrimary() {
        return primary;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public boolean isNullable() {
        return nullable;
    }

    public Class<? extends Hasher> getHasher() {
        return hasher;
    }

    public Class<?> getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "ColumnData { " +
            "name=" + name +
            ", sqlType=" + sqlType.getName() +
            ", primary=" + primary +
            ", autoIncrement=" + autoIncrement +
            ", nullable=" + nullable +
            ", hasher=" + hasher.getSimpleName() +
            ", type=" + type.getSimpleName() +
            ", value=" + value +
            ", length=" + length +
            " }";
    }
}
