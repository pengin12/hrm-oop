
import java.time.LocalDate;

public abstract class Insurance {
    public enum InsuranceType {
        LIFE,
        HEALTH,
        SOCIAL,
        UNEMPLOYMENT
    }

    private String insuranceNumber;
    private InsuranceType type;
    private LocalDate startDate;
    private LocalDate endDate;
    private double rate;

    public Insurance(String insuranceNumber, InsuranceType type, LocalDate startDate, LocalDate endDate, double rate) {
        this.insuranceNumber = insuranceNumber;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rate = rate;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public InsuranceType getType() {
        return type;
    }

    public void setType(InsuranceType type) {
        this.type = type;
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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}