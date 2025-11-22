import java.io.*;
import java.util.List;

public class FileController {
    public void loadStudentsFromCSV(String filePath, List<Student> students) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length >= 5) {
                    String studentID = data[0].trim();
                    String name = data[1].trim();
                    String major = data[2].trim();
                    int yearOfStudy = Integer.parseInt(data[3].trim());
                    String email = data[4].trim();

                    Student student = new Student(studentID, studentID, name, "password",
                            yearOfStudy, major, null, new Application[3], email);
                    students.add(student);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading students: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing student data: " + e.getMessage());
        }
    }
    public void loadStaffFromCSV(String filePath, List<CareerCenterStaff> schoolStaff) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length >= 5) {
                    String staffID = data[0].trim();
                    String name = data[1].trim();
                    String department = data[3].trim();
                    String email = data[4].trim();

                    String userID = extractStaffIDFromEmail(email);
                    CareerCenterStaff staff = new CareerCenterStaff(userID, staffID, name, department, email);
                    schoolStaff.add(staff);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading staff: " + e.getMessage());
        }
    }
    public void loadCompanyRepsFromCSV(String filePath, List<CompanyRepresentative> companyRepresentatives) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");
                if (data.length >= 6 && !data[0].trim().isEmpty()) {
                    String repID = data[0].trim();
                    String name = data[1].trim();
                    String companyName = data[2].trim();
                    String department = data[3].trim();
                    String position = data[4].trim();
                    String email = data[5].trim();
                    String status = data[6].trim();

                    CompanyRepresentative rep = new CompanyRepresentative(repID, name, "password",
                            companyName, department, position, email);
                    if (status.equals("Approved")) {
                        rep.setStatus("Approved");
                    }
                    companyRepresentatives.add(rep);
                }
            }

            System.out.println("Loaded " + companyRepresentatives.size() + " company representatives");

        } catch (IOException e) {
            System.out.println("Note: No company representatives loaded - file may be empty or not exist");
            System.out.println("Company representatives can register through the system");
        }
    }
    public void saveStudentsToCSV(String filePath, List<Student> students) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("StudentID,Name,Major,Year,Email");
            for (Student student : students) {
                pw.println(String.join(",",
                        student.getID(),
                        student.getName(),
                        student.getMajor(),
                        String.valueOf(student.getYearOfStudy()),
                        student.getEmail()));
            }
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }
    public void saveStaffToCSV(String filePath, List<CareerCenterStaff> schoolStaff) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("StaffID,Name,Role,Department,Email");
            for (CareerCenterStaff staff : schoolStaff) {
                pw.println(String.join(",",
                        staff.getID(),
                        staff.getName(),
                        "Career Center Staff",
                        staff.getStaffDepartment(),
                        staff.getEmail()));
            }
        } catch (IOException e) {
            System.out.println("Error saving staff: " + e.getMessage());
        }
    }
    public void saveCompanyRepsToCSV(String filePath, List<CompanyRepresentative> companyRepresentatives) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("CompanyRepID,Name,CompanyName,Department,Position,Email,Status");
            for (CompanyRepresentative rep : companyRepresentatives) {
                pw.println(String.join(",",
                        rep.getID(),
                        rep.getName(),
                        rep.getCompanyName(),
                        rep.getDepartment(),
                        rep.getPosition(),
                        rep.getEmail(),
                        rep.getStatus()));
            }
        } catch (IOException e) {
            System.out.println("Error saving company representatives: " + e.getMessage());
        }
    }
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
