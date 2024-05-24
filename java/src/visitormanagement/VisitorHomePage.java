package visitormanagement;

import java.util.Scanner;

public class VisitorHomePage {
    public void displayHomePage(String email) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to Visitor Home Page");
            System.out.println("Please select an option:");
            System.out.println("1. Create Appointment");
            System.out.println("2. Appointment Confirmation");
            System.out.println("3. Logout");

            int choice; 
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        VisitorAppointment visitorAppointment = new VisitorAppointment();
                        visitorAppointment.createAppointment(email);
                        break;
                    case 2:
                        AppointmentConfirmation appointmentConfirmation = new AppointmentConfirmation();
                        appointmentConfirmation.getConfirmedAppointments(email);
                        break;
                    
                    default:
                    System.out.println("Logging out. Goodbye!");
                        
                        scanner.close();
                        System.exit(0);
                        
                }
            } else {
                System.out.println("Invalid input. Please enter a valid choice.");
                scanner.nextLine(); 
            }
        }
    }
}
