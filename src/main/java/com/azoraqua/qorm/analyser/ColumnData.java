package com.azoraqua.qorm.analyser;

import java.sql.JDBCType;

public final class ColumnData implements Data {

    protected String name;
    protected JDBCType sqlType;
    protected boolean primary, autoIncrement, nullable;
    protected Class<?> type;
    protected Object value;

    public ColumnData(String name, JDBCType sqlType, boolean primary, boolean autoIncrement, boolean nullable, Class<?> type, Object value) {
        this.name = name;
        this.sqlType = sqlType;
        this.primary = primary;
        this.autoIncrement = autoIncrement;
        this.nullable = nullable;
        this.type = type;
        this.value = value;
    }

    // Only internal.
    protected ColumnData() {
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

    public Class<?> getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ColumnData { " +
            "name='" + name + '\'' +
            ", sqlType=" + sqlType.getName() +
            ", primary=" + primary +
            ", autoIncrement=" + autoIncrement +
            ", nullable=" + nullable +
            ", type=" + type.getSimpleName() +
            ", value=" + value +
            " }";
    }
}
