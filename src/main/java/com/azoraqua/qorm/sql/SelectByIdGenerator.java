package com.azoraqua.qorm.sql;

import com.azoraqua.qorm.analyser.ColumnData;
import com.azoraqua.qorm.analyser.TableData;

import java.util.Arrays;

public final class SelectByIdGenerator implements Generator {

    @Override
    public String generate(TableData td, ColumnData... cds) {
        return String.format("SELECT * FROM %s WHERE id=%d;", td.getName(), (int) Arrays.stream(cds).filter(ColumnData::isPrimary).findFirst().orElseThrow().getValue());
    }
}
