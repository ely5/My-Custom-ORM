import java.sql.*;

class ConnectionUtil {
    private static final String url = "jdbc:postgresql://cardealership.cu2djz5cez21.us-east-2.rds.amazonaws.com/postgres?currentSchema=orm";
    private static final String user = "liz";
    private static final String password = "bob";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
}
