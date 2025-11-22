import java.util.Scanner;

// boundary class for user registration
public class RegistrationBoundary {
    private final Scanner scanner;

    // constructor
    public RegistrationBoundary(Scanner scanner) {
        this.scanner = scanner;
    }

    // get student registration details
    public String[] getStudentRegistrationDetails() {
        System.out.println("\n=== STUDENT REGISTRATION ===");
        System.out.print("Enter your student ID: ");
        String stuID = scanner.nextLine();
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your Major: ");
        String major = scanner.nextLine();
        System.out.print("Enter your year: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        return new String[] { stuID, name, major, String.valueOf(year), email };
    }

    // get company representative registration details
    public String[] getCompanyRepRegistrationDetails() {
        System.out.println("\n=== COMPANY REPRESENTATIVE REGISTRATION ===");
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter company name: ");
        String companyName = scanner.nextLine();
        System.out.print("Enter department: ");
        String department = scanner.nextLine();
        System.out.print("Enter position: ");
        String position = scanner.nextLine();

        return new String[] { email, name, companyName, department, position };
    }

    // get staff registration details
    public String[] getStaffRegistrationDetails() {
        System.out.println("\n=== STAFF REGISTRATION ===");
        System.out.print("Enter your ID: ");
        String staffID = scanner.nextLine();
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your department: ");
        String department = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        return new String[] { staffID, name, department, email };
    }

    // display registration success message
    public void displayRegistrationSuccess(String userType) {
        System.out.println("You have registered! Default password is 'password'.");
        if (userType.equals("CompanyRep")) {
            System.out.println("Please wait for approval from Career Center Staff.");
        }
    }

    // display registration failure message
    public void displayRegistrationFailure(String error) {
        System.out.println("Registration completed but failed to save: " + error);
    }

    // display company representative ID
    public void displayCompanyRepID(String repID) {
        System.out.println("Your Company Rep ID: " + repID);
    }
}
