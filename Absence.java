import java.time.LocalDate;

public class Absence {
    public enum AbsenceStatus {
        PENDING,
        REJECTED,
        APPROVED
    }

    private String reason;
    private LocalDate fromDate;
    private LocalDate toDate;
    private AbsenceStatus status;

    public Absence(String reason, LocalDate fromDate, LocalDate toDate) {
        this.reason = reason;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.status = AbsenceStatus.PENDING;
    }

    public Absence(String reason, LocalDate fromDate, LocalDate toDate, AbsenceStatus status) {
        this.reason = reason;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public AbsenceStatus getStatus() {
        return status;
    }

    public void setStatus(AbsenceStatus status) {
        this.status = status;
    }
}