package com.azoraqua.qorm.sql;

import com.azoraqua.qorm.analyser.ColumnData;
import com.azoraqua.qorm.analyser.TableData;

public interface Generator {

    String generate(TableData td, ColumnData... cds);
}
