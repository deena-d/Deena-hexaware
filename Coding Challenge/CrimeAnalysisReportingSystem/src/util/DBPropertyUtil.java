package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {

    public static Properties getPropertyString(String fileName) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(fileName)) {
            props.load(fis);
        } catch (IOException e) {
            System.out.println("Error reading property file: " + e.getMessage());
        }
        return props;
    }
}