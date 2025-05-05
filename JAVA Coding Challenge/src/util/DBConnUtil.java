package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnUtil {

    public static Connection getConnection() throws SQLException {
        Properties props = DBPropertyUtil.getConnectionString();
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String pass = props.getProperty("db.password");

        return DriverManager.getConnection(url, user, pass);
    }
}
