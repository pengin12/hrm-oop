import java.time.LocalDate;

public class Resignation {
    public enum ResignationStatus {
        PENDING,
        REJECTED,
        APPROVED
    }

    private String reason;
    private LocalDate date;
    private ResignationStatus status;

    public Resignation(String reason, LocalDate date) {
        this.reason = reason;
        this.date = date;
        this.status = ResignationStatus.PENDING;
    }

    public Resignation(String reason, LocalDate date, ResignationStatus status) {
        this.reason = reason;
        this.date = date;
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ResignationStatus getStatus() {
        return status;
    }

    public void setStatus(ResignationStatus status) {
        this.status = status;
    }
}