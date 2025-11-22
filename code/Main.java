import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;
    private static final List<Student> students = new ArrayList<>();
    private static final List<CompanyRepresentative> companyRepresentatives = new ArrayList<>();
    private static final List<CareerCenterStaff> schoolStaff = new ArrayList<>();
    private static final List<WithdrawalRequest> withdrawalRequests = new ArrayList<>();
    private static final List<Internship> internships = new ArrayList<>();
    private static final Map<String, InternshipFilter> userFilterSettings = new HashMap<>();
    private static FileController dataController;
    private static AuthenticationController authController;
    private static RegistrationController registrationController;
    private static ProfileController profileController;
    private static StudentController studentController;
    private static InternshipBrowsingController browsingController;
    private static LoginBoundary loginBoundary;
    private static RegistrationBoundary registrationBoundary;
    private static MenuBoundary menuBoundary;

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            dataController.saveCompanyRepsToCSV("sample_company_representative_list.csv", companyRepresentatives);
            dataController.saveStudentsToCSV("sample_student_list.csv", students);
            dataController.saveStaffToCSV("sample_staff_list.csv", schoolStaff);
        }));
        initializeSystem();
        int exitCode = 0;
        while (exitCode != 5) {
            loginBoundary.displayLoginScreen();

            int userType = loginBoundary.getUserTypeSelection();
            authenticateUser(userType);

            if (currentUser != null) {
                exitCode = runMainMenu();
            }
            currentUser = null;
        }

        System.out.println("Thank you for using Internship Placement Management System!");
        scanner.close();
    }

    private static void initializeSystem() {
        System.out.println("Initializing system...");
        dataController = new FileController();
        authController = new AuthenticationController(students, companyRepresentatives, schoolStaff);
        registrationController = new RegistrationController(students, companyRepresentatives, schoolStaff);
        profileController = new ProfileController(scanner);
        studentController = new StudentController(scanner, internships);
        browsingController = new InternshipBrowsingController(scanner, internships);
        loginBoundary = new LoginBoundary(scanner);
        registrationBoundary = new RegistrationBoundary(scanner);
        menuBoundary = new MenuBoundary(scanner);
        dataController.loadStudentsFromCSV("sample_student_list.csv", students);
        dataController.loadStaffFromCSV("sample_staff_list.csv", schoolStaff);
        dataController.loadCompanyRepsFromCSV("sample_company_representative_list.csv", companyRepresentatives);

        System.out.println("System initialized successfully!");
        System.out.println("Loaded: " + students.size() + " students, " +
                schoolStaff.size() + " staff, " +
                companyRepresentatives.size() + " company representatives");
    }
    private static void authenticateUser(int userType) {
        String username = loginBoundary.getUsername();
        String password = loginBoundary.getPassword();

        boolean authenticated = false;

        switch (userType) {
            case 1:
                Student student = authController.findStudent(username);
                if (student == null && loginBoundary.askToRegister()) {
                    registerStudent();
                    return;
                }
                else if (student == null){
                    loginBoundary.displayLoginInvalidUser();
                    return;
                }
                if (authController.checkStudentPassword(student, password)) {
                    currentUser = student;
                    authenticated = true;
                    break;
                }
                else {
                    loginBoundary.displayLoginPasswordFailure();
                    return;
                }

            case 2:
                CompanyRepresentative rep = authController.findRep(username);
                if (rep == null && loginBoundary.askToRegister()) {
                    registerCompanyRepresentative();
                    return;
                }
                else if (rep == null) {
                    loginBoundary.displayLoginInvalidUser();
                    return;
                }
                String status = authController.checkCompanyRepStatus(rep);
                if ("Approved".equals(status)) {
                    if (authController.checkRepPassword(rep, password)) {
                        currentUser = rep;
                        authenticated = true;
                        break;
                    }
                    else {
                        loginBoundary.displayLoginPasswordFailure();
                        return;
                    }
                } else if ("Pending".equals(status)) {
                    loginBoundary.displayAccountPending();
                    return;
                } else {
                    loginBoundary.displayAccountRejected();
                    return;
                }

            case 3:
                CareerCenterStaff staff = authController.findStaff(username);
                if (staff == null && loginBoundary.askToRegister()) {
                    registerStaff();
                    return;
                }
                else if(staff == null){
                    loginBoundary.displayLoginInvalidUser();
                    return;
                }
                if (authController.checkStaffPassword(staff, password)) {
                    currentUser = staff;
                    authenticated = true;
                    break;
                }
                else {
                    loginBoundary.displayLoginPasswordFailure();
                    return;
                }
        }
        if (authenticated) {
            loginBoundary.displayLoginSuccess(currentUser.getName());
        }
    }
    private static void registerStudent() {
        String[] details = registrationBoundary.getStudentRegistrationDetails();
        try {
            registrationController.registerStudent(details);
            dataController.saveStudentsToCSV("sample_student_list.csv", students);
            registrationBoundary.displayRegistrationSuccess("Student");
        } catch (Exception e) {
            registrationBoundary.displayRegistrationFailure(e.getMessage());
        }
    }

    private static void registerCompanyRepresentative() {
        String[] details = registrationBoundary.getCompanyRepRegistrationDetails();
        try {
            CompanyRepresentative newRep = registrationController.registerCompanyRep(details);
            dataController.saveCompanyRepsToCSV("sample_company_representative_list.csv", companyRepresentatives);
            registrationBoundary.displayCompanyRepID(newRep.getID());
            registrationBoundary.displayRegistrationSuccess("CompanyRep");
        } catch (Exception e) {
            registrationBoundary.displayRegistrationFailure(e.getMessage());
        }
    }
    private static void registerStaff() {
        String[] details = registrationBoundary.getStaffRegistrationDetails();
        try {
            registrationController.registerStaff(details);
            dataController.saveStaffToCSV("sample_staff_list.csv", schoolStaff);
            registrationBoundary.displayRegistrationSuccess("Staff");
        } catch (Exception e) {
            registrationBoundary.displayRegistrationFailure(e.getMessage());
        }
    }
    private static int runMainMenu() {
        boolean running = true;
        int choice = 0;

        while (running) {
            choice = menuBoundary.displayMainMenu();

            switch (choice) {
                case 1 -> handleProfileManagement();
                case 2 -> handleRoleSpecificFunctions();
                case 3 -> handleInternshipBrowsing();
                case 4, 5 -> running = false;
                default -> menuBoundary.displayInvalidChoice();
            }
        }

        menuBoundary.displayLogout();
        return choice;
    }
    private static void handleProfileManagement() {
        int choice = menuBoundary.displayProfileMenu();

        switch (choice) {
            case 1 -> profileController.changePassword(currentUser);
            case 2 -> profileController.viewProfile(currentUser);
            default -> menuBoundary.displayInvalidChoice();
        }
    }
    private static void handleRoleSpecificFunctions() {
        if (currentUser instanceof Student) {
            handleStudentFunctions();
        } else if (currentUser instanceof CompanyRepresentative) {
            handleCompanyRepFunctions();
        } else if (currentUser instanceof CareerCenterStaff) {
            handleStaffFunctions();
        }
    }
    private static void handleStudentFunctions() {
        Student student = (Student) currentUser;
        int choice = menuBoundary.displayStudentMenu();

        switch (choice) {
            case 1 -> studentController.applyForInternship(student, loadUserFilterSettings());
            case 2 -> student.viewApplication();
            case 3 -> studentController.acceptInternshipOffer(student);
            case 4 -> student.viewInternship();
            case 5 -> studentController.makeWithdrawal(student, withdrawalRequests);
            default -> menuBoundary.displayInvalidChoice();
        }
    }

    private static void handleCompanyRepFunctions() {
        CompanyRepresentative rep = (CompanyRepresentative) currentUser;
        boolean inMenu = true;

        while (inMenu) {
            int choice = menuBoundary.displayCompanyRepMenu();

            switch (choice) {
                case 1 -> CompanyRepController.createInternshipOpportunity(rep, scanner, internships);
                case 2 -> CompanyRepController.viewCompanyApplications(rep, scanner);
                case 3 -> CompanyRepController.approveRejectApplications(rep, scanner);
                case 4 -> CompanyRepController.toggleInternshipVisibility(rep, scanner);
                case 5 -> CompanyRepController.viewMyInternships(rep);
                case 6 -> inMenu = false;
                default -> menuBoundary.displayInvalidChoice();
            }
        }
    }

    private static void handleStaffFunctions() {
        CareerCenterStaff staff = (CareerCenterStaff) currentUser;
        int choice = menuBoundary.displayStaffMenu();

        switch (choice) {
            case 1 -> StaffController.authorizeCompanyRepresentatives(staff, scanner, companyRepresentatives);
            case 2 -> StaffController.approveRejectInternships(staff, scanner, internships);
            case 3 -> StaffController.processWithdrawalRequests(staff, scanner, withdrawalRequests, internships);
            case 4 -> StaffController.generateReport(staff, scanner, internships, menuBoundary);
            default -> menuBoundary.displayInvalidChoice();
        }
    }
    private static void handleInternshipBrowsing() {
        InternshipFilter filter = loadUserFilterSettings();
        boolean browsing = true;

        while (browsing) {
            int choice = menuBoundary.displayBrowsingMenu();

            switch (choice) {
                case 1 ->
                        InternshipBrowsingController.displayInternships(internships, currentUser, new InternshipFilter());
                case 2 -> configureAndSearchFilters(filter);
                case 3 -> filter.displayCurrentFilters();
                case 4 -> {
                    filter.resetFilters();
                    System.out.println("Filters reset to default.");
                }
                case 5 -> {
                    saveUserFilterSettings(filter);
                    System.out.println("Filter settings saved.");
                }
                case 6 -> browsing = false;
                default -> menuBoundary.displayInvalidChoice();
            }
        }
    }
    private static void configureAndSearchFilters(InternshipFilter filter) {
        boolean configuring = true;

        while (configuring) {
            int choice = menuBoundary.displayFilterMenu(filter);

            if (choice >= 1 && choice <= 6) {
                browsingController.configureFilter(filter, choice);
            } else if (choice == 7) {
                InternshipBrowsingController.displayInternships(internships, currentUser, filter);
                configuring = false;
            } else if (choice == 8) {
                configuring = false;
            } else {
                menuBoundary.displayInvalidChoice();
            }
        }
    }
    private static InternshipFilter loadUserFilterSettings() {
        if (currentUser != null) {
            InternshipFilter savedFilter = userFilterSettings.get(currentUser.getID());
            if (savedFilter != null) {
                return savedFilter.copy();
            }
        }
        return new InternshipFilter();
    }
    private static void saveUserFilterSettings(InternshipFilter filter) {
        if (currentUser != null) {
            userFilterSettings.put(currentUser.getID(), filter.copy());
        }
    }
}
