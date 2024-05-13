
package Database;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class DatabaseConfig {
    private static final String PROPERTIES_FILE = "Database/database.properties";
    private static final Properties properties = new Properties();
    static {
        try (InputStream inputStream = DatabaseConfig.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getDriver() {
        return properties.getProperty("db.driver");
    }
    public static String getUrl() {
        return properties.getProperty("db.url");
    }
    public static String getUsername() {
        return properties.getProperty("db.username");
    }
    public static String getPassword() {
        return properties.getProperty("db.password");
    }
}
