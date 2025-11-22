import java.util.List;

// control class for handling user registration
public class RegistrationController {
    private final List<Student> students;
    private final List<CompanyRepresentative> companyRepresentatives;
    private final List<CareerCenterStaff> schoolStaff;

    // constructor
    public RegistrationController(List<Student> students,
            List<CompanyRepresentative> companyRepresentatives,
            List<CareerCenterStaff> schoolStaff) {
        this.students = students;
        this.companyRepresentatives = companyRepresentatives;
        this.schoolStaff = schoolStaff;
    }

    // register a new student
    public Student registerStudent(String[] details) {
        String stuID = details[0];
        String name = details[1];
        String major = details[2];
        int year = Integer.parseInt(details[3]);
        String email = details[4];
        Student newStudent = new Student(stuID, stuID, name, "password", year, major,
                null, new Application[3], email);
        students.add(newStudent);

        return newStudent;
    }

    // register a new company representative
    public CompanyRepresentative registerCompanyRep(String[] details) {
        String email = details[0];
        String name = details[1];
        String companyName = details[2];
        String department = details[3];
        String position = details[4];

        String companyRepID = generateNextCompanyRepID();

        CompanyRepresentative newRep = new CompanyRepresentative(
                companyRepID, name, "password", companyName, department, position, email);

        companyRepresentatives.add(newRep);

        return newRep;
    }

    // register a new career center staff
    public CareerCenterStaff registerStaff(String[] details) {
        String staffID = details[0];
        String name = details[1];
        String department = details[2];
        String email = details[3];

        String userID = extractStaffIDFromEmail(email);
        CareerCenterStaff newStaff = new CareerCenterStaff(userID, staffID, name, department, email);

        schoolStaff.add(newStaff);

        return newStaff;
    }

    // generate the next unique company representative ID
    private String generateNextCompanyRepID() {
        int maxID = 2000;

        for (CompanyRepresentative rep : companyRepresentatives) {
            String repID = rep.getID();
            if (repID.startsWith("R")) {
                try {
                    int idNum = Integer.parseInt(repID.substring(1));
                    if (idNum > maxID) {
                        maxID = idNum;
                    }
                } catch (NumberFormatException e) {
                    // Skip if ID format is invalid
                }
            }
        }

        return "R" + (maxID + 1);
    }

    // extract staff ID from email address
    private String extractStaffIDFromEmail(String email) {
        if (email == null || email.isEmpty()) {
            return null;
        }

        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            return null;
        }

        return email.substring(0, atIndex);
    }
}
