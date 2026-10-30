import java.time.LocalDate;

public class Employee extends Person {
  public enum EmployeeStatus {
    WORKING,
    ON_LEAVE,
    RESIGNED
  }

  private String id;
  private LocalDate hireDate;
  private String specialization;
  private EmployeeStatus status;
  private Evaluation[] evaluation;
  private Contract[] contract;
  private Payroll[] payroll;
  private Attendance[] attendance;
  private Absence[] absence;
  private Resignation resignation;
  private Insurance[] insurance;
  private String taxId;
  private Position position;
  private Department department;

  public Employee() {
    this.id = "";
    this.hireDate = LocalDate.parse("1000-01-01");
    this.specialization = "";
    this.status = EmployeeStatus.WORKING;
    this.evaluation = new Evaluation[1000];
    this.contract = new Contract[1000];
    this.payroll = new Payroll[1000];
    this.attendance = new Attendance[1000];
    this.resignation = new Resignation[1000];
    this.insurance = new Insurance[1000];
    this.taxId = "";
  }

  public Employee(String id, LocalDate hireDate, String specialization, String status, Evaluation[] evaluation,
      Contract[] contract, Payroll[] payroll, Attendance[] attendance, Absence[] absence, Resignation resignation,
      Position position, Department department, String taxId, Insurance[] insurance) {
    this.id = id;
    this.hireDate = hireDate;
    this.specialization = specialization;
    this.status = status;
    this.evaluation = evaluation;
    this.contract = contract;
    this.payroll = payroll;
    this.attendance = attendance;
    this.absence = absence;
    this.resignation = resignation;
    this.position = position;
    this.department = department;
    this.taxId = taxId;
    this.insurance = insurance;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public LocalDate getHireDate() {
    return hireDate;
  }

  public void setHireDate(LocalDate hireDate) {
    this.hireDate = hireDate;
  }

  public String getSpecialization() {
    return specialization;
  }

  public void setSpecialization(String specialization) {
    this.specialization = specialization;
  }

  public EmployeeStatus getStatus() {
    return status;
  }

  public void setStatus(EmployeeStatus status) {
    this.status = status;
  }

  public Evaluation[] getEvaluation() {
    return evaluation;
  }

  public void setEvaluation(Evaluation[] evaluation) {
    this.evaluation = evaluation;
  }

  public Contract[] getContract() {
    return contract;
  }

  public void setContract(Contract[] contract) {
    this.contract = contract;
  }

  public Payroll[] getPayroll() {
    return payroll;
  }

  public void setPayroll(Payroll[] payroll) {
    this.payroll = payroll;
  }

  public Attendance[] getAttendance() {
    return attendance;
  }

  public void setAttendance(Attendance[] attendance) {
    this.attendance = attendance;
  }

  public Absence[] getAbsence() {
    return absence;
  }

  public void setAbsence(Absence[] absence) {
    this.absence = absence;
  }

  public Resignation getResignation() {
    return resignation;
  }

  public void setResignation(Resignation resignation) {
    this.resignation = resignation;
  }

  public Insurance[] getInsurance() {
    return insurance;
  }

  public void setInsurance(Insurance[] insurance) {
    this.insurance = insurance;
  }

  public String getTaxId() {
    return taxId;
  }

  public void setTaxId(String taxId) {
    this.taxId = taxId;
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }
}
