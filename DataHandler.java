import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Scanner;

public class DataHandler {
  private int maxEvaluations;
  private int maxContracts;
  private int maxPayrolls;
  private int maxAttendances;
  private int maxAbsences;
  private int maxInsurances;
  private int maxResignations;

  private Account[] accounts;
  private Employee[] employees;
  private Department[] departments;
  private Position[] positions;
  private Recruitment[] recruitments;

  private String currentDirectory = System.getProperty("user.dir");
  private String directoryPath = currentDirectory + File.separator + "data";

  private String accountPath = directoryPath + File.separator + "accounts.txt";
  private String employeePath = directoryPath + File.separator + "employees.txt";
  private String departmentPath = directoryPath + File.separator + "departments.txt";
  private String positionPath = directoryPath + File.separator + "positions.txt";
  private String recruitmentPath = directoryPath + File.separator + "recruitments.txt";

  public DataHandler(
      int maxAccounts,
      int maxEmployees,
      int maxDepartments,
      int maxPositions,
      int maxRecruitments,
      int maxEvaluations,
      int maxContracts,
      int maxPayrolls,
      int maxAttendances,
      int maxAbsences,
      int maxInsurances,
      int maxResignations) {
    this.accounts = new Account[maxAccounts];
    this.employees = new Employee[maxEmployees];
    this.departments = new Department[maxDepartments];
    this.positions = new Position[maxPositions];
    this.recruitments = new Recruitment[maxRecruitments];

    this.maxEvaluations = maxEvaluations;
    this.maxContracts = maxContracts;
    this.maxPayrolls = maxPayrolls;
    this.maxAttendances = maxAttendances;
    this.maxAbsences = maxAbsences;
    this.maxResignations = maxResignations;
    this.maxInsurances = maxInsurances;
  }

