import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

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
  private Evaluation[] evaluations;
  private Contract[] contracts;
  private Payroll[] payrolls;
  private Attendance[] attendances;
  private Absence[] absences;
  private Resignation[] resignations;
  private Insurance[] insurances;
  private String taxId;
  private Position position;
  private Department department;

  private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  public Employee() {
    super(null, null, null, null, null, null, null);
    this.id = null;
    this.hireDate = null;
    this.specialization = null;
    this.status = null;
    this.evaluations = null;
    this.contracts = null;
    this.payrolls = null;
    this.attendances = null;
    this.absences = null;
    this.resignations = null;
    this.insurances = null;
    this.taxId = null;
    this.position = null;
    this.department = null;
  }

  public Employee(
      String firstName,
      String lastName,
      String email,
      String phone,
      LocalDate dateOfBirth,
      String gender,
      Address address,
      String id,
      LocalDate hireDate,
      String specialization,
      Employee.EmployeeStatus status,
      Evaluation[] evaluations,
      Contract[] contracts,
      Payroll[] payrolls,
      Attendance[] attendances,
      Absence[] absences,
      Resignation[] resignations,
      String taxId,
      Insurance[] insurances,
      Position position,
      Department department) {
    super(firstName, lastName, email, phone, dateOfBirth, gender, address);
    this.id = id;
    this.hireDate = hireDate;
    this.specialization = specialization;
    this.status = status;
    this.evaluations = evaluations;
    this.contracts = contracts;
    this.payrolls = payrolls;
    this.attendances = attendances;
    this.absences = absences;
    this.resignations = resignations;
    this.taxId = taxId;
    this.insurances = insurances;
    this.position = position;
    this.department = department;
  }

  private int getEvaluationsOccupied() {
    int count = 0;
    for (int i = 0; i < getEvaluations().length; i++) {
      if (getEvaluations()[i] == null) {
        break;
      }

      count++;
    }

    return count;
  }

  private int getContractsOccupied() {
    int count = 0;
    for (int i = 0; i < getContracts().length; i++) {
      if (getContracts()[i] == null) {
        break;
      }

      count++;
    }

    return count;
  }

  private int getPayrollsOccupied() {
    int count = 0;
    for (int i = 0; i < getPayrolls().length; i++) {
      if (getPayrolls()[i] == null) {
        break;
      }

      count++;
    }

    return count;
  }

  private int getAttendancesOccupied() {
    int count = 0;
    for (int i = 0; i < getAttendances().length; i++) {
      if (getAttendances()[i] == null) {
        break;
      }

      count++;
    }

    return count;
  }

  private int getResignationsOccupied() {
    int count = 0;
    for (int i = 0; i < getResignations().length; i++) {
      if (getResignations()[i] == null) {
        break;
      }

      count++;
    }

    return count;
  }

  private int getInsurancesOccupied() {
    int count = 0;
    for (int i = 0; i < getInsurances().length; i++) {
      if (getInsurances()[i] == null) {
        break;
      }

      count++;
    }

    return count;
  }

  public String getInfo(Scanner sc) {
    String content = "";

    content += "Mã: " + getId() + "\n";
    content += "Ngày tuyển dụng: " + getHireDate().format(dateFormatter) + "\n";
    content += "Chuyên môn: " + getSpecialization() + "\n";
    content += "Trạng thái: " + getStatus() + "\n";
    content += "Số đánh giá: " + getEvaluationsOccupied() + "\n";
    content += "Số hợp đồng: " + getContractsOccupied() + "\n";
    content += "Số bảng lương: " + getPayrollsOccupied() + "\n";
    content += "Số lượt chấm công: " + getAttendancesOccupied() + "\n";
    content += "Số đơn thôi việc: " + getResignationsOccupied() + "\n";
    content += "Mã số thuế: " + getTaxId() + "\n";
    content += "Số bảo hiểm: " + getInsurancesOccupied() + "\n";
    content += "Chức vụ: " + getPosition().getName() + "\n";
    content += "Phòng ban: " + getDepartment().getName() + "\n";

    return content;
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

  public Evaluation[] getEvaluations() {
    return evaluations;
  }

  public void setEvaluations(Evaluation[] evaluation) {
    this.evaluations = evaluation;
  }

  public Contract[] getContracts() {
    return contracts;
  }

  public void setContracts(Contract[] contract) {
    this.contracts = contract;
  }

  public Payroll[] getPayrolls() {
    return payrolls;
  }

  public void setPayrolls(Payroll[] payroll) {
    this.payrolls = payroll;
  }

  public Attendance[] getAttendances() {
    return attendances;
  }

  public void setAttendances(Attendance[] attendance) {
    this.attendances = attendance;
  }

  public Absence[] getAbsences() {
    return absences;
  }

  public void setAbsences(Absence[] absence) {
    this.absences = absence;
  }

  public Resignation[] getResignations() {
    return resignations;
  }

  public void setResignations(Resignation[] resignation) {
    this.resignations = resignation;
  }

  public Insurance[] getInsurances() {
    return insurances;
  }

  public void setInsurances(Insurance[] insurance) {
    this.insurances = insurance;
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
