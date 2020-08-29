package com.azoraqua.qorm.analyser;

import com.azoraqua.qorm.com.azoraqua.qorm.hasher.Hasher;

import java.sql.JDBCType;
import java.util.Arrays;

@SuppressWarnings("unused")
public final class ColumnData implements Data {

    private final TableData parent;
    private final String name;
    private final JDBCType sqlType;
    private final boolean primary;
    private final boolean autoIncrement;
    private final boolean nullable;
    private final Hasher hasher;
    private final Class<?> type;
    private final Object value;
    private final int length;
    private final Object hashedValue;
    private final int hashedValueLength;
    private final int desiredLength;

    public ColumnData(TableData parent, String name, JDBCType sqlType, boolean primary, boolean autoIncrement, boolean nullable, Hasher hasher, Class<?> type, Object value, int length, Object hashedValue, int hashedValueLength, int desiredLength) {
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
        this.hashedValue = hashedValue;
        this.hashedValueLength = hashedValueLength;
        this.desiredLength = desiredLength;
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

    public Hasher getHasher() {
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

    public Object getHashedValue() {
        return hashedValue;
    }

    public Object getHashedValueLength() {
        return hashedValueLength;
    }

    @Override
    public String toString() {
        return "ColumnData { " +
            "name=" + name +
            ", sqlType=" + sqlType.getName() +
            ", primary=" + primary +
            ", autoIncrement=" + autoIncrement +
            ", nullable=" + nullable +
            ", hasher=" + hasher.getClass().getSimpleName() +
            ", type=" + type.getSimpleName() +
            ", value=" + value +
            ", length=" + length +
            ", hashedValue=" + Arrays.toString((byte[]) hashedValue) +
            ", hashedValueLength=" + hashedValueLength +
            ", desiredLength=" + desiredLength +
            " }";
    }
}
