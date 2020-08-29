package com.azoraqua.qorm.sql;

import com.azoraqua.qorm.analyser.ColumnData;
import com.azoraqua.qorm.analyser.TableData;
import com.azoraqua.qorm.com.azoraqua.qorm.hasher.DefaultHasher;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CreateTableGenerator implements Generator {

    @Override
    public String generate(TableData td, ColumnData... cds) {
        final StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ")
            .append(td.getName())
            .append(" (");

        sb.append(Stream.of(cds).map(this::columnToString).collect(Collectors.joining(", ")));

        if (Stream.of(cds).anyMatch(ColumnData::isPrimary)) {
            sb.append(", ").append("PRIMARY KEY (")
                .append(Stream.of(cds)
                    .filter(ColumnData::isPrimary)
                    .map(ColumnData::getName)
                    .collect(Collectors.joining(", ")))
                .append(")");
        }

        sb.append(");");

        return sb.toString().trim();
    }

    private String columnToString(ColumnData c) {
        final StringBuilder sb = new StringBuilder();

        sb.append(c.getName())
            .append(" ")
            .append(c.getSqlType())
            .append("(");

        if (c.getHasher().getClass().equals(DefaultHasher.class)) {
            sb.append(c.getLength());
        } else {
            sb.append(c.getHashedValueLength());
        }

        sb.append(")");

        if (!c.isNullable()) {
            sb.append(" ")
                .append("NOT NULL");
        }

        if (c.isAutoIncrement()) {
            sb.append(" ")
                .append("AUTO_INCREMENT");
        }

        return sb.toString().trim();
    }
}
