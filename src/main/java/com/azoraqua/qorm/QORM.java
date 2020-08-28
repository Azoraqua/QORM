package com.azoraqua.qorm;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public final class QORM {

    @SuppressWarnings("FieldCanBeLocal")
    private static MysqlDataSource source;

    public QORM(Properties properties) {
        this.init(properties);
    }

    @SuppressWarnings("unused")
    public <T> void save(T obj) {
        // TODO: Implement.
    }

    @SuppressWarnings("unused")
    public <T> T load(Class<? extends T> clazz) {
        return null; // TODO: Implement.
    }

    private void init(Properties properties) {
        source = new MysqlDataSource();
        source.setURL(properties.getProperty("url"));
        source.setUser(properties.getProperty("user"));
        source.setPassword(properties.getProperty("password"));

        try (Connection c = source.getConnection()) {
            ;
            ;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
