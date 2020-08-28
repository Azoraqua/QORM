package com.azoraqua.qorm;

import com.azoraqua.qorm.annotation.Column;
import com.azoraqua.qorm.annotation.Table;

import java.io.Serializable;

@Table(name = "Users")
public final class User implements Serializable {

    @Column(primary = true, auto = true)
    protected int id;

    @Column
    protected String name;

    @Column
    protected String password;

    @Column
    protected Role role;
}
