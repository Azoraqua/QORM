package com.azoraqua.qorm;

import com.azoraqua.qorm.annotation.Column;
import com.azoraqua.qorm.annotation.Table;

@Table(name = "Roles")
public final class Role {

    @Column(primary = true, auto = true)
    protected int id;

    @Column
    protected String name;
}
