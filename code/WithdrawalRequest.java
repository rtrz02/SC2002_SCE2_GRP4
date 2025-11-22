import java.util.Date;

public class WithdrawalRequest {
    private String requestID;
    private String reason;
    private String status;
    private Date requestDate;
    private Application application;

    // constructor
    public WithdrawalRequest(String id, Application app, String reason) {
        this.requestID = id;
        this.application = app;
        this.reason = reason;
        this.status = "Pending";
        this.requestDate = new Date();
    }

    // getter functions
    public String getRequestID() {
        return requestID;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public Application getApplication() {
        return application;
    }

    // display function
    public void show() {
        System.out.println("Withdrawal Request #" + requestID);
        System.out.println("Reason: " + reason);
        System.out.println("Status: " + status);
        System.out.println("Date: " + requestDate);
    }

    // required functions
    public void updateStatus(String newStatus) {
        if (newStatus != null && !newStatus.isEmpty()) {
            this.status = newStatus;
        }
    }

    // approve the withdrawal request, change application status to "Withdrawn"
    public void approve() {
        this.status = "Approved";
        if (application != null) {
            application.updateStatus("Withdrawn");
        }
    }

    // reject the withdrawal request
    public void reject() {
        this.status = "Rejected";
    }
}