  private Account[] readAccountsData() {
    try (Scanner sc = new Scanner(new File(accountPath))) {
      int i = 0;
      while (sc.hasNextLine() && i < accounts.length) {
        String type = sc.nextLine();
        String username = sc.nextLine();
        String password = sc.nextLine();
        Account.AccountStatus status = Account.AccountStatus.valueOf(sc.nextLine());

        if (type.equals("admin")) {
          this.accounts[i] = new AdminAccount(username, password, status);
        } else {
          this.accounts[i] = new EmployeeAccount(username, password, status);
        }

        i++;
        sc.nextLine();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return this.accounts;
  }

  private Employee[] readEmployeesData() {
    try (Scanner sc = new Scanner(new File(employeePath))) {
      int i = 0;
      while (sc.hasNextLine() && i < employees.length) {
        this.employees[i] = new Employee();
        Employee thisEmployee = this.employees[i];

        thisEmployee.setEvaluations(new Evaluation[maxEvaluations]);
        thisEmployee.setContracts(new Contract[maxContracts]);
        thisEmployee.setPayrolls(new Payroll[maxPayrolls]);
        thisEmployee.setAttendances(new Attendance[maxAttendances]);
        thisEmployee.setAbsences(new Absence[maxAbsences]);
        thisEmployee.setResignations(new Resignation[maxResignations]);
        thisEmployee.setInsurances(new Insurance[maxInsurances]);

        String tmp;
        int idx = 0;

        thisEmployee.setFirstName(sc.nextLine());
        thisEmployee.setLastName(sc.nextLine());
        thisEmployee.setEmail(sc.nextLine());
        thisEmployee.setPhone(sc.nextLine());
        thisEmployee.setDateOfBirth(LocalDate.parse(sc.nextLine()));
        thisEmployee.setGender(sc.nextLine());

        sc.nextLine();
        thisEmployee.setAddress(new Address());
        Address thisAddress = thisEmployee.getAddress();
        thisAddress.setNumber(sc.nextLine());
        thisAddress.setStreet(sc.nextLine());
        thisAddress.setWard(sc.nextLine());
        thisAddress.setDistrict(sc.nextLine());
        thisAddress.setCity(sc.nextLine());
        sc.nextLine();

        thisEmployee.setId(sc.nextLine());
        thisEmployee.setHireDate(LocalDate.parse(sc.nextLine()));
        thisEmployee.setSpecialization(sc.nextLine());
        thisEmployee.setStatus(Employee.EmployeeStatus.valueOf(sc.nextLine()));

        sc.nextLine();
        tmp = sc.nextLine();

        Evaluation[] thisEvaluations = thisEmployee.getEvaluations();

        while (!tmp.equals("}")) {
          tmp = sc.nextLine();
          thisEvaluations[idx] = new Evaluation();
          thisEvaluations[idx].setMonth(YearMonth.parse(tmp));
          thisEvaluations[idx].setComment(sc.nextLine());
          thisEvaluations[idx].setWorkScore(Double.parseDouble(sc.nextLine()));
          thisEvaluations[idx].setAttitudeScore(Double.parseDouble(sc.nextLine()));
          thisEvaluations[idx].setDisciplineScore(Double.parseDouble(sc.nextLine()));
          thisEvaluations[idx].setWorkQuality(Double.parseDouble(sc.nextLine()));
          thisEvaluations[idx].setProductivity(Double.parseDouble(sc.nextLine()));
          thisEvaluations[idx].setTeamWork(Double.parseDouble(sc.nextLine()));

          idx++;

          sc.nextLine();
          tmp = sc.nextLine();
        }
        idx = 0;

        sc.nextLine();
        tmp = sc.nextLine();

        Contract[] thisContracts = thisEmployee.getContracts();

        while (!tmp.equals("}")) {
          tmp = sc.nextLine();
          thisContracts[idx] = new Contract();
          thisContracts[idx].setStartDate(LocalDate.parse(tmp));
          thisContracts[idx].setEndDate(LocalDate.parse(sc.nextLine()));
          thisContracts[idx].setType(Contract.ContractType.valueOf(sc.nextLine()));
          thisContracts[idx].setStatus(Contract.ContractStatus.valueOf(sc.nextLine()));

          sc.nextLine();
          idx++;

          tmp = sc.nextLine();
        }
        idx = 0;

        sc.nextLine();
        tmp = sc.nextLine();

        Payroll[] thisPayrolls = thisEmployee.getPayrolls();

        while (!tmp.equals("}")) {
          tmp = sc.nextLine();
          thisPayrolls[idx] = new Payroll();
          thisPayrolls[idx].setMonth(YearMonth.parse(tmp));
          thisPayrolls[idx].setBaseSalary(Double.parseDouble(sc.nextLine()));
          thisPayrolls[idx].setBonus(Double.parseDouble(sc.nextLine()));
          thisPayrolls[idx].setAllowance(Double.parseDouble(sc.nextLine()));
          thisPayrolls[idx].setDeduction(Double.parseDouble(sc.nextLine()));
          thisPayrolls[idx].setTaxAmount(Double.parseDouble(sc.nextLine()));
          thisPayrolls[idx].setInsuranceAmount(Double.parseDouble(sc.nextLine()));
          thisPayrolls[idx].setWorkedHours(Integer.parseInt(sc.nextLine()));
          thisPayrolls[idx].setOvertimeHours(Integer.parseInt(sc.nextLine()));
          thisPayrolls[idx].setLastSalaryIncreaseDate(LocalDate.parse(sc.nextLine()));

          sc.nextLine();
          idx++;

          tmp = sc.nextLine();
        }
        idx = 0;

        sc.nextLine();
        tmp = sc.nextLine();

        Attendance[] thisAttendances = thisEmployee.getAttendances();

        while (!tmp.equals("}")) {
          tmp = sc.nextLine();

          thisAttendances[idx] = new Attendance();
          thisAttendances[idx].setDate(LocalDate.parse(tmp));
          thisAttendances[idx].setClockIn(LocalTime.parse(sc.nextLine()));
          String outStr = sc.nextLine();
          thisAttendances[idx].setClockOut(outStr.equals("null") ? null : LocalTime.parse(outStr));

          sc.nextLine();
          idx++;

          tmp = sc.nextLine();
        }
        idx = 0;

        sc.nextLine();
        tmp = sc.nextLine();

        Absence[] thisAbsences = thisEmployee.getAbsences();

        while (!tmp.equals("}")) {
          tmp = sc.nextLine();
          thisAbsences[idx] = new Absence();
          thisAbsences[idx].setReason(tmp);
          thisAbsences[idx].setFromDate(LocalDate.parse(sc.nextLine()));
          thisAbsences[idx].setToDate(LocalDate.parse(sc.nextLine()));
          thisAbsences[idx].setStatus(Absence.AbsenceStatus.valueOf(sc.nextLine()));

          sc.nextLine();
          idx++;

          tmp = sc.nextLine();
        }
        idx = 0;

        sc.nextLine();
        tmp = sc.nextLine();

        Resignation[] thisResignations = thisEmployee.getResignations();

        while (!tmp.equals("}")) {
          tmp = sc.nextLine();
          thisResignations[idx] = new Resignation();
          thisResignations[idx].setReason(tmp);
          thisResignations[idx].setDate(LocalDate.parse(sc.nextLine()));
          thisResignations[idx].setStatus(Resignation.ResignationStatus.valueOf(sc.nextLine()));

          sc.nextLine();
          idx++;

          tmp = sc.nextLine();
        }
        idx = 0;

        sc.nextLine();
        tmp = sc.nextLine();

        Insurance[] thisInsurances = thisEmployee.getInsurances();

        while (!tmp.equals("}")) {
          tmp = sc.nextLine();
          thisInsurances[idx] = new Insurance();
          thisInsurances[idx].setInsuranceNumber(tmp);
          thisInsurances[idx].setType(Insurance.InsuranceType.valueOf(sc.nextLine()));
          thisInsurances[idx].setStartDate(LocalDate.parse(sc.nextLine()));
          thisInsurances[idx].setEndDate(LocalDate.parse(sc.nextLine()));
          thisInsurances[idx].setRate(Double.parseDouble(sc.nextLine()));

          sc.nextLine();
          idx++;

          tmp = sc.nextLine();
        }
        idx = 0;

        thisEmployee.setTaxId(sc.nextLine());

        thisEmployee.setPosition(positions[Integer.parseInt(sc.nextLine())]);
        thisEmployee.setDepartment(departments[Integer.parseInt(sc.nextLine())]);

        sc.nextLine();

        i++;
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return this.employees;
  }

  private Department[] readDepartmentsData() {
    int idx = 0;
    try (Scanner sc = new Scanner(new File(departmentPath))) {
      while (sc.hasNextLine() && idx < departments.length) {
        departments[idx] = new Department();
        Department thisDepartment = departments[idx];
        thisDepartment.setName(sc.nextLine());
        thisDepartment.setPhone(sc.nextLine());
        thisDepartment.setPosition(sc.nextLine());
        sc.nextLine();

        idx++;
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return this.departments;
  }

  private Position[] readPositionsData() {
    int idx = 0;
    try (Scanner sc = new Scanner(new File(positionPath))) {
      while (sc.hasNextLine() && idx < positions.length) {
        positions[idx] = new Position();
        Position thisPosition = positions[idx];
        thisPosition.setName(sc.nextLine());
        thisPosition.setPositionCoefficient(Double.parseDouble(sc.nextLine()));
        thisPosition.setResponsibilities(sc.nextLine());
        sc.nextLine();
        idx++;
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return this.positions;
  }

  private Recruitment[] readRecruitmentsData() {
    int idx = 0;
    try (Scanner sc = new Scanner(new File(recruitmentPath))) {
      while (sc.hasNextLine() && idx < recruitments.length) {
        Recruitment thisRecruitment = new Recruitment();

        thisRecruitment.setNumberOfPositions(Integer.parseInt(sc.nextLine()));
        thisRecruitment.setDeadline(LocalDate.parse(sc.nextLine()));
        thisRecruitment.setStatus(Recruitment.RecruitmentStatus.valueOf(sc.nextLine()));

        thisRecruitment.setDepartment(departments[Integer.parseInt(sc.nextLine())]);
        thisRecruitment.setPosition(positions[Integer.parseInt(sc.nextLine())]);

        sc.nextLine();
        String tmp = sc.nextLine();

        while (!tmp.equals("}")) {
          tmp = sc.nextLine();

          LocalDate applyDate = LocalDate.parse(tmp);
          String coverLetter = sc.nextLine();
          String resume = sc.nextLine();
          Application.ApplicationStatus status = Application.ApplicationStatus.valueOf(sc.nextLine());

          Candidate candidate = new Candidate();
          candidate.setFirstName(sc.nextLine());
          candidate.setLastName(sc.nextLine());
          candidate.setEmail(sc.nextLine());
          candidate.setPhone(sc.nextLine());
          candidate.setDateOfBirth(LocalDate.parse(sc.nextLine()));
          candidate.setGender(sc.nextLine());

          candidate.setAddress(new Address());
          candidate.getAddress().setNumber(sc.nextLine());
          candidate.getAddress().setStreet(sc.nextLine());
          candidate.getAddress().setWard(sc.nextLine());
          candidate.getAddress().setDistrict(sc.nextLine());
          candidate.getAddress().setCity(sc.nextLine());

          thisRecruitment.addApplication(new Application(
              applyDate,
              coverLetter,
              resume,
              status,
              candidate));

          sc.nextLine();
          tmp = sc.nextLine();
        }
        recruitments[idx] = thisRecruitment;

        sc.nextLine();
        idx++;
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return this.recruitments;
  }

  public DataHandler readData() {
    readDepartmentsData();
    readPositionsData();
    readEmployeesData();
    readAccountsData();
    readRecruitmentsData();
    return this;
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
      for (int j = 0; j < evaluations.length; j++) {
        Evaluation currentEvaluation = evaluations[j];
        if (currentEvaluation == null) {
          break;
        }

        data += "{\n";

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
      data += "}\n";

      data += "{\n";
      Contract[] contracts = currentEmployee.getContracts();
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
      data += "}\n";

      data += "{\n";
      Payroll[] payrolls = currentEmployee.getPayrolls();
      for (int j = 0; j < payrolls.length; j++) {
        Payroll currentPayroll = payrolls[j];
        if (currentPayroll == null) {
          break;
        }

        data += "{\n";

        data += currentPayroll.getMonth() + "\n";
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
      data += "}\n";

      data += "{\n";
      Attendance[] attendances = currentEmployee.getAttendances();
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
      data += "}\n";

      data += "{\n";
      Absence[] absences = currentEmployee.getAbsences();
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
      data += "}\n";

      data += "{\n";
      Resignation[] resignations = currentEmployee.getResignations();
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
      data += "}\n";

      data += "{\n";
      Insurance[] insurances = currentEmployee.getInsurances();
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
      data += "}\n";

      data += currentEmployee.getTaxId() + "\n";

      int positionIdx = -1;
      for (int j = 0; j < positions.length; j++) {
        if (positions[j] != null && currentEmployee.getPosition().getName().equals(positions[j].getName())) {
          positionIdx = j;
          break;
        }
      }

      data += positionIdx + "\n";

      int departmentIdx = -1;
      for (int j = 0; j < departments.length; j++) {
        if (departments[j] != null && currentEmployee.getDepartment().getName().equals(departments[j].getName())) {
          departmentIdx = j;
          break;
        }
      }

      data += departmentIdx + "\n";

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

  private String writeRecruitmentData(DataHandler data) {
    if (data == null ||
        data.getRecruitments() == null ||
        data.getDepartments() == null ||
        data.getPositions() == null) {
      return null;
    }

    String content = "";

    if (data.getRecruitments() == null) {
      return null;
    }

    for (int i = 0; i < data.getRecruitments().length; i++) {
      Recruitment currRecruitment = data.getRecruitments()[i];
      if (currRecruitment == null) {
        break;
      }

      content += currRecruitment.getNumberOfPositions() + "\n";
      content += currRecruitment.getDeadline() + "\n";
      content += currRecruitment.getStatus() + "\n";

      if (data.getDepartments() == null) {
        return null;
      }

      for (int j = 0; j < data.getDepartments().length; j++) {
        if (data.getDepartments()[j] != null &&
            data.getDepartments()[j].getName() != null &&
            currRecruitment.getDepartment() != null &&
            currRecruitment.getDepartment().getName() != null &&
            currRecruitment.getDepartment().getName().equals(
                data.getDepartments()[j].getName())) {
          content += j + "\n";
          break;
        }
      }

      if (data.getPositions() == null) {
        return null;
      }

      for (int j = 0; j < data.getPositions().length; j++) {
        if (data.getPositions()[j] != null &&
            data.getPositions()[j].getName() != null &&
            currRecruitment.getPosition() != null &&
            currRecruitment.getPosition().getName() != null &&
            currRecruitment.getPosition().getName().equals(
                data.getPositions()[j].getName())) {
          content += j + "\n";
          break;
        }
      }

      content += "{\n";
      int count = currRecruitment.getApplicationCount();
      if (count > 0) {
        for (int j = 0; j < count; j++) {
          if (currRecruitment.getApplications() == null) {
            break;
          }

          Application currApplication = currRecruitment.getApplications()[j];
          if (currApplication == null) {
            break;
          }

          Candidate currCandidate = currApplication.getCandidate();
          if (currCandidate == null || currCandidate.getAddress() == null) {
            break;
          }

          content += "{\n";
          content += currApplication.getApplyDate() + "\n";
          content += currApplication.getCoverLetter() + "\n";
          content += currApplication.getResume() + "\n";
          content += currApplication.getStatus() + "\n";

          content += currCandidate.getFirstName() + "\n";
          content += currCandidate.getLastName() + "\n";
          content += currCandidate.getEmail() + "\n";
          content += currCandidate.getPhone() + "\n";
          content += currCandidate.getDateOfBirth() + "\n";
          content += currCandidate.getGender() + "\n";

          content += currCandidate.getAddress().getNumber() + "\n";
          content += currCandidate.getAddress().getStreet() + "\n";
          content += currCandidate.getAddress().getWard() + "\n";
          content += currCandidate.getAddress().getDistrict() + "\n";
          content += currCandidate.getAddress().getCity() + "\n";
          content += "}\n";
        }
      }
      content += "}\n";

      content += "\n";
    }

    return content;
  }

  public void writeData() {
    try {
      FileWriter accountFileWriter = new FileWriter(accountPath);
      FileWriter employeeFileWriter = new FileWriter(employeePath);
      FileWriter departmentFileWriter = new FileWriter(departmentPath);
      FileWriter positionFileWriter = new FileWriter(positionPath);
      FileWriter recruitmentFileWriter = new FileWriter(recruitmentPath);

      accountFileWriter.write(writeAccountData(accounts));
      employeeFileWriter.write(writeEmployeeData(employees));
      departmentFileWriter.write(writeDepartmentData(departments));
      positionFileWriter.write(writePositionData(positions));
      recruitmentFileWriter.write(writeRecruitmentData(this));

      accountFileWriter.close();
      employeeFileWriter.close();
      departmentFileWriter.close();
      positionFileWriter.close();
      recruitmentFileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String writeSampleAccountData() {
    String data = "";

    String[][] samples = {
        { "admin", "admin01", "admin123", "ACTIVE" },
        { "employee", "EMP001", "john123", "ACTIVE" },
        { "employee", "EMP002", "mary123", "SUSPENDED" },
        { "employee", "EMP003", "david123", "INACTIVE" },
    };

    for (String[] sample : samples) {
      data += String.join("\n", sample) + "\n\n";
    }

    return data;
  }

  private String writeSampleEmployeeData() {
    String data = "";

    String[][] samples = {
        // John Smith
        {
            "John", "Smith",
            "john.smith@example.com",
            "0912345678",
            "1998-06-15", "Male",
            "{",
            "25", "Nguyễn Huệ", "Bến Nghé",
            "1", "Thành phố Hồ Chí Minh",
            "}",
            "EMP001",
            "2025-01-01",
            "Phát triển Backend",
            "WORKING",

            // Evaluations
            "{",
            "{",
            "2025-01",
            "Có thành tích xuất sắc trong phát triển Backend",
            "9.0", "8.5", "9.0", "9.5", "9.0", "8.5",
            "}",
            "{",
            "2025-02",
            "Cải thiện hiệu suất API và tối ưu hóa cơ sở dữ liệu",
            "9.5", "9.0", "9.0", "9.0", "9.5", "9.0",
            "}",
            "{",
            "2025-03",
            "Hoàn thành các nhiệm vụ phát triển Backend trước thời hạn",
            "9.0", "9.5", "8.5", "9.0", "9.0", "9.5",
            "}",
            "}",

            // Contracts
            "{",
            "{",
            "2025-01-01",
            "2026-01-01",
            "FULL_TIME",
            "ACTIVE",
            "}",
            "{",
            "2026-01-01",
            "2027-01-01",
            "FULL_TIME",
            "ACTIVE",
            "}",
            "}",

            // Payrolls
            "{",
            "{",
            "2025-01",
            "15000000", "2000000", "1000000",
            "500000", "1000000", "500000",
            "176", "10",
            "2025-01-31",
            "}",
            "{",
            "2025-02",
            "15000000", "2000000", "1000000",
            "500000", "1000000", "500000",
            "168", "5",
            "2025-02-28",
            "}",
            "{",
            "2025-03",
            "15000000", "2500000", "1000000",
            "500000", "1200000", "500000",
            "176", "8",
            "2025-03-31",
            "}",
            "}",

            // Attendances
            "{",
            "{",
            "2025-01-02",
            "08:00", "17:00",
            "}",
            "{",
            "2025-01-03",
            "08:05", "17:10",
            "}",
            "{",
            "2025-01-06",
            "07:55", "17:00",
            "}",
            "{",
            "2025-01-07",
            "08:00", "17:15",
            "}",
            "}",

            // Absences
            "{",
            "{",
            "Nghỉ việc riêng",
            "2025-01-10",
            "2025-01-10",
            "REJECTED",
            "}",
            "{",
            "Việc gia đình",
            "2025-02-15",
            "2025-02-16",
            "APPROVED",
            "}",
            "}",

            // Resignations
            "{",
            "{",
            "Lý do cá nhân",
            "2025-01-15",
            "REJECTED",
            "}",
            "}",

            // Insurances
            "{",
            "{",
            "INS001",
            "HEALTH",
            "2025-01-01",
            "2025-12-31",
            "4.5",
            "}",
            "{",
            "INS004",
            "SOCIAL",
            "2025-01-01",
            "2025-12-31",
            "8.0",
            "}",
            "}",

            "TAX001",
            "2",
            "0"
        },

        // Mary Jones
        {
            "Mary", "Jones",
            "mary.jones@example.com",
            "0987654321",
            "1999-03-20", "Female",
            "{",
            "12", "Lê Lợi", "Bến Thành",
            "1", "Thành phố Hồ Chí Minh",
            "}",
            "EMP002",
            "2025-02-01",
            "Nhân sự",
            "WORKING",

            // Evaluations
            "{",
            "{",
            "2025-02",
            "Giao tiếp và làm việc nhóm xuất sắc",
            "9.5", "9.0", "9.0", "9.0", "8.5", "9.5",
            "}",
            "{",
            "2025-03",
            "Tổ chức thành công quy trình tiếp nhận nhân viên mới",
            "9.0", "9.5", "9.0", "9.5", "9.0", "9.0",
            "}",
            "{",
            "2025-04",
            "Cải thiện quy trình tuyển dụng và quản lý hồ sơ nhân sự",
            "9.0", "9.0", "9.5", "9.0", "9.5", "9.0",
            "}",
            "}",

            // Contracts
            "{",
            "{",
            "2025-02-01",
            "2026-02-01",
            "FULL_TIME",
            "ACTIVE",
            "}",
            "{",
            "2026-02-01",
            "2027-02-01",
            "FULL_TIME",
            "ACTIVE",
            "}",
            "}",

            // Payrolls
            "{",
            "{",
            "2025-02",
            "12000000", "1500000", "800000",
            "300000", "800000", "400000",
            "168", "5",
            "2025-02-28",
            "}",
            "{",
            "2025-03",
            "12000000", "1500000", "800000",
            "300000", "800000", "400000",
            "176", "8",
            "2025-03-31",
            "}",
            "{",
            "2025-04",
            "12000000", "1800000", "800000",
            "300000", "900000", "400000",
            "168", "4",
            "2025-04-30",
            "}",
            "}",

            // Attendances
            "{",
            "{",
            "2025-02-03",
            "08:15", "17:15",
            "}",
            "{",
            "2025-02-04",
            "08:00", "17:00",
            "}",
            "{",
            "2025-02-05",
            "08:10", "17:05",
            "}",
            "{",
            "2025-02-06",
            "07:55", "17:00",
            "}",
            "}",

            // Absences
            "{",
            "{",
            "Khám bệnh",
            "2025-02-10",
            "2025-02-10",
            "REJECTED",
            "}",
            "{",
            "Nghỉ việc riêng",
            "2025-03-12",
            "2025-03-12",
            "APPROVED",
            "}",
            "}",

            // Resignations
            "{",
            "{",
            "Thay đổi định hướng nghề nghiệp",
            "2025-02-15",
            "REJECTED",
            "}",
            "}",

            // Insurances
            "{",
            "{",
            "INS002",
            "HEALTH",
            "2025-02-01",
            "2026-01-31",
            "4.5",
            "}",
            "{",
            "INS005",
            "SOCIAL",
            "2025-02-01",
            "2026-01-31",
            "8.0",
            "}",
            "}",

            "TAX002",
            "4",
            "1"
        },

        // David Wilson
        {
            "David", "Wilson",
            "david.wilson@example.com",
            "0901234567",
            "1997-08-10", "Male",
            "{",
            "30", "Trần Hưng Đạo", "Cầu Kho",
            "1", "Thành phố Hồ Chí Minh",
            "}",
            "EMP003",
            "2025-03-01",
            "Phát triển phần mềm",
            "WORKING",

            // Evaluations
            "{",
            "{",
            "2025-03",
            "Có tiến triển tốt trong công việc phát triển phần mềm",
            "8.0", "8.5", "9.0", "8.0", "8.5", "8.0",
            "}",
            "{",
            "2025-04",
            "Khắc phục thành công nhiều lỗi phần mềm",
            "8.5", "9.0", "8.5", "9.0", "8.5", "9.0",
            "}",
            "{",
            "2025-05",
            "Thể hiện kỹ năng giải quyết vấn đề tốt",
            "9.0", "8.5", "9.0", "8.5", "9.0", "8.5",
            "}",
            "}",

            // Contracts
            "{",
            "{",
            "2025-03-01",
            "2026-03-01",
            "FULL_TIME",
            "ACTIVE",
            "}",
            "{",
            "2026-03-01",
            "2027-03-01",
            "FULL_TIME",
            "ACTIVE",
            "}",
            "}",

            // Payrolls
            "{",
            "{",
            "2025-03",
            "10000000", "1000000", "500000",
            "200000", "600000", "300000",
            "176", "8",
            "2025-03-31",
            "}",
            "{",
            "2025-04",
            "10000000", "1000000", "500000",
            "200000", "600000", "300000",
            "168", "4",
            "2025-04-30",
            "}",
            "{",
            "2025-05",
            "10000000", "1500000", "500000",
            "200000", "700000", "300000",
            "176", "6",
            "2025-05-31",
            "}",
            "}",

            // Attendances
            "{",
            "{",
            "2025-03-03",
            "08:00", "17:00",
            "}",
            "{",
            "2025-03-04",
            "08:05", "17:10",
            "}",
            "{",
            "2025-03-05",
            "08:00", "17:00",
            "}",
            "{",
            "2025-03-06",
            "07:50", "17:00",
            "}",
            "}",

            // Absences
            "{",
            "{",
            "Việc gia đình",
            "2025-03-12",
            "2025-03-12",
            "REJECTED",
            "}",
            "{",
            "Nghỉ việc riêng",
            "2025-04-08",
            "2025-04-08",
            "APPROVED",
            "}",
            "}",

            // Resignations
            "{",
            "{",
            "Lý do cá nhân",
            "2025-03-20",
            "REJECTED",
            "}",
            "}",

            // Insurances
            "{",
            "{",
            "INS003",
            "HEALTH",
            "2025-03-01",
            "2026-02-28",
            "4.5",
            "}",
            "{",
            "INS006",
            "SOCIAL",
            "2025-03-01",
            "2026-02-28",
            "8.0",
            "}",
            "}",

            "TAX003",
            "3",
            "0"
        }
    };

    for (String[] sample : samples) {
      data += String.join("\n", sample) + "\n\n";
    }

    return data;
  }

  private String writeSampleDepartmentData() {
    String data = "";

    String[][] samples = {
        { "Công nghệ thông tin", "02812345678", "C.A206" },
        { "Nhân sự", "02812345679", "1.B302" },
        { "Quản lý tài chính", "02812345680", "2.D103" },
        { "Tiếp thị và Truyền thông", "02812345681", "C.E403" },
        { "Kinh doanh và Phát triển thị trường", "02812345682", "C.A209" }
    };

    for (String[] sample : samples) {
      data += String.join("\n", sample) + "\n\n";
    }

    return data;
  }

  private String writeSamplePositionData() {
    String data = "";

    String[][] samples = {
        { "Quản lý", "3.0", "Quản lý phòng ban" },
        { "Trưởng nhóm", "2.5", "Giám sát và điều phối nhóm" },
        { "Lập trình viên cấp cao", "2.0", "Phát triển phần mềm cấp cao" },
        { "Lập trình viên cấp thấp", "1.5", "Phát triển phần mềm cấp thấp" },
        { "Chuyên viên nhân sự", "1.8", "Quản lý nguồn nhân lực" }
    };

    for (String[] sample : samples) {
      data += String.join("\n", sample) + "\n\n";
    }

    return data;
  }

  private String writeSampleRecruitmentData() {
    String content = "";

    // Đợt tuyển dụng 1
    content += "3\n";
    content += "2026-12-30\n";
    content += "OPEN\n";
    content += "0\n"; // Department index
    content += "0\n"; // Position index

    content += "{\n";

    // Ứng viên 1
    content += "{\n";
    content += "2026-12-01\n";
    content += "Tôi mong muốn được làm việc tại công ty và đóng góp kiến thức, "
        + "kinh nghiệm của mình cho sự phát triển của công ty.\n";
    content += "CV_NguyenVanAn.pdf\n";
    content += "SUBMITTED\n";

    content += "Nguyễn\n";
    content += "Văn An\n";
    content += "an.nguyen@gmail.com\n";
    content += "0901234567\n";
    content += "2000-05-15\n";
    content += "Nam\n";

    content += "12\n";
    content += "Nguyễn Trãi\n";
    content += "Phường Bến Thành\n";
    content += "Quận 1\n";
    content += "Thành phố Hồ Chí Minh\n";
    content += "}\n";

    // Ứng viên 2
    content += "{\n";
    content += "2026-12-05\n";
    content += "Với kiến thức chuyên môn và kinh nghiệm làm việc, "
        + "tôi tin rằng mình phù hợp với vị trí ứng tuyển.\n";
    content += "CV_TranThiBinh.pdf\n";
    content += "UNDER_REVIEW\n";

    content += "Trần\n";
    content += "Thị Bình\n";
    content += "binh.tran@gmail.com\n";
    content += "0912345678\n";
    content += "1999-08-20\n";
    content += "Nữ\n";

    content += "25\n";
    content += "Lê Lợi\n";
    content += "Phường Bến Nghé\n";
    content += "Quận 1\n";
    content += "Thành phố Hồ Chí Minh\n";
    content += "}\n";

    // Ứng viên 3
    content += "{\n";
    content += "2026-12-10\n";
    content += "Tôi rất mong muốn có cơ hội được làm việc, "
        + "học hỏi và phát triển cùng quý công ty.\n";
    content += "CV_LeVanCuong.pdf\n";
    content += "SHORTLISTED\n";

    content += "Lê\n";
    content += "Văn Cường\n";
    content += "cuong.le@gmail.com\n";
    content += "0987654321\n";
    content += "2001-03-10\n";
    content += "Nam\n";

    content += "8\n";
    content += "Cách Mạng Tháng 8\n";
    content += "Phường 5\n";
    content += "Quận 3\n";
    content += "Thành phố Hồ Chí Minh\n";
    content += "}\n";

    content += "}\n";
    content += "\n";

    return content;
  }

  public void writeSampleData() {
    try {
      FileWriter accountFileWriter = new FileWriter(accountPath);
      FileWriter employeeFileWriter = new FileWriter(employeePath);
      FileWriter departmentFileWriter = new FileWriter(departmentPath);
      FileWriter positionFileWriter = new FileWriter(positionPath);
      FileWriter recruitmentFileWriter = new FileWriter(recruitmentPath);

      accountFileWriter.write(writeSampleAccountData());
      employeeFileWriter.write(writeSampleEmployeeData());
      departmentFileWriter.write(writeSampleDepartmentData());
      positionFileWriter.write(writeSamplePositionData());
      recruitmentFileWriter.write(writeSampleRecruitmentData());

      accountFileWriter.close();
      employeeFileWriter.close();
      departmentFileWriter.close();
      positionFileWriter.close();
      recruitmentFileWriter.close();
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

  public Recruitment[] getRecruitments() {
    return recruitments;
  }

  public void setRecruitments(Recruitment[] recruitments) {
    this.recruitments = recruitments;
  }
}
