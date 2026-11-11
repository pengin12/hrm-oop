import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
  private static LimitHandler limits = new LimitHandler();
  private static DataHandler data = new DataHandler();

  static {
    data.setAccounts(new Account[limits.getMaxAccounts()]);
    data.setEmployees(new Employee[limits.getMaxEmployees()]);
    data.setDepartments(new Department[limits.getMaxDepartments()]);
    data.setPositions(new Position[limits.getMaxPositions()]);
  }

  private static int accountsOccupied = -1;
  private static int employeesOccupied = -1;
  private static int departmentsOccupied = -1;
  private static int positionsOccupied = -1;

  private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

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

      if (evaluationCount < 0 || limits.getMaxEvaluations() < evaluationCount) {
        System.out.print("Invalid evaluation count");
        System.out.printf("Min: 0, Max: %d", limits.getMaxEvaluations());
      }
    } while (evaluationCount < 0 || limits.getMaxEvaluations() < evaluationCount);

    Evaluation[] evaluations = new Evaluation[limits.getMaxEvaluations()];
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

      if (contractCount < 0 || limits.getMaxContracts() < contractCount) {
        System.out.println("Invalid contract count");
        System.out.printf("Min: 0, Max: %d\n", limits.getMaxContracts());
      }
    } while (contractCount < 0 || limits.getMaxContracts() < contractCount);

    Contract[] contracts = new Contract[limits.getMaxContracts()];
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

      if (payrollCount < 0 || limits.getMaxPayrolls() < payrollCount) {
        System.out.println("Invalid payroll count");
        System.out.printf("Min: 0, Max: %d\n", limits.getMaxPayrolls());
      }
    } while (payrollCount < 0 || limits.getMaxPayrolls() < payrollCount);

    Payroll[] payrolls = new Payroll[limits.getMaxPayrolls()];
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

      if (attendanceCount < 0 || limits.getMaxAttendances() < attendanceCount) {
        System.out.println("Invalid attendance count");
        System.out.printf("Min: 0, Max: %d\n", limits.getMaxAttendances());
      }
    } while (attendanceCount < 0 || limits.getMaxAttendances() < attendanceCount);

    Attendance[] attendances = new Attendance[limits.getMaxAttendances()];
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

    Resignation[] resignations = new Resignation[limits.getMaxResignations()];

    String reason;
    LocalDate date;
    Resignation.ResignationStatus resigntationStatus = Resignation.ResignationStatus.REJECTED;

    if (resignationChoice == 1) {
      int resignationCount;

      do {
        System.out.print("How many resignations? ");
        resignationCount = Integer.parseInt(sc.nextLine());

        if (resignationCount < 0 || limits.getMaxResignations() < resignationCount) {
          System.out.println("Invalid resignation count");
          System.out.printf("Min: 0, Max: %d\n", limits.getMaxResignations());
        }
      } while (resignationCount < 0 || limits.getMaxResignations() < resignationCount);

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

      if (insuranceCount < 0 || limits.getMaxInsurances() < insuranceCount) {
        System.out.println("Invalid insurance count");
        System.out.printf("Min: 0, Max: %d\n", limits.getMaxInsurances());
      }
    } while (insuranceCount < 0 || limits.getMaxInsurances() < insuranceCount);

    Insurance[] insurances = new Insurance[limits.getMaxInsurances()];
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
      for (int i = 0; i < data.getPositions().length; i++) {
        if (data.getPositions()[i] == null) {
          break;
        }

        System.out.printf("[%d] %s\n", i + 1, data.getPositions()[i].getName());
      }

      System.out.print("Choose positions: ");
      index = Integer.parseInt(sc.nextLine()) - 1;

      if (index < 0 || index > positionsOccupied) {
        System.out.println("Invalid position");
      }
    } while (index < 0 || index > positionsOccupied);

    return data.getPositions()[index];
  }

  private static Department chooseDepartment(Scanner sc) {
    if (departmentsOccupied < 0) {
      System.out.println("No departments available");
      return null;
    }

    int index;
    do {
      for (int i = 0; i < data.getDepartments().length; i++) {
        if (data.getDepartments()[i] == null) {
          break;
        }

        System.out.printf("[%d] %s\n", i + 1, data.getDepartments()[i].getName());
      }

      System.out.print("Choose department: ");
      index = Integer.parseInt(sc.nextLine()) - 1;

      if (index < 0 || index > departmentsOccupied) {
        System.out.println("Invalid department");
      }
    } while (index < 0 || index > departmentsOccupied);

    return data.getDepartments()[index];
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

  private static int getIndex(String username) {
    for (int i = 0; i < data.getAccounts().length; i++) {
      if (data.getAccounts()[i] != null && data.getAccounts()[i].getUsername().equals(username)) {
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

    if (!password.equals(data.getAccounts()[index].getPassword())) {
      System.out.println("Wrong password");
      return false;
    }

    if (data.getAccounts()[index].getStatus() != Account.AccountStatus.ACTIVE) {
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
    } while (usernameExist(data.getAccounts(), current, username));

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
              setAccountName(sc, data.getAccounts(), account);
              break;
            case 2:
              setAccountStatus(sc, data.getAccounts(), account);
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
        "Search department", "Create Position", "Create Department" };

    int choice;
    do {
      for (int i = 0; i < options.length; i++) {
        System.out.printf("[%d] %s\n", i, options[i]);
      }
      System.out.println("Choice: ");
      choice = Integer.parseInt(sc.nextLine());

      System.out.print("\033[H\033[2J");

      String id;
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
              setAccountName(sc, data.getAccounts(), account);
              break;
            case 2:
              setAccountStatus(sc, data.getAccounts(), account);
              break;

            default:
              break;
          }
          break;
        case 4:
          account.addEmployee(data.getEmployees(), createEmployee(sc));
          break;
        case 5:
          System.out.println("Edit Employee");
          System.out.print("Id: ");
          id = sc.nextLine();
          account.editEmployee(sc, data.getEmployees(), id, data.getPositions(), data.getDepartments());
          break;
        case 6:
          String removeEmployee = sc.nextLine();
          account.removeEmployee(data.getAccounts(), removeEmployee);
          break;
        case 7:
          account.printEmployees(data.getEmployees());
          break;
        case 8:
          account.printDepartments(data.getDepartments());
          break;
        case 9:
          System.out.println("Print payrolls");
          System.out.print("Id: ");
          id = sc.nextLine();
          account.printPayrolls(data.getEmployees(), id);
          break;
        case 10:
          System.out.print("Reply to username: ");
          String replyUsername = sc.nextLine();

          System.out.print("Reply: ");
          String reply = sc.nextLine();

          account.replyRequest(data.getAccounts(), replyUsername, reply);
          break;
        case 11:
          account.searchEmployee(data.getEmployees());
          break;
        case 12:
          account.searchDepartment(data.getDepartments());
          break;
        case 13:
          account.createPosition(sc, data.getPositions());
          break;
        case 14:
          account.createDepartment(sc, data.getDepartments());
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

    limits.setMaxAccounts(inputNonNegative(sc, "Max accounts"));
    limits.setMaxEmployees(inputNonNegative(sc, "Max employees"));
    limits.setMaxDepartments(inputNonNegative(sc, "Max departments"));
    limits.setMaxPositions(inputNonNegative(sc, "Max positions"));

    data.setAccounts(new Account[limits.getMaxAccounts()]);
    data.setEmployees(new Employee[limits.getMaxEmployees()]);
    data.setDepartments(new Department[limits.getMaxDepartments()]);
    data.setPositions(new Position[limits.getMaxPositions()]);

    limits.setMaxEvaluations(inputNonNegative(sc, "Max evaluations"));
    limits.setMaxContracts(inputNonNegative(sc, "Max contracts"));
    limits.setMaxPayrolls(inputNonNegative(sc, "Max payrolls"));
    limits.setMaxAttendances(inputNonNegative(sc, "Max attendances"));
    limits.setMaxInsurances(inputNonNegative(sc, "Max insurances"));
    limits.setMaxResignations(inputNonNegative(sc, "Max resignations"));

    System.out.print("\033[H\033[2J");

    try {
      String currentDirectory = System.getProperty("user.dir");
      String directoryPath = currentDirectory + File.separator + "data";

      File directory = new File(directoryPath);
      if (!directory.exists()) {
        directory.mkdir();
      }

      String accountPath = directoryPath + File.separator + "data.getAccounts().txt";
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

  private static void createSampleData() {
    if (limits.getMaxAccounts() < 2 || limits.getMaxEmployees() < 1 ||
        limits.getMaxDepartments() < 2 || limits.getMaxPositions() < 2 ||
        limits.getMaxEvaluations() < 1 || limits.getMaxContracts() < 1 ||
        limits.getMaxPayrolls() < 1 || limits.getMaxAttendances() < 1 ||
        limits.getMaxInsurances() < 1) {
      throw new IllegalStateException(
          "Not enough capacity. Increase the maximum values in setup().");
    }

    data.getAccounts()[0] = new AdminAccount(
        "admin",
        "admin123",
        Account.AccountStatus.ACTIVE);

    data.getAccounts()[1] = new EmployeeAccount(
        "john",
        "john123",
        Account.AccountStatus.ACTIVE);

    accountsOccupied = 1;

    data.getDepartments()[0] = new Department(
        "IT",
        "0901234567",
        "Software Development");

    data.getDepartments()[1] = new Department(
        "Human Resources",
        "0907654321",
        "Recruitment");

    departmentsOccupied = 1;

    data.getPositions()[0] = new Position(
        "Develop and maintain software",
        "Software Developer",
        2.5);

    data.getPositions()[1] = new Position(
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

    Evaluation[] evaluations = new Evaluation[limits.getMaxEvaluations()];

    evaluations[0] = new Evaluation(
        "October 2026",
        "Excellent performance",
        9.0,
        9.5,
        9.0,
        9.0,
        8.5,
        9.0);

    Contract[] contracts = new Contract[limits.getMaxContracts()];

    contracts[0] = new Contract(
        LocalDate.of(2025, 1, 1),
        LocalDate.of(2027, 12, 31),
        Contract.ContractType.FULL_TIME,
        Contract.ContractStatus.ACTIVE);

    Payroll[] payrolls = new Payroll[limits.getMaxPayrolls()];

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

    Attendance[] attendances = new Attendance[limits.getMaxAttendances()];

    attendances[0] = new Attendance(
        LocalDate.of(2026, 10, 5),
        LocalTime.of(8, 0),
        LocalTime.of(17, 0));

    Resignation[] resignations = new Resignation[limits.getMaxInsurances()];

    resignations[0] = new Resignation(
        "",
        LocalDate.of(2012, 1, 13),
        Resignation.ResignationStatus.REJECTED);

    Insurance[] insurances = new Insurance[limits.getMaxInsurances()];

    insurances[0] = new Insurance(
        "INS001",
        Insurance.InsuranceType.HEALTH,
        LocalDate.of(2025, 1, 1),
        LocalDate.of(2027, 12, 31),
        0.08);

    data.getEmployees()[0] = new Employee(
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
        data.getPositions()[0],
        data.getDepartments()[0]);

    employeesOccupied = 0;

    System.out.println("Sample data created successfully!");
  }

  private static void runUI(Scanner sc) {
    int choice;
    do {
      System.out.println("[0] Exit");
      System.out.println("[1] Create admin account");
      System.out.println("[2] Login");
      System.out.println("[3] Create sample data");
      System.out.print("Choice: ");
      choice = Integer.parseInt(sc.nextLine());

      System.out.print("\033[H\033[2J");

      switch (choice) {
        case 0:
          return;
        case 1:
          if (accountsOccupied >= data.getAccounts().length) {
            System.out.println("Accounts storage is full");
          }

          System.out.print("Username: ");
          String adminUsername = sc.nextLine();
          String adminPassword = sc.nextLine();
          data.getAccounts()[accountsOccupied + 1] = new AdminAccount(adminUsername, adminPassword,
              Account.AccountStatus.ACTIVE);

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
            if (data.getAccounts()[index] instanceof EmployeeAccount) {
              runEmployeeUI((EmployeeAccount) data.getAccounts()[index], sc);
            } else {
              runAdminUI((AdminAccount) data.getAccounts()[index], sc);
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
    data.readData();
    runUI(sc);
    limits.writeLimit();
    data.writeData();

    sc.close();
  }
}
