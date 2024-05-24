package visitormanagement;
import java.util.InputMismatchException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class VisitorLogin {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/project_db"; 
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = "Vaishu@3005"; 

    private VisitorRegistration visitorRegistration; 

    public VisitorLogin() {
        visitorRegistration = new VisitorRegistration();
    }

    public void login() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Visitor Login");    
            System.out.print("Enter Username (Email): ");
            
            
            String email = null;
            if (scanner.hasNextLine()) {
                email = scanner.nextLine();
            } else {
                System.out.println("Error: No input found for email.");
                return; 
            }
    
            System.out.print("Enter Password: ");
            
            
            String password = null;
            if (scanner.hasNextLine()) {
                password = scanner.nextLine();
            } else {
                System.out.println("Error: No input found for password.");
                return; 
            }
        

            if (isValidCredentials(email, password)) {
                 System.out.println("Login successful. Welcome, " + email + "!");
                VisitorHomePage visitorHomePage = new VisitorHomePage();
                visitorHomePage.displayHomePage(email); 
            } else {
                System.out.println("Login failed. Invalid username or password.");

                while (true) {
                    System.out.print("Do you want to register? (Y/N): ");
                    String choice = scanner.nextLine();

                    if (choice.equalsIgnoreCase("Y")) {
                        visitorRegistration.register();
                        break;
                    } else if (choice.equalsIgnoreCase("N")) {
                        System.out.println("You can still book an appointment! Please register for a better experience.");
                        //break;
                    } else {
                        System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                    }
                }  
            }
            
        }
        catch (InputMismatchException e) {
                System.out.println("Error: Invalid input format.");
            }

    }

    private boolean isValidCredentials(String email, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT COUNT(*) FROM visitor_registration WHERE email = ? AND password = ?";
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
