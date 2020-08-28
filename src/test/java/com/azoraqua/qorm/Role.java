package com.azoraqua.qorm;

import com.azoraqua.qorm.annotation.Column;
import com.azoraqua.qorm.annotation.Table;

import java.io.Serializable;

@Table(name = "Roles")
public final class Role implements Serializable {

    @Column
    protected int id;

    @Column
    protected String name;
}
