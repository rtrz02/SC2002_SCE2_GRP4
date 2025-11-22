import java.util.Date;public class Application {
    String applicationID;
    String status;
    Date applicationDate;
    Student student;
    Internship internship;

    public Application(String ID, Internship opportunity, Student applicant){
        applicationDate = new Date();
        internship = opportunity;
        student = applicant;
        applicationID = ID;
        status = "Pending";
    }

    public void updateStatus(String state) {
        switch(state.toLowerCase()) {
            case "pending": status = "Pending";
                break;
            case "successful": status = "Successful";
                break;

            case "accepted": status = "Accepted";
                break;
            case "unsuccessful": status = "Unsuccessful";
                break;
            case "withdrawn": status = "Withdrawn";
                break;
            default:
                System.out.println("Invalid status: " + state + ". Status not changed");
                break;
        }
    }

    public Date getDate() {
        return applicationDate;
    }
    public Internship getInternship() {
        return internship;
    }
    public Student getStudent() {
        return student;
    }
    public String getApplicationID() {
        return applicationID;
    }
    public String getStatus() {
        return status;
    }
    public void setDate(Date newDate) {
        applicationDate = newDate;
    }
    public void setInternship(Internship newOpportunity) {
        internship = newOpportunity;
    }
    public void setStudent(Student newDude) {
        student = newDude;
    }
    public void setID(String newID) {
        applicationID = newID;
    }
}