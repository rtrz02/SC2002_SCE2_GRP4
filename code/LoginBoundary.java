import java.util.InputMismatchException;
import java.util.Scanner;
public class LoginBoundary {
    private final Scanner scanner;

    public LoginBoundary(Scanner scanner) {
        this.scanner = scanner;
    }
    public void displayLoginScreen() {
        System.out.println("===============================================");
        System.out.println("     Internship Placement Management System    ");
        System.out.println("===============================================");
        System.out.println();
    }
    public int getUserTypeSelection() {
        int userType = 0;
        while (userType < 1 || userType > 3) {
            System.out.println("Logging in as... ");
            System.out.println("1. Student");
            System.out.println("2. Company Representative");
            System.out.println("3. Staff");
            System.out.print("Enter your choice (1-3): ");
            userType = getMenuChoice();
        }
        return userType;
    }
    public String getUsername() {
        System.out.print("Enter your username: ");
        return scanner.nextLine();
    }
    public String getPassword() {
        System.out.print("Enter your password: ");
        return scanner.nextLine();
    }
    public void displayLoginSuccess(String userName) {
        System.out.println("Login successful! Welcome " + userName);
    }
    public void displayLoginPasswordFailure() {
        System.out.println("Invalid password. Please try again.");
    }
    public void displayLoginInvalidUser() {
        System.out.println("Invalid username. Please try again.");
    }
    public void displayAccountPending() {
        System.out.println("Your account is still pending approval.");
    }
    public void displayAccountRejected() {
        System.out.println("Your account has been rejected. Please contact Career Center.");
    }
    public boolean askToRegister() {
        System.out.println("Account not found. Would you like to register? (y/n)");
        String response = scanner.nextLine();
        return "y".equalsIgnoreCase(response);
    }
    public int getMenuChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            return choice;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Please enter a valid number!");
            return -1;
        }
    }
}
