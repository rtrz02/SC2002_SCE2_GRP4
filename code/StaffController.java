import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

// control class for career center staff operations
public class StaffController {

    // authorize or reject company representatives
    public static void authorizeCompanyRepresentatives(CareerCenterStaff staff, Scanner scanner,
            List<CompanyRepresentative> companyRepresentatives) {
        System.out.println("\n=== AUTHORIZE COMPANY REPRESENTATIVES ===");

        List<CompanyRepresentative> pendingRepsList = companyRepresentatives.stream()
                .filter(rep -> "Pending".equals(rep.getStatus()))
                .collect(Collectors.toList());

        CompanyRepresentative[] pendingReps = pendingRepsList.toArray(new CompanyRepresentative[0]);

        if (pendingReps.length == 0) {
            System.out.println("No pending company representative requests.");
            return;
        }

        System.out.println("Pending Company Representatives:");
        for (int i = 0; i < pendingReps.length; i++) {
            CompanyRepresentative rep = pendingReps[i];
            System.out.println((i + 1) + ". " + rep.getName() +
                    " - Company: " + rep.getCompanyName() +
                    " - Email: " + rep.getEmail());
        }

        System.out.print("Select representative to process (1-" + pendingReps.length + "): ");
        int repChoice = getMenuChoice(scanner) - 1;

        if (repChoice < 0 || repChoice >= pendingReps.length) {
            System.out.println("Invalid selection.");
            return;
        }

        CompanyRepresentative selectedRep = pendingReps[repChoice];

        System.out.println("\nSelected Representative:");
        System.out.println("Name: " + selectedRep.getName());
        System.out.println("Company: " + selectedRep.getCompanyName());
        System.out.println("Department: " + selectedRep.getDepartment());
        System.out.println("Position: " + selectedRep.getPosition());
        System.out.println("Email: " + selectedRep.getEmail());

        System.out.print("Approve this representative? (y/n): ");
        String decision = scanner.nextLine();
        boolean approve = decision.equalsIgnoreCase("y");
        boolean result = staff.authoriseCompanyRep(selectedRep, approve, pendingReps);
        if (result) {
            if (approve) {
                System.out.println("Company representative approved successfully!");
            } else {
                System.out.println("Company representative rejected.");
            }
        } else {
            System.out.println("Error: Could not process the request.");
        }
    }

    // approve or reject internship requests
    public static void approveRejectInternships(CareerCenterStaff staff, Scanner scanner,
            List<Internship> internships) {
        System.out.println("\n=== APPROVE/REJECT INTERNSHIPS ===");
        List<Internship> pendingInternshipsList = internships.stream()
                .filter(internship -> "Pending".equals(internship.getStatus()))
                .collect(Collectors.toList());

        Internship[] pendingInternships = pendingInternshipsList.toArray(new Internship[0]);

        if (pendingInternships.length == 0) {
            System.out.println("No pending internship requests.");
            return;
        }

        System.out.println("Pending Internships:");
        for (int i = 0; i < pendingInternships.length; i++) {
            Internship internship = pendingInternships[i];
            System.out.println((i + 1) + ". " + internship.getTitle() +
                    " - Company: " + internship.getCompanyName() +
                    " - Level: " + internship.getLevel());
        }

        System.out.print("Select internship to process (1-" + pendingInternships.length + "): ");
        int internshipChoice = getMenuChoice(scanner) - 1;

        if (internshipChoice < 0 || internshipChoice >= pendingInternships.length) {
            System.out.println("Invalid selection.");
            return;
        }

        Internship selectedInternship = pendingInternships[internshipChoice];

        System.out.println("\nSelected Internship:");
        System.out.println("Title: " + selectedInternship.getTitle());
        System.out.println("Company: " + selectedInternship.getCompanyName());
        System.out.println("Level: " + selectedInternship.getLevel());
        System.out.println("Preferred Major: " + selectedInternship.getPreferredMajor());
        System.out.println("Description: " + selectedInternship.getDescription());
        System.out.println("Available Slots: " + selectedInternship.getAvailableSlots());

        System.out.print("Approve this internship? (y/n): ");
        String decision = scanner.nextLine();
        boolean approve = decision.equalsIgnoreCase("y");

        boolean result = staff.approveRejectInternship(selectedInternship, approve, pendingInternships);

        if (result) {
            if (approve) {
                System.out.println("Internship approved successfully!");
                if (!internships.contains(selectedInternship)) {
                    internships.add(selectedInternship);
                }
            } else {
                System.out.println("Internship rejected.");
            }
        } else {
            System.out.println("Error: Could not process the request.");
        }
    }

