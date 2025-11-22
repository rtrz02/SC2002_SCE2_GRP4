import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class InternshipBrowsingController {
    private final Scanner scanner;
    private final List<Internship> internships;

    public InternshipBrowsingController(Scanner scanner, List<Internship> internships) {
        this.scanner = scanner;
        this.internships = internships;
    }

    public void configureFilter(InternshipFilter filter, int choice) {
        switch (choice) {
            case 1 -> setStatusFilter(filter);
            case 2 -> setMajorFilter(filter);
            case 3 -> setLevelFilter(filter);
            case 4 -> setCompanyFilter(filter);
            case 5 -> setDateFilter(filter);
            case 6 -> setSortFilter(filter);
        }
    }

    public void setStatusFilter(InternshipFilter filter) {
        System.out.println("\n=== SET STATUS FILTER ===");
        System.out.println("Available statuses: All, Approved, Pending, Filled, Rejected");
        System.out.print("Enter status (or 'All' for no filter): ");
        String status = scanner.nextLine();

        if (status.equals("All") || status.equals("Approved") || status.equals("Pending") ||
                status.equals("Filled") || status.equals("Rejected")) {
            filter.setStatus(status);
            System.out.println("Status filter set to: " + status);
        } else {
            System.out.println("Invalid status. Please use: All, Approved, Pending, Filled, Rejected");
        }
    }

    public void setMajorFilter(InternshipFilter filter) {
        System.out.println("\n=== SET PREFERRED MAJOR FILTER ===");
        System.out.println("Available majors: All, CSC, EEE, MAE, etc.");
        System.out.print("Enter preferred major (or 'All' for no filter): ");
        String major = scanner.nextLine();
        filter.setPreferredMajor(major);
        System.out.println("Major filter set to: " + major);
    }

    public void setLevelFilter(InternshipFilter filter) {
        System.out.println("\n=== SET INTERNSHIP LEVEL FILTER ===");
        System.out.println("Available levels: All, Basic, Intermediate, Advanced");
        System.out.print("Enter internship level (or 'All' for no filter): ");
        String level = scanner.nextLine();

        if (level.equals("All") || level.equals("Basic") || level.equals("Intermediate") ||
                level.equals("Advanced")) {
            filter.setInternshipLevel(level);
            System.out.println("Level filter set to: " + level);
        } else {
            System.out.println("Invalid level. Please use: All, Basic, Intermediate, Advanced");
        }
    }

    public void setCompanyFilter(InternshipFilter filter) {
        System.out.println("\n=== SET COMPANY NAME FILTER ===");
        System.out.print("Enter company name (or 'All' for no filter): ");
        String company = scanner.nextLine();
        filter.setCompanyName(company);
        System.out.println("Company filter set to: " + (company.isEmpty() ? "Any" : company));
    }

    public void setDateFilter(InternshipFilter filter) {
        System.out.println("\n=== SET CLOSING DATE RANGE ===");
        System.out.println("Note: Date format should be YYYY-MM-DD");

        try {
            System.out.print("Enter closing date from (or leave empty for no start date): ");
            String fromDateStr = scanner.nextLine();

            if (!fromDateStr.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fromDate = sdf.parse(fromDateStr);
                filter.setClosingDateFrom(fromDate);
                System.out.println("From date set to: " + fromDateStr);
            } else {
                filter.setClosingDateFrom(null);
            }

            System.out.print("Enter closing date to (or leave empty for no end date): ");
            String toDateStr = scanner.nextLine();

            if (!toDateStr.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date toDate = sdf.parse(toDateStr);
                filter.setClosingDateTo(toDate);
                System.out.println("To date set to: " + toDateStr);
            } else {
                filter.setClosingDateTo(null);
            }

        } catch (Exception e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD");
        }
    }

    public void setSortFilter(InternshipFilter filter) {
        System.out.println("\n=== SET SORT OPTIONS ===");
        System.out.println("Available sort fields: title, company, closingDate, level");
        System.out.print("Enter field to sort by: ");
        String sortBy = scanner.nextLine();

        if (sortBy.equals("title") || sortBy.equals("company") ||
                sortBy.equals("closingDate") || sortBy.equals("level")) {
            filter.setSortBy(sortBy);

            System.out.print("Sort ascending? (y/n): ");
            String ascending = scanner.nextLine();
            filter.setSortAscending(ascending.equalsIgnoreCase("y"));

            System.out.println("Sort set to: " + sortBy + " (" +
                    (filter.isSortAscending() ? "Ascending" : "Descending") + ")");
        } else {
            System.out.println("Invalid sort field. Please use: title, company, closingDate, level");
        }
    }

    public static void displayInternships(List<Internship> internships, User currentUser, InternshipFilter filter) {
        System.out.println("\n=== AVAILABLE INTERNSHIPS ===");

        List<Internship> filteredInternships = new ArrayList<>();

        for (Internship internship : internships) {
            if (currentUser instanceof Student && !internship.isVisible()) {
                continue;
            }
            if ("Pending".equals(internship.getStatus())) {
                continue;
            }
            if (!filter.getStatus().equals("All") &&
                    !internship.getStatus().equals(filter.getStatus())) {
                continue;
            }

            if (!filter.getPreferredMajor().equals("All") &&
                    !internship.getPreferredMajor().equals(filter.getPreferredMajor())) {
                continue;
            }

            if (!filter.getInternshipLevel().equals("All") &&
                    !internship.getLevel().equals(filter.getInternshipLevel())) {
                continue;
            }

            if (!filter.getCompanyName().isEmpty() &&
                    !internship.getCompanyName().toLowerCase().contains(
                            filter.getCompanyName().toLowerCase())) {
                continue;
            }

            if (filter.getClosingDateFrom() != null &&
                    internship.getClosingDate().before(filter.getClosingDateFrom())) {
                continue;
            }

            if (filter.getClosingDateTo() != null &&
                    internship.getClosingDate().after(filter.getClosingDateTo())) {
                continue;
            }

            filteredInternships.add(internship);
        }

        filteredInternships.sort((i1, i2) -> {
            int result = 0;
            switch (filter.getSortBy()) {
                case "title" -> result = i1.getTitle().compareToIgnoreCase(i2.getTitle());
                case "company" -> result = i1.getCompanyName().compareToIgnoreCase(i2.getCompanyName());
                case "level" -> result = i1.getLevel().compareToIgnoreCase(i2.getLevel());
                case "closingDate" -> result = i1.getClosingDate().compareTo(i2.getClosingDate());
                default -> result = i1.getTitle().compareToIgnoreCase(i2.getTitle());
            }
            return filter.isSortAscending() ? result : -result;
        });

        if (filteredInternships.isEmpty()) {
            System.out.println("No internships found matching your criteria.");
        } else {
            System.out.println("Found " + filteredInternships.size() + " internship(s):");
            System.out.println("------------------------------------------------------------");

            for (int i = 0; i < filteredInternships.size(); i++) {
                Internship internship = filteredInternships.get(i);
                System.out.println((i + 1) + ". " + internship.getTitle());
                System.out.println("   Internship ID: " + internship.getInternshipID());
                System.out.println("   Company: " + internship.getCompanyName());
                System.out.println("   Level: " + internship.getLevel());
                System.out.println("   Preferred Major: " + internship.getPreferredMajor());
                System.out.println("   Status: " + internship.getStatus());
                System.out.println("   Closing Date: " + internship.getClosingDate());
                System.out.println("   Available Slots: " + internship.getAvailableSlots());
                System.out.println("------------------------------------------------------------");
            }
        }

        if (filter.hasActiveFilters()) {
            System.out.println("Note: Active filters are applied to this view.");
        }
    }
}