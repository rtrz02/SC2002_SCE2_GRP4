import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Student extends User {
    private int yearOfStudy;
    private String major;
    private String studentID;
    private Internship internship;
    private Application[] applications;
    private String email;

    // constructor
    public Student(String userID, String studentID, String name, String password, int yearOfStudy, String major,
            Internship internship, Application[] applications, String email) {
        super(userID, name, password);
        this.studentID = studentID;
        this.yearOfStudy = yearOfStudy;
        this.major = major;
        this.internship = internship;
        this.applications = applications;
        this.email = email;
    }

    // getters
    public String getStudentID() {
        return super.getID();
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public String getMajor() {
        return major;
    }

    public String getEmail() {
        return email;
    }

    public Application[] getApplications() {
        return applications;
    }

    public Internship getInternship() {
        return internship;
    }

    public int getNoOfApplications() {
        int give = 0;
        for (int index = 0; index < applications.length; index++) {
            if (applications[index] != null) {
                give = give + 1;
            }
        }
        return give;
    }

    // setters
    public void setStudentID(String id) {
        super.setID(id);
    }

    public void setYearOfStudy(int year) {
        this.yearOfStudy = year;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setInternship(Internship internship) {
        this.internship = internship;
    }

    public void removeInternship() {
        this.internship = null;
    }

    // apply for an internship
    public int apply(Internship opportunity) {
        int index = 0;
        for (index = 0; index < applications.length; index++) {
            if (applications[index] == null) {
                break;
            }
        }
        if (index == 3) {
            System.out.println("Max internship limit reached!");
            return 0;
        } else {
            this.applications[index] = new Application(String.valueOf(index), opportunity, this);
            System.out.printf("Successfully applied for internship ID: %s!\n", opportunity.getInternshipID());
            return 1;
        }
    }

    // view all applications
    public void viewApplication() {
        int index = applications.length;
        for (int x = 0; x < index; x++) {
            if (applications[x] != null) {
                System.out.println("------------------------------------------------------------");
                System.out.printf("%d.  Title: %s\n   Status: %s\n   Date applied:", x + 1,
                        applications[x].getInternship().getTitle(), applications[x].getStatus());
                System.out.println(applications[x].getDate());
                System.out.println("------------------------------------------------------------");
            }
        }
    }

    // view current internship
    public void viewInternship() {
        if (internship == null) {
            System.out.println("You have no internship yet!");
        } else {
            System.out.println("------------------------------------------------------------");
            System.out.printf("%d.  Title: %s\n   Status: %s\n   Date applied:", 1, internship.getTitle(),
                    internship.getStatus());
            System.out.println("------------------------------------------------------------");
        }
    }

    // accept an internship
    public void acceptInternship(int choice) {
        // choice is 1-based index from user input
        int index = choice - 1;
        // move the accepted application to position 0
        Application acceptedApp = applications[index];
        // shift other applications
        for (int i = index; i > 0; i--) {
            applications[i] = applications[i - 1];
        }
        applications[0] = acceptedApp;
        // set as current internship
        internship = acceptedApp.getInternship();
        System.out.println("Internship accepted: " + internship.getTitle());
    }

    // reject an internship
    public void rejectInternship(int index) {
        if (index < 0 || index >= applications.length || applications[index] == null) {
            System.out.println("Invalid application index!");
            return;
        }
        // shift applications to fill the gap
        for (int i = index; i < applications.length - 1; i++) {
            applications[i] = applications[i + 1];
        }
        applications[applications.length - 1] = null;

        System.out.println("Internship rejected successfully!");
    }

    public void requestWithdraw(int choice) {
        // applications[(choice - 1)].updateStatus("remove");
        applications[(choice - 1)] = null;
    }

}