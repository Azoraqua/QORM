package com.azoraqua.qorm.sql;

import com.azoraqua.qorm.analyser.ColumnData;
import com.azoraqua.qorm.analyser.TableData;

public final class DropTableGenerator implements Generator {

    @Override
    public String generate(TableData td, ColumnData... cds) {
        return String.format("DROP TABLE %s;", td.getName());
    }
}
