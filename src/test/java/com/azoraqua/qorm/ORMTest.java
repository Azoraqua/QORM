package com.azoraqua.qorm;

import com.azoraqua.qorm.analyser.Analyser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
    public void test_describe() {
        analyser.describe();
    }

    @AfterEach
    public void teardown() {
        analyser.cleanup();
    }
}
