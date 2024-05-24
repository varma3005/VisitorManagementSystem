package visitormanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class VisitorAppointment {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project_db"; 
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = "Vaishu@3005"; 

    public void createAppointment(String email) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Create Visitor Appointment");

            System.out.print("Enter Staff Member: ");
            String staffMember = scanner.nextLine();

            System.out.print("Enter Designation of Staff Member: ");
            String designation = scanner.nextLine();

            System.out.print("Enter Visiting Time: ");
            String visitingTime = scanner.nextLine();

            System.out.print("Enter Purpose of Visit: ");
            String purposeOfVisit = scanner.nextLine();

            
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String insertQuery = "INSERT INTO appointment (staff_member, designation, visiting_time, purpose_of_visit, email, confirmation) VALUES (?, ?, ?, ?, ?, 'no')";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                    preparedStatement.setString(1, staffMember);
                    preparedStatement.setString(2, designation);
                    preparedStatement.setString(3, visitingTime);
                    preparedStatement.setString(4, purposeOfVisit);
                    preparedStatement.setString(5, email);

                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            int appointmentId = generatedKeys.getInt(1);
                            System.out.println("Appointment created successfully. Your Appointment ID is: " + appointmentId);
                        } else {
                            System.out.println("Failed to create the appointment.");
                        }
                    } else {
                        System.out.println("Failed to create the appointment.");
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error: Unable to create the appointment.");
                e.printStackTrace();
            }

            redirectHome(email);
        }
    }

    private void redirectHome(String email) {
        VisitorHomePage homePage = new VisitorHomePage();
        homePage.displayHomePage(email);
    }
}
