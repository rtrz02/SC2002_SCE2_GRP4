import java.util.Scanner;

// boundary class for handling menu-related user interface
public class MenuBoundary {
    private final Scanner scanner;

    public MenuBoundary(Scanner scanner) {
        this.scanner = scanner;
    }

    // display main menu and get choice
    public int displayMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Profile Management");
        System.out.println("2. Role-specific Functions");
        System.out.println("3. Browse Internships");
        System.out.println("4. Logout");
        System.out.println("5. Switch off program");
        System.out.print("Enter your choice: ");
        return getMenuChoice();
    }

    // display profile management menu and get choice
    public int displayProfileMenu() {
        System.out.println("\n=== PROFILE MANAGEMENT ===");
        System.out.println("1. Change Password");
        System.out.println("2. View Profile");

        int choice = 0;
        while (choice < 1 || choice > 2) {
            System.out.print("Enter your choice: ");
            choice = getMenuChoice();
        }
        return choice;
    }

    // display student functions menu and get choice
    public int displayStudentMenu() {
        System.out.println("\n=== STUDENT FUNCTIONS ===");
        System.out.println("1. Apply for Internship");
        System.out.println("2. View My Applications");
        System.out.println("3. Accept Internship Offer");
        System.out.println("4. View Accepted Internship");
        System.out.println("5. Request Withdrawal");
        return getMenuChoice();
    }

    // display company representative functions menu and get choice
    public int displayCompanyRepMenu() {
        System.out.println("\n=== COMPANY REPRESENTATIVE FUNCTIONS ===");
        System.out.println("1. Create Internship Opportunity");
        System.out.println("2. View Applications");
        System.out.println("3. Approve/Reject Applications");
        System.out.println("4. Toggle Internship Visibility");
        System.out.println("5. View My Internships");
        System.out.println("6. Back to Main Menu");
        System.out.print("Enter your choice: ");
        return getMenuChoice();
    }

    // display staff functions menu and get choice
    public int displayStaffMenu() {
        System.out.println("\n=== STAFF FUNCTIONS ===");
        System.out.println("1. Authorize Company Representatives");
        System.out.println("2. Approve/Reject Internships");
        System.out.println("3. Process Withdrawal Requests");
        System.out.println("4. Generate Reports");
        System.out.print("Enter your choice: ");
        return getMenuChoice();
    }

    // display internship browsing menu and get choice
    public int displayBrowsingMenu() {
        System.out.println("\n=== INTERNSHIP BROWSING MENU ===");
        System.out.println("1. Quick Search (No Filters)");
        System.out.println("2. Search with Filters");
        System.out.println("3. View Current Filter Settings");
        System.out.println("4. Reset Filters to Default");
        System.out.println("5. Save Filter Settings");
        System.out.println("6. Back to Main Menu");
        System.out.print("Enter your choice: ");
        return getMenuChoice();
    }

    // display filter configuration menu and get choice
    public int displayFilterMenu(InternshipFilter filter) {
        System.out.println("\n=== CONFIGURE FILTERS ===");
        System.out.println("1. Set Status: " + filter.getStatus());
        System.out.println("2. Set Preferred Major: " + filter.getPreferredMajor());
        System.out.println("3. Set Internship Level: " + filter.getInternshipLevel());
        System.out.println("4. Set Company Name: " +
                (filter.getCompanyName().isEmpty() ? "Any" : filter.getCompanyName()));
        System.out.println("5. Set Closing Date Range");
        System.out.println("6. Set Sort By: " + filter.getSortBy() +
                " (" + (filter.isSortAscending() ? "Ascending" : "Descending") + ")");
        System.out.println("7. Apply Filters and Search");
        System.out.println("8. Back to Browsing Menu");
        System.out.print("Enter your choice: ");
        return getMenuChoice();
    }

    // display report generation menu and get choice
    public int displayReportMenu() {
        System.out.println("\n=== GENERATE REPORTS ===");
        System.out.println("1. Quick Summary Report");
        System.out.println("2. Custom Report with Filters");
        System.out.println("3. Back to Staff Menu");
        System.out.print("Enter your choice: ");
        return getMenuChoice();
    }

    // display custom report filter menu and get choice
    public int displayCustomReportMenu(InternshipFilter filter) {
        System.out.println("\nCurrent Filter Settings:");
        filter.displayCurrentFilters();

        System.out.println("\nFilter Options:");
        System.out.println("1. Set Status Filter");
        System.out.println("2. Set Preferred Major Filter");
        System.out.println("3. Set Internship Level Filter");
        System.out.println("4. Set Company Name Filter");
        System.out.println("5. Reset All Filters");
        System.out.println("6. Generate Report with Current Filters");
        System.out.println("7. Cancel");
        System.out.print("Enter your choice: ");
        return getMenuChoice();
    }

    // get menu choice with input validation
    private int getMenuChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            return choice;
        } catch (Exception e) {
            scanner.nextLine(); // clear invalid input
            System.out.println("Please enter a valid number!");
            return -1;
        }
    }

    // display invalid choice message
    public void displayInvalidChoice() {
        System.out.println("Invalid choice. Please try again.");
    }

    // display logout message
    public void displayLogout() {
        System.out.println("Logging out...");
    }
}
