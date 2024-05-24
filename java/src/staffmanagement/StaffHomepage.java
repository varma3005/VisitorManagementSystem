package staffmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

//import javax.management.Notification;

public class StaffHomepage {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project_db"; 
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = "Vaishu@3005"; 

    public int getStaffIdByEmail(String email) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT staff_id FROM staff_registration WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("staff_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; 
    }

    public void displayHomepage(String email) {
        int staffId = getStaffIdByEmail(email);
        if (staffId != -1) {
            System.out.println("Welcome to Staff Homepage, " + email + "!");
            System.out.println("Your Staff ID: " + staffId);

            
            retrieveVisitorAppointments(staffId);

            
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.print("Enter the Appointment ID to confirm (or 0 to exit): ");
                int appointmentId = scanner.nextInt();
                scanner.nextLine(); 

                if (appointmentId == 0) {
                    System.out.println("Exiting staff homepage.");
                } else {
                    boolean confirmationStatus = confirmVisitorAppointment(appointmentId);
                    if (confirmationStatus) {
                        System.out.println("Appointment with ID " + appointmentId + " confirmed.");
                    } else {
                        System.out.println("Failed to confirm the appointment with ID " + appointmentId + ".");
                    }
                }
            }
        } else {
            System.out.println("Staff ID not found for the given email.");
        }
    }

    private void retrieveVisitorAppointments(int staffId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM appointment WHERE staff_member = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, staffId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    System.out.println("Visitor Appointments for Staff ID: " + staffId);
                    while (resultSet.next()) {
                        int appointmentId = resultSet.getInt("appointment_id");
                        String Email= resultSet.getString("email");
                        String visitingTime = resultSet.getString("visiting_time");
                        String purposeOfVisit = resultSet.getString("purpose_of_visit");
                        String confirmation = resultSet.getString("confirmation");

                        System.out.println("Appointment ID: " + appointmentId);
                        System.out.println("Email : " + Email);
                        System.out.println("Visiting Time: " + visitingTime);
                        System.out.println("Purpose of Visit: " + purposeOfVisit);
                        System.out.println("Confirmation Status: " + confirmation);
                        System.out.println("------------------------------");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: Unable to fetch visitor appointments.");
            e.printStackTrace();
        }
    }
    private boolean confirmVisitorAppointment(int appointmentId) {
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
        String selectQuery = "SELECT * FROM appointment WHERE appointment_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, appointmentId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Appointment found. Confirm (Yes/No): ");
                    Scanner scanner = new Scanner(System.in);
                    String confirmationChoice = scanner.nextLine().toLowerCase();
                    if (confirmationChoice.equals("yes") || confirmationChoice.equals("no")) {
                        String updateQuery = "UPDATE appointment SET confirmation = ? WHERE appointment_id = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setString(1, confirmationChoice);
                            updateStatement.setInt(2, appointmentId);
                            int rowsAffected = updateStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Appointment with ID " + appointmentId + " confirmed: " + confirmationChoice);
                                // Redirect to Notification class to notify the visitor
                                //Notification notification = new Notification();
                                //notification.notifyVisitor(appointmentId, confirmationChoice);
                                
                            } else {
                                System.out.println("Failed to update confirmation status for appointment with ID " + appointmentId + ".");
                            }
                            System.exit(0);
                            return true;
                        }
                    } else {
                        System.out.println("Invalid input. Please enter 'Yes' or 'No'.");
                    }
                    scanner.close();
                } else {
                    System.out.println("Appointment with ID " + appointmentId + " not found.");
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Error: Unable to confirm the visitor appointment.");
        e.printStackTrace();
    }
    
    
    return false;
}

}