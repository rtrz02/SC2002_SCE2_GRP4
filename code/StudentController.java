import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// control class for student-specific operations

public class StudentController {
    private final Scanner scanner;
    private final List<Internship> internships;

    // constructor
    public StudentController(Scanner scanner, List<Internship> internships) {
        this.scanner = scanner;
        this.internships = internships;
    }

    // check if the student is applying for an internship they have already applied
    // for
    public boolean checkDuplicate(Student student, Internship opportunity) {
        for (Application x : student.getApplications()) {
            if (x != null) {
                if (x.getInternship().getInternshipID().equals(opportunity.getInternshipID())) {
                    return true;
                }
            }
        }
        return false;
    }

    // used to print messages to prevent application if they already applied or do
    // not meet criteria
    public int checkApplication(Student student, Internship opportunity) {
        // get student Major
        String Major_check = student.getMajor();
        // get student year
        int year_check = student.getYearOfStudy();
        // if the major of the applied internship is not the same as the applicant's
        if (!opportunity.getPreferredMajor().equals(Major_check)) {
            return 1;
        } else if (this.checkDuplicate(student, opportunity)) {
            System.out.println("You already applied for that internship!");
            return 1;
        } else {
            switch (year_check) {
                case (1) -> {
                    // if the year of the student is 1 and the internship level is basic, allow
                    if (opportunity.getLevel().equals("Basic")) {
                        // if the internship has no available slots, don't allow
                        if (opportunity.getAvailableSlots() == 0) {
                            System.out.println("This internship is full!");
                            return 1;
                        }
                        return 0;
                    }
                    // if not don't allow, level is too high
                    else {
                        System.out.printf("Year 1 students are not allowed to apply for %s level internships!\n",
                                opportunity.getLevel());
                        return 1;
                    }
                }
                case (2) -> {
                    // if the year of the student is 2 and the internship level is basic, allow
                    if (opportunity.getLevel().equals("Basic")) {
                        if (opportunity.getAvailableSlots() == 0) {
                            System.out.println("This internship is full!");
                            return 1;
                        }
                        return 0;
                    }
                    // if not don't allow, level is too high
                    else {
                        System.out.printf("Year 2 students are not allowed to apply for %s level internships!\n",
                                opportunity.getLevel());
                        return 1;
                    }
                }
                default -> {
                    // year 3 and above students can apply for any level internship
                    if (opportunity.getAvailableSlots() == 0) {
                        System.out.println("This internship is full!");
                        return 1;
                    }
                    return 0;
                }
            }
        }
    }

    // handle student applying for an internship
    public void applyForInternship(Student student, InternshipFilter filter) {
        // prevent application if max limit hit
        if (student.getNoOfApplications() == 3) {
            System.out.println("You cannot apply for more than 3 internships at once!");
        } else {
            // filter internships shown by student's year (Basic for Y1&2, Y3 and above see
            // everything)
            switch (student.getYearOfStudy()) {
                case 1, 2 -> filter.setInternshipLevel("Basic");
                default -> filter.getStatus();
            }
            filter.setStatus("Approved"); // only show approved internships
            filter.setPreferredMajor(student.getMajor()); // filter by the student's Major
            InternshipBrowsingController.displayInternships(internships, student, filter);

            System.out.println("Input your preferred internship (input Internship ID):");
            String option = scanner.nextLine();
            option = option.toUpperCase();

            boolean success = false; // flag to check if an internship of the specified type can be found
            for (Internship opportunity : internships) {
                // if a matching ID is found
                if (opportunity.getInternshipID().compareTo(option) == 0) {

                    // if checkApplication returns a 0, then the application criterion is valid
                    if (this.checkApplication(student, opportunity) == 0) {
                        student.apply(opportunity);
                        // REMOVED: opportunity.setAvailableSlots(((opportunity.getAvailableSlots())
                        // -1));
                        for (Application fresh : student.getApplications()) {
                            if (fresh != null) { // incase there are null portions
                                // get the ID of the internship that the application corresponds to
                                String compare = fresh.getInternship().getInternshipID();
                                // if the application corresponds to the internship that is being applied for,
                                // then add that application to the internship
                                if (compare.compareTo(opportunity.getInternshipID()) == 0) {
                                    opportunity.addApplication(fresh);
                                }
                            }
                        }
                    } else {
                        System.out.println("You cannot apply for the internship of that ID!");
                    }
                    success = true; // indicates that the input was valid/ can be found
                    break;
                }
            }
            if (!success) {
                System.out.println("This internship ID cannot be found!");
            }
        }
    }

    // handle student accepting/rejecting internship offers
    public void acceptInternshipOffer(Student student) {
        // map to track successful application indices
        int[] translator = { -1, -1, -1 };
        Application[] options = student.getApplications();
        if (options.length == 0) {
            System.out.println("You have no applications pending! Apply for an internship first!");
            return;
        }
        int counter = 0;
        for (int y = 0; y < options.length; y++) {
            // only show successful applications
            if (options[y] != null && options[y].getStatus().equals("Successful")) {
                translator[counter] = y; // Store the actual array index
                counter++;
                // display the successful application
                System.out.println("------------------------------------------------------------");
                System.out.printf("%d.  Title: %s\n   Status: %s\n   Date applied:", counter,
                        options[y].getInternship().getTitle(), options[y].getStatus());
                System.out.println(options[y].getDate());
                System.out.println("------------------------------------------------------------");
            }
        }
        if (counter == 0) {
            System.out.println(
                    "You have no successful applications! Wait for a company representative to accept one of your applications first!");
            return;
        }
        System.out.printf("\nInput index of the application that you want to accept or reject (1-%d): ", counter);
        int choice = getMenuChoice();
        if (choice == -1)
            return; // invalid input handled in getMenuChoice
        choice = choice - 1; // convert to 0-based index
        if (choice < 0 || choice >= counter || translator[choice] == -1) {
            System.out.println("Invalid option!");
            return;
        }
        int actualIndex = translator[choice]; // get the actual array index
        Application selectedApp = student.getApplications()[actualIndex];
        System.out.printf("\nDo you want to accept or reject internship ID:%s? (a/r)\n",
                selectedApp.getInternship().getInternshipID());
        String selection = scanner.nextLine();

        switch (selection.toLowerCase()) {
            case "a" -> {
                // accept the selected internship
                selectedApp.updateStatus("Accepted");

                // reject all other applications and increment their slots
                Application[] allApplications = student.getApplications();
                for (int i = 0; i < allApplications.length; i++) {
                    if (allApplications[i] != null && i != actualIndex) {
                        String status = allApplications[i].getStatus();
                        if ("Successful".equals(status) || "Accepted".equals(status)) {
                            allApplications[i].getInternship().setAvailableSlots(
                                    allApplications[i].getInternship().getAvailableSlots() + 1
                            );
                        }
                        allApplications[i].updateStatus("Unsuccessful");
                    }
                }
                student.acceptInternship(actualIndex + 1); // +1 because student method expects 1-based index
                System.out.println("Internship accepted successfully!");
            }
            case "r" -> {
                selectedApp.updateStatus("Unsuccessful");
                selectedApp.getInternship().setAvailableSlots(
                        selectedApp.getInternship().getAvailableSlots() + 1);
                student.rejectInternship(actualIndex);
            }
            default -> System.out.println("Not a valid input!");
        }
    }

    // handle student making a withdrawal request
    public void makeWithdrawal(Student student, List<WithdrawalRequest> withdrawalRequests) {
        int length = 0;
        Application[] valid = student.getApplications();

        // get the number of applications the student has
        for (Application x : student.getApplications()) {
            if (x != null) {
                length = length + 1;
            }
        }

        // if no applications, cannot withdraw
        if (length == 0) {
            System.out.println("You have no internships/application to withdraw from!");
            return;
        }
        student.viewApplication();
        System.out.printf("Choose which application you would like to withdraw from (1-%d)\n", length);

        int choice = getMenuChoice();
        if (choice == -1)
            return; // invalid input
        if (choice > length || choice < 1) {
            System.out.println("Index is out of range!");
            return;
        }
        if (valid[choice - 1] != null) {
            Application working = valid[choice - 1];
            System.out.println("Give a short reason why you want to withdraw:");
            String reason = scanner.nextLine();
            WithdrawalRequest request = new WithdrawalRequest(student.getID(), working, reason);
            withdrawalRequests.add(request);

            // increment available slots when withdrawing
            working.getInternship().setAvailableSlots(
                    working.getInternship().getAvailableSlots() + 1);

            // update application status to withdrawn
            working.updateStatus("Withdrawn");

            // remove the application by shifting
            student.requestWithdraw(choice);

            System.out.println("Withdrawal request submitted successfully!");
        } else {
            System.out.println("Index is out of range!");
        }
    }

    // get menu choice with error handling
    private int getMenuChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            return choice;
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Please enter a valid number!");
            return -1;
        }
    }
}