package hostmanagement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminHomepage {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Vaishu@3005"; 

    public void displayHomepage() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Host Home Page");

            System.out.print("Enter Visitor Email: ");
            String visitorEmail = scanner.nextLine();

            System.out.print("Enter Appointment ID: ");
            int appointmentId = scanner.nextInt();
            scanner.nextLine();

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String query = "SELECT confirmation FROM appointment WHERE email = ? AND appointment_id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, visitorEmail);
                    preparedStatement.setInt(2, appointmentId);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            String confirmation = resultSet.getString("confirmation");
                            if (confirmation.equalsIgnoreCase("yes")) {
                                System.out.println("Allow the visitor.");
                            } else {
                                System.out.println("Reject the visitor.");
                            }
                        } else {
                            System.out.println("No appointment found for the provided email and appointment ID.");
                        }
                        System.exit(0);
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error: Unable to fetch visitor appointment details.");
                e.printStackTrace();
            }
        }

        System.exit(0);
    }
}