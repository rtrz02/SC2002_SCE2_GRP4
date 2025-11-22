import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Internship {
    private String internshipID;
    private String title;
    private String description;
    private String level;
    private String preferredMajor;
    private String companyRepresentative;
    private Date openingDate;
    private Date closingDate;
    private String status;
    private List<String> applicants;
    private String companyName;
    private int totalSlots;
    private int availableSlots;
    private int noOfStudents;
    private boolean isVisible;
    private List<Application> applications;
    public Internship(String internshipID, String title, String description, String level,
                      String preferredMajor, String companyRepresentative, Date openingDate,
                      Date closingDate, String companyName, int totalSlots) {
        this.internshipID = internshipID;
        this.title = title;
        this.description = description;
        this.level = level;
        this.preferredMajor = preferredMajor;
        this.companyRepresentative = companyRepresentative;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.status = "Pending";
        this.applicants = new ArrayList<>();
        this.companyName = companyName;
        this.totalSlots = totalSlots;
        this.availableSlots = totalSlots;
        this.noOfStudents = 0;
        this.isVisible = true;
        this.applications = new ArrayList<>();
    }
    public boolean enrollStudent() {
        if (availableSlots > 0 && noOfStudents < totalSlots) {
            noOfStudents++;
            availableSlots--;
            updateFilledStatus();
            return true;
        }
        return false;
    }
    public void updateFilledStatus() {
        if (noOfStudents >= totalSlots && "Approved".equals(this.status)) {
            this.status = "Filled";
            this.isVisible = false;
            System.out.println("Internship '" + this.title + "' is now filled. No more slots available.");
        } else if ("Filled".equals(this.status) && noOfStudents < totalSlots) {
            this.status = "Approved";
            System.out.println("Internship '" + this.title + "' now has available slots.");
        }
    }
    public int getTotalSlots() {
        return totalSlots;
    }
    public int getNoOfStudents() {
        return noOfStudents;
    }
    public void setNoOfStudents(int noOfStudents) {
        this.noOfStudents = noOfStudents;
        this.availableSlots = this.totalSlots - noOfStudents;
        updateFilledStatus();
    }
    public int getAvailableSlots() {
        return availableSlots;
    }
    public void setAvailableSlots(int availableSlots) {
        this.availableSlots = availableSlots;
        this.noOfStudents = this.totalSlots - availableSlots;
        updateFilledStatus();
    }
    public void updateStatus(String newStatus) {
        if (newStatus.equals("Pending") || newStatus.equals("Approved") ||
                newStatus.equals("Filled") || newStatus.equals("Closed")) {
            this.status = newStatus;
            if ("Filled".equals(newStatus)) {
                this.isVisible = false;
            }
        } else {
            System.out.println("Invalid status value: " + newStatus);
        }
    }

    public void addApplicant(String applicantName) {
        this.applicants.add(applicantName);
    }

    public void addApplication(Application application) {
        this.applications.add(application);
    }

    public boolean removeApplicant(String applicantName) {
        boolean removed = applicants.remove(applicantName);
        if (removed) {
            System.out.println("Applicant '" + applicantName + "' removed successfully.");
        } else {
            System.out.println("Applicant '" + applicantName + "' not found.");
        }
        return removed;
    }

    public int getFilledSlots() {
        return noOfStudents;
    }

    public boolean removeApplication(Application application) {
        boolean removed = applications.remove(application);
        if (removed) {
            System.out.println("Application removed successfully from internship: " + this.title);
            String applicantName = application.getStudent().getName();
            applicants.remove(applicantName);
        } else {
            System.out.println("Application not found in internship: " + this.title);
        }
        return removed;
    }

    public boolean containsApplication(Application application) {
        if (application == null) {
            return false;
        }
        for (Application app : applications) {
            if (app.equals(application)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsApplicationbyid(Application application) {
        if (application == null)
            return false;
        for (Application app : applications) {
            if (app.getApplicationID().equals(application.getApplicationID())) {
                return true;
            }
        }
        return false;
    }
    public String getInternshipID() {
        return internshipID;
    }
    public void setInternshipID(String internshipID) {
        this.internshipID = internshipID;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public String getPreferredMajor() {
        return preferredMajor;
    }
    public void setPreferredMajor(String preferredMajor) {
        this.preferredMajor = preferredMajor;
    }
    public String getCompanyRepresentative() {
        return companyRepresentative;
    }
    public void setCompanyRepresentative(String companyRepresentative) {
        this.companyRepresentative = companyRepresentative;
    }
    public Date getOpeningDate() {
        return openingDate;
    }
    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }
    public Date getClosingDate() {
        return closingDate;
    }
    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
        if ("Filled".equals(status)) {
            this.isVisible = false;
        }
    }
    public List<String> getApplicants() {
        return applicants;
    }
    public void setApplicants(List<String> applicants) {
        this.applicants = applicants;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public boolean isVisible() {
        return isVisible;
    }
    public void setVisible(boolean visible) {
        isVisible = visible;
    }
    public List<Application> getApplications() {
        return applications;
    }
    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}