package visitormanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentConfirmation {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project_db"; 
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = "Vaishu@3005"; 

    public void getConfirmedAppointments(String email) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM appointment WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.isBeforeFirst()) {
                        System.out.println("Appointments for " + email + ":");
                        System.out.println("-------------------------------------");
                        System.out.printf("%-15s%-20s%-20s%-20s%-20s%-10s\n", "Appointment ID", "Staff Member", "Designation", "Visiting Time", "Purpose of Visit", "Confirmation");
                        System.out.println("-------------------------------------");
                        while (resultSet.next()) {
                            int appointmentId = resultSet.getInt("appointment_id");
                            String staffMember = resultSet.getString("staff_member");
                            String designation = resultSet.getString("designation");
                            String visitingTime = resultSet.getString("visiting_time");
                            String purposeOfVisit = resultSet.getString("purpose_of_visit");
                            String confirmation = resultSet.getString("confirmation");
                            System.out.printf("%-15s%-20s%-20s%-20s%-20s%-10s\n", appointmentId, staffMember, designation, visitingTime, purposeOfVisit, confirmation);
                        }
                        System.out.println("-------------------------------------");
                    } else {
                        System.out.println("No appointments found for " + email + ".");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        redirectHome();
    }

    private void redirectHome() {
        VisitorHomePage homePage = new VisitorHomePage();
        
        String visitorEmail = "example@example.com"; 
        homePage.displayHomePage(visitorEmail);
    }
}
