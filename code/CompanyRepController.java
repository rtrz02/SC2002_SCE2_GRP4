import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CompanyRepController {
    public static void createInternshipOpportunity(CompanyRepresentative rep, Scanner scanner, List<Internship> internships) {
        System.out.println("\n=== CREATE INTERNSHIP OPPORTUNITY ===");
        
        try {
            List<Internship> companyInternships = rep.getInternships();
            if (companyInternships.size() >= 5) {
                System.out.println("ERROR: You have reached the maximum limit of 5 internships.");
                System.out.println("You currently have " + companyInternships.size() + " internship(s).");
                System.out.println("Please manage your existing internships before creating new ones.");
                return;
            }
            
            System.out.println("You currently have " + companyInternships.size() + "/5 internships.");
            System.out.print("Enter internship title: ");
            String title = scanner.nextLine().trim();
            if (title.isEmpty()) {
                System.out.println("ERROR: Internship title cannot be empty.");
                return;
            }
            
            System.out.print("Enter description: ");
            String description = scanner.nextLine().trim();
            if (description.isEmpty()) {
                System.out.println("ERROR: Internship description cannot be empty.");
                return;
            }
            String level;
            while (true) {
                System.out.print("Enter level (Basic/Intermediate/Advanced): ");
                level = scanner.nextLine().trim();
                if (level.equals("Basic") || level.equals("Intermediate") || level.equals("Advanced")) {
                    break;
                }
                System.out.println("ERROR: Invalid level. Please enter Basic, Intermediate, or Advanced.");
            }
            
            System.out.print("Enter preferred major: ");
            String preferredMajor = scanner.nextLine().trim();
            if (preferredMajor.isEmpty()) {
                System.out.println("ERROR: Preferred major cannot be empty.");
                return;
            }
            Date openingDate = null;
            Date closingDate = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);

            while (openingDate == null) {
                System.out.print("Enter opening date (YYYY-MM-DD): ");
                String openingDateStr = scanner.nextLine().trim();
                if (openingDateStr.isEmpty()) {
                    System.out.println("ERROR: Opening date cannot be empty.");
                    continue;
                }
                try {
                    openingDate = dateFormat.parse(openingDateStr);
                    Date today = new Date();
                    if (openingDate.before(today)) {
                        System.out.println("ERROR: Opening date cannot be in the past.");
                        openingDate = null;
                    }
                } catch (Exception e) {
                    System.out.println("ERROR: Invalid date format. Please use YYYY-MM-DD (e.g., 2024-01-15).");
                }
            }

            while (closingDate == null) {
                System.out.print("Enter closing date (YYYY-MM-DD): ");
                String closingDateStr = scanner.nextLine().trim();
                if (closingDateStr.isEmpty()) {
                    System.out.println("ERROR: Closing date cannot be empty.");
                    continue;
                }
                try {
                    closingDate = dateFormat.parse(closingDateStr);
                    
                    if (closingDate.before(openingDate)) {
                        System.out.println("ERROR: Closing date must be after opening date.");
                        closingDate = null;
                        continue;
                    }
                    
                    Date today = new Date();
                    if (closingDate.before(today)) {
                        System.out.println("ERROR: Closing date cannot be in the past.");
                        closingDate = null;
                    }
                } catch (Exception e) {
                    System.out.println("ERROR: Invalid date format. Please use YYYY-MM-DD (e.g., 2024-12-31).");
                }
            }

            int slots = 0;
            while (slots < 1 || slots > 10) {
                System.out.print("Enter number of slots (1-10): ");
                try {
                    String slotsInput = scanner.nextLine().trim();
                    if (slotsInput.isEmpty()) {
                        System.out.println("ERROR: Number of slots cannot be empty.");
                        continue;
                    }
                    slots = Integer.parseInt(slotsInput);
                    if (slots < 1 || slots > 10) {
                        System.out.println("ERROR: Slots must be between 1 and 10.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Please enter a valid number between 1 and 10.");
                }
            }
            System.out.println("\n=== INTERNSHIP SUMMARY ===");
            System.out.println("Title: " + title);
            System.out.println("Description: " + description);
            System.out.println("Level: " + level);
            System.out.println("Preferred Major: " + preferredMajor);
            System.out.println("Opening Date: " + dateFormat.format(openingDate));
            System.out.println("Closing Date: " + dateFormat.format(closingDate));
            System.out.println("Number of Slots: " + slots);
            
            System.out.print("\nCreate this internship? (y/n): ");
            String confirmation = scanner.nextLine().trim();
            
            if (!confirmation.equalsIgnoreCase("y")) {
                System.out.println("Internship creation cancelled.");
                return;
            }
            if (rep.getInternships().size() >= 5) {
                System.out.println("ERROR: Cannot create internship - maximum capacity reached.");
                return;
            }
            rep.createInternship(title, description, level, preferredMajor, openingDate, closingDate, slots, internships);

            List<Internship> updatedInternships = rep.getInternships();
            if (!updatedInternships.isEmpty()) {
                Internship newInternship = updatedInternships.get(updatedInternships.size() - 1);
                System.out.println("SUCCESS: Internship created and added for staff approval!");
                System.out.println("Internship ID: " + newInternship.getInternshipID());
                System.out.println("You now have " + updatedInternships.size() + "/5 internships.");
            }
            
        } catch (Exception e) {
            System.out.println("ERROR: Unexpected error creating internship: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * View company applications
     * @param rep company representative
     * @param scanner input scanner
     */
    public static void viewCompanyApplications(CompanyRepresentative rep, Scanner scanner) {
        System.out.println("\n=== VIEW APPLICATIONS ===");
        System.out.println("1. View All Applications");
        System.out.println("2. View Applications by Status");
        System.out.print("Enter your choice: ");
        
        int choice = getMenuChoice(scanner);
        List<Application> applications;
        
        switch (choice) {
            case 1:
                applications = rep.viewApplications();
                break;
            case 2:
                System.out.print("Enter status to filter by (Pending/Successful/Unsuccessful): ");
                String statusFilter = scanner.nextLine();
                applications = rep.viewApplicationsbystatus(statusFilter);
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }
        
        if (applications.isEmpty()) {
            System.out.println("No applications found.");
            return;
        }
        
        System.out.println("\n=== APPLICATIONS ===");
        for (int i = 0; i < applications.size(); i++) {
            Application app = applications.get(i);
            System.out.println((i + 1) + ". Student: " + app.getStudent().getName() +
                    " | Internship: " + app.getInternship().getTitle() +
                    " | Status: " + app.getStatus() +
                    " | Applied: " + app.getDate());
        }
    }
    public static void approveRejectApplications(CompanyRepresentative rep, Scanner scanner) {
        System.out.println("\n=== APPROVE/REJECT APPLICATIONS ===");
        
        List<Application> pendingApps = rep.viewApplicationsbystatus("Pending");
        
        if (pendingApps.isEmpty()) {
            System.out.println("No pending applications to process.");
            return;
        }
        
        System.out.println("Pending Applications:");
        for (int i = 0; i < pendingApps.size(); i++) {
            Application app = pendingApps.get(i);
            System.out.println((i + 1) + ". Student: " + app.getStudent().getName() +
                    " | Internship: " + app.getInternship().getTitle() +
                    " | Major: " + app.getStudent().getMajor() +
                    " | Year: " + app.getStudent().getYearOfStudy());
        }
        
        System.out.print("Select application to process (1-" + pendingApps.size() + "): ");
        int appChoice = getMenuChoice(scanner) - 1;
        
        if (appChoice < 0 || appChoice >= pendingApps.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        
        Application selectedApp = pendingApps.get(appChoice);
        
        System.out.println("\nSelected Application:");
        System.out.println("Student: " + selectedApp.getStudent().getName());
        System.out.println("Internship: " + selectedApp.getInternship().getTitle());
        System.out.println("Student Major: " + selectedApp.getStudent().getMajor());
        System.out.println("Student Year: " + selectedApp.getStudent().getYearOfStudy());
        
        System.out.print("Approve this application? (y/n): ");
        String decision = scanner.nextLine();
        boolean approve = decision.equalsIgnoreCase("y");
        
        rep.approveRejectApplication(selectedApp, approve);
    }

    public static void toggleInternshipVisibility(CompanyRepresentative rep, Scanner scanner) {
        System.out.println("\n=== TOGGLE INTERNSHIP VISIBILITY ===");
        
        List<Internship> companyInternships = rep.getInternships();
        
        if (companyInternships.isEmpty()) {
            System.out.println("No internships found.");
            return;
        }
        
        System.out.println("Your Internships:");
        for (int i = 0; i < companyInternships.size(); i++) {
            Internship internship = companyInternships.get(i);
            System.out.println((i + 1) + ". " + internship.getTitle() +
                    " | Status: " + internship.getStatus() +
                    " | Visible: " + (internship.isVisible() ? "Yes" : "No") +
                    " | Level: " + internship.getLevel());
        }
        
        System.out.print("Select internship to toggle visibility (1-" + companyInternships.size() + "): ");
        int internshipChoice = getMenuChoice(scanner) - 1;
        
        if (internshipChoice < 0 || internshipChoice >= companyInternships.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        
        Internship selectedInternship = companyInternships.get(internshipChoice);
        
        System.out.print("Make this internship visible to students? (y/n): ");
        String decision = scanner.nextLine();
        boolean makeVisible = decision.equalsIgnoreCase("y");
        
        rep.toggleVisibility(selectedInternship, makeVisible);
    }
    public static void viewMyInternships(CompanyRepresentative rep) {
        System.out.println("\n=== MY INTERNSHIPS ===");
        
        List<Internship> companyInternships = rep.getInternships();
        
        if (companyInternships.isEmpty()) {
            System.out.println("No internships created yet.");
            return;
        }
        
        System.out.println("You have " + companyInternships.size() + " internship(s):");
        for (int i = 0; i < companyInternships.size(); i++) {
            Internship internship = companyInternships.get(i);
            System.out.println((i + 1) + ". " + internship.getTitle() +
                    " | Status: " + internship.getStatus() +
                    " | Visible: " + (internship.isVisible() ? "Yes" : "No") +
                    " | Level: " + internship.getLevel() +
                    " | Slots: " + internship.getAvailableSlots() +
                    " | Applications: " + internship.getApplications().size());
        }
    }

    private static int getMenuChoice(Scanner scanner) {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            return choice;
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Please enter a valid number!");
            return -1;
        }
    }
}
