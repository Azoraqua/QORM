import com.azoraqua.qorm.QORM;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public final class ORMTest {

    private static QORM orm;

    @BeforeAll
    public static void setup() {
        try (InputStream is = new FileInputStream(new File("src/test/resources/config.properties"))) {
            final Properties properties = new Properties();
            properties.load(is);

            orm = new QORM(properties);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot setup ORMTest", e);
        }
    }

    @Test
    public void test() {
        orm.analyse(UserFactory.of(1, "Dummy", "Dummy123", RoleFactory.of(1, "Admin")));
    }
}
