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
    String title = "Tài khoản nhân viên";
    String subtitle = "Vui lòng chọn chức năng";
    String[] options = { "Đăng xuất", "Đổi mật khẩu", "Xem thông tin", "Cập nhật thông tin", "Gửi đơn xin nghỉ",
        "Gửi đơn xin thôi việc", "Chấm công vào", "Chấm công ra", "Xem lịch sử chấm công", "Xem yêu cầu của tôi" };

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
          String setTitle = "Cập nhật thông tin nhân viên";
          int setChoice;
          do {
            String[] setOptions = { "Thoát", "Đổi tên đăng nhập" };
            setChoice = validateInt(console.printUI(setTitle, subtitle, setOptions, sc));

            if (setChoice < 0 || 1 < setChoice) {
              console.printResult(title, "Lựa chọn không hợp lệ", sc);
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
        case 9:
          account.viewMyRequests(sc);
          break;

        default:
          title = "Tài khoản nhân viên";
          console.printResult(title, "Lựa chọn không hợp lệ", sc);
          break;
      }
    } while (true);
  }

  private static void runAdminUI(AdminAccount account, Scanner sc) {
    String title = "Tài khoản quản trị";
    String subtitle = "Vui lòng chọn chức năng";
    String[] sectionNames = { "Tài khoản", "Thêm", "In", "Tìm kiếm", "Chỉnh sửa", "Xóa", "Khác" };
    String[][] sections = { { "Đăng xuất", "Đổi mật khẩu", "Xem thông tin", "Cập nhật thông tin" },
        { "Nhân viên", "Đánh giá", "Hợp đồng", "Bảng lương", "Chấm công", "Bảo hiểm", "Chức vụ", "Phòng ban" },
        { "Nhân viên", "Phòng ban", "Chức vụ", "Bảng lương", "Đánh giá", "Hợp đồng",
            "Lịch chấm công", "Đơn nghỉ phép", "Đơn thôi việc", "Bảo hiểm" },
        { "Nhân viên", "Phòng ban", "Chức vụ" },
        { "Nhân viên", "Phòng ban", "Chức vụ" },
        { "Nhân viên" },
        { "Phản hồi yêu cầu", "Đánh giá hồ sơ ứng viên" },
    };

    int choice;
    do {
      int pendingCount = AdminInbox.countPending(data.getEmployees());
      sections[6][0] = "Phản hồi yêu cầu (Hộp thư: " + pendingCount + ")";

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
          String setTitle = "Cập nhật thông tin quản trị";

          int setChoice;
          do {
            String[] setOptions = { "Thoát", "Đổi tên đăng nhập", "Đổi trạng thái" };
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
          account.addEmployeeAccount(data.getAccounts(), emp, sc);
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
          title = "Chỉnh sửa nhân viên";
          subtitle = "";
          String[] editOptions = {};
          String input = "Mã nhân viên";

          id = console.printUI(title, subtitle, editOptions, input, sc);
          account.editEmployee(sc, data.getEmployees(), id, data.getPositions(), data.getDepartments());
          break;
        case 26:
          title = "Chỉnh sửa phòng ban";
          account.editDepartment(sc, data.getDepartments());
          break;
        case 27:
          title = "Chỉnh sửa chức vụ";
          account.editPosition(sc, data.getPositions());
          break;

        case 28:
          title = "Xóa nhân viên";
          subtitle = "";
          String[] removeOptions = {};
          input = "Mã nhân viên";

          String removeEmployee = console.printUI(title, subtitle, removeOptions, input, sc);
          account.removeEmployee(data.getEmployees(), removeEmployee, sc);
          account.removeAccount(data.getAccounts(), removeEmployee, sc);
          break;

        case 29:
          account.replyRequest(data, sc);
          break;
        case 30:
          RecruitmentMenu.recruitmentMenu(data, sc);
          break;

        default:
          title = "Tài khoản quản trị";
          console.printResult(title, "Lựa chọn không hợp lệ", sc);
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
        System.out.println("Giá trị không được âm");
      }
    } while (num < 0);

    return num;
  }

  private static void setup(Scanner sc) {
    String title = "Quản lý nhân sự";
    String subtitle = "Cài đặt hệ thống";

    int choice;
    do {
      String[] options = { "Thoát", "Nhập thủ công", "Tải dữ liệu mẫu" };
      choice = validateInt(console.printUI(title, subtitle, options, sc));

      if (choice < 0 || 2 < choice) {
        console.printResult(subtitle, "Lựa chọn không hợp lệ", sc);
      }
    } while (choice < 0 || 2 < choice);

    switch (choice) {
      case 0:
        return;
      case 1:
        limits.setMaxAccounts(inputNonNegative(sc, "Số lượng tài khoản tối đa"));
        limits.setMaxEmployees(inputNonNegative(sc, "Số lượng nhân viên tối đa"));
        limits.setMaxDepartments(inputNonNegative(sc, "Số lượng phòng ban tối đa"));
        limits.setMaxPositions(inputNonNegative(sc, "Số lượng chức vụ tối đa"));
        limits.setMaxRecruitments(inputNonNegative(sc, "Số lượng tuyển dụng"));

        data = new DataHandler(
            limits.getMaxAccounts(),
            limits.getMaxEmployees(),
            limits.getMaxDepartments(),
            limits.getMaxPositions(),
            limits.getMaxRecruitments(),
            limits.getMaxEvaluations(),
            limits.getMaxContracts(),
            limits.getMaxPayrolls(),
            limits.getMaxAttendances(),
            limits.getMaxAbsences(),
            limits.getMaxResignations(),
            limits.getMaxInsurances());

        limits.setMaxEvaluations(inputNonNegative(sc, "Số lượng đánh giá tối đa"));
        limits.setMaxContracts(inputNonNegative(sc, "Số lượng hợp đồng tối đa"));
        limits.setMaxPayrolls(inputNonNegative(sc, "Số lượng bảng lương tối đa"));
        limits.setMaxAttendances(inputNonNegative(sc, "Số lượng bản ghi chấm công tối đa"));
        limits.setMaxInsurances(inputNonNegative(sc, "Số lượng bảo hiểm tối đa"));
        limits.setMaxResignations(inputNonNegative(sc, "Số lượng đơn thôi việc tối đa"));
        break;
      case 2:
        limits.writeSampleLimit();
        limits.readLimit();

        data = new DataHandler(
            limits.getMaxAccounts(),
            limits.getMaxEmployees(),
            limits.getMaxDepartments(),
            limits.getMaxPositions(),
            limits.getMaxRecruitments(),
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
        AdminInbox.syncEmployeeStatuses(data.getEmployees());
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
      title = "Quản lý nhân sự";
      subtitle = "Vui lòng chọn chức năng";
      String[] options = { "Thoát", "Tạo tài khoản quản trị", "Đăng nhập" };
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
            console.printResult(title, "Danh sách tài khoản đã đầy", sc);
            break;
          }

          title = "Tạo tài khoản quản trị";
          subtitle = "";
          String[] createOptions = {};

          String createInput = "Tên đăng nhập";
          String adminUsername = console.printUI(title, subtitle, createOptions, createInput, sc);
          createInput = "Mật khẩu";
          String adminPassword = console.printUI(title, subtitle, createOptions, createInput, sc);

          data.getAccounts()[accountsOccupied + 1] = new AdminAccount(adminUsername, adminPassword,
              Account.AccountStatus.ACTIVE);

          break;
        case 2:
          title = "Đăng nhập";
          subtitle = "";
          String[] loginOptions = {};
          String input = "Tên đăng nhập";

          String username = console.printUI(title, subtitle, loginOptions, input, sc);
          int index = getIndex(data, username);

          if (index == -1) {
            console.printResult(title, "Tài khoản không tồn tại", sc);
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
          title = "Quản lý nhân sự";
          console.printResult(title, "Lựa chọn không hợp lệ", sc);
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
          limits.getMaxRecruitments(),
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
