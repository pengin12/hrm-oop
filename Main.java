import java.io.File;
import java.util.Scanner;

public class Main {
  private static LimitHandler limits = new LimitHandler();
  private static DataHandler data;
  private static ConsoleUI console = new ConsoleUI();

  private static int validateInt(String str) {
    if (str.matches("[0-9]+")) {
      return Integer.parseInt(str);
    }

    return -1;
  }

  private static int getIndex(DataHandler data, String username) {
    for (int i = 0; i < data.getAccounts().length; i++) {
      if (data.getAccounts()[i] != null && data.getAccounts()[i].getUsername().equals(username)) {
        return i;
      }
    }

    return -1;
  }

  private static void linkAccountsToEmployees() {
    if (data == null || data.getAccounts() == null || data.getEmployees() == null)
      return;
    for (Account acc : data.getAccounts()) {
      if (acc instanceof EmployeeAccount) {
        EmployeeAccount empAcc = (EmployeeAccount) acc;
        for (Employee emp : data.getEmployees()) {
          if (emp != null && emp.getId() != null && emp.getId().equalsIgnoreCase(empAcc.getUsername())) {
            empAcc.setEmployee(emp);
            break;
          }
        }
      }
    }
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
          account.submitAbsence(sc);
          break;
        case 5:
          account.submitResignation(sc);
          break;
        case 6:
          account.checkIn(sc);
          break;
        case 7:
          account.checkOut(sc);
          break;
        case 8:
          account.viewAttendance(sc);
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
    String content = "";
    String[] sectionNames = { "Account", "Add", "Print", "Search", "Edit", "Remove", "Other" };
    String[][] sections = { { "Logout", "Change password", "Get info", "Set info" },
        { "Employee", "Evaluation", "Contract", "Payroll", "Attendance", "Insurance", "Position", "Department" },
        { "Employees", "Departments", "Positions", "Payrolls", "Evaluations", "Contracts",
            "Attendances", "Absences", "Resignations", "Insurances" },
        { "Employee", "Department", "Position" },
        { "Employee", "Department", "Position" },
        { "Employee" },
        { "Reply request" },
    };

    int choice;
    do {
      choice = validateInt(console.printUIWithSections(sc, title, sectionNames, sections));

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
          Employee emp = account.createEmployee(data, limits, sc);
          account.addEmployee(data.getEmployees(), emp, sc);
          account.addEmployeeAccount(data.getAccounts(), emp);
          break;
        case 5:
          account.addEvaluation(data.getEmployees(), sc);
          break;
        case 6:
          account.addContract(data.getEmployees(), sc);
          break;
        case 7:
          account.addPayroll(data.getEmployees(), sc);
          break;
        case 8:
          account.addAttendance(data.getEmployees(), sc);
          break;
        case 9:
          account.addInsurance(data.getEmployees(), sc);
          break;
        case 10:
          for (int i = 1; i < data.getPositions().length; i++) {
            if (data.getPositions()[i] == null) {
              data.getPositions()[i] = account.createPosition(sc, data.getPositions());
              break;
            }
          }
          break;
        case 11:
          for (int i = 1; i < data.getDepartments().length; i++) {
            if (data.getDepartments()[i] == null) {
              data.getDepartments()[i] = account.createDepartment(sc, data.getDepartments());
              break;
            }
          }
          break;

        case 12:
          account.printEmployees(data, sc);
          break;
        case 13:
          account.printDepartments(data, sc);
          break;
        case 14:
          account.printPositions(data, sc);
          break;
        case 15:
          account.printPayrolls(data, sc);
          break;
        case 16:
          account.printEvaluations(data, sc);
          break;
        case 17:
          account.printContracts(data, sc);
          break;
        case 18:
          account.printAttendances(data, sc);
          break;
        case 19:
          account.printAbsences(data, sc);
          break;
        case 20:
          account.printResignations(data, sc);
          break;
        case 21:
          account.printInsurances(data, sc);
          break;

        case 22:
          account.searchEmployee(data.getEmployees(), sc);
          break;
        case 23:
          account.searchDepartment(data.getDepartments(), sc);
          break;
        case 24:
          account.searchPosition(data.getPositions(), sc);
          break;

        case 25:
          title = "Edit Employee";
          subtitle = "";
          String[] editOptions = {};
          String input = "Id";

          id = console.printUI(title, subtitle, editOptions, input, sc);
          account.editEmployee(sc, data.getEmployees(), id, data.getPositions(), data.getDepartments());
          break;
        case 26:
          title = "Edit Department";
          account.editDepartment(sc, data.getDepartments());
          break;
        case 27:
          title = "Edit Position";
          account.editPosition(sc, data.getPositions());
          break;

        case 28:
          title = "Remove Employee";
          subtitle = "";
          String[] removeOptions = {};
          input = "Id";

          String removeEmployee = console.printUI(title, subtitle, removeOptions, input, sc);
          account.removeEmployee(data.getEmployees(), removeEmployee, sc);
          account.removeAccount(data.getAccounts(), removeEmployee, sc);
          break;

        case 29:
          title = "Reply to username: ";
          subtitle = "";
          content = "";

          input = "Username";
          String replyUsername = console.printUI(title, subtitle, content, input, sc);

          input = "Reply";
          String reply = console.printUI(title, subtitle, content, input, sc);

          account.replyRequest(data.getAccounts(), replyUsername, reply, sc);
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
        linkAccountsToEmployees();
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
          int accountsOccupied = 0;
          for (int i = 0; i < data.getAccounts().length; i++) {
            if (data.getAccounts()[i] != null) {
              accountsOccupied++;
            }
          }

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
      linkAccountsToEmployees();
    }

    runUI(sc);

    limits.writeLimit();
    data.writeData();

    sc.close();
  }
}
