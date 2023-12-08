package Database;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@NoArgsConstructor @AllArgsConstructor
public class connector {

    Properties properties = loadPropertiesFile();

    private String jdbcUrl = properties.getProperty("db.url");
    private String username = properties.getProperty("db.username");
    private String password = properties.getProperty("db.password");

    public Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(this.jdbcUrl, this.username, this.password);
        connection.setAutoCommit(true);

        return connection;
    }

    private Properties loadPropertiesFile() {
        Properties properties = new Properties();
        try (InputStream input = connector.class.getClassLoader().getResourceAsStream(".properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find .properties");
                return properties;
            }
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }
}
