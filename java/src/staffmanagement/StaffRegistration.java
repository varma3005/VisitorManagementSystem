package staffmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class StaffRegistration {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project_db"; // Replace with your database URL
    private static final String DB_USER = "root"; // Replace with your database username
    private static final String DB_PASSWORD = "Vaishu@3005"; // Replace with your database password

    public void register() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Staff Registration");

            System.out.print("Enter Staff ID: ");
            String staff_id = scanner.nextLine();

            System.out.print("Enter First Name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter Last Name: ");
            String lastName = scanner.nextLine();

            System.out.print("Enter Designation: ");
            String designation = scanner.nextLine();

            System.out.print("Enter Email: ");
            String email = scanner.nextLine();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String insertQuery = "INSERT INTO staff_registration (staff_id,first_name, last_name, designation, email, password) VALUES (?, ?, ?,?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    preparedStatement.setString(1, staff_id);
                    preparedStatement.setString(2, firstName);
                    preparedStatement.setString(3, lastName);
                    preparedStatement.setString(4, designation);
                    preparedStatement.setString(5, email);
                    preparedStatement.setString(6, password);

                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Staff registration successful. Welcome, " + firstName + "!");
                    } else {
                        System.out.println("Failed to complete the registration.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error: Unable to complete the registration.");
                e.printStackTrace();
            }

            
            StaffLogin login = new StaffLogin();
            login.login();
        }
    }
}
