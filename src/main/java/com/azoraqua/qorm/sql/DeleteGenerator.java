package com.azoraqua.qorm.sql;

import com.azoraqua.qorm.analyser.ColumnData;
import com.azoraqua.qorm.analyser.TableData;

public final class DeleteGenerator implements Generator {

    @Override
    public String generate(TableData td, ColumnData... cds) {
        return String.format("DELETE FROM %s;", td.getName());
    }
}
