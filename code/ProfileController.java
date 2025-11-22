import java.util.Scanner;

// controller class for managing user profile operations
public class ProfileController {
    private final Scanner scanner;

    public ProfileController(Scanner scanner) {
        this.scanner = scanner;
    }

    // change user password
    public boolean changePassword(User user) {
        System.out.print("Enter current password: ");
        String currentPass = scanner.nextLine();

        if (currentPass.equals(user.getPassword())) {
            System.out.print("Enter new password: ");
            String newPass = scanner.nextLine();
            user.changePassword(newPass);
            System.out.println("Password changed successfully!");
            return true;
        } else {
            System.out.println("Current password is incorrect!");
            return false;
        }
    }

    // view user profile details
    public void viewProfile(User user) {
        System.out.println("\n=== YOUR PROFILE ===");
        System.out.println("Name: " + user.getName());
        System.out.println("ID: " + user.getID());

        if (user instanceof Student) {
            Student student = (Student) user;
            System.out.println("Year of Study: " + student.getYearOfStudy());
            System.out.println("Major: " + student.getMajor());
        } else if (user instanceof CompanyRepresentative) {
            CompanyRepresentative rep = (CompanyRepresentative) user;
            System.out.println("Company: " + rep.getCompanyName());
            System.out.println("Department: " + rep.getDepartment());
            System.out.println("Position: " + rep.getPosition());
            System.out.println("Status: " + rep.getStatus());
        } else if (user instanceof CareerCenterStaff) {
            CareerCenterStaff staff = (CareerCenterStaff) user;
            System.out.println("Department: " + staff.getStaffDepartment());
        }
    }
}
