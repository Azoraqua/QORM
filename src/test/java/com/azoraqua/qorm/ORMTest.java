package com.azoraqua.qorm;

import com.azoraqua.qorm.analyser.Analyser;
import com.azoraqua.qorm.analyser.TableData;
import com.azoraqua.qorm.sql.*;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public final class ORMTest {

    private static final Role ROLE = RoleFactory.of(1, "Admin");
    private static final User USER = UserFactory.of(1, "Dummy", "Dummy123", ROLE);

    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private static QORM orm;
    private static Analyser analyser;

    @BeforeAll
    public static void setup() {
        try (InputStream is = new FileInputStream(new File("src/test/resources/config.properties"))) {
            final Properties properties = new Properties();
            properties.load(is);

            orm = new QORM(properties);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot setup ORMTest", e);
        }

        analyser = new Analyser();
    }

    @BeforeEach
    public void analyse() {
        analyser.analyse(USER);
        analyser.describe();
    }

    @Test
    public void testGenerateSQLCreateTable() {
        final Generator generator = new CreateTableGenerator();
        final Optional<TableData> optUsersTable = analyser.getTableData(User.class);

        if (optUsersTable.isPresent()) {
            final String sql = generator.generate(optUsersTable.get(), analyser.getColumnData(User.class));
            Assertions.assertEquals(
                "CREATE TABLE IF NOT EXISTS Users (id INTEGER(1) NOT NULL AUTO_INCREMENT, name VARCHAR(5) NOT NULL, password VARCHAR(32) NOT NULL, role OTHER(31) NOT NULL, PRIMARY KEY (id));",
                sql
            );
            System.out.println(sql);
        } else {
            throw new IllegalStateException("Table does not exist in data-set.");
        }

        final Optional<TableData> optRolesTable = analyser.getTableData(Role.class);

        if (optRolesTable.isPresent()) {
            final String sql = generator.generate(optRolesTable.get(), analyser.getColumnData(Role.class));
            Assertions.assertEquals(
                "CREATE TABLE IF NOT EXISTS Roles (id INTEGER(1) NOT NULL AUTO_INCREMENT, name VARCHAR(5) NOT NULL, PRIMARY KEY (id));",
                sql
            );
            System.out.println(sql);
        } else {
            throw new IllegalStateException("Table does not exist in data-set.");
        }
    }

    @Test
    public void testGenerateSQLDropTable() {
        final Generator generator = new DropTableGenerator();

        String sql = generator.generate(analyser.getTableData(User.class).orElseThrow(), analyser.getColumnData(User.class));
        Assertions.assertEquals("DROP TABLE Users;", sql);
        System.out.println(sql);

        sql = generator.generate(analyser.getTableData(Role.class).orElseThrow(), analyser.getColumnData(Role.class));
        Assertions.assertEquals("DROP TABLE Roles;", sql);
        System.out.println(sql);
    }

    @Test
    public void testGenerateSQLSelect() {
        final Generator generator = new SelectGenerator();

        String sql = generator.generate(analyser.getTableData(User.class).orElseThrow(), analyser.getColumnData(User.class));
        Assertions.assertEquals("SELECT * FROM Users;", sql);
        System.out.println(sql);

        sql = generator.generate(analyser.getTableData(Role.class).orElseThrow(), analyser.getColumnData(Role.class));
        Assertions.assertEquals("SELECT * FROM Roles;", sql);
        System.out.println(sql);
    }

    @Test
    public void testGenerateSQLSelectById() {
        final Generator generator = new SelectByIdGenerator();

        String sql = generator.generate(analyser.getTableData(User.class).orElseThrow(), analyser.getColumnData(User.class));
        Assertions.assertEquals("SELECT * FROM Users WHERE id=:id;", placeholderify(sql));
        System.out.println(sql);

        sql = generator.generate(analyser.getTableData(Role.class).orElseThrow(), analyser.getColumnData(Role.class));
        Assertions.assertEquals("SELECT * FROM Roles WHERE id=:id;", placeholderify(sql));
        System.out.println(sql);
    }

    @Test
    public void testGenerateSQLDelete() {
        final Generator generator = new DeleteGenerator();

        String sql = generator.generate(analyser.getTableData(User.class).orElseThrow(), analyser.getColumnData(User.class));
        Assertions.assertEquals("DELETE FROM Users;", sql);
        System.out.println(sql);

        sql = generator.generate(analyser.getTableData(Role.class).orElseThrow(), analyser.getColumnData(Role.class));
        Assertions.assertEquals("DELETE FROM Roles;", sql);
        System.out.println(sql);
    }

    @Test
    public void testGenerateSQLDeleteById() {
        final Generator generator = new DeleteByIdGenerator();

        String sql = generator.generate(analyser.getTableData(User.class).orElseThrow(), analyser.getColumnData(User.class));
        Assertions.assertEquals("DELETE FROM Users WHERE id=:id;", placeholderify(sql));
        System.out.println(sql);

        sql = generator.generate(analyser.getTableData(Role.class).orElseThrow(), analyser.getColumnData(Role.class));
        Assertions.assertEquals("DELETE FROM Roles WHERE id=:id;", placeholderify(sql));
        System.out.println(sql);
    }

    @AfterEach
    public void teardown() {
        analyser.cleanup();
    }

    private String placeholderify(String str) {
        return str.replaceAll("=[0-9]+", "=:id")
            .replaceAll("=[a-zA-Z]+", "=:name");
    }
}
