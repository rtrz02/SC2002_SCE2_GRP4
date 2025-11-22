import java.util.Date;

public class InternshipFilter {
    private String status;
    private String preferredMajor;
    private String internshipLevel;
    private String companyName;
    private Date closingDateFrom;
    private Date closingDateTo;
    private String sortBy;
    private boolean sortAscending;


    public InternshipFilter() {
        this.status = "All";
        this.preferredMajor = "All";
        this.internshipLevel = "All";
        this.companyName = "";
        this.sortBy = "title";
        this.sortAscending = true;
    }


    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPreferredMajor() { return preferredMajor; }
    public void setPreferredMajor(String preferredMajor) { this.preferredMajor = preferredMajor; }
    public String getInternshipLevel() { return internshipLevel; }
    public void setInternshipLevel(String internshipLevel) { this.internshipLevel = internshipLevel; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public Date getClosingDateFrom() { return closingDateFrom; }
    public void setClosingDateFrom(Date closingDateFrom) { this.closingDateFrom = closingDateFrom; }
    public Date getClosingDateTo() { return closingDateTo; }
    public void setClosingDateTo(Date closingDateTo) { this.closingDateTo = closingDateTo; }
    public String getSortBy() { return sortBy; }
    public void setSortBy(String sortBy) { this.sortBy = sortBy; }
    public boolean isSortAscending() { return sortAscending; }
    public void setSortAscending(boolean sortAscending) { this.sortAscending = sortAscending; }

    public boolean hasActiveFilters() {
        return !"All".equals(status) ||
                !"All".equals(preferredMajor) ||
                !"All".equals(internshipLevel) ||
                (companyName != null && !companyName.isEmpty()) ||
                closingDateFrom != null ||
                closingDateTo != null;
    }

    public boolean matches(Internship internship, String userType) {
        if (internship == null) return false;
        if ("Student".equals(userType)) {
            if (!"Approved".equals(internship.getStatus()) ||
                    !internship.isVisible() ||
                    "Filled".equals(internship.getStatus()) ||
                    internship.getAvailableSlots() <= 0) {
                return false;
            }
        }

        if (!"All".equals(status) && !status.equals(internship.getStatus())) {
            return false;
        }

        if (!"All".equals(preferredMajor) && !preferredMajor.equals(internship.getPreferredMajor())) {
            return false;
        }

        if (!"All".equals(internshipLevel) && !internshipLevel.equals(internship.getLevel())) {
            return false;
        }

        if (companyName != null && !companyName.isEmpty() &&
                !internship.getCompanyName().toLowerCase().contains(companyName.toLowerCase())) {
            return false;
        }

        if (closingDateFrom != null && internship.getClosingDate().before(closingDateFrom)) {
            return false;
        }
        if (closingDateTo != null && internship.getClosingDate().after(closingDateTo)) {
            return false;
        }

        return true;
    }

    public boolean matches(Internship internship) {
        return matches(internship, "Student");
    }

    public void resetFilters() {
        this.status = "All";
        this.preferredMajor = "All";
        this.internshipLevel = "All";
        this.companyName = "";
        this.closingDateFrom = null;
        this.closingDateTo = null;
        this.sortBy = "title";
        this.sortAscending = true;
    }

    public void displayCurrentFilters() {
        System.out.println("=== Current Filter Settings ===");
        System.out.println("Status: " + status);
        System.out.println("Preferred Major: " + preferredMajor);
        System.out.println("Internship Level: " + internshipLevel);
        System.out.println("Company Name: " + (companyName.isEmpty() ? "Any" : companyName));
        System.out.println("Sort By: " + sortBy + " (" + (sortAscending ? "Ascending" : "Descending") + ")");
        System.out.println("===============================");
    }

    public InternshipFilter copy() {
        InternshipFilter copy = new InternshipFilter();
        copy.status = this.status;
        copy.preferredMajor = this.preferredMajor;
        copy.internshipLevel = this.internshipLevel;
        copy.companyName = this.companyName;
        copy.closingDateFrom = this.closingDateFrom;
        copy.closingDateTo = this.closingDateTo;
        copy.sortBy = this.sortBy;
        copy.sortAscending = this.sortAscending;
        return copy;
    }



    @Override
    public String toString() {
        return "InternshipFilter{" +
                "status='" + status + '\'' +
                ", preferredMajor='" + preferredMajor + '\'' +
                ", level='" + internshipLevel + '\'' +
                ", company='" + companyName + '\'' +
                ", sortBy='" + sortBy + '\'' +
                '}';
    }
}

