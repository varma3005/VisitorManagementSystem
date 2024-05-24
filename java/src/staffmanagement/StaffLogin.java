package staffmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StaffLogin {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project_db"; 
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = "Vaishu@3005"; 

    public void login() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Staff Login");
                System.out.print("Enter Email: ");
                String email = scanner.nextLine();

                System.out.print("Enter Password: ");
                String password = scanner.nextLine();

                if (isValidCredentials(email, password)) {
                    System.out.println("Login successful. Welcome, " + email + "!");
                    StaffHomepage staffHomepage = new StaffHomepage();
                    staffHomepage.displayHomepage(email);
                    break;
                } else {
                    System.out.println("Login failed. Invalid username or password.");

                    while (true) {
                        System.out.print("Do you want to register? (Y/N): ");
                        String choice = scanner.nextLine();

                        if (choice.equalsIgnoreCase("Y")) {
                            StaffRegistration staffRegistration = new StaffRegistration();
                            staffRegistration.register();
                            break;
                        } else if (choice.equalsIgnoreCase("N")) {
                            System.out.println("You can still try to log in with correct credentials or register later.");
                            break;
                        } else {
                            System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                        }
                    }
                }
            }
        }
    }

    private boolean isValidCredentials(String email, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT COUNT(*) FROM staff_registration WHERE email = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}