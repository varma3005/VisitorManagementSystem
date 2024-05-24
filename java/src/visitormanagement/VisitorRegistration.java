package visitormanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class VisitorRegistration {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project_db"; // Replace with your database URL
    private static final String DB_USER = "root"; // Replace with your database username
    private static final String DB_PASSWORD = "Vaishu@3005"; // Replace with your database password

    public void register() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Visitor Registration");
            System.out.print("Enter First Name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter Last Name: ");
            String lastName = scanner.nextLine();

            System.out.print("Enter Email: ");
            String email = scanner.nextLine();

            System.out.print("Enter Phone Number: ");
            String phoneNumber = scanner.nextLine();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            if (isEmailAvailable(email)) {
                // Insert the visitor registration details into the database
                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String insertQuery = "INSERT INTO visitor_registration (first_name, last_name, email, phone_number, password) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                        preparedStatement.setString(1, firstName);
                        preparedStatement.setString(2, lastName);
                        preparedStatement.setString(3, email);
                        preparedStatement.setString(4, phoneNumber);
                        preparedStatement.setString(5, password);

                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Visitor registration successful. Welcome, " + firstName + "!");
                        } else {
                            System.out.println("Failed to complete the registration.");
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Error: Unable to complete the registration.");
                    e.printStackTrace();
                }
            } else {
                System.out.println("Email is already registered. Please try again with a different email.");
            }

            // Redirect back to the login screen
            VisitorLogin login = new VisitorLogin();
            login.login();
        }
    }

    private boolean isEmailAvailable(String email) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT COUNT(*) FROM visitor_registration WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count == 0; // Return true if email is not found (available for registration)
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
