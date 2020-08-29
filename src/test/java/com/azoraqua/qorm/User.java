package com.azoraqua.qorm;

import com.azoraqua.qorm.annotation.Column;
import com.azoraqua.qorm.annotation.Table;
import com.azoraqua.qorm.com.azoraqua.qorm.hasher.SHA256Hasher;

import java.io.Serializable;

@Table(name = "Users")
public final class User implements Serializable {

    @Column(primary = true, auto = true, length = 11)
    protected int id;

    @Column(length = 16)
    protected String name;

    @Column(hasher = SHA256Hasher.class, length = 255)
    protected String password;

    @Column
    protected Role role;
}
