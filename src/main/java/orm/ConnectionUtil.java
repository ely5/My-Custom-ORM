package orm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class ConnectionUtil {
    private static String url = "jdbc:postgresql://cardealership.cu2djz5cez21.us-east-2.rds.amazonaws.com/postgres?currentSchema=orm";
    private static String user = "liz";
    private static String password = "bob";
    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();
    private static int INITIAL_POOL_SIZE = 10;

//    public static orm.ConnectionUtil create(String url, String user, String password) throws SQLException {
//
//        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
//        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
//            pool.add(connect());
//        }
//    }

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



    public Connection getConnection() {
        Connection connection = connectionPool
                .remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

}
