import java.util.Scanner;
import visitormanagement.VisitorLogin;
import staffmanagement.StaffLogin;
import hostmanagement.HostLogin;

public class LoginSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Login System!");

        while (true) {
            System.out.println("Please select your login type:");
            System.out.println("1. Visitor");
            System.out.println("2. Host");
            System.out.println("3. Staff");
            System.out.println("0. Exit");

            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character after reading the integer
                
                switch (choice) {
                    case 1:
                        VisitorLogin visitorLogin = new VisitorLogin();
                        visitorLogin.login();
                        break;
                    case 2:
                        HostLogin hostLogin = new HostLogin();
                        hostLogin.login();
                        break;
                    case 3:
                        StaffLogin staffLogin = new StaffLogin();
                        staffLogin.login();
                        break;
                    
                    default:
                        System.out.println("Exiting the Login System. Goodbye!");
                        scanner.close();
                        System.exit(0);
                }
            
            } else {
                System.out.println("Invalid input. Please enter a valid choice.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }
}
