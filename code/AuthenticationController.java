import java.util.List;

public class AuthenticationController {
    private final List<Student> students;
    private final List<CompanyRepresentative> companyRepresentatives;
    private final List<CareerCenterStaff> schoolStaff;

    public AuthenticationController(List<Student> students,
                                    List<CompanyRepresentative> companyRepresentatives,
                                    List<CareerCenterStaff> schoolStaff) {
        this.students = students;
        this.companyRepresentatives = companyRepresentatives;
        this.schoolStaff = schoolStaff;
    }
    public Student findStudent(String username) {
        for (Student student : students) {
            if (student.getID().equals(username)) {
                return student;
            }
        }
        return null;
    }
    public boolean checkStudentPassword(Student student, String password) {
        if (student.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
    public CompanyRepresentative findRep(String email) {
        for (CompanyRepresentative rep : companyRepresentatives) {
            if (rep.getEmail().equals(email)) {
                return rep;
            }
        }
        return null;
    }

    public boolean checkRepPassword(CompanyRepresentative rep, String password) {
        if (rep.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    public String checkCompanyRepStatus(CompanyRepresentative rep) {
        if (rep == null) return null;
        return rep.getStatus();
    }
    public CareerCenterStaff findStaff(String email) {
        for (CareerCenterStaff staff : schoolStaff) {
            if (staff.getEmail().equals(email)) {
                return staff;
            }
        }
        return null;
    }
    public boolean checkStaffPassword(CareerCenterStaff staff, String password) {
        if (staff.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
    public boolean userExists(String username, int userType) {
        switch (userType) {
            case 1:
                return students.stream().anyMatch(s -> s.getID().equals(username));
            case 2:
                return companyRepresentatives.stream().anyMatch(r -> r.getEmail().equals(username));
            case 3:
                return schoolStaff.stream().anyMatch(s -> s.getEmail().equals(username));
            default:
                return false;
        }
    }
}
