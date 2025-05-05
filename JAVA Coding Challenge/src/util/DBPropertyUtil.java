package util;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {
    public static Properties getConnectionString() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/database.properties")) {
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }
}
