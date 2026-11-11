import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DataHandler {
  private Account[] accounts;
  private Employee[] employees;
  private Department[] departments;
  private Position[] positions;

  private String currentDirectory = System.getProperty("user.dir");
  private String directoryPath = currentDirectory + File.separator + "data";

  private String accountPath = directoryPath + File.separator + "accounts.txt";
  private String employeePath = directoryPath + File.separator + "employee.txt";
  private String departmentPath = directoryPath + File.separator + "department.txt";
  private String positionPath = directoryPath + File.separator + "position.txt";

  private Account[] readAccountsData() {
    try (Scanner sc = new Scanner(accountPath)) {

    }

  }

  public DataHandler readData() {
    DataHandler data = new DataHandler();

    return data;
  }

  private String writeAccountData(Account[] accounts) {
    String data = "";

    for (int i = 0; i < accounts.length; i++) {
      Account currentAccount = accounts[i];
      if (currentAccount == null) {
        continue;
      }

      if (currentAccount instanceof AdminAccount) {
        data += "admin\n";
      } else {
        data += "employee\n";
      }

      data += currentAccount.getUsername() + "\n";
      data += currentAccount.getPassword() + "\n";
      data += currentAccount.getStatus() + "\n";
      data += "\n";
    }

    return data;
  }

  private String writeEmployeeData(Employee[] employees) {
    String data = "";

    for (int i = 0; i < employees.length; i++) {
      Employee currentEmployee = employees[i];
      if (currentEmployee == null) {
        continue;
      }

      data += currentEmployee.getFirstName() + "\n";
      data += currentEmployee.getLastName() + "\n";
      data += currentEmployee.getEmail() + "\n";
      data += currentEmployee.getPhone() + "\n";
      data += currentEmployee.getDateOfBirth() + "\n";
      data += currentEmployee.getGender() + "\n";

      data += "{\n";
      data += currentEmployee.getAddress().getNumber() + "\n";
      data += currentEmployee.getAddress().getStreet() + "\n";
      data += currentEmployee.getAddress().getWard() + "\n";
      data += currentEmployee.getAddress().getDistrict() + "\n";
      data += currentEmployee.getAddress().getCity() + "\n";
      data += "}\n";

      data += currentEmployee.getId() + "\n";
      data += currentEmployee.getHireDate() + "\n";
      data += currentEmployee.getSpecialization() + "\n";
      data += currentEmployee.getStatus() + "\n";

      data += "{\n";
      Evaluation[] evaluations = currentEmployee.getEvaluations();
      if (evaluations == null) {
        data += "null\n";
      } else {
        for (int j = 0; j < evaluations.length; j++) {
          Evaluation currentEvaluation = evaluations[j];
          if (currentEvaluation == null) {
            break;
          }

          data += "{\n";

          data += currentEvaluation.getMonth() + "\n";
          data += currentEvaluation.getMonth() + "\n";
          data += currentEvaluation.getComment() + "\n";
          data += currentEvaluation.getWorkScore() + "\n";
          data += currentEvaluation.getAttitudeScore() + "\n";
          data += currentEvaluation.getDisciplineScore() + "\n";
          data += currentEvaluation.getWorkQuality() + "\n";
          data += currentEvaluation.getProductivity() + "\n";
          data += currentEvaluation.getTeamWork() + "\n";

          data += "}\n";
        }
      }
      data += "}\n";

      data += "{\n";
      Contract[] contracts = currentEmployee.getContracts();
      if (contracts == null) {
        data += "null\n";
      } else {
        for (int j = 0; j < contracts.length; j++) {
          Contract currentContract = contracts[j];
          if (currentContract == null) {
            break;
          }

          data += "{\n";

          data += currentContract.getStartDate() + "\n";
          data += currentContract.getEndDate() + "\n";
          data += currentContract.getType() + "\n";
          data += currentContract.getStatus() + "\n";

          data += "}\n";
        }
      }
      data += "}\n";

      data += "{\n";
      Payroll[] payrolls = currentEmployee.getPayrolls();
      if (payrolls == null) {
        data += "null\n";
      } else {
        for (int j = 0; j < payrolls.length; j++) {
          Payroll currentPayroll = payrolls[j];
          if (currentPayroll == null) {
            break;
          }

          data += "{\n";

          data += currentPayroll.getMonth() + "\n";
          data += currentPayroll.getYear() + "\n";
          data += currentPayroll.getBaseSalary() + "\n";
          data += currentPayroll.getBonus() + "\n";
          data += currentPayroll.getAllowance() + "\n";
          data += currentPayroll.getDeduction() + "\n";
          data += currentPayroll.getTaxAmount() + "\n";
          data += currentPayroll.getInsuranceAmount() + "\n";
          data += currentPayroll.getWorkedHours() + "\n";
          data += currentPayroll.getOvertimeHours() + "\n";
          data += currentPayroll.getLastSalaryIncreaseDate() + "\n";

          data += "}\n";
        }
      }
      data += "}\n";

      data += "{\n";
      Attendance[] attendances = currentEmployee.getAttendances();
      if (attendances == null) {
        data += "null\n";
      } else {
        for (int j = 0; j < attendances.length; j++) {
          Attendance currentAttendance = attendances[j];
          if (currentAttendance == null) {
            break;
          }

          data += "{\n";

          data += currentAttendance.getDate() + "\n";
          data += currentAttendance.getClockIn() + "\n";
          data += currentAttendance.getClockOut() + "\n";

          data += "}\n";
        }
      }
      data += "}\n";

      data += "{\n";
      Absence[] absences = currentEmployee.getAbsences();
      if (absences == null) {
        data += "null\n";
      } else {
        for (int j = 0; j < absences.length; j++) {
          Absence currentAbsence = absences[j];
          if (currentAbsence == null) {
            break;
          }

          data += "{\n";

          data += currentAbsence.getReason() + "\n";
          data += currentAbsence.getFromDate() + "\n";
          data += currentAbsence.getToDate() + "\n";
          data += currentAbsence.getStatus() + "\n";

          data += "}\n";
        }
      }
      data += "}\n";

      data += "{\n";
      Resignation[] resignations = currentEmployee.getResignations();
      if (resignations == null) {
        data += "null\n";
      } else {
        for (int j = 0; j < resignations.length; j++) {
          Resignation currentResignation = resignations[j];
          if (currentResignation == null) {
            break;
          }

          data += "{\n";

          data += currentResignation.getReason() + "\n";
          data += currentResignation.getDate() + "\n";
          data += currentResignation.getStatus() + "\n";

          data += "}\n";
        }
      }
      data += "}\n";

      data += "{\n";
      Insurance[] insurances = currentEmployee.getInsurances();
      if (insurances == null) {
        data += "null\n";
      } else {
        for (int j = 0; j < insurances.length; j++) {
          Insurance currentInsurance = insurances[j];
          if (currentInsurance == null) {
            break;
          }

          data += "{\n";

          data += currentInsurance.getInsuranceNumber() + "\n";
          data += currentInsurance.getType() + "\n";
          data += currentInsurance.getStartDate() + "\n";
          data += currentInsurance.getEndDate() + "\n";
          data += currentInsurance.getRate() + "\n";

          data += "}\n";
        }
      }
      data += "}\n";

      data += currentEmployee.getTaxId() + "\n";

      data += currentEmployee.getPosition() + "\n";
      data += currentEmployee.getDepartment() + "\n";
      data += "\n";
    }

    return data;
  }

  private String writeDepartmentData(Department[] departments) {
    String data = "";

    for (int i = 0; i < departments.length; i++) {
      Department currentDepartment = departments[i];
      if (currentDepartment == null) {
        continue;
      }

      data += currentDepartment.getName() + "\n";
      data += currentDepartment.getPhone() + "\n";
      data += currentDepartment.getPosition() + "\n";
      data += "\n";
    }

    return data;
  }

  private String writePositionData(Position[] positions) {
    String data = "";

    for (int i = 0; i < positions.length; i++) {
      Position currentPosition = positions[i];
      if (currentPosition == null) {
        continue;
      }

      data += currentPosition.getName() + "\n";
      data += currentPosition.getPositionCoefficient() + "\n";
      data += currentPosition.getResponsibilities() + "\n";
      data += "\n";
    }

    return data;
  }

  public void writeData() {
    try {
      FileWriter accountFileWriter = new FileWriter(accountPath);
      FileWriter employeeFileWriter = new FileWriter(employeePath);
      FileWriter departmentFileWriter = new FileWriter(departmentPath);
      FileWriter positionFileWriter = new FileWriter(positionPath);

      accountFileWriter.write(writeAccountData(accounts));
      employeeFileWriter.write(writeEmployeeData(employees));
      departmentFileWriter.write(writeDepartmentData(departments));
      positionFileWriter.write(writePositionData(positions));

      accountFileWriter.close();
      employeeFileWriter.close();
      departmentFileWriter.close();
      positionFileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Account[] getAccounts() {
    return accounts;
  }

  public void setAccounts(Account[] accounts) {
    this.accounts = accounts;
  }

  public Employee[] getEmployees() {
    return employees;
  }

  public void setEmployees(Employee[] employees) {
    this.employees = employees;
  }

  public Department[] getDepartments() {
    return departments;
  }

  public void setDepartments(Department[] departments) {
    this.departments = departments;
  }

  public Position[] getPositions() {
    return positions;
  }

  public void setPositions(Position[] positions) {
    this.positions = positions;
  }
}
