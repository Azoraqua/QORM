package com.azoraqua.qorm;

import com.azoraqua.qorm.analyser.Analyser;
import com.azoraqua.qorm.analyser.ColumnData;
import com.azoraqua.qorm.analyser.Data;
import com.azoraqua.qorm.analyser.TableData;
import com.azoraqua.qorm.sql.CreateTableGenerator;
import com.azoraqua.qorm.sql.Generator;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
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
    }

    @Test
    public void testDescribe() {
        analyser.describe();
    }

    @Test
    public void testGenerateSQLUsersTable() {
        final Generator generator = new CreateTableGenerator();
        final List<Data> dataList = analyser.getData();

        final Optional<TableData> optUsersTable = dataList.stream()
            .filter(d -> d instanceof TableData)
            .map(d -> (TableData) d)
            .filter(d -> d.getType().equals(User.class))
            .findAny();

        if (optUsersTable.isPresent()) {
            final ColumnData[] columns = dataList.stream()
                .filter(d -> d instanceof ColumnData)
                .map(d -> (ColumnData) d)
                .filter(d -> d.getParent().equals(optUsersTable.get()))
                .toArray(ColumnData[]::new);

            final String sql = generator.generate(optUsersTable.get(), columns);
            Assertions.assertEquals(
                "CREATE TABLE IF NOT EXISTS Users (id INTEGER(1) NOT NULL AUTO_INCREMENT, name VARCHAR(5) NOT NULL, password VARCHAR(32) NOT NULL, role OTHER(31) NOT NULL, PRIMARY KEY (id));",
                sql
            );
            System.out.println(sql);
        } else {
            throw new IllegalStateException("Table does not exist in data-set.");
        }
    }

    @Test
    public void testGenerateSQLRolesTable() {
        final Generator generator = new CreateTableGenerator();
        final List<Data> dataList = analyser.getData();

        final Optional<TableData> optRolesTable = dataList.stream()
            .filter(d -> d instanceof TableData)
            .map(d -> (TableData) d)
            .filter(d -> d.getType().equals(Role.class))
            .findAny();

        if (optRolesTable.isPresent()) {
            final ColumnData[] columns = dataList.stream()
                .filter(d -> d instanceof ColumnData)
                .map(d -> (ColumnData) d)
                .filter(d -> d.getParent().equals(optRolesTable.get()))
                .toArray(ColumnData[]::new);

            final String sql = generator.generate(optRolesTable.get(), columns);
            Assertions.assertEquals(
                "CREATE TABLE IF NOT EXISTS Roles (id INTEGER(1) NOT NULL AUTO_INCREMENT, name VARCHAR(5) NOT NULL, PRIMARY KEY (id));",
                sql
            );
            System.out.println(sql);
        } else {
            throw new IllegalStateException("Table does not exist in data-set.");
        }
    }

    @AfterEach
    public void teardown() {
        analyser.cleanup();
    }
}
