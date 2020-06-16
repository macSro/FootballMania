package DatabaseUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private String password;
    private String user;
    private String url;
    private Connection connection;


    public DBConnection(String password, String user, String url) {
        this.password = password;
        this.user = user;
        this.url = url;
    }

    public void connectDB(){
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}





