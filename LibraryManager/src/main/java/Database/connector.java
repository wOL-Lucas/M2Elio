package Database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@NoArgsConstructor @AllArgsConstructor
public class connector {
    private String jdbcUrl = "jdbc:mysql://18.216.230.107:3306/library";
    private String username = "eliosProject";
    private String password = "professor";


    public Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(this.jdbcUrl,this.username,this.password);
        connection.setAutoCommit(true);

        return connection;
    }

}
