package com.azoraqua.qorm;

public final class RoleFactory {

    public static Role of(int id, String name) {
        final Role role = new Role();
        role.id = id;
        role.name = name;

        return role;
    }
}
