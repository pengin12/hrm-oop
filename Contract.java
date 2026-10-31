import java.time.LocalDate;

public class Contract {
    public enum ContractStatus {
        DRAFT,
        ACTIVE,
        EXPIRED
    }

    public enum ContractType {
        PROBATION,
        FULL_TIME,
        PART_TIME,
        TEMPORARY,
        FIXED_TERM,
        PERMANENT
    }

    private LocalDate startDate;
    private LocalDate endDate;
    private ContractType type;
    private ContractStatus status;

    public Contract(LocalDate startDate, LocalDate endDate, ContractType type) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.status = ContractStatus.DRAFT;
    }

    public Contract(LocalDate startDate, LocalDate endDate, ContractType type, ContractStatus status) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ContractType getType() {
        return type;
    }

    public void setType(ContractType type) {
        this.type = type;
    }

    public ContractStatus getStatus() {
        return status;
    }

    public void setStatus(ContractStatus status) {
        this.status = status;
    }
}
