package hostmanagement;

import java.util.Scanner;

public class HostLogin {

    public void login() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Host Login");
            System.out.print("Enter Username: ");
            String username = scanner.nextLine();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            if (isValidCredentials(username, password)) {
                System.out.println("Login successful. Welcome, " + username + "!");
                AdminHomepage adminHomepage = new AdminHomepage();
                adminHomepage.displayHomepage();
                
            } else {
                System.out.println("Login failed. Invalid username or password.");
                
            }
        }
    }

    private boolean isValidCredentials(String username, String password) {
        
        return username.equals("kmit_admin") && password.equals("kmit123$");
    }
}