    // process withdrawal requests from students
    public static void processWithdrawalRequests(CareerCenterStaff staff, Scanner scanner,
            List<WithdrawalRequest> withdrawalRequests,
            List<Internship> internships) {
        System.out.println("\n=== PROCESS WITHDRAWAL REQUESTS ===");

        List<WithdrawalRequest> pendingWithdrawalsList = withdrawalRequests.stream()
                .filter(wr -> "Pending".equals(wr.getStatus()))
                .collect(Collectors.toList());

        WithdrawalRequest[] pendingWithdrawals = pendingWithdrawalsList.toArray(new WithdrawalRequest[0]);

        if (pendingWithdrawals.length == 0) {
            System.out.println("No pending withdrawal requests.");
            return;
        }

        System.out.println("Pending Withdrawal Requests:");
        for (int i = 0; i < pendingWithdrawals.length; i++) {
            WithdrawalRequest withdrawal = pendingWithdrawals[i];
            Application app = withdrawal.getApplication();
            System.out.println((i + 1) + ". Student: " + app.getStudent().getName() +
                    " - Internship: " + app.getInternship().getTitle() +
                    " - Reason: " + withdrawal.getReason());
        }

        System.out.print("Select withdrawal request to process (1-" + pendingWithdrawals.length + "): ");
        int withdrawalChoice = getMenuChoice(scanner) - 1;

        if (withdrawalChoice < 0 || withdrawalChoice >= pendingWithdrawals.length) {
            System.out.println("Invalid selection.");
            return;
        }

        WithdrawalRequest selectedWithdrawal = pendingWithdrawals[withdrawalChoice];
        Application app = selectedWithdrawal.getApplication();

        System.out.println("\nSelected Withdrawal Request:");
        System.out.println("Student: " + app.getStudent().getName());
        System.out.println("Student ID: " + app.getStudent().getID());
        System.out.println("Internship: " + app.getInternship().getTitle());
        System.out.println("Company: " + app.getInternship().getCompanyName());
        System.out.println("Reason: " + selectedWithdrawal.getReason());
        System.out.println("Request Date: " + selectedWithdrawal.getRequestDate());

        System.out.print("Approve this withdrawal request? (y/n): ");
        String decision = scanner.nextLine();
        boolean approve = decision.equalsIgnoreCase("y");

        boolean result = staff.approveRejectWithdrawal(selectedWithdrawal, approve,
                internships, pendingWithdrawals);

        if (result) {
            if (approve) {
                selectedWithdrawal.approve();
                Student student = app.getStudent();
                Application[] studentApps = student.getApplications();
                for (int i = 0; i < studentApps.length; i++) {
                    if (studentApps[i] != null && studentApps[i].equals(app)) {
                        studentApps[i] = null;
                        break;
                    }
                }
            } else {
                System.out.println("Withdrawal request rejected.");
                selectedWithdrawal.reject();
            }
        } else {
            System.out.println("Error: Could not process the request.");
        }
    }

