package spendingdb;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbLogin {

    // Update these values with your actual database information
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/spendingdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkLogin(String username, String password) {
        Connection connection = connect();
        if (connection != null) {
            try {
                String query = "SELECT * FROM users WHERE username = ? AND password = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    return resultSet.next(); // Returns true if there is a matching record
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeConnection(connection);
            }
        }
        return false;
    }

    // Example method to register a new user
    public static boolean registerUser(String username, String password) {
        Connection connection = connect();
        if (connection != null) {
            try {
                String query = "INSERT INTO users (username, password) VALUES (?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    int rowsAffected = preparedStatement.executeUpdate();
                    return rowsAffected > 0; // Returns true if the registration was successful
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeConnection(connection);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        // Example of using registerUser method
        String newUsername = "new_user";
        String newPassword = "new_password";
        if (registerUser(newUsername, newPassword)) {
            System.out.println("Registration successful");
        } else {
            System.out.println("Registration failed");
        }
    }
}