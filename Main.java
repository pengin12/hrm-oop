import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
  private static LimitHandler limits = new LimitHandler();
  private static DataHandler data;
  private static ConsoleUI console = new ConsoleUI();

  private static int accountsOccupied = -1;
  private static int employeesOccupied = -1;
  private static int departmentsOccupied = -1;
  private static int positionsOccupied = -1;

  private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

  private static int validateInt(String str) {
    if (str.matches("[0-9]+")) {
      return Integer.parseInt(str);
    }

    return -1;
  }

  private static Address createAddress(Scanner sc) {
    String title = "Create Employee";
    String subtitle = "Create Address";
    String[] options = {};
    String input;

    input = "Number";
    String number = console.printUI(title, subtitle, options, input, sc);

    input = "Street";
    String street = console.printUI(title, subtitle, options, input, sc);

    input = "Ward";
    String ward = console.printUI(title, subtitle, options, input, sc);

    input = "District";
    String district = console.printUI(title, subtitle, options, input, sc);

    input = "City";
    String city = console.printUI(title, subtitle, options, input, sc);

    return new Address(number, street, ward, district, city);
  }

  private static Evaluation createEvaluation(Scanner sc) {
    String title = "Create Employee";
    String subtitle = "Create Evaluation";
    String[] options = {};
    String input;

    input = "Month: ";
    String month = console.printUI(title, subtitle, options, input, sc);

    input = "Comment";
    String comment = console.printUI(title, subtitle, options, input, sc);

    input = "Work score: ";
    double workScore = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

    input = "Attitude score: ";
    double attitudeScore = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

    input = "Discipline score: ";
    double disciplineScore = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

    input = "Work quality: ";
    double workQuality = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

    input = "Productivity: ";
    double productivity = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

    input = "Team work: ";
    double teamWork = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

    return new Evaluation(month, comment, workScore, attitudeScore, disciplineScore, workQuality, productivity,
        teamWork);
  }

  private static Contract createContract(Scanner sc) {
    String title = "Create Employee";
    String subtitle = "Create Contracts";
    String[] options = {};
    String input;

    LocalDate startDate;
    LocalDate endDate;
    do {
      input = "Start date: ";
      startDate = console.printDateUI(title, subtitle, options, input, sc);

      input = "End date: ";
      endDate = console.printDateUI(title, subtitle, options, input, sc);

      if (startDate.isAfter(endDate)) {
        System.out.println("Start date should be before end date");
      }
    } while (startDate.isBefore(endDate));

    int typeNum = 0;
    Contract.ContractType type = Contract.ContractType.TEMPORARY;
    do {
      String[] typeOptions = { "Probation", "Full time", "Part time", "Temporary", "Fixed term", "Permanent" };
      typeNum = validateInt(console.printUI(title, subtitle, typeOptions, input, sc));

      if (typeNum < 0 || 5 < typeNum) {
        console.printResult(title, "Invalid choice", sc);
      }
    } while (typeNum < 0 || 5 < typeNum);

    switch (typeNum) {
      case 0:
        type = Contract.ContractType.PROBATION;
        break;
      case 1:
        type = Contract.ContractType.FULL_TIME;
        break;
      case 2:
        type = Contract.ContractType.PART_TIME;
        break;
      case 3:
        type = Contract.ContractType.TEMPORARY;
        break;
      case 4:
        type = Contract.ContractType.FIXED_TERM;
        break;
      case 5:
        type = Contract.ContractType.PERMANENT;
        break;

      default:
        break;
    }

    int statusNum = 0;
    Contract.ContractStatus status = Contract.ContractStatus.ACTIVE;
    do {
      String[] statusOptions = { "Draft", "Active", "Expired" };
      statusNum = validateInt(console.printUI(title, subtitle, statusOptions, input, sc));

      if (statusNum < 0 || 2 < statusNum) {
        console.printResult(title, "Invalid choice", sc);
      }
    } while (statusNum < 0 || 2 < statusNum);

    switch (statusNum) {
      case 0:
        status = Contract.ContractStatus.DRAFT;
        break;
      case 1:
        status = Contract.ContractStatus.ACTIVE;
        break;
      case 2:
        status = Contract.ContractStatus.EXPIRED;
        break;

      default:
        break;
    }

    return new Contract(startDate, endDate, type, status);
  }

  private static Payroll createPayroll(Scanner sc) {
    String title = "Create Employee";
    String subtitle = "Create Payrolls";
    String[] options = {};
    String input;

    input = "Month: ";
    int month = validateInt(console.printUI(title, subtitle, options, input, sc));

    input = "Year: ";
    int year = validateInt(console.printUI(title, subtitle, options, input, sc));

    input = "Base Salary";
    double baseSalary = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

    input = "Bonus: ";
    double bonus = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

    input = "Allowance: ";
    double allowance = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

    input = "Deduction: ";
    double deduction = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

    input = "Tax amount: ";
    double taxAmount = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

    input = "Insurance amount: ";
    double insuranceAmount = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

    input = "Worked hours: ";
    int workedHours = validateInt(console.printUI(title, subtitle, options, input, sc));

    input = "Overtime hours: ";
    int overtimeHours = validateInt(console.printUI(title, subtitle, options, input, sc));

    input = "Last salary increase (dd/MM/yyyy): ";
    LocalDate lastSalaryIncreaseDate = console.printDateUI(title, subtitle, options, input, sc);

    return new Payroll(month, year, baseSalary, bonus, allowance, deduction, taxAmount, insuranceAmount, workedHours,
        overtimeHours, lastSalaryIncreaseDate);
  }

  private static Attendance createAttendance(Scanner sc) {
    String title = "Create Employee";
    String subtitle = "Create Attendance";
    String[] options = {};
    String input;

    input = "Date (dd/MM/yyyy): ";
    LocalDate date = console.printDateUI(title, subtitle, options, input, sc);

    LocalTime clockIn;
    LocalTime clockOut;
    do {
      input = "Clock in (HH:mm): ";
      clockIn = LocalTime.parse(console.printUI(title, subtitle, options, input, sc), timeFormatter);

      input = "Clock out (HH:mm): ";
      clockOut = LocalTime.parse(console.printUI(title, subtitle, options, input, sc), timeFormatter);

      if (clockIn.isAfter(clockOut)) {
        console.printResult(title, "Clock in time should be before clock out time", sc);
      }
    } while (clockIn.isBefore(clockOut));

    return new Attendance(date, clockIn, clockOut);
  }

  private static Insurance createInsurance(Scanner sc) {
    String title = "Create Employee";
    String subtitle = "Create Insurance";
    String[] options = {};
    String input;

    input = "Insurance number: ";
    String insuranceNumber = console.printUI(title, subtitle, options, input, sc);

    int typeNum;
    Insurance.InsuranceType type = Insurance.InsuranceType.HEALTH;
    do {
      String[] typeOptions = { "Life", "Health", "Social", "Unemployment" };
      typeNum = validateInt(console.printUI(title, subtitle, typeOptions, sc));
    } while (typeNum < 0 || 3 < typeNum);

    switch (typeNum) {
      case 0:
        type = Insurance.InsuranceType.LIFE;
        break;
      case 1:
        type = Insurance.InsuranceType.HEALTH;
        break;
      case 2:
        type = Insurance.InsuranceType.SOCIAL;
        break;
      case 3:
        type = Insurance.InsuranceType.UNEMPLOYMENT;
        break;

      default:
        break;
    }

    LocalDate startDate;
    LocalDate endDate;
    do {
      input = "Start date (dd/MM/yyyy): ";
      startDate = console.printDateUI(title, subtitle, options, input, sc);

      input = "End date (dd/MM/yyyy): ";
      endDate = console.printDateUI(title, subtitle, options, input, sc);

      if (startDate.isAfter(endDate)) {
        console.printResult(title, "Start date should be before end date", sc);
      }
    } while (startDate.isBefore(endDate));

    input = "Rate: ";
    double rate = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

    return new Insurance(insuranceNumber, type, startDate, endDate, rate);
  }

  private static Employee.EmployeeStatus chooseEmployeeStatus(Scanner sc) {
    String title = "Create Employee";
    String subtitle = "";
    String[] options = { "Working", "On leave", "Resigned" };

    int statusChoice;
    do {
      statusChoice = validateInt(console.printUI(title, subtitle, options, sc));

      if (statusChoice < 0 || 2 < statusChoice) {
        console.printResult(title, "Invalid choice", sc);
      }
    } while (statusChoice < 0 || 2 < statusChoice);

    switch (statusChoice) {
      case 0:
        return Employee.EmployeeStatus.WORKING;
      case 1:
        return Employee.EmployeeStatus.ON_LEAVE;
      case 2:
        return Employee.EmployeeStatus.RESIGNED;

      default:
        break;
    }

    return Employee.EmployeeStatus.RESIGNED;
  }

  private static Evaluation[] createEvaluations(Scanner sc) {
    String title = "Create Employee";
    String subtitle = "Create Evaluation";
    String[] options = { "Exit", "Create an evaluation" };

    int index = 0;
    Evaluation[] evaluations = new Evaluation[limits.getMaxEvaluations()];

    int choice;
    do {
      choice = validateInt(console.printUI(title, subtitle, options, sc));

      if (choice < 0 || 1 < choice) {
        console.printResult(title, "Invalid choice", sc);
        continue;
      }

      if (choice == 0) {
        break;
      }

      if (index + 1 < limits.getMaxEvaluations()) {
        String result = "Evaluation storage is full\n" + "Max: %d" + limits.getMaxEvaluations();
        console.printResult(title, result, sc);
      }

      evaluations[index] = createEvaluation(sc);
      index++;
    } while (choice < 0 || 1 < choice);

    return evaluations;
  }

  private static Contract[] createContracts(Scanner sc) {
    String title = "Create Employee";
    String subtitle = "Create Contracts";
    String[] options = { "Exit", "Create a contract" };

    int index = 0;
    Contract[] contracts = new Contract[limits.getMaxContracts()];

    int choice;
    do {
      choice = validateInt(console.printUI(title, subtitle, options, sc));

      if (choice < 0 || 1 < choice) {
        console.printResult(title, "Invalid choice", sc);
        continue;
      }

      if (choice == 0) {
        break;
      }

      if (index + 1 < limits.getMaxContracts()) {
        String result = "Contracts storage is full\n" + "Max: %d" + limits.getMaxContracts();
        console.printResult(title, result, sc);
      }

      contracts[index] = createContract(sc);
      index++;
    } while (choice < 0 || 1 < choice);

    return contracts;
  }

  private static Payroll[] createPayrolls(Scanner sc) {
    String title = "Create Employee";
    String subtitle = "Create Payrolls";
    String[] options = { "Exit", "Create a Payroll" };

    int index = 0;
    Payroll[] payrolls = new Payroll[limits.getMaxPayrolls()];

    int choice;
    do {
      choice = validateInt(console.printUI(title, subtitle, options, sc));

      if (choice < 0 || 1 < choice) {
        console.printResult(title, "Invalid choice", sc);
        continue;
      }

      if (choice == 0) {
        break;
      }

      if (index + 1 < limits.getMaxPayrolls()) {
        String result = "Payrolls storage is full\n" + "Max: %d" + limits.getMaxPayrolls();
        console.printResult(title, result, sc);
      }

      payrolls[index] = createPayroll(sc);
      index++;
    } while (choice < 0 || 1 < choice);

    return payrolls;
  }

  private static Attendance[] createAttendances(Scanner sc) {
    String title = "Create Employee";
    String subtitle = "Create Attendances";
    String[] options = { "Exit", "Create a attendance" };

    int index = 0;
    Attendance[] attendances = new Attendance[limits.getMaxAttendances()];

    int choice;
    do {
      choice = validateInt(console.printUI(title, subtitle, options, sc));

      if (choice < 0 || 1 < choice) {
        console.printResult(title, "Invalid choice", sc);
        continue;
      }

      if (choice == 0) {
        break;
      }

      if (index + 1 < limits.getMaxAttendances()) {
        String result = "Attendance storage is full\n" + "Max: %d" + limits.getMaxAttendances();
        console.printResult(title, result, sc);
      }

      attendances[index] = createAttendance(sc);
      index++;
    } while (choice < 0 || 1 < choice);

    return attendances;
  }

  private static Resignation createResignation(Scanner sc) {
    String title = "Create Employee";
    String subtitle = "Create Resignation";
    String[] options = {};
    String input;

    input = "Reason: ";
    String reason = sc.nextLine();

    input = "Date (dd/MM/yyyy): ";
    LocalDate date = console.printDateUI(title, subtitle, options, input, sc);

    int statusNum;
    do {
      String[] statusOptions = { "Pending", "Rejected", "Approved" };
      statusNum = validateInt(console.printUI(title, subtitle, statusOptions, sc));

      if (statusNum < 0 || 2 < statusNum) {
        console.printResult(title, "Invalid choice", sc);
      }
    } while (statusNum < 0 || 2 < statusNum);

    Resignation.ResignationStatus resignationStatus = Resignation.ResignationStatus.PENDING;
    switch (statusNum) {
      case 0:
        resignationStatus = Resignation.ResignationStatus.PENDING;
        break;
      case 1:
        resignationStatus = Resignation.ResignationStatus.REJECTED;
        break;
      case 2:
        resignationStatus = Resignation.ResignationStatus.APPROVED;
        break;

      default:
        break;
    }

    return new Resignation(reason, date, resignationStatus);
  }

  private static Resignation[] createResignations(Scanner sc) {
    String title = "Create Employee";
    String subtitle = "Create Resignations";
    String[] options = { "Exit", "Create a resignation" };

    int index = 0;
    Resignation[] resignations = new Resignation[limits.getMaxResignations()];

    int choice;
    do {
      choice = validateInt(console.printUI(title, subtitle, options, sc));

      if (choice < 0 || 1 < choice) {
        console.printResult(title, "Invalid choice", sc);
        continue;
      }

      if (choice == 0) {
        break;
      }

      if (index + 1 < limits.getMaxResignations()) {
        String result = "Resignation storage is full\n" + "Max: %d" + limits.getMaxResignations();
        console.printResult(title, result, sc);
      }

      resignations[index] = createResignation(sc);
      index++;
    } while (choice < 0 || 1 < choice);

    return resignations;
  }

  private static Insurance[] createInsurances(Scanner sc) {
    String title = "Create Employee";
    String subtitle = "Create Insurances";
    String[] options = { "Exit", "Create a insurance" };

    int index = 0;
    Insurance[] insurances = new Insurance[limits.getMaxInsurances()];

    int choice;
    do {
      choice = validateInt(console.printUI(title, subtitle, options, sc));

      if (choice < 0 || 1 < choice) {
        console.printResult(title, "Invalid choice", sc);
        continue;
      }

      if (choice == 0) {
        break;
      }

      if (index + 1 < limits.getMaxInsurances()) {
        String result = "Insurance storage is full\n" + "Max: %d" + limits.getMaxInsurances();
        console.printResult(title, result, sc);
      }

      insurances[index] = createInsurance(sc);
      index++;
    } while (choice < 0 || 1 < choice);

    return insurances;
  }

  private static Position choosePosition(Scanner sc) {
    String title = "Create Employee";
    String subtitle = "Choose Position";
    String[] options = { "Exit", "Choose a position" };

    if (positionsOccupied < 0) {
      console.printResult(title, "No positions available", sc);
      return null;
    }

    int index;
    do {
      String[] positionOptions = new String[limits.getMaxPositions()];

      for (int i = 0; i < data.getPositions().length; i++) {
        if (data.getPositions()[i] == null) {
          break;
        }

        positionOptions[i] = data.getPositions()[i].getName();
      }

      index = validateInt(console.printUI(title, subtitle, options, sc));

      if (index < 0 || index > positionsOccupied) {
        console.printResult(title, "Invalid choice", sc);
      }
    } while (index < 0 || index > positionsOccupied);

    return data.getPositions()[index];
  }

  private static Department chooseDepartment(Scanner sc) {
    String title = "Create Employee";
    String subtitle = "Choose Department";
    String[] options = { "Exit", "Choose a department" };

    if (departmentsOccupied < 0) {
      console.printResult(title, "No departments available", sc);
      return null;
    }

    int index;
    do {
      String[] departmentOptions = new String[limits.getMaxDepartments()];

      for (int i = 0; i < data.getDepartments().length; i++) {
        if (data.getDepartments()[i] == null) {
          break;
        }

        departmentOptions[i] = data.getDepartments()[i].getName();
      }

      index = validateInt(console.printUI(title, subtitle, options, sc));

      if (index < 0 || index > departmentsOccupied) {
        console.printResult(title, "Invalid choice", sc);
      }
    } while (index < 0 || index > departmentsOccupied);

    return data.getDepartments()[index];
  }

  private static Employee createEmployee(Scanner sc) {
    String title = "Create Employee";
    String subtitle = "";
    String[] options = {};
    String input;

    input = "First name";
    String firstName = console.printUI(title, subtitle, options, input, sc);

    input = "Last name";
    String lastName = console.printUI(title, subtitle, options, input, sc);

    input = "Email";
    String email = console.printUI(title, subtitle, options, input, sc);

    input = "Phone number";
    String phone = console.printUI(title, subtitle, options, input, sc);

    input = "Date of birth (dd/MM/yyyy)";
    LocalDate dateOfBirth = console.printDateUI(title, subtitle, options, input, sc);

    input = "Gender";
    String gender = console.printUI(title, subtitle, options, input, sc);

    input = "Address";
    Address address = createAddress(sc);

    input = "Id";
    String id = console.printUI(title, subtitle, options, input, sc);

    input = "Hire date (dd/MM/yyyy)";
    LocalDate hireDate = console.printDateUI(title, subtitle, options, input, sc);
    ;

    input = "Specialization";
    String specialization = console.printUI(title, subtitle, options, input, sc);

    Employee.EmployeeStatus status = chooseEmployeeStatus(sc);

    Evaluation[] evaluations = createEvaluations(sc);
    Contract[] contracts = createContracts(sc);
    Payroll[] payrolls = createPayrolls(sc);
    Attendance[] attendances = createAttendances(sc);
    Resignation[] resignations = createResignations(sc);

    input = "Tax id";
    String taxId = console.printUI(title, subtitle, options, input, sc);

    Insurance[] insurances = createInsurances(sc);

    Position position = choosePosition(sc);
    Department department = chooseDepartment(sc);

    return new Employee(firstName, lastName, email, phone, dateOfBirth, gender, address, id, hireDate, specialization,
        status, evaluations, contracts, payrolls, attendances, resignations, taxId, insurances, position, department);
  }

  private static int getIndex(DataHandler data, String username) {
    for (int i = 0; i < data.getAccounts().length; i++) {
      if (data.getAccounts()[i] != null && data.getAccounts()[i].getUsername().equals(username)) {
        return i;
      }
    }

    return -1;
  }

  private static void runEmployeeUI(EmployeeAccount account, Scanner sc) {
    String title = "Employee Account";
    String subtitle = "Choose an option";
    String[] options = { "Logout", "Change password", "Get info", "Set info", "Submit absence", "Submit resignation",
        "Check in", "Check out", "View attendance" };

    int choice;
    do {
      choice = validateInt(console.printUI(title, subtitle, options, sc));

      switch (choice) {
        case 0:
          return;
        case 1:
          account.resetPassword(sc);
          break;
        case 2:
          account.getInfo(sc);
          break;
        case 3:
          String setTitle = "Set Employee Information";
          int setChoice;
          do {
            String[] setOptions = { "Exit", "Set username" };
            setChoice = validateInt(console.printUI(setTitle, subtitle, setOptions, sc));

            if (setChoice < 0 || 1 < setChoice) {
              console.printResult(title, "Invalid choice", sc);
            }
          } while (setChoice < 0 || 1 < setChoice);

          switch (setChoice) {
            case 1:
              account.setAccountName(sc, data.getAccounts(), account);
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
          title = "Employee Account";
          console.printResult(title, "Invalid choice", sc);
          break;
      }
    } while (true);
  }

  private static void runAdminUI(AdminAccount account, Scanner sc) {
    String title = "Admin Account";
    String subtitle = "Choose an option";
    String[] options = { "Logout", "Change password", "Get info", "Set info", "Add employee", "Edit employee",
        "Remove employee", "Print employees", "Print departments", "Print positions", "Print payrolls", "Reply request",
        "Search employee", "Search department", "Create Position", "Create Department" };

    int choice;
    do {
      choice = validateInt(console.printUI(title, subtitle, options, sc));

      String id;
      switch (choice) {
        case 0:
          return;
        case 1:
          account.resetPassword(sc);
          break;
        case 2:
          account.getInfo(sc);
          break;
        case 3:
          String setTitle = "Set Admin Information";

          int setChoice;
          do {
            String[] setOptions = { "Exit", "Set username", "Set status" };
            setChoice = validateInt(console.printUI(setTitle, subtitle, setOptions, sc));
          } while (setChoice < 0 || 2 < setChoice);

          switch (setChoice) {
            case 1:
              account.setAccountName(sc, data.getAccounts(), account);
              break;
            case 2:
              account.setAccountStatus(sc, account);
              break;

            default:
              break;
          }
          break;
        case 4:
          account.addEmployee(data.getEmployees(), createEmployee(sc), sc);
          break;
        case 5:
          title = "Edit Employee";
          subtitle = "";
          String[] editOptions = {};
          String input = "Id";

          id = console.printUI(title, subtitle, editOptions, input, sc);
          account.editEmployee(sc, data.getEmployees(), id, data.getPositions(), data.getDepartments());
          break;
        case 6:
          title = "Remove Employee";
          subtitle = "";
          String[] removeOptions = {};
          input = "Id";

          String removeEmployee = console.printUI(title, subtitle, removeOptions, input, sc);
          account.removeEmployee(data.getAccounts(), removeEmployee, sc);
          break;
        case 7:
          account.printEmployees(data.getEmployees(), sc);
          break;
        case 8:
          title = "Print Department";
          String content = account.getDepartments(data.getDepartments());

          console.printResult(title, content, sc);
          break;
        case 9:
          title = "Print Position";
          content = account.getPositions(data.getPositions());

          console.printResult(title, content, sc);
          break;
        case 10:
          title = "Print payrolls";
          subtitle = "";
          content = "";
          input = "Id";

          id = console.printUI(title, subtitle, content, input, sc);

          content = account.printPayrolls(data.getEmployees(), id, sc);
          console.printResult(title, content, sc);
          break;
        case 11:
          title = "Reply to username: ";
          subtitle = "";
          content = "";

          input = "Username";
          String replyUsername = console.printUI(title, subtitle, content, input, sc);

          input = "Reply";
          String reply = console.printUI(title, subtitle, content, input, sc);

          account.replyRequest(data.getAccounts(), replyUsername, reply, sc);
          break;
        case 12:
          account.searchEmployee(data.getEmployees(), sc);
          break;
        case 13:
          account.searchDepartment(data.getDepartments(), sc);
          break;
        case 14:
          for (int i = 1; i < data.getPositions().length; i++) {
            if (data.getPositions()[i] == null) {
              data.getPositions()[i] = account.createPosition(sc, data.getPositions());
              break;
            }
          }
          break;
        case 15:
          for (int i = 1; i < data.getDepartments().length; i++) {
            if (data.getDepartments()[i] == null) {
              data.getDepartments()[i] = account.createDepartment(sc, data.getDepartments());
              break;
            }
          }
          break;

        default:
          title = "Admin Account";
          console.printResult(title, "Invalid choice", sc);
          break;
      }
    } while (true);
  }

  private static int inputNonNegative(Scanner sc, String name) {
    int num;

    do {
      System.out.print(name + ": ");
      num = validateInt(sc.nextLine());

      if (num < 0) {
        System.out.println("This cannot be negative");
      }
    } while (num < 0);

    return num;
  }

  private static int getOccupied(Department[] departments) {
    int occupied = 0;

    for (int i = 0; i < departments.length; i++) {
      if (departments[i] == null) {
        break;
      }

      occupied++;
    }

    return occupied;
  }

  private static int getOccupied(Position[] positions) {
    int occupied = 0;

    for (int i = 0; i < positions.length; i++) {
      if (positions[i] == null) {
        break;
      }

      occupied++;
    }

    return occupied;
  }

  private static void setup(Scanner sc) {
    String title = "Human Resource Management";
    String subtitle = "Setup system";

    int choice;
    do {
      String[] options = { "Exit", "Manually", "Load sample data" };
      choice = validateInt(console.printUI(title, subtitle, options, sc));

      if (choice < 0 || 2 < choice) {
        console.printResult(subtitle, "Invalid choice", sc);
      }
    } while (choice < 0 || 2 < choice);

    switch (choice) {
      case 0:
        return;
      case 1:
        limits.setMaxAccounts(inputNonNegative(sc, "Max accounts"));
        limits.setMaxEmployees(inputNonNegative(sc, "Max employees"));
        limits.setMaxDepartments(inputNonNegative(sc, "Max departments"));
        limits.setMaxPositions(inputNonNegative(sc, "Max positions"));

        data = new DataHandler(
            limits.getMaxAccounts(),
            limits.getMaxEmployees(),
            limits.getMaxDepartments(),
            limits.getMaxPositions(),
            limits.getMaxEvaluations(),
            limits.getMaxContracts(),
            limits.getMaxPayrolls(),
            limits.getMaxAttendances(),
            limits.getMaxAbsences(),
            limits.getMaxResignations(),
            limits.getMaxInsurances());

        limits.setMaxEvaluations(inputNonNegative(sc, "Max evaluations"));
        limits.setMaxContracts(inputNonNegative(sc, "Max contracts"));
        limits.setMaxPayrolls(inputNonNegative(sc, "Max payrolls"));
        limits.setMaxAttendances(inputNonNegative(sc, "Max attendances"));
        limits.setMaxInsurances(inputNonNegative(sc, "Max insurances"));
        limits.setMaxResignations(inputNonNegative(sc, "Max resignations"));
        break;
      case 2:
        limits.writeSampleLimit();
        limits.readLimit();

        data = new DataHandler(
            limits.getMaxAccounts(),
            limits.getMaxEmployees(),
            limits.getMaxDepartments(),
            limits.getMaxPositions(),
            limits.getMaxEvaluations(),
            limits.getMaxContracts(),
            limits.getMaxPayrolls(),
            limits.getMaxAttendances(),
            limits.getMaxAbsences(),
            limits.getMaxResignations(),
            limits.getMaxInsurances());

        data.writeSampleData();
        data.readData();

        departmentsOccupied = getOccupied(data.getDepartments());
        positionsOccupied = getOccupied(data.getPositions());
        break;

      default:
        break;
    }

    System.out.print("\033[H\033[2J");
  }

  private static void runUI(Scanner sc) {
    String title;
    String subtitle;

    int choice;
    do {
      title = "Human Resource Management";
      subtitle = "Choose an option";
      String[] options = { "Exit", "Create admin account", "Login" };
      choice = validateInt(console.printUI(title, subtitle, options, sc));

      switch (choice) {
        case 0:
          return;
        case 1:
          if (accountsOccupied >= data.getAccounts().length) {
            console.printResult(title, "Accounts storage is full", sc);
            break;
          }

          title = "Creat admin account";
          subtitle = "";
          String[] createOptions = {};

          String createInput = "Username";
          String adminUsername = console.printUI(title, subtitle, createOptions, createInput, sc);
          createInput = "Password";
          String adminPassword = console.printUI(title, subtitle, createOptions, createInput, sc);

          data.getAccounts()[accountsOccupied + 1] = new AdminAccount(adminUsername, adminPassword,
              Account.AccountStatus.ACTIVE);

          break;
        case 2:
          title = "Login";
          subtitle = "";
          String[] loginOptions = {};
          String input = "Username";

          String username = console.printUI(title, subtitle, loginOptions, input, sc);
          int index = getIndex(data, username);

          if (index == -1) {
            console.printResult(title, "Account not exist", sc);
            break;
          }

          boolean isLoggedIn = data.getAccounts()[index].login(sc);

          if (isLoggedIn) {
            if (data.getAccounts()[index] instanceof EmployeeAccount) {
              runEmployeeUI((EmployeeAccount) data.getAccounts()[index], sc);
            } else {
              runAdminUI((AdminAccount) data.getAccounts()[index], sc);
            }
          }
          break;

        default:
          title = "Human Resource Management";
          console.printResult(title, "Invalid choice", sc);
          break;
      }
    } while (true);
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    String currentDirectory = System.getProperty("user.dir");
    String directoryPath = currentDirectory + File.separator + "data";
    File directory = new File(directoryPath);

    if (!directory.exists()) {
      directory.mkdir();
      setup(sc);
    } else {
      limits.readLimit();

      data = new DataHandler(
          limits.getMaxAccounts(),
          limits.getMaxEmployees(),
          limits.getMaxDepartments(),
          limits.getMaxPositions(),
          limits.getMaxEvaluations(),
          limits.getMaxContracts(),
          limits.getMaxPayrolls(),
          limits.getMaxAttendances(),
          limits.getMaxAbsences(),
          limits.getMaxResignations(),
          limits.getMaxInsurances());

      data.readData();
    }

    runUI(sc);
    limits.writeLimit();
    data.writeData();

    sc.close();
  }
}
