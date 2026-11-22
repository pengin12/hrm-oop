import java.time.LocalDate;

public class Recruitment {
    public enum RecruitmentStatus {
        OPEN,
        CLOSED,
        CANCELLED
    }

    private int numberOfPositions;
    private LocalDate deadline;
    private RecruitmentStatus status;
    private Department department;
    private Position position;
    private Application[] applications = new Application[10];

    private int applicationCount = 0;

    public Recruitment() {
    }

    public Recruitment(int numberOfPositions,
            LocalDate deadline,
            RecruitmentStatus status,
            Department department,
            Position position) {
        this.numberOfPositions = numberOfPositions;
        this.deadline = deadline;
        this.status = status;
        this.department = department;
        this.position = position;
    }

    public void close() {
        this.status = RecruitmentStatus.CLOSED;
    }

    public int getNumberOfPositions() {
        return numberOfPositions;
    }

    public void setNumberOfPositions(int numberOfPositions) {
        this.numberOfPositions = numberOfPositions;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public RecruitmentStatus getStatus() {
        return status;
    }

    public void setStatus(RecruitmentStatus status) {
        this.status = status;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void addApplication(Application application) {
        if (application == null) {
            return;
        }

        if (applicationCount == applications.length) {
            Application[] newApplications = new Application[applications.length * 2];

            for (int i = 0; i < applications.length; i++) {
                newApplications[i] = applications[i];
            }

            applications = newApplications;
        }

        applications[applicationCount] = application;
        applicationCount++;
    }

    public Application[] getApplications() {
        return applications;
    }

    public int getApplicationCount() {
        return applicationCount;
    }

    public void setApplications(Application[] applications) {
        this.applications = applications;
    }
}