    // generate reports
    public static void generateReport(CareerCenterStaff staff, Scanner scanner,
            List<Internship> internships, MenuBoundary menuBoundary) {
        int reportChoice = menuBoundary.displayReportMenu();

        switch (reportChoice) {
            case 1 -> generateQuickSummaryReport(staff, scanner, internships);
            case 2 -> generateCustomReport(staff, scanner, internships, menuBoundary);
            case 3 -> System.out.println("Returning to staff menu...");
            default -> System.out.println("Invalid choice.");
        }
    }

    // generate quick summary report
    private static void generateQuickSummaryReport(CareerCenterStaff staff, Scanner scanner,
            List<Internship> internships) {
        System.out.println("\n=== QUICK SUMMARY REPORT ===");

        Report quickReport = staff.generateQuickReport(internships);
        String exportChoice;
        while (true) {
            System.out.print("\nWould you like to export this report to a file? (y/n): ");
            exportChoice = scanner.nextLine().trim().toLowerCase();

            if (exportChoice.equals("y")) {
                System.out.print("Enter filename (without extension): ");
                String filename = scanner.nextLine();
                quickReport.exportToFile(filename + ".txt");
                System.out.println("Report exported successfully!");
                break;
            } else if (exportChoice.equals("n")) {
                System.out.println("\n" + quickReport.getReportData());
                break;
            } else {
                System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
            }
        }
    }

    // generate custom report with filters
    private static void generateCustomReport(CareerCenterStaff staff, Scanner scanner,
            List<Internship> internships, MenuBoundary menuBoundary) {
        System.out.println("\n=== CUSTOM REPORT WITH FILTERS ===");

        InternshipFilter filter = new InternshipFilter();
        InternshipBrowsingController browsingController = new InternshipBrowsingController(scanner, internships);
        boolean configuring = true;

        while (configuring) {
            int filterChoice = menuBoundary.displayCustomReportMenu(filter);

            switch (filterChoice) {
                case 1 -> browsingController.setStatusFilter(filter);
                case 2 -> browsingController.setMajorFilter(filter);
                case 3 -> browsingController.setLevelFilter(filter);
                case 4 -> browsingController.setCompanyFilter(filter);
                case 5 -> {
                    filter.resetFilters();
                    System.out.println("All filters reset to default.");
                }
                case 6 -> {
                    generateReportWithFilters(staff, scanner, filter, internships);
                    configuring = false;
                }
                case 7 -> {
                    System.out.println("Report generation cancelled.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // generate report with current filters
    private static void generateReportWithFilters(CareerCenterStaff staff, Scanner scanner,
            InternshipFilter filter, List<Internship> internships) {
        System.out.println("\nGenerating report with current filters...");
        Map<String, Object> filterMap = new HashMap<>();

        if (!"All".equals(filter.getStatus())) {
            filterMap.put("status", filter.getStatus());
        }
        if (!"All".equals(filter.getPreferredMajor())) {
            filterMap.put("preferred_major", filter.getPreferredMajor());
        }
        if (!"All".equals(filter.getInternshipLevel())) {
            filterMap.put("internship_level", filter.getInternshipLevel());
        }
        if (filter.getCompanyName() != null && !filter.getCompanyName().isEmpty()) {
            filterMap.put("company_name", filter.getCompanyName());
        }

        Report customReport = staff.generateReportWithFilters(filterMap, "Custom Filtered Report", internships);
        String exportChoice;
        while (true) {
            System.out.print("\nWould you like to export this report to a file? (y/n): ");
            exportChoice = scanner.nextLine().trim().toLowerCase();

            if (exportChoice.equals("y")) {
                System.out.print("Enter filename (without extension): ");
                String filename = scanner.nextLine();
                customReport.exportToFile(filename + ".txt");
                System.out.println("Report exported successfully!");
                break;
            } else if (exportChoice.equals("n")) {
                System.out.println("\n" + customReport.getReportData());
                break;
            } else {
                System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
            }
        }
    }

    // get menu choice with error handling
    private static int getMenuChoice(Scanner scanner) {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            return choice;
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            System.out.println("Please enter a valid number!");
            return -1;
        }
    }
}