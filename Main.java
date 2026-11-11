import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
  private static int maxAccounts;
  private static int maxEmployees;
  private static int maxDepartments;
  private static int maxPositions;
  private static int maxEvaluations;
  private static int maxContracts;
  private static int maxPayrolls;
  private static int maxAttendances;
  private static int maxInsurances;
  private static int maxResignations;

  private static Account[] accounts;
  private static Employee[] employees;
  private static Department[] departments;
  private static Position[] positions;

  private static int accountsOccupied = -1;
  private static int employeesOccupied = -1;
  private static int departmentsOccupied = -1;
  private static int positionsOccupied = -1;

  private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

  private enum CreatingType {
    ACCOUNT,
    EMPLOYEE,
    DEPARTMENT,
    POSITION
  }

  private static Account createAccount(Scanner sc) {
    System.out.print("\033[H\033[2J");
    System.out.println("Create Account");
    System.out.print("Username: ");
    String username = sc.nextLine();

    System.out.print("Password: ");
    String password = sc.nextLine();

    int statusNum = 0;
    Account.AccountStatus status = Account.AccountStatus.ACTIVE;
    do {
      System.out.println("[1] Active");
      System.out.println("[2] Inactive");
      System.out.println("[3] Suspended");
      System.out.println("[4] Pending");
      System.out.println("[5] Locked");
      System.out.println("[6] Terminated");
      System.out.print("Status:");

      statusNum = Integer.parseInt(sc.nextLine());
    } while (statusNum < 1 || 6 < statusNum);

    switch (statusNum) {
      case 1:
        status = Account.AccountStatus.ACTIVE;
        break;
      case 2:
        status = Account.AccountStatus.INACTIVE;
        break;
      case 3:
        status = Account.AccountStatus.SUSPENDED;
        break;
      case 4:
        status = Account.AccountStatus.PENDING;
        break;
      case 5:
        status = Account.AccountStatus.LOCKED;
        break;
      case 6:
        status = Account.AccountStatus.TERMINATED;
        break;

      default:
        break;
    }

    int type;
    do {
      System.out.println("[1] Admin");
      System.out.println("[2] Employee");
      System.out.print("Choose: ");
      type = Integer.parseInt(sc.nextLine());
    } while (type != 1 && type != 2);

    if (type == 1) {
      return new AdminAccount(username, password, status);
    } else {
      return new EmployeeAccount(username, password, status);
    }
  }

  private static Address createAddress(Scanner sc) {
    System.out.print("Number: ");
    int number = Integer.parseInt(sc.nextLine());

    System.out.print("Street: ");
    String street = sc.nextLine();

    System.out.print("Ward: ");
    String ward = sc.nextLine();

    System.out.print("District: ");
    String district = sc.nextLine();

    System.out.print("City: ");
    String city = sc.nextLine();

    return new Address(number, street, ward, district, city);
  }

  private static Evaluation createEvaluation(Scanner sc) {
    System.out.print("Month: ");
    String month = sc.nextLine();

    System.out.print("Comment");
    String comment = sc.nextLine();

    System.out.print("Work score: ");
    double workScore = Double.parseDouble(sc.nextLine());

    System.out.print("Attitude score: ");
    double attitudeScore = Double.parseDouble(sc.nextLine());

    System.out.print("Discipline score: ");
    double disciplineScore = Double.parseDouble(sc.nextLine());

    System.out.print("Work quality: ");
    double workQuality = Double.parseDouble(sc.nextLine());

    System.out.print("Productivity: ");
    double productivity = Double.parseDouble(sc.nextLine());

    System.out.print("Team work: ");
    double teamWork = Double.parseDouble(sc.nextLine());

    return new Evaluation(month, comment, workScore, attitudeScore, disciplineScore, workQuality, productivity,
        teamWork);
  }

  private static Contract createContract(Scanner sc) {
    LocalDate startDate;
    LocalDate endDate;
    do {
      System.out.print("Start date: ");
      startDate = LocalDate.parse(sc.nextLine(), dateFormatter);

      System.out.print("End date: ");
      endDate = LocalDate.parse(sc.nextLine(), dateFormatter);

      if (startDate.isAfter(endDate)) {
        System.out.println("Start date should be before end date");
      }
    } while (startDate.isBefore(endDate));

    int typeNum = 0;
    Contract.ContractType type = Contract.ContractType.TEMPORARY;
    do {
      System.out.println("[1] Probation");
      System.out.println("[2] Full time");
      System.out.println("[3] Part time");
      System.out.println("[4] Temporary");
      System.out.println("[5] Fixed term");
      System.out.println("[6] Permanent");
      System.out.print("Type: ");

      typeNum = Integer.parseInt(sc.nextLine());
    } while (typeNum < 1 || 6 < typeNum);

    switch (typeNum) {
      case 1:
        type = Contract.ContractType.PROBATION;
        break;
      case 2:
        type = Contract.ContractType.FULL_TIME;
        break;
      case 3:
        type = Contract.ContractType.PART_TIME;
        break;
      case 4:
        type = Contract.ContractType.TEMPORARY;
        break;
      case 5:
        type = Contract.ContractType.FIXED_TERM;
        break;
      case 6:
        type = Contract.ContractType.PERMANENT;
        break;

      default:
        break;
    }

    int statusNum = 0;
    Contract.ContractStatus status = Contract.ContractStatus.ACTIVE;
    do {
      System.out.println("[1] Draft");
      System.out.println("[2] Active");
      System.out.println("[3] Expired");
      System.out.print("Status: ");

      statusNum = Integer.parseInt(sc.nextLine());
    } while (statusNum < 1 || 3 < statusNum);

    switch (statusNum) {
      case 1:
        status = Contract.ContractStatus.DRAFT;
        break;
      case 2:
        status = Contract.ContractStatus.ACTIVE;
        break;
      case 3:
        status = Contract.ContractStatus.EXPIRED;
        break;

      default:
        break;
    }

    return new Contract(startDate, endDate, type, status);
  }

  private static Payroll createPayroll(Scanner sc) {
    System.out.print("Month: ");
    int month = Integer.parseInt(sc.nextLine());

    System.out.print("Year: ");
    int year = Integer.parseInt(sc.nextLine());

    System.out.print("Base Salary");
    double baseSalary = Double.parseDouble(sc.nextLine());

    System.out.print("Bonus: ");
    double bonus = Double.parseDouble(sc.nextLine());

    System.out.print("Allowance: ");
    double allowance = Double.parseDouble(sc.nextLine());

    System.out.print("Deduction: ");
    double deduction = Double.parseDouble(sc.nextLine());

    System.out.print("Tax amount: ");
    double taxAmount = Double.parseDouble(sc.nextLine());

    System.out.print("Insurance amount: ");
    double insuranceAmount = Double.parseDouble(sc.nextLine());

    System.out.print("Worked hours: ");
    int workedHours = Integer.parseInt(sc.nextLine());

    System.out.print("Overtime hours: ");
    int overtimeHours = Integer.parseInt(sc.nextLine());

    System.out.print("Last salary increase (dd/MM/yyyy): ");
    LocalDate lastSalaryIncreaseDate = LocalDate.parse(sc.nextLine(), dateFormatter);

    return new Payroll(month, year, baseSalary, bonus, allowance, deduction, taxAmount, insuranceAmount, workedHours,
        overtimeHours, lastSalaryIncreaseDate);
  }

  private static Attendance createAttendance(Scanner sc) {
    System.out.print("Date (dd/MM/yyyy): ");
    LocalDate date = LocalDate.parse(sc.nextLine(), dateFormatter);

    LocalTime clockIn;
    LocalTime clockOut;
    do {
      System.out.print("Clock in (HH:mm): ");
      clockIn = LocalTime.parse(sc.nextLine(), timeFormatter);

      System.out.print("Clock out (HH:mm): ");
      clockOut = LocalTime.parse(sc.nextLine(), timeFormatter);

      if (clockIn.isAfter(clockOut)) {
        System.out.println("Clock in time should be before clock out time");
      }
    } while (clockIn.isBefore(clockOut));

    return new Attendance(date, clockIn, clockOut);
  }

  private static Insurance createInsurance(Scanner sc) {
    System.out.print("Insurance number: ");
    String insuranceNumber = sc.nextLine();

    int typeNum;
    Insurance.InsuranceType type = Insurance.InsuranceType.HEALTH;
    do {
      System.out.println("[1] Life");
      System.out.println("[2] Health");
      System.out.println("[3] Social");
      System.out.println("[4] Unemployment");
      System.out.print("Type: ");
      typeNum = Integer.parseInt(sc.nextLine());
    } while (typeNum < 1 || 4 < typeNum);

    switch (typeNum) {
      case 1:
        type = Insurance.InsuranceType.LIFE;
        break;
      case 2:
        type = Insurance.InsuranceType.HEALTH;
        break;
      case 3:
        type = Insurance.InsuranceType.SOCIAL;
        break;
      case 4:
        type = Insurance.InsuranceType.UNEMPLOYMENT;
        break;

      default:
        break;
    }

    LocalDate startDate;
    LocalDate endDate;
    do {
      System.out.print("Start date (dd/MM/yyyy): ");
      startDate = LocalDate.parse(sc.nextLine(), dateFormatter);

      System.out.print("End date (dd/MM/yyyy): ");
      endDate = LocalDate.parse(sc.nextLine(), dateFormatter);

      if (startDate.isAfter(endDate)) {
        System.out.println("Start date should be before end date");
      }
    } while (startDate.isBefore(endDate));

    System.out.print("Rate: ");
    double rate = Double.parseDouble(sc.nextLine());

    return new Insurance(insuranceNumber, type, startDate, endDate, rate);
  }

  private static Employee.EmployeeStatus chooseEmployeeStatus(Scanner sc) {
    int statusChoice;
    do {
      System.out.println("[1] Working");
      System.out.println("[2] On leave");
      System.out.println("[3] Resigned");
      System.out.print("Status:");
      statusChoice = Integer.parseInt(sc.nextLine());
    } while (statusChoice < 1 || 3 < statusChoice);

    switch (statusChoice) {
      case 1:
        return Employee.EmployeeStatus.WORKING;
      case 2:
        return Employee.EmployeeStatus.ON_LEAVE;
      case 3:
        return Employee.EmployeeStatus.RESIGNED;

      default:
        break;
    }

    return Employee.EmployeeStatus.RESIGNED;
  }

  private static Evaluation[] createEvaluations(Scanner sc) {
    int evaluationCount;
    do {
      System.out.print("How many evaluations? ");
      evaluationCount = Integer.parseInt(sc.nextLine());

      if (evaluationCount < 0 || maxEvaluations < evaluationCount) {
        System.out.print("Invalid evaluation count");
        System.out.printf("Min: 0, Max: %d", maxEvaluations);
      }
    } while (evaluationCount < 0 || maxEvaluations < evaluationCount);

    Evaluation[] evaluations = new Evaluation[maxEvaluations];
    for (int i = 0; i < evaluationCount; i++) {
      evaluations[i] = createEvaluation(sc);
    }

    return evaluations;
  }

  private static Contract[] createContracts(Scanner sc) {
    int contractCount;
    do {
      System.out.print("How many contracts? ");
      contractCount = Integer.parseInt(sc.nextLine());

      if (contractCount < 0 || maxContracts < contractCount) {
        System.out.println("Invalid contract count");
        System.out.printf("Min: 0, Max: %d\n", maxContracts);
      }
    } while (contractCount < 0 || maxContracts < contractCount);

    Contract[] contracts = new Contract[maxContracts];
    for (int i = 0; i < contractCount; i++) {
      contracts[i] = createContract(sc);
    }

    return contracts;
  }

  private static Payroll[] createPayrolls(Scanner sc) {
    int payrollCount;

    do {
      System.out.print("How many payrolls? ");
      payrollCount = Integer.parseInt(sc.nextLine());

      if (payrollCount < 0 || maxPayrolls < payrollCount) {
        System.out.println("Invalid payroll count");
        System.out.printf("Min: 0, Max: %d\n", maxPayrolls);
      }
    } while (payrollCount < 0 || maxPayrolls < payrollCount);

    Payroll[] payrolls = new Payroll[maxPayrolls];
    for (int i = 0; i < payrollCount; i++) {
      payrolls[i] = createPayroll(sc);
    }

    return payrolls;
  }

  private static Attendance[] createAttendances(Scanner sc) {
    int attendanceCount;

    do {
      System.out.print("How many attendances? ");
      attendanceCount = Integer.parseInt(sc.nextLine());

      if (attendanceCount < 0 || maxAttendances < attendanceCount) {
        System.out.println("Invalid attendance count");
        System.out.printf("Min: 0, Max: %d\n", maxAttendances);
      }
    } while (attendanceCount < 0 || maxAttendances < attendanceCount);

    Attendance[] attendances = new Attendance[maxAttendances];
    for (int i = 0; i < attendanceCount; i++) {
      attendances[i] = createAttendance(sc);
    }

    return attendances;
  }

  private static Resignation[] createResignation(Scanner sc) {
    int resignationChoice;
    do {
      System.out.println("[0] No");
      System.out.println("[1] Yes");
      System.out.print("Have resignation: ");
      resignationChoice = Integer.parseInt(sc.nextLine());
    } while (resignationChoice != 0 && resignationChoice != 1);

    Resignation[] resignations = new Resignation[maxResignations];

    String reason;
    LocalDate date;
    Resignation.ResignationStatus resigntationStatus = Resignation.ResignationStatus.REJECTED;

    if (resignationChoice == 1) {
      int resignationCount;

      do {
        System.out.print("How many resignations? ");
        resignationCount = Integer.parseInt(sc.nextLine());

        if (resignationCount < 0 || maxResignations < resignationCount) {
          System.out.println("Invalid resignation count");
          System.out.printf("Min: 0, Max: %d\n", maxResignations);
        }
      } while (resignationCount < 0 || maxResignations < resignationCount);

      for (int i = 0; i < resignationCount; i++) {
        System.out.print("Reason: ");
        reason = sc.nextLine();

        System.out.print("Date (dd/MM/yyyy): ");
        date = LocalDate.parse(sc.nextLine(), dateFormatter);

        int statusNum;
        do {
          System.out.println("[1] Pending");
          System.out.println("[2] Rejected");
          System.out.println("[3] Approved");
          System.out.print("Status: ");
          statusNum = Integer.parseInt(sc.nextLine());
        } while (statusNum < 1 || 3 < statusNum);

        switch (statusNum) {
          case 1:
            resigntationStatus = Resignation.ResignationStatus.PENDING;
            break;
          case 2:
            resigntationStatus = Resignation.ResignationStatus.REJECTED;
            break;
          case 3:
            resigntationStatus = Resignation.ResignationStatus.APPROVED;
            break;

          default:
            break;
        }

        resignations[i] = new Resignation(reason, date, resigntationStatus);
      }
    }

    return resignations;
  }

  private static Insurance[] createInsurances(Scanner sc) {
    int insuranceCount;
    do {
      System.out.print("How many insurances? ");
      insuranceCount = Integer.parseInt(sc.nextLine());

      if (insuranceCount < 0 || maxInsurances < insuranceCount) {
        System.out.println("Invalid insurance count");
        System.out.printf("Min: 0, Max: %d\n", maxInsurances);
      }
    } while (insuranceCount < 0 || maxInsurances < insuranceCount);

    Insurance[] insurances = new Insurance[maxInsurances];
    for (int i = 0; i < insuranceCount; i++) {
      insurances[i] = createInsurance(sc);
    }

    return insurances;
  }

  private static Position choosePosition(Scanner sc) {
    if (positionsOccupied < 0) {
      System.out.println("No positions available");
      return null;
    }

    int index;
    do {
      for (int i = 0; i < positions.length; i++) {
        if (positions[i] == null) {
          break;
        }

        System.out.printf("[%d] %s\n", i + 1, positions[i].getName());
      }

      System.out.print("Choose positions: ");
      index = Integer.parseInt(sc.nextLine()) - 1;

      if (index < 0 || index > positionsOccupied) {
        System.out.println("Invalid position");
      }
    } while (index < 0 || index > positionsOccupied);

    return positions[index];
  }

  private static Department chooseDepartment(Scanner sc) {
    if (departmentsOccupied < 0) {
      System.out.println("No departments available");
      return null;
    }

    int index;
    do {
      for (int i = 0; i < departments.length; i++) {
        if (departments[i] == null) {
          break;
        }

        System.out.printf("[%d] %s\n", i + 1, departments[i].getName());
      }

      System.out.print("Choose department: ");
      index = Integer.parseInt(sc.nextLine()) - 1;

      if (index < 0 || index > departmentsOccupied) {
        System.out.println("Invalid department");
      }
    } while (index < 0 || index > departmentsOccupied);

    return departments[index];
  }

  private static Employee createEmployee(Scanner sc) {
    System.out.print("\033[H\033[2J");
    System.out.println("Create Employee");
    System.out.print("First name: ");
    String firstName = sc.nextLine();

    System.out.print("Last name: ");
    String lastName = sc.nextLine();

    System.out.print("Email: ");
    String email = sc.nextLine();

    System.out.print("Phone number: ");
    String phone = sc.nextLine();

    System.out.print("Date of birth (dd/MM/yyyy): ");
    LocalDate dateOfBirth = LocalDate.parse(sc.nextLine(), dateFormatter);

    System.out.print("Gender: ");
    String gender = sc.nextLine();

    System.out.print("Address");
    Address address = createAddress(sc);

    System.out.print("Id: ");
    String id = sc.nextLine();

    System.out.print("Hire date (dd/MM/yyyy): ");
    LocalDate hireDate = LocalDate.parse(sc.nextLine(), dateFormatter);

    System.out.print("Specialization: ");
    String specialization = sc.nextLine();

    Employee.EmployeeStatus status = chooseEmployeeStatus(sc);

    Evaluation[] evaluations = createEvaluations(sc);
    Contract[] contracts = createContracts(sc);
    Payroll[] payrolls = createPayrolls(sc);
    Attendance[] attendances = createAttendances(sc);

    Resignation[] resignations = createResignation(sc);

    System.out.print("Tax id: ");
    String taxId = sc.nextLine();

    Insurance[] insurances = createInsurances(sc);

    Position position = choosePosition(sc);
    Department department = chooseDepartment(sc);

    return new Employee(firstName, lastName, email, phone, dateOfBirth, gender, address, id, hireDate, specialization,
        status, evaluations, contracts, payrolls, attendances, resignations, taxId, insurances, position, department);
  }

  private static Department createDepartment(Scanner sc) {
    System.out.print("\033[H\033[2J");
    System.out.println("Create Department");
    System.out.print("Name: ");
    String name = sc.nextLine();

    System.out.print("Phone: ");
    String phone = sc.nextLine();

    System.out.print("Position: ");
    String position = sc.nextLine();

    return new Department(name, phone, position);
  }

  private static Position createPosition(Scanner sc) {
    System.out.print("\033[H\033[2J");
    System.out.println("Create Position");
    System.out.print("Name: ");
    String name = sc.nextLine();

    System.out.print("Position coefficient: ");
    double positionCoefficient = Double.parseDouble(sc.nextLine());

    System.out.print("Responsibilities: ");
    String responsibilities = sc.nextLine();

    return new Position(responsibilities, name, positionCoefficient);
  }

  private static CreatingType choiceType(Scanner sc) {
    int choice;

    do {
      System.out.println("[0] Exit");
      System.out.println("[1] Account");
      System.out.println("[2] Employee");
      System.out.println("[3] Department");
      System.out.println("[4] Position");
      System.out.print("Create: ");

      choice = Integer.parseInt(sc.nextLine());
      switch (choice) {
        case 0:
          return null;
        case 1:
          return CreatingType.ACCOUNT;
        case 2:
          return CreatingType.EMPLOYEE;
        case 3:
          return CreatingType.DEPARTMENT;
        case 4:
          return CreatingType.POSITION;

        default:
          break;
      }
    } while (choice < 0 || 4 < choice);

    return null;
  }

  private static int choiceCount(Scanner sc) {
    return inputNonNegative(sc, "How many");
  }

  private static void createNElements(Scanner sc) {
    CreatingType type = choiceType(sc);
    if (type == null) {
      return;
    }

    int count = choiceCount(sc);

    for (int i = 0; i < count; i++) {
      switch (type) {
        case ACCOUNT:
          if (accountsOccupied + 1 >= accounts.length) {
            System.out.print("\033[H\033[2J");
            System.out.println("Account storage is full");
            break;
          }

          accounts[accountsOccupied + 1] = createAccount(sc);
          accountsOccupied++;
          break;
        case EMPLOYEE:
          if (employeesOccupied + 1 >= employees.length) {
            System.out.print("\033[H\033[2J");
            System.out.println("Employee storage is full");
            break;
          }

          employees[employeesOccupied + 1] = createEmployee(sc);
          employeesOccupied++;
          break;
        case DEPARTMENT:
          if (departmentsOccupied + 1 >= departments.length) {
            System.out.print("\033[H\033[2J");
            System.out.println("Department storage is full");
            break;
          }

          departments[departmentsOccupied + 1] = createDepartment(sc);
          departmentsOccupied++;
          break;
        case POSITION:
          if (positionsOccupied + 1 >= positions.length) {
            System.out.print("\033[H\033[2J");
            System.out.println("Position storage is full");
            break;
          }

          positions[positionsOccupied + 1] = createPosition(sc);
          positionsOccupied++;
          break;

        default:
          break;
      }
    }
  }

  private static int getIndex(String username) {
    for (int i = 0; i < accounts.length; i++) {
      if (accounts[i] != null && accounts[i].getUsername().equals(username)) {
        return i;
      }
    }

    return -1;
  }

  private static boolean logIn(int index, Scanner sc) {
    if (index == -1) {
      System.out.println("Username not exists");
      return false;
    }

    System.out.print("Password: ");
    String password = sc.nextLine();

    if (!password.equals(accounts[index].getPassword())) {
      System.out.println("Wrong password");
      return false;
    }

    if (accounts[index].getStatus() != Account.AccountStatus.ACTIVE) {
      System.out.println("Account is not active");
      return false;
    }

    System.out.print("\033[H\033[2J");
    System.out.println("Login successfully");
    return true;
  }

  private static boolean usernameExist(Account[] accounts, Account current, String username) {
    for (Account account : accounts) {
      if (account != null && account.getUsername().equals(username)
          && account.getUsername().equals(current.getUsername())) {
        System.out.println(username + " already exists");
        return true;
      }
    }

    return false;
  }

  private static void setAccountName(Scanner sc, Account[] accounts, Account current) {
    String username;
    do {
      System.out.print("Set username: ");
      username = sc.nextLine();
    } while (usernameExist(accounts, current, username));

    current.setUsername(username);
    System.out.print("\033[H\033[2J");
    System.out.println("Set successfully");
  }

  private static void setAccountStatus(Scanner sc, Account[] accounts, Account current) {
    int statusNum = 0;
    do {
      System.out.println("[1] Active");
      System.out.println("[2] Inactive");
      System.out.println("[3] Suspended");
      System.out.println("[4] Pending");
      System.out.println("[5] Locked");
      System.out.println("[6] Terminated");
      System.out.print("Status:");

      statusNum = Integer.parseInt(sc.nextLine());
    } while (statusNum < 1 || 6 < statusNum);

    switch (statusNum) {
      case 1:
        current.setStatus(Account.AccountStatus.ACTIVE);
        break;
      case 2:
        current.setStatus(Account.AccountStatus.INACTIVE);
        break;
      case 3:
        current.setStatus(Account.AccountStatus.SUSPENDED);
        break;
      case 4:
        current.setStatus(Account.AccountStatus.PENDING);
        break;
      case 5:
        current.setStatus(Account.AccountStatus.LOCKED);
        break;
      case 6:
        current.setStatus(Account.AccountStatus.TERMINATED);
        break;

      default:
        break;
    }

    System.out.print("\033[H\033[2J");
    System.out.println("Set successfully");
  }

  private static void runEmployeeUI(EmployeeAccount account, Scanner sc) {
    String[] options = { "Logout", "Change password", "Get info", "Set info", "Submit absence", "Submit resignation",
        "Check in", "Check out", "View attendance" };

    int choice;
    do {
      for (int i = 0; i < options.length; i++) {
        System.out.printf("[%d] %s\n", i, options[i]);
      }
      System.out.print("Choice: ");
      choice = Integer.parseInt(sc.nextLine());

      System.out.print("\033[H\033[2J");

      switch (choice) {
        case 0:
          return;
        case 1:
          account.resetPassword(sc);
          break;
        case 2:
          account.getInfo();
          break;
        case 3:
          int setChoice;
          do {
            System.out.println("[0] Exit");
            System.out.println("[1] Set username");
            System.out.println("[2] Set status");
            System.out.print("Choice: ");
            setChoice = Integer.parseInt(sc.nextLine());
          } while (setChoice < 0 || 2 < setChoice);

          switch (setChoice) {
            case 1:
              setAccountName(sc, accounts, account);
              break;
            case 2:
              setAccountStatus(sc, accounts, account);
              break;

            default:
              break;
          }
          break;
        case 4:
          account.submitAbsence();
          break;
        case 5:
          account.submitResignation();
          break;
        case 6:
          account.checkIn();
          break;
        case 7:
          account.checkOut();
          break;
        case 8:
          account.viewAttendance();
          break;

        default:
          break;
      }
    } while (true);
  }

  private static void runAdminUI(AdminAccount account, Scanner sc) {
    String[] options = { "Logout", "Change password", "Get info", "Set info", "Add employee", "Edit employee",
        "Remove employee", "Print employees", "Print departments", "Print payrolls", "Reply request", "Search employee",
        "Search department" };

    int choice;
    do {
      for (int i = 0; i < options.length; i++) {
        System.out.printf("[%d] %s\n", i, options[i]);
      }
      System.out.println("Choice: ");
      choice = Integer.parseInt(sc.nextLine());

      System.out.print("\033[H\033[2J");

      switch (choice) {
        case 0:
          return;
        case 1:
          account.resetPassword(sc);
          break;
        case 2:
          account.getInfo();
          break;
        case 3:
          int setChoice;
          do {
            System.out.println("[0] Exit");
            System.out.println("[1] Set username");
            System.out.println("[2] Set status");
            System.out.print("Choice: ");
            setChoice = Integer.parseInt(sc.nextLine());

            System.out.print("\033[H\033[2J");
          } while (setChoice < 0 || 2 < setChoice);

          switch (setChoice) {
            case 1:
              setAccountName(sc, accounts, account);
              break;
            case 2:
              setAccountStatus(sc, accounts, account);
              break;

            default:
              break;
          }
          break;
        case 4:
          account.addEmployee(employees, createEmployee(sc));
          break;
        case 5:
          String editUsername = sc.nextLine();
          account.editEmployee(accounts, editUsername);
          break;
        case 6:
          String removeEmployee = sc.nextLine();
          account.removeEmployee(accounts, removeEmployee);
          break;
        case 7:
          account.printEmployee(employees);
          break;
        case 8:
          account.printDepartments(departments);
          break;
        case 9:
          account.printPayrolls();
          break;
        case 10:
          System.out.print("Reply to username: ");
          String replyUsername = sc.nextLine();

          System.out.print("Reply: ");
          String reply = sc.nextLine();

          account.replyRequest(accounts, replyUsername, reply);
          break;
        case 11:
          account.searchEmployee(employees);
          break;
        case 12:
          account.searchDepartment(departments);
          break;

        default:
          break;
      }
    } while (true);
  }

  private static int inputNonNegative(Scanner sc, String name) {
    int num;

    do {
      System.out.print(name + ": ");
      num = Integer.parseInt(sc.nextLine());

      if (num < 0) {
        System.out.println("This cannot be negative");
      }
    } while (num < 0);

    return num;
  }

  private static void setup(Scanner sc) {
    System.out.println("Setup system");

    maxAccounts = inputNonNegative(sc, "Max accounts");
    maxEmployees = inputNonNegative(sc, "Max employees");
    maxDepartments = inputNonNegative(sc, "Max departments");
    maxPositions = inputNonNegative(sc, "Max positions");

    accounts = new Account[maxAccounts];
    employees = new Employee[maxEmployees];
    departments = new Department[maxDepartments];
    positions = new Position[maxPositions];

    maxEvaluations = inputNonNegative(sc, "Max evaluations");
    maxContracts = inputNonNegative(sc, "Max contracts");
    maxPayrolls = inputNonNegative(sc, "Max payrolls");
    maxAttendances = inputNonNegative(sc, "Max attendances");
    maxInsurances = inputNonNegative(sc, "Max insurances");
    maxResignations = inputNonNegative(sc, "Max resignations");

    System.out.print("\033[H\033[2J");

    try {
      String currentDirectory = System.getProperty("user.dir");
      String directoryPath = currentDirectory + File.separator + "data";

      File directory = new File(directoryPath);
      if (!directory.exists()) {
        directory.mkdir();
      }

      String accountPath = directoryPath + File.separator + "accounts.txt";
      String employeePath = directoryPath + File.separator + "employee.txt";
      String departmentPath = directoryPath + File.separator + "department.txt";
      String positionPath = directoryPath + File.separator + "position.txt";

      File accountFile = new File(accountPath);
      File employeeFile = new File(employeePath);
      File departmentFile = new File(departmentPath);
      File positionFile = new File(positionPath);

      if (!accountFile.exists()) {
        accountFile.createNewFile();
      }
      if (!employeeFile.exists()) {
        employeeFile.createNewFile();
      }
      if (!departmentFile.exists()) {
        departmentFile.createNewFile();
      }
      if (!positionFile.exists()) {
        positionFile.createNewFile();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void readData() {

  }

  private static String writeAccountData() {
    String data = "";

    for (int i = 0; i <= accountsOccupied; i++) {
      if (accounts[i] instanceof AdminAccount) {
        data += "admin\n";
      } else {
        data += "employee\n";
      }
      data += accounts[i].getUsername() + "\n";
      data += accounts[i].getPassword() + "\n";
      data += accounts[i].getStatus() + "\n";
      data += "\n";
    }

    return data;
  }

  private static String writeEmployeeData() {
    String data = "";

    for (int i = 0; i <= employeesOccupied; i++) {
      Employee currentEmployee = employees[i];
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

  private static String writeDepartmentData() {
    String data = "";

    for (int i = 0; i <= departmentsOccupied; i++) {
      data += departments[i].getName() + "\n";
      data += departments[i].getPhone() + "\n";
      data += departments[i].getPosition() + "\n";
      data += "\n";
    }

    return data;
  }

  private static String writePositionData() {
    String data = "";

    for (int i = 0; i <= positionsOccupied; i++) {
      data += positions[i].getName() + "\n";
      data += positions[i].getPositionCoefficient() + "\n";
      data += positions[i].getResponsibilities() + "\n";
      data += "\n";
    }

    return data;
  }

  private static void writeData() {
    try {
      String currentDirectory = System.getProperty("user.dir");
      String directoryPath = currentDirectory + File.separator + "data";

      String accountPath = directoryPath + File.separator + "accounts.txt";
      String employeePath = directoryPath + File.separator + "employee.txt";
      String departmentPath = directoryPath + File.separator + "department.txt";
      String positionPath = directoryPath + File.separator + "position.txt";

      FileWriter accountFileWriter = new FileWriter(accountPath);
      FileWriter employeeFileWriter = new FileWriter(employeePath);
      FileWriter departmentFileWriter = new FileWriter(departmentPath);
      FileWriter positionFileWriter = new FileWriter(positionPath);

      accountFileWriter.write(writeAccountData());
      employeeFileWriter.write(writeEmployeeData());
      departmentFileWriter.write(writeDepartmentData());
      positionFileWriter.write(writePositionData());

      accountFileWriter.close();
      employeeFileWriter.close();
      departmentFileWriter.close();
      positionFileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void createSampleData() {
    if (maxAccounts < 2 || maxEmployees < 1 ||
        maxDepartments < 2 || maxPositions < 2 ||
        maxEvaluations < 1 || maxContracts < 1 ||
        maxPayrolls < 1 || maxAttendances < 1 ||
        maxInsurances < 1) {
      throw new IllegalStateException(
          "Not enough capacity. Increase the maximum values in setup().");
    }

    accounts[0] = new AdminAccount(
        "admin",
        "admin123",
        Account.AccountStatus.ACTIVE);

    accounts[1] = new EmployeeAccount(
        "john",
        "john123",
        Account.AccountStatus.ACTIVE);

    accountsOccupied = 1;

    departments[0] = new Department(
        "IT",
        "0901234567",
        "Software Development");

    departments[1] = new Department(
        "Human Resources",
        "0907654321",
        "Recruitment");

    departmentsOccupied = 1;

    positions[0] = new Position(
        "Develop and maintain software",
        "Software Developer",
        2.5);

    positions[1] = new Position(
        "Manage recruitment and employee relations",
        "HR Manager",
        3.0);

    positionsOccupied = 1;

    Address address = new Address(
        25,
        "Nguyen Hue",
        "Ben Nghe",
        "District 1",
        "Ho Chi Minh City");

    Evaluation[] evaluations = new Evaluation[maxEvaluations];

    evaluations[0] = new Evaluation(
        "October 2026",
        "Excellent performance",
        9.0,
        9.5,
        9.0,
        9.0,
        8.5,
        9.0);

    Contract[] contracts = new Contract[maxContracts];

    contracts[0] = new Contract(
        LocalDate.of(2025, 1, 1),
        LocalDate.of(2027, 12, 31),
        Contract.ContractType.FULL_TIME,
        Contract.ContractStatus.ACTIVE);

    Payroll[] payrolls = new Payroll[maxPayrolls];

    payrolls[0] = new Payroll(
        10,
        2026,
        20000000,
        2000000,
        1000000,
        500000,
        1500000,
        1000000,
        176,
        10,
        LocalDate.of(2026, 1, 1));

    Attendance[] attendances = new Attendance[maxAttendances];

    attendances[0] = new Attendance(
        LocalDate.of(2026, 10, 5),
        LocalTime.of(8, 0),
        LocalTime.of(17, 0));

    Resignation[] resignations = new Resignation[maxInsurances];

    resignations[0] = new Resignation(
        "",
        LocalDate.of(2012, 1, 13),
        Resignation.ResignationStatus.REJECTED);

    Insurance[] insurances = new Insurance[maxInsurances];

    insurances[0] = new Insurance(
        "INS001",
        Insurance.InsuranceType.HEALTH,
        LocalDate.of(2025, 1, 1),
        LocalDate.of(2027, 12, 31),
        0.08);

    employees[0] = new Employee(
        "John",
        "Smith",
        "john.smith@example.com",
        "0912345678",
        LocalDate.of(1998, 6, 15),
        "Male",
        address,
        "EMP001",
        LocalDate.of(2025, 1, 1),
        "Backend Development",
        Employee.EmployeeStatus.WORKING,
        evaluations,
        contracts,
        payrolls,
        attendances,
        resignations,
        "TAX001",
        insurances,
        positions[0],
        departments[0]);

    employeesOccupied = 0;

    System.out.println("Sample data created successfully!");
  }

  private static void runUI(Scanner sc) {
    int choice;
    do {
      System.out.println("[0] Exit");
      System.out.println("[1] Create N elements");
      System.out.println("[2] Login");
      System.out.println("[3] Create sample data");
      System.out.print("Choice: ");
      choice = Integer.parseInt(sc.nextLine());

      System.out.print("\033[H\033[2J");

      switch (choice) {
        case 0:
          return;
        case 1:
          createNElements(sc);
          break;
        case 2:
          String username;
          int index;
          do {
            System.out.print("Username: ");
            username = sc.nextLine();
            index = getIndex(username);

            if (index == -1) {
              System.out.println("Username not exists");
            }
          } while (index == -1);
          boolean isLoggedIn = logIn(index, sc);

          if (isLoggedIn) {
            if (accounts[index] instanceof EmployeeAccount) {
              runEmployeeUI((EmployeeAccount) accounts[index], sc);
            } else {
              runAdminUI((AdminAccount) accounts[index], sc);
            }
          }
          break;
        case 3:
          createSampleData();
          break;

        default:
          break;
      }
    } while (true);
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    setup(sc);
    readData();
    runUI(sc);
    writeData();

    sc.close();
  }
}
