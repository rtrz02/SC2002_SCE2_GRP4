import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompanyRepresentative extends User {
    private String companyName;
    private String department;
    private String position;
    private String status;
    private List<Internship> internships;
    private String email;
    private static final int MAX_INTERNSHIPS = 5;

    public CompanyRepresentative(String globalID, String representativeName, String password,
                                 String companyName, String department, String position, String email) {
        super(globalID, representativeName, password);
        this.companyName = companyName;
        this.department = department;
        this.position = position;
        this.status = "Pending";
        this.internships = new ArrayList<>();
        this.email = email;
    }
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Internship> getInternships() {
        return new ArrayList<>(internships);
    }

    public String getGlobalID() {
        return this.getID();
    }

    public String getEmailID() {
        return this.email;
    }

    public void register() {
        this.status = "Pending";
    }

    public void createInternship(String title, String description, String level,
                                 String preferredMajor, Date openingDate,
                                 Date closingDate, int numberOfSlots, List<Internship> allInternships) {
        if (!"Approved".equals(this.status)) {
            System.out.println("Cannot create internship: Account not approved by Career Center.");
            return;
        }
        if (internships.size() >= MAX_INTERNSHIPS) {
            System.out.println("Cannot create internship: Maximum limit of " + MAX_INTERNSHIPS + " internships reached.");
            return;
        }
        if (numberOfSlots < 1 || numberOfSlots > 10) {
            System.out.println("Cannot create internship: Number of slots must be between 1 and 10.");
            return;
        }
        int nextIndex = 1;
        if (!allInternships.isEmpty()) {
               int maxId = 0;
            for (Internship internship : allInternships) {
                String id = internship.getInternshipID();
                if (id.startsWith("INT")) {
                    try {
                        int currentId = Integer.parseInt(id.substring(3));
                        if (currentId > maxId) {
                            maxId = currentId;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Warning: Invalid internship ID format: " + id);
                    }
                }
            }
            nextIndex = maxId + 1;
        }

        String internshipId = String.format("INT%05d", nextIndex);
        Internship internship = new Internship(internshipId, title, description, level, preferredMajor,
                this.getGlobalID(), openingDate, closingDate, companyName, numberOfSlots);

        internships.add(internship);
        allInternships.add(internship);
        System.out.println("Internship '" + title + "' created successfully. Waiting for Career Center approval.");
    }

    public List<Application> viewApplications() {
        List<Application> allApplications = new ArrayList<>();

        System.out.println("\n=== viewApplications() DEBUG ===");

        for (Internship internship : internships) {
            System.out.println("Checking internship: " + internship.getTitle());
            System.out.println("  - My Rep ID: " + this.getGlobalID());
            System.out.println("  - Internship Rep ID: " + internship.getCompanyRepresentative());
            System.out.println("  - Rep owns internship: " + internship.getCompanyRepresentative().equals(this.getGlobalID()));
            System.out.println("  - Internship status: " + internship.getStatus());
            System.out.println("  - Is approved: " + "Approved".equals(internship.getStatus()));
            System.out.println("  - Applications count: " + internship.getApplications().size());
            if (internship.getCompanyRepresentative().equals(this.getGlobalID()) &&
                    "Approved".equals(internship.getStatus())) {

                System.out.println("  - ADDING applications from this internship");
                allApplications.addAll(internship.getApplications());
            } else {
                System.out.println("  - SKIPPING applications (not owned or not approved)");
            }
        }

        System.out.println("Final count: " + allApplications.size() + " applications");
        return allApplications;
    }

    public List<Application> viewApplications(String internshipId) {
        Internship internship = findInternshipById(internshipId);
        if (internship == null) {
            System.out.println("Internship not found with ID: " + internshipId);
            return new ArrayList<>();
        }

        if (!internship.getCompanyRepresentative().equals(this.getGlobalID())) {
            System.out.println("Not authorized to view applications for this internship.");
            return new ArrayList<>();
        }

        if (!"Approved".equals(internship.getStatus())) {
            System.out.println("Cannot view applications: Internship is not approved yet.");
            return new ArrayList<>();
        }

        List<Application> applications = internship.getApplications();
        System.out.println("Found " + applications.size() + " applications for internship: " + internship.getTitle());
        return applications;
    }
    public List<Application> viewApplications(Internship internship) {
        if (internship == null) {
            System.out.println("Internship is null.");
            return new ArrayList<>();
        }
        if (!internship.getCompanyRepresentative().equals(this.getGlobalID())) {
            System.out.println("Not authorized to view applications for this internship.");
            return new ArrayList<>();
        }
        if (!"Approved".equals(internship.getStatus())) {
            System.out.println("Cannot view applications: Internship is not approved yet.");
            return new ArrayList<>();
        }

        List<Application> applications = internship.getApplications();
        System.out.println("Found " + applications.size() + " applications for internship: " + internship.getTitle());
        return applications;
    }

    public List<Application> viewApplicationsbystatus(String statusFilter) {
        List<Application> filteredApplications = new ArrayList<>();

        for (Internship internship : internships) {
            if ("Approved".equals(internship.getStatus())) {
                for (Application application : internship.getApplications()) {
                    if (statusFilter == null || statusFilter.equals(application.getStatus())) {
                        filteredApplications.add(application);
                    }
                }
            }
        }

        System.out.println("Found " + filteredApplications.size() + " applications with status: " +
                (statusFilter == null ? "ALL" : statusFilter));
        return filteredApplications;
    }

    public void approveRejectApplication(Application application, boolean approve) {
        if (application == null) {
            System.out.println("Cannot process: Application is null.");
            return;
        }

        Internship internship = application.getInternship();
        if (internship == null) {
            System.out.println("Cannot process: Application is not associated with any internship.");
            return;
        }
        if (!internship.getCompanyRepresentative().equals(this.getGlobalID())) {
            System.out.println("Cannot process: You are not authorized to manage applications for this internship.");
            return;
        }
        if (!"Approved".equals(internship.getStatus())) {
            System.out.println("Cannot process application: Internship is not approved.");
            return;
        }
        if (approve) {
            if (internship.getAvailableSlots() <= 0) {
                System.out.println("Cannot approve application: All internship slots are filled.");
                return;
            }

            if (internship.enrollStudent()) {
                application.updateStatus("Successful");
                System.out.println("Application from " + application.getStudent().getName() + " approved successfully.");
                System.out.println("Remaining slots: " + internship.getAvailableSlots() + "/" + internship.getTotalSlots());
            } else {
                System.out.println("Failed to enroll student: No available slots.");
            }

        } else {
            application.updateStatus("Unsuccessful");
            System.out.println("Application from " + application.getStudent().getName() + " rejected.");
        }
    }

    private Application findApplicationById(Internship internship, String applicationId) {
        for (Application application : internship.getApplications()) {
            if (application.getApplicationID().equals(applicationId)) {
                return application;
            }
        }
        return null;
    }

    private Internship findInternshipById(String internshipId) {
        for (Internship internship : internships) {
            if (internship.getInternshipID().equals(internshipId)) {
                return internship;
            }
        }
        return null;
    }

    public void toggleVisibility(Internship internship, boolean isVisible) {
        if (internship == null) {
            System.out.println("Cannot toggle visibility: Internship is null.");
            return;
        }

        if (!internship.getCompanyRepresentative().equals(this.getGlobalID())) {
            System.out.println("Cannot toggle visibility: You are not authorized to manage this internship.");
            return;
        }

        if (isVisible) {
            if (!"Approved".equals(internship.getStatus())) {
                System.out.println("Cannot make internship visible: Internship must be approved by Career Center first. Current status: " + internship.getStatus());
                return;
            }

            if ("Filled".equals(internship.getStatus())) {
                System.out.println("Cannot make internship visible: Internship is already filled.");
                return;
            }
        }
        internship.setVisible(isVisible);
        String status = isVisible ? "ON" : "OFF";
        System.out.println("Internship '" + internship.getTitle() + "' visibility set to: " + status);
    }

    @Override
    public String toString() {
        return String.format("CompanyRepresentative{GlobalID='%s', EmailID='%s', name='%s', company='%s', status='%s'}",
                this.getGlobalID(), this.getEmailID(), this.getName(), companyName, status);
    }
}