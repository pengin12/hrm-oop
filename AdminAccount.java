import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class AdminAccount extends Account {
    private final String DEFAULT_PASSWORD = "Password123@";

    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private static DateTimeFormatter yearMonthFormatter = DateTimeFormatter.ofPattern("MM/yyyy");

    private static int validateInt(String str) {
        if (str.matches("[0-9]+")) {
            return Integer.parseInt(str);
        }

        return -1;
    }

    private static int getIndex(DataHandler data, String username) {
        for (int i = 0; i < data.getAccounts().length; i++) {
            if (data.getAccounts()[i] != null && data.getAccounts()[i].getUsername().equalsIgnoreCase(username)) {
                return i;
            }
        }

        return -1;
    }

    private String askId(String title, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        return console.printUI(title, "", new String[] {}, "Id", sc);
    }

    private boolean askSortOrder(String title, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String choice = console.printUI(title, "", new String[] {}, "Có/Không [Y/n]", sc);
        return !choice.equalsIgnoreCase("n");
    }

    public AdminAccount(String username, String password, AccountStatus status) {
        super(username, password, status);
    }

    @Override
    public void getInfo(Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        String result = "";
        result += "Tên đăng nhập: " + this.getUsername() + "\n";
        result += "Trạng thái: " + this.getStatus() + "\n";

        console.printResult("Thông tin nhân viên", result, sc);
    }

    public void addEmployee(Employee[] employees, Employee employee, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        String title = "Thêm nhân viên";

        for (int i = 0; i < employees.length; i++) {
            if (employees[i] == null) {
                employees[i] = employee;

                String result = "Đã thêm nhân viên: " + employee.getId()
                        + " - " + employee.getFirstName() + " " + employee.getLastName();
                console.printResult(title, result, sc);
                return;
            }
        }

        console.printResult(title, "Danh sách nhân viên đã đầy, không thể thêm.", sc);
    }

    public void addEmployeeAccount(Account[] accounts, Employee emp) {
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] == null) {
                EmployeeAccount acc = new EmployeeAccount(emp.getId(), DEFAULT_PASSWORD, Account.AccountStatus.ACTIVE);
                accounts[i] = acc;
            }
        }
    }

    public void listEmployee(Account[] accounts, Scanner sc) {
        System.out.println("Danh sách tài khoản:");
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] != null) {
                System.out.print((i + 1) + ". ");
                accounts[i].getInfo(sc);
            }
        }
    }

    public void removeEmployee(Employee[] employees, String id, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Xóa nhân viên";

        for (int i = 0; i < employees.length; i++) {
            if (employees[i] != null && employees[i].getId().equals(id)) {
                employees[i] = null;
                console.printResult(title, "Đã xóa tài khoản: " + id, sc);
                return;
            }
        }

        console.printResult(title, "Không tìm thấy nhân viên: " + id, sc);
    }

    public void removeAccount(Account[] accounts, String id, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Xóa tài khoản";

        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] != null && accounts[i].getUsername().equals(id)) {
                accounts[i] = null;
                console.printResult(title, "Đã xóa tài khoản: " + id, sc);
                return;
            }
        }

        console.printResult(title, "Không tìm thấy tài khoản: " + id, sc);
    }

    public void replyRequest(Account[] accounts, String username, String reply, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Phản hồi yêu cầu";

        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] != null && accounts[i].getUsername().equals(username)) {
                console.printResult(title, "Quản trị viên phan hoi " + username + ": " + reply, sc);
                return;
            }
        }
        console.printResult(title, "Không tìm thấy tài khoản: " + username, sc);
    }

    public void replyRequest(DataHandler data, Scanner sc) {
        AdminInbox.openInbox(data.getEmployees(), sc);
    }

    public Employee searchEmployee(Employee[] employees, String id) {
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] != null && employees[i].getId().equals(id)) {
                return employees[i];
            }
        }
        return null;
    }

    public String getPositions(Position[] positions) {
        final int TABLE_WIDTH = 109;
        final int NUMBER_WIDTH = 3;
        final int NAME_WIDTH = 30;
        final int POSITION_COEFFICIENT_WIDTH = 25;
        final int RESPONSIBILITY_WIDTH = TABLE_WIDTH - NUMBER_WIDTH - NAME_WIDTH - POSITION_COEFFICIENT_WIDTH -
                (3 * 2 - 2);

        int nameLeftPadding = (NAME_WIDTH - 4) / 2;
        int positionCoefficientLeftPadding = (POSITION_COEFFICIENT_WIDTH - 20) / 2;
        int responsibilityLeftPadding = (RESPONSIBILITY_WIDTH - 16) / 2;
        int nameRightPadding = NAME_WIDTH - nameLeftPadding - 4;
        int positionCoefficientRightPadding = POSITION_COEFFICIENT_WIDTH - positionCoefficientLeftPadding - 20;
        int responsibilityRightPadding = RESPONSIBILITY_WIDTH - responsibilityLeftPadding - 16;

        String header = "| " + " ".repeat(NUMBER_WIDTH) + " | " +
                " ".repeat(nameLeftPadding) + "Tên" + " ".repeat(nameRightPadding) + " | " +
                " ".repeat(positionCoefficientLeftPadding) + "Hệ số chức vụ"
                + " ".repeat(positionCoefficientRightPadding)
                + " | " +
                " ".repeat(responsibilityLeftPadding) + "Trách nhiệm" + " ".repeat(responsibilityRightPadding)
                + " |\n";

        String separateLine = "| " + "-".repeat(NUMBER_WIDTH) + " | " +
                "-".repeat(NAME_WIDTH) + " | " +
                "-".repeat(POSITION_COEFFICIENT_WIDTH) + " | " +
                "-".repeat(RESPONSIBILITY_WIDTH) + " |\n";

        String content = "";

        content += "Danh sach vi tri\n\n";
        content += header;
        content += separateLine;
        for (int i = 0; i < positions.length; i++) {
            if (positions[i] != null) {
                Position current = positions[i];

                int numberLength = Integer.toString(i).length();
                int nameLength = current.getName().length();
                int positionCoefficientLength = Double.toString(current.getPositionCoefficient()).length();
                int responsibilityLength = current.getResponsibilities().length();

                int numberPadding = NUMBER_WIDTH - numberLength;
                int namePadding = NAME_WIDTH - nameLength;
                int positionCoefficientPadding = POSITION_COEFFICIENT_WIDTH - positionCoefficientLength;
                int responsibilityPadding = RESPONSIBILITY_WIDTH - responsibilityLength;

                content += "| " + " ".repeat(numberPadding) + (i + 1) + " | " +
                        current.getName() + " ".repeat(namePadding) + " | " +
                        " ".repeat(positionCoefficientPadding) + current.getPositionCoefficient() + " | " +
                        current.getResponsibilities() + " ".repeat(responsibilityPadding) + " |\n";
            }
        }

        return content;
    }

    public String getDepartments(Department[] departments) {
        final int TABLE_WIDTH = 109;
        final int NUMBER_WIDTH = 3;
        final int NAME_WIDTH = 30;
        final int PHONE_WIDTH = 11;
        final int POSITION_WIDTH = TABLE_WIDTH - NUMBER_WIDTH - NAME_WIDTH - PHONE_WIDTH -
                (3 * 2 - 2);

        int nameLeftPadding = (NAME_WIDTH - 4) / 2;
        int phoneLeftPadding = (PHONE_WIDTH - 5) / 2;
        int positionLeftPadding = (POSITION_WIDTH - 8) / 2;
        int nameRightPadding = NAME_WIDTH - nameLeftPadding - 4;
        int phoneRightPadding = PHONE_WIDTH - phoneLeftPadding - 5;
        int positionRightPadding = POSITION_WIDTH - positionLeftPadding - 8;

        String header = "| " + " ".repeat(NUMBER_WIDTH) + " | " +
                " ".repeat(nameLeftPadding) + "Tên" + " ".repeat(nameRightPadding) + " | " +
                " ".repeat(phoneLeftPadding) + "Điện thoại" + " ".repeat(phoneRightPadding) + " | " +
                " ".repeat(positionLeftPadding) + "Chức vụ" + " ".repeat(positionRightPadding)
                + " |\n";

        String separateLine = "| " + "-".repeat(NUMBER_WIDTH) + " | " +
                "-".repeat(NAME_WIDTH) + " | " +
                "-".repeat(PHONE_WIDTH) + " | " +
                "-".repeat(POSITION_WIDTH) + " |\n";

        String content = "";

        content += "Danh sach phong ban\n\n";
        content += header;
        content += separateLine;
        for (int i = 0; i < departments.length; i++) {
            if (departments[i] != null) {
                Department current = departments[i];

                int numberLength = Integer.toString(i).length();
                int nameLength = current.getName().length();
                int phoneLength = current.getPhone().length();
                int positionLength = current.getPosition().length();

                int numberPadding = NUMBER_WIDTH - numberLength;
                int namePadding = NAME_WIDTH - nameLength;
                int phonePadding = PHONE_WIDTH - phoneLength;
                int positionPadding = POSITION_WIDTH - positionLength;

                content += "| " + " ".repeat(numberPadding) + (i + 1) + " | " +
                        current.getName() + " ".repeat(namePadding) + " | " +
                        " ".repeat(phonePadding) + current.getPhone() + " | " +
                        current.getPosition() + " ".repeat(positionPadding) + " |\n";
            }
        }

        return content;
    }

    private void editEmployeeHireDate(Scanner sc, Employee employee) {
        ConsoleUI console = new ConsoleUI();

        String title = "Sửa nhân viên";
        String subtitle = "Sửa ngày tuyển dụng";
        String content = "Ngày tuyển dụng hiện tại: " + employee.getHireDate();
        String input;

        input = "Ngày tuyển dụng (dd/MM/yyyy)";
        LocalDate hireDate = console.printDateUI(title, subtitle, content, input, sc);
        employee.setHireDate(hireDate);

        console.printResult(title, "Đặt ngày tuyển dụng thành công", sc);
    }

    private void editEmployeeSpecialization(Scanner sc, Employee employee) {
        ConsoleUI console = new ConsoleUI();

        String title = "Sửa nhân viên";
        String subtitle = "Đặt chuyên môn";
        String content = "Chuyên môn hiện tại: " + employee.getSpecialization();

        String input = "Chuyên môn";
        employee.setSpecialization(console.printUI(title, subtitle, content, input, sc));

        console.printResult(title, "Đặt chuyên môn thành công", sc);
    }

    private void editEmployeeStatus(Scanner sc, Employee employee) {
        ConsoleUI console = new ConsoleUI();

        String title = "Sửa nhân viên";
        String subtitle = "Đặt trạng thái";
        String content = "Trạng thái hiện tại: " + employee.getStatus();

        int statusNum = 0;
        do {
            String[] options = { "Đang làm việc", "Đang nghỉ phép", "Đã nghỉ việc" };
            statusNum = Integer.parseInt(console.printUI(title, subtitle, content, options, sc));
        } while (statusNum < 0 || 2 < statusNum);

        switch (statusNum) {
            case 1:
                employee.setStatus(Employee.EmployeeStatus.WORKING);
                break;
            case 2:
                employee.setStatus(Employee.EmployeeStatus.ON_LEAVE);
                break;
            case 3:
                employee.setStatus(Employee.EmployeeStatus.RESIGNED);
                break;

            default:
                break;
        }

        console.printResult(title, "Đặt trạng thái thành công", sc);
    }

    private void editEmployeeTaxId(Scanner sc, Employee employee) {
        ConsoleUI console = new ConsoleUI();

        String title = "Sửa nhân viên";
        String subtitle = "Đặt mã số thuế";
        String content = "Mã số thuế hiện tại: " + employee.getTaxId();

        String input = "Mã số thuế";
        employee.setTaxId(console.printUI(title, subtitle, content, input, sc));

        console.printResult(title, "Đặt mã số thuế thành công", sc);
    }

    private void editEmployeePosition(Scanner sc, Employee employee, Position[] positions) {
        ConsoleUI console = new ConsoleUI();

        String title = "Sửa nhân viên";
        String subtitle = "Đặt chức vụ";
        String content = "Chức vụ hiện tại: " + employee.getPosition().getName() + "\n";

        int choice;
        do {
            content += getPositions(positions);
            choice = Integer.parseInt(console.printUI(title, subtitle, content, sc));

            if (positions[choice - 1] == null) {
                console.printResult(title, "Chức vụ không tồn tại", sc);
            }
        } while (positions[choice - 1] == null);

        console.printResult(title, "Đặt chức vụ thành công", sc);
    }

    private void editEmployeeDepartment(Scanner sc, Employee employee, Department[] departments) {
        ConsoleUI console = new ConsoleUI();

        String title = "Sửa nhân viên";
        String subtitle = "Đặt phòng ban";
        String content = "Phòng ban hiện tại: " + employee.getDepartment().getName() + "\n";

        int choice;
        do {
            content += getDepartments(departments);
            choice = Integer.parseInt(console.printUI(title, subtitle, content, sc));

            if (departments[choice - 1] == null) {
                console.printResult(title, "Phòng ban không tồn tại", sc);
            }
        } while (departments[choice - 1] == null);

        console.printResult(title, "Đặt phòng ban thành công", sc);
    }

    public void editEmployee(Scanner sc, Employee[] employees, String id, Position[] positions,
            Department[] departments) {
        ConsoleUI console = new ConsoleUI();
        String title = "Sửa nhân viên";
        String subtitle = "Chọn một tùy chọn";

        Employee employee = this.searchEmployee(employees, id);
        if (employee == null) {
            console.printResult(title, "Không tìm thấy nhân viên: " + id, sc);
            return;
        }

        int setChoice;
        do {
            String[] options = { "Thoát", "Ngày tuyển dụng", "Chuyên môn", "Trạng thái", "Mã số thuế", "Chức vụ",
                    "Phòng ban" };
            setChoice = Integer.parseInt(console.printUI(title, subtitle, options, sc));

            switch (setChoice) {
                case 0:
                    break;
                case 1:
                    editEmployeeHireDate(sc, employee);
                    break;
                case 2:
                    editEmployeeSpecialization(sc, employee);
                    break;
                case 3:
                    editEmployeeStatus(sc, employee);
                    break;
                case 4:
                    editEmployeeTaxId(sc, employee);
                    break;
                case 5:
                    editEmployeePosition(sc, employee, positions);
                    break;
                case 6:
                    editEmployeeDepartment(sc, employee, departments);
                    break;

                default:
                    console.printResult(title, "Lựa chọn không hợp lệ", sc);
                    break;
            }
        } while (setChoice != 0);

        console.printResult(title, "Đã sửa nhân viên: " + id, sc);
    }

    public void printEmployees(DataHandler data, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        SortData sort = new SortData();
        String title = "In nhân viên";
        String subtitle = "Chọn một tùy chọn";

        Employee[] currEmployees = data.getEmployees();

        int sortBy;
        do {
            String[] options = { "Id", "Ngày tuyển dụng", "Chuyên môn", "Trạng thái", "Mã số thuế", "Chức vụ",
                    "Phòng ban" };
            sortBy = validateInt(console.printUI(title, subtitle, options, sc));

            if (sortBy < 0 || 6 < sortBy) {
                console.printResult(title, "Lựa chọn không hợp lệ", sc);
            }
        } while (sortBy < 0 || 6 < sortBy);

        boolean isAscending = askSortOrder("Sắp xếp tăng dần", sc);

        switch (sortBy) {
            case 0:
                sort.sortById(currEmployees, isAscending);
                break;
            case 1:
                sort.sortByHireDate(currEmployees, isAscending);
                break;
            case 2:
                sort.sortBySpecialization(currEmployees, isAscending);
                break;
            case 3:
                sort.sortByStatus(currEmployees, isAscending);
                break;
            case 4:
                sort.sortByTaxId(currEmployees, isAscending);
                break;
            case 5:
                sort.sortByPosition(currEmployees, isAscending);
                break;
            case 6:
                sort.sortByDepartment(currEmployees, isAscending);
                break;

            default:
                break;
        }

        PrintTables.printEmployees(currEmployees, sc);
    }

    public void printDepartments(DataHandler data, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        SortData sort = new SortData();
        String title = "In phòng ban";
        String subtitle = "Chọn một tùy chọn";

        Department[] currDepartments = data.getDepartments();

        int sortBy;
        do {
            String[] options = { "Tên", "Điện thoại", "Chức vụ" };
            sortBy = validateInt(console.printUI(title, subtitle, options, sc));

            if (sortBy < 0 || 2 < sortBy) {
                console.printResult(title, "Lựa chọn không hợp lệ", sc);
            }
        } while (sortBy < 0 || 2 < sortBy);

        boolean isAscending = askSortOrder("Sắp xếp tăng dần", sc);

        switch (sortBy) {
            case 0:
                sort.sortByName(currDepartments, isAscending);
                break;
            case 1:
                sort.sortByPhone(currDepartments, isAscending);
                break;
            case 2:
                sort.sortByPosition(currDepartments, isAscending);
                break;

            default:
                break;
        }

        PrintTables.printDepartments(currDepartments, sc);
    }

    public void printPositions(DataHandler data, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        SortData sort = new SortData();
        String title = "In chức vụ";
        String subtitle = "Chọn một tùy chọn";

        Position[] currPositions = data.getPositions();

        int sortBy;
        do {
            String[] options = { "Tên", "Điện thoại", "Chức vụ" };
            sortBy = validateInt(console.printUI(title, subtitle, options, sc));

            if (sortBy < 0 || 2 < sortBy) {
                console.printResult(title, "Lựa chọn không hợp lệ", sc);
            }
        } while (sortBy < 0 || 2 < sortBy);

        boolean isAscending = askSortOrder("Sắp xếp tăng dần", sc);

        switch (sortBy) {
            case 0:
                sort.sortByName(currPositions, isAscending);
                break;
            case 1:
                sort.sortByCoefficient(currPositions, isAscending);
                break;
            case 2:
                sort.sortByResponsibilities(currPositions, isAscending);
                break;

            default:
                break;
        }

        PrintTables.printPositions(currPositions, sc);
    }

    public void searchEmployee(Employee[] employees, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        String title = "Tìm nhân viên";
        String subtitle = "";
        String content = "";
        String input = "Id";

        String id = console.printUI(title, subtitle, content, input, sc).toLowerCase();

        boolean found = false;
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] != null && employees[i].getId().toLowerCase().equals(id)) {
                content = employees[i].getInfo(sc);
                found = true;
            }
        }

        if (found) {
            console.printResult(title, content, sc);
        } else {
            console.printResult(title, "Nhân viên không tồn tại", sc);
        }
    }

    public void searchPosition(Position[] positions, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        String title = "Tìm chức vụ";
        String input = "Tên chức vụ";

        String name = console.printUI(title, "", "", input, sc).trim();
        String content = "";
        boolean found = false;

        for (int i = 0; i < positions.length; i++) {
            Position position = positions[i];

            if (position != null && position.getName() != null
                    && position.getName().equalsIgnoreCase(name)) {
                content += "Tên chức vụ: " + position.getName() + "\n";
                content += "Hệ số chức vụ: "
                        + position.getPositionCoefficient() + "\n";
                content += "Trách nhiệm: "
                        + position.getResponsibilities() + "\n";
                found = true;
                break;
            }
        }

        if (found) {
            console.printResult(title, content, sc);
        } else {
            console.printResult(title, "Chức vụ không tồn tại", sc);
        }
    }

    public void searchDepartment(Department[] departments, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        String title = "Tìm phòng ban";
        String subtitle = "";
        String content = "";
        String input = "Id";

        String name = console.printUI(title, subtitle, content, input, sc).toLowerCase();

        boolean found = false;
        for (int i = 0; i < departments.length; i++) {
            if (departments[i] != null && departments[i].getName().toLowerCase().equals(name)) {
                content = departments[i].getInfo(sc);
                found = true;
            }
        }

        if (found) {
            console.printResult(title, content, sc);
        } else {
            console.printResult(title, "Phòng ban không tồn tại", sc);
        }
    }

    public Account createAccount(Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        String title = "Tạo tài khoản";
        String subtitle = "";
        String content = "";
        String input;

        input = "Tên đăng nhập";
        String username = console.printUI(title, subtitle, content, input, sc);

        input = "Mật khẩu";
        String password = console.printUI(title, subtitle, content, input, sc);

        int statusNum = 0;
        Account.AccountStatus status = Account.AccountStatus.ACTIVE;
        do {
            String[] options = { "Hoạt động", "Không hoạt động", "Đình chỉ", "Chờ xử lý", "Đã khóa", "Đã chấm dứt" };
            statusNum = Integer.parseInt(console.printUI(title, subtitle, options, input, sc));
        } while (statusNum < 0 || 5 < statusNum);

        switch (statusNum) {
            case 0:
                status = Account.AccountStatus.ACTIVE;
                break;
            case 1:
                status = Account.AccountStatus.INACTIVE;
                break;
            case 2:
                status = Account.AccountStatus.SUSPENDED;
                break;
            case 3:
                status = Account.AccountStatus.PENDING;
                break;
            case 4:
                status = Account.AccountStatus.LOCKED;
                break;
            case 5:
                status = Account.AccountStatus.TERMINATED;
                break;

            default:
                break;
        }

        int type;
        do {
            String[] options = { "Quản trị viên", "Nhân viên" };
            type = Integer.parseInt(console.printUI(title, subtitle, options, input, sc));
        } while (type != 0 && type != 1);

        if (type == 0) {
            return new AdminAccount(username, password, status);
        } else {
            return new EmployeeAccount(username, password, status);
        }
    }

    public Department createDepartment(Scanner sc, Department[] departments) {
        ConsoleUI console = new ConsoleUI();
        String title = "Tạo phòng ban";
        String subtitle = "";
        String content = "";

        String input = "Tên";
        String name = console.printUI(title, subtitle, subtitle, input, sc);
        content += "Tên: " + name + "\n";

        input = "Điện thoại";
        String phone = console.printUI(title, subtitle, content, input, sc);
        content += "Điện thoại: " + phone + "\n";

        input = "Chức vụ";
        String position = console.printUI(title, subtitle, content, input, sc);
        content += "Chức vụ: " + position + "\n";

        console.printResult(title, content, sc);

        return new Department(name, phone, position);
    }

    public Position createPosition(Scanner sc, Position[] positions) {
        ConsoleUI console = new ConsoleUI();
        String title = "Tạo chức vụ";
        String subtitle = "";
        String content = "";

        String input = "Tên";
        String name = console.printUI(title, subtitle, subtitle, input, sc);
        content += "Tên: " + name + "\n";

        input = "Hệ số chức vụ";
        double positionCoefficient = Double.parseDouble(console.printUI(title, subtitle, content, input, sc));
        content += "Hệ số chức vụ: " + positionCoefficient + "\n";

        input = "Trách nhiệm";
        String responsibilities = console.printUI(title, subtitle, content, input, sc);
        content += "Trách nhiệm: " + responsibilities + "\n";

        console.printResult(title, content, sc);

        return new Position(responsibilities, name, positionCoefficient);
    }

    public Address createAddress(Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Tạo nhân viên";
        String subtitle = "Tạo địa chỉ";
        String[] options = {};
        String input;

        input = "Số";
        String number = console.printUI(title, subtitle, options, input, sc);

        input = "Đường";
        String street = console.printUI(title, subtitle, options, input, sc);

        input = "Phường";
        String ward = console.printUI(title, subtitle, options, input, sc);

        input = "Quận/Huyện";
        String district = console.printUI(title, subtitle, options, input, sc);

        input = "Thành phố";
        String city = console.printUI(title, subtitle, options, input, sc);

        return new Address(number, street, ward, district, city);
    }

    public Evaluation createEvaluation(Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Tạo nhân viên";
        String subtitle = "Tạo đánh giá";
        String[] options = {};
        String input;

        input = "Tháng (MM/yyyy)";
        YearMonth month = YearMonth.parse(
                console.printUI(title, subtitle, options, input, sc).trim(),
                yearMonthFormatter);

        input = "Nhận xét";
        String comment = console.printUI(title, subtitle, options, input, sc);

        input = "Điểm công việc";
        double workScore = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

        input = "Attitude score";
        double attitudeScore = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

        input = "Điểm kỷ luật";
        double disciplineScore = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

        input = "Chất lượng công việc";
        double workQuality = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

        input = "Năng suất";
        double productivity = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

        input = "Làm việc nhóm";
        double teamWork = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

        return new Evaluation(month, comment, workScore, attitudeScore, disciplineScore, workQuality, productivity,
                teamWork);
    }

    public Contract createContract(Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Tạo nhân viên";
        String subtitle = "Tạo hợp đồng";
        String[] options = {};
        String input;

        LocalDate startDate;
        LocalDate endDate;
        do {
            input = "Ngày bắt đầu: ";
            startDate = LocalDate.parse(console.printUI(title, subtitle, options, input, sc), dateFormatter);

            input = "Ngày kết thúc: ";
            endDate = LocalDate.parse(console.printUI(title, subtitle, options, input, sc), dateFormatter);

            if (startDate.isAfter(endDate)) {
                System.out.println("Ngày bắt đầu nên trước ngày kết thúc");
            }
        } while (startDate.isBefore(endDate));

        int typeNum = 0;
        Contract.ContractType type = Contract.ContractType.TEMPORARY;
        do {
            String[] typeOptions = { "Thử việc", "Toàn thời gian", "Bán thời gian", "Tạm thời", "Có thời hạn",
                    "Permanent" };
            typeNum = validateInt(console.printUI(title, subtitle, typeOptions, input, sc));

            if (typeNum < 0 || 5 < typeNum) {
                console.printResult(title, "Lựa chọn không hợp lệ", sc);
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
            String[] statusOptions = { "Bản nháp", "Hoạt động", "Hết hạn" };
            statusNum = validateInt(console.printUI(title, subtitle, statusOptions, input, sc));

            if (statusNum < 0 || 2 < statusNum) {
                console.printResult(title, "Lựa chọn không hợp lệ", sc);
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

    public Payroll createPayroll(Scanner sc) {
        DataCheck checker = new DataCheck("^(\\d{2})/(\\d{4})$");
        ConsoleUI console = new ConsoleUI();

        String title = "Tạo nhân viên";
        String subtitle = "Tạo bảng lương";
        String content = "";
        String[] options = {};
        String input;

        input = "Tháng (MM/yyyy)";
        String monthStr = console.printUI(title, subtitle, content, input, sc).trim();
        while (!checker.isValid(monthStr)) {
            console.printResult(title, "Dữ liệu không hợp lệ", sc);
            monthStr = console.printUI(title, subtitle, content, input, sc).trim();
        }
        YearMonth month = YearMonth.parse(monthStr, yearMonthFormatter);

        input = "Lương cơ bản";
        double baseSalary = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

        input = "Thưởng";
        double bonus = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

        input = "Phụ cấp";
        double allowance = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

        input = "Khấu trừ";
        double deduction = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

        input = "Thuế";
        double taxAmount = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

        input = "Số tiền bảo hiểm";
        double insuranceAmount = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

        input = "Số giờ làm việc";
        int workedHours = validateInt(console.printUI(title, subtitle, options, input, sc));

        input = "Số giờ tăng ca";
        int overtimeHours = validateInt(console.printUI(title, subtitle, options, input, sc));

        input = "Ngày tăng lương gần nhất (dd/MM/yyyy)";
        LocalDate lastSalaryIncreaseDate = console.printDateUI(title, subtitle, options, input, sc);

        return new Payroll(month, baseSalary, bonus, allowance, deduction, taxAmount, insuranceAmount,
                workedHours,
                overtimeHours, lastSalaryIncreaseDate);
    }

    public Attendance createAttendance(Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Tạo nhân viên";
        String subtitle = "Tạo chấm công";
        String[] options = {};
        String input;

        input = "Ngày (dd/MM/yyyy): ";
        LocalDate date = console.printDateUI(title, subtitle, options, input, sc);

        LocalTime clockIn;
        LocalTime clockOut;
        do {
            input = "Giờ vào (HH:mm): ";
            clockIn = LocalTime.parse(console.printUI(title, subtitle, options, input, sc), timeFormatter);

            input = "Giờ ra (HH:mm): ";
            clockOut = LocalTime.parse(console.printUI(title, subtitle, options, input, sc), timeFormatter);

            if (clockIn.isBefore(clockOut)) {
                console.printResult(title, "Giờ vào nên trước giờ ra", sc);
            }
        } while (clockIn.isBefore(clockOut));

        return new Attendance(date, clockIn, clockOut);
    }

    public Insurance createInsurance(Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Tạo nhân viên";
        String subtitle = "Tạo bảo hiểm";
        String[] options = {};
        String input;

        input = "Số bảo hiểm: ";
        String insuranceNumber = console.printUI(title, subtitle, options, input, sc);

        int typeNum;
        Insurance.InsuranceType type = Insurance.InsuranceType.HEALTH;
        do {
            String[] typeOptions = { "Nhân thọ", "Sức khỏe", "Xã hội", "Thất nghiệp" };
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
        boolean valid;
        do {
            input = "Ngày bắt đầu (dd/MM/yyyy): ";
            startDate = console.printDateUI(title, subtitle, options, input, sc);

            input = "Ngày kết thúc (dd/MM/yyyy): ";
            endDate = console.printDateUI(title, subtitle, options, input, sc);

            valid = !startDate.isAfter(endDate);
            if (!valid) {
                console.printResult(title, "Ngày bắt đầu nên trước ngày kết thúc", sc);
            }
        } while (!valid);

        input = "Mức: ";
        double rate = Double.parseDouble(console.printUI(title, subtitle, options, input, sc).trim());

        return new Insurance(insuranceNumber, type, startDate, endDate, rate);
    }

    public Employee.EmployeeStatus chooseEmployeeStatus(Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Tạo nhân viên";
        String subtitle = "";
        String[] options = { "Đang làm việc", "Đang nghỉ phép", "Đã nghỉ việc" };

        int statusChoice;
        do {
            statusChoice = validateInt(console.printUI(title, subtitle, options, sc));

            if (statusChoice < 0 || 2 < statusChoice) {
                console.printResult(title, "Lựa chọn không hợp lệ", sc);
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

    public Evaluation[] createEvaluations(LimitHandler limits, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Tạo nhân viên";
        String subtitle = "Tạo đánh giá";
        String[] options = { "Thoát", "Tạo đánh giá" };

        int index = 0;
        Evaluation[] evaluations = new Evaluation[limits.getMaxEvaluations()];

        int choice;
        do {
            choice = validateInt(console.printUI(title, subtitle, options, sc));

            if (choice < 0 || 1 < choice) {
                console.printResult(title, "Lựa chọn không hợp lệ", sc);
                continue;
            }

            if (choice == 0) {
                break;
            }

            if (index + 1 < limits.getMaxEvaluations()) {
                String result = "Evaluation storage is full\n" + "Tối đa: %d" + limits.getMaxEvaluations();
                console.printResult(title, result, sc);
            }

            evaluations[index] = createEvaluation(sc);
            index++;
        } while (choice < 0 || 1 < choice);

        return evaluations;
    }

    public Contract[] createContracts(LimitHandler limits, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Tạo nhân viên";
        String subtitle = "Tạo hợp đồng";
        String[] options = { "Thoát", "Tạo hợp đồng" };

        int index = 0;
        Contract[] contracts = new Contract[limits.getMaxContracts()];

        int choice;
        do {
            choice = validateInt(console.printUI(title, subtitle, options, sc));

            if (choice < 0 || 1 < choice) {
                console.printResult(title, "Lựa chọn không hợp lệ", sc);
                continue;
            }

            if (choice == 0) {
                break;
            }

            if (index + 1 < limits.getMaxContracts()) {
                String result = "Contracts storage is full\n" + "Tối đa: %d" + limits.getMaxContracts();
                console.printResult(title, result, sc);
            }

            contracts[index] = createContract(sc);
            index++;
        } while (choice < 0 || 1 < choice);

        return contracts;
    }

    public Payroll[] createPayrolls(LimitHandler limits, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Tạo nhân viên";
        String subtitle = "Tạo bảng lương";
        String[] options = { "Thoát", "Tạo bảng lương" };

        int index = 0;
        Payroll[] payrolls = new Payroll[limits.getMaxPayrolls()];

        int choice;
        do {
            choice = validateInt(console.printUI(title, subtitle, options, sc));

            if (choice < 0 || 1 < choice) {
                console.printResult(title, "Lựa chọn không hợp lệ", sc);
                continue;
            }

            if (choice == 0) {
                break;
            }

            if (index + 1 < limits.getMaxPayrolls()) {
                String result = "Payrolls storage is full\n" + "Tối đa: %d" + limits.getMaxPayrolls();
                console.printResult(title, result, sc);
            }

            payrolls[index] = createPayroll(sc);
            index++;
        } while (choice < 0 || 1 < choice);

        return payrolls;
    }

    public Attendance[] createAttendances(LimitHandler limits, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Tạo nhân viên";
        String subtitle = "Tạo danh sách chấm công";
        String[] options = { "Thoát", "Tạo chấm công" };

        int index = 0;
        Attendance[] attendances = new Attendance[limits.getMaxAttendances()];

        int choice;
        do {
            choice = validateInt(console.printUI(title, subtitle, options, sc));

            if (choice < 0 || 1 < choice) {
                console.printResult(title, "Lựa chọn không hợp lệ", sc);
                continue;
            }

            if (choice == 0) {
                break;
            }

            if (index + 1 < limits.getMaxAttendances()) {
                String result = "Danh sách chấm công đã đầy\n" + "Tối đa: %d" + limits.getMaxAttendances();
                console.printResult(title, result, sc);
            }

            attendances[index] = createAttendance(sc);
            index++;
        } while (choice < 0 || 1 < choice);

        return attendances;
    }

    public Resignation createResignation(Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Tạo nhân viên";
        String subtitle = "Tạo đơn thôi việc";
        String[] options = {};
        String content = "";
        String input;

        input = "Lý do: ";
        String reason = console.printUI(title, subtitle, content, input, sc);

        input = "Ngày (dd/MM/yyyy): ";
        LocalDate date = console.printDateUI(title, subtitle, options, input, sc);

        int statusNum;
        do {
            String[] statusOptions = { "Chờ xử lý", "Từ chối", "Đã duyệt" };
            statusNum = validateInt(console.printUI(title, subtitle, statusOptions, sc));

            if (statusNum < 0 || 2 < statusNum) {
                console.printResult(title, "Lựa chọn không hợp lệ", sc);
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

    public Resignation[] createResignations(LimitHandler limits, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Tạo nhân viên";
        String subtitle = "Tạo đơn thôi việc";
        String[] options = { "Thoát", "Tạo đơn thôi việc" };

        int index = 0;
        Resignation[] resignations = new Resignation[limits.getMaxResignations()];

        int choice;
        do {
            choice = validateInt(console.printUI(title, subtitle, options, sc));

            if (choice < 0 || 1 < choice) {
                console.printResult(title, "Lựa chọn không hợp lệ", sc);
                continue;
            }

            if (choice == 0) {
                break;
            }

            if (index + 1 < limits.getMaxResignations()) {
                String result = "Resignation storage is full\n" + "Tối đa: %d" + limits.getMaxResignations();
                console.printResult(title, result, sc);
            }

            resignations[index] = createResignation(sc);
            index++;
        } while (choice < 0 || 1 < choice);

        return resignations;
    }

    public Insurance[] createInsurances(LimitHandler limits, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Tạo nhân viên";
        String subtitle = "Tạo danh sách bảo hiểm";
        String[] options = { "Thoát", "Tạo bảo hiểm" };

        int index = 0;
        Insurance[] insurances = new Insurance[limits.getMaxInsurances()];

        int choice;
        do {
            choice = validateInt(console.printUI(title, subtitle, options, sc));

            if (choice < 0 || 1 < choice) {
                console.printResult(title, "Lựa chọn không hợp lệ", sc);
                continue;
            }

            if (choice == 0) {
                break;
            }

            if (index + 1 < limits.getMaxInsurances()) {
                String result = "Danh sách bảo hiểm đã đầy\n" + "Tối đa: %d" + limits.getMaxInsurances();
                console.printResult(title, result, sc);
            }

            insurances[index] = createInsurance(sc);
            index++;
        } while (choice < 0 || 1 < choice);

        return insurances;
    }

    public Position choosePosition(DataHandler data, LimitHandler limits, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Tạo nhân viên";
        String subtitle = "Chọn chức vụ";
        String[] options = { "Thoát", "Chọn chức vụ" };

        int positionsOccupied = 0;
        for (int i = 0; i < data.getPositions().length; i++) {
            if (data.getPositions()[i] != null) {
                positionsOccupied++;
            }
        }

        if (positionsOccupied < 0) {
            console.printResult(title, "Không có chức vụ nào", sc);
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
                console.printResult(title, "Lựa chọn không hợp lệ", sc);
            }
        } while (index < 0 || index > positionsOccupied);

        return data.getPositions()[index];
    }

    public Department chooseDepartment(DataHandler data, LimitHandler limits, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Tạo nhân viên";
        String subtitle = "Chọn phòng ban";
        String[] options = { "Thoát", "Chọn phòng ban" };

        int departmentsOccupied = 0;
        for (int i = 0; i < data.getDepartments().length; i++) {
            if (data.getDepartments()[i] != null) {
                departmentsOccupied++;
            }
        }

        if (departmentsOccupied < 0) {
            console.printResult(title, "Không có phòng ban nào", sc);
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
                console.printResult(title, "Lựa chọn không hợp lệ", sc);
            }
        } while (index < 0 || index > departmentsOccupied);

        return data.getDepartments()[index];
    }

    public Employee createEmployee(DataHandler data, LimitHandler limits, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Tạo nhân viên";
        String subtitle = "";
        String[] options = {};
        String input;

        input = "Tên";
        String firstName = console.printUI(title, subtitle, options, input, sc);

        input = "Họ";
        String lastName = console.printUI(title, subtitle, options, input, sc);

        input = "Email";
        String email = console.printUI(title, subtitle, options, input, sc);

        input = "Số điện thoại";
        String phone = console.printUI(title, subtitle, options, input, sc);

        input = "Ngày sinh (dd/MM/yyyy)";
        LocalDate dateOfBirth = console.printDateUI(title, subtitle, options, input, sc);

        input = "Giới tính";
        String gender = console.printUI(title, subtitle, options, input, sc);

        input = "Địa chỉ";
        Address address = createAddress(sc);

        input = "Id";
        String id = console.printUI(title, subtitle, options, input, sc);

        input = "Ngày tuyển dụng (dd/MM/yyyy)";
        LocalDate hireDate = console.printDateUI(title, subtitle, options, input, sc);

        input = "Chuyên môn";
        String specialization = console.printUI(title, subtitle, options, input, sc);

        Employee.EmployeeStatus status = chooseEmployeeStatus(sc);

        Evaluation[] evaluations = createEvaluations(limits, sc);
        Contract[] contracts = createContracts(limits, sc);
        Payroll[] payrolls = createPayrolls(limits, sc);
        Attendance[] attendances = createAttendances(limits, sc);
        Resignation[] resignations = createResignations(limits, sc);

        input = "Mã số thuế";
        String taxId = console.printUI(title, subtitle, options, input, sc);

        Insurance[] insurances = createInsurances(limits, sc);

        Position position = choosePosition(data, limits, sc);
        Department department = chooseDepartment(data, limits, sc);

        return new Employee(firstName, lastName, email, phone, dateOfBirth, gender, address, id, hireDate,
                specialization,
                status, evaluations, contracts, payrolls, attendances, resignations, taxId, insurances, position,
                department);
    }

    private Employee selectEmployeePrompt(Employee[] employees, String title, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        String content = "Nhân viêns list\n";

        for (int i = 0; i < employees.length; i++) {
            if (employees[i] != null) {
                content += "- ID: " + employees[i].getId() + " | "
                        + employees[i].getFirstName() + " "
                        + employees[i].getLastName() + "\n";
            }
        }

        if (content.equals("List Nhân viên:\n")) {
            console.printResult(title, "Danh sách nhân viên trống!", sc);
            return null;
        }

        String inputField = "Nhập nhân viên (Mã)";
        String employeeId = console.printUI(title, "Nhân viên đã tuyển", content, inputField, sc);

        Employee employee = searchEmployee(employees, employeeId);
        return employee;
    }

    public void addEvaluation(Employee[] employees, Scanner sc) {
        DataCheck checker = new DataCheck("^(\\d{2})/(\\d{4})$");
        ConsoleUI console = new ConsoleUI();
        String title = "Thêm đánh giá";

        Employee employee = selectEmployeePrompt(employees, title, sc);
        if (employee == null) {
            console.printResult(title, "Không tìm thấy nhân viên!", sc);
            return;
        }

        Evaluation[] evals = employee.getEvaluations();
        int index = -1;
        for (int i = 0; i < evals.length; i++) {
            if (evals[i] == null) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            console.printResult(title, "Danh sách đánh giá đã đầy!", sc);
            return;
        }

        String subtitle = "Nhập thông tin đánh giá cho nhân viên " + employee.getId();
        String content = "";

        String monthStr = console.printUI(title, subtitle, content, "Tháng đánh giá (MM/yyyy)", sc).trim();

        if (!checker.isValid(monthStr)) {
            console.printResult(title, "Dữ liệu không hợp lệ", sc);
            return;
        }

        YearMonth month = YearMonth.parse(monthStr, yearMonthFormatter);

        String comment = console.printUI(title, subtitle, content, "Nhận xét đánh giá", sc).trim();

        String[] scoreNames = { "Điểm công việc (0-10)", "Attitude score (0-10)", "Điểm kỷ luật (0-10)",
                "Chất lượng công việc (0-10)", "Năng suất (0-10)", "Làm việc nhóm (0-10)" };
        double[] scores = new double[scoreNames.length];
        for (int i = 0; i < scoreNames.length; i++) {
            do {
                try {
                    scores[i] = Double
                            .parseDouble(console.printUI(title, subtitle, content, scoreNames[i], sc).trim());
                } catch (NumberFormatException e) {
                    scores[i] = -1;
                }
                if (!(scores[i] >= 0 && scores[i] <= 10)) {
                    console.printResult(title, "Điểm phải là số từ 0 đến 10!", sc);
                }
            } while (!(scores[i] >= 0 && scores[i] <= 10));
        }

        evals[index] = new Evaluation(month, comment, scores[0], scores[1], scores[2], scores[3], scores[4],
                scores[5]);
        console.printResult(title, "Thêm đánh giá thành công!", sc);
    }

    public void addContract(Employee[] employees, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        String title = "Thêm hợp đồng";

        Employee employee = selectEmployeePrompt(employees, title, sc);
        if (employee == null) {
            console.printResult(title, "Không tìm thấy nhân viên", sc);
            return;
        }

        Contract[] contracts = employee.getContracts();
        int index = -1;
        for (int i = 0; i < contracts.length; i++) {
            if (contracts[i] == null) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            console.printResult(title, "Danh sách hợp đồng đã đầy", sc);
            return;
        }

        String subtitle = "Hợp đồng của nhân viên " + employee.getId();
        String content = "";

        try {
            LocalDate startDate;
            LocalDate endDate;
            do {
                startDate = console.printDateUI(title, subtitle, content, "Ngày bắt đầu (dd/MM/yyyy)", sc);
                endDate = console.printDateUI(title, subtitle, content, "Ngày kết thúc (dd/MM/yyyy)", sc);

                if (startDate.isAfter(endDate)) {
                    console.printResult(title, "Ngày bắt đầu phải trước ngày kết thúc", sc);
                }
            } while (startDate.isAfter(endDate));

            String[] typeNames = { "Thử việc", "Toàn thời gian", "Bán thời gian", "Tạm thời", "Có thời hạn",
                    "Permanent" };
            Contract.ContractType[] types = {
                    Contract.ContractType.PROBATION,
                    Contract.ContractType.FULL_TIME,
                    Contract.ContractType.PART_TIME,
                    Contract.ContractType.TEMPORARY,
                    Contract.ContractType.FIXED_TERM,
                    Contract.ContractType.PERMANENT };
            int typeChoice;
            do {
                typeChoice = Integer.parseInt(console.printUI(title, subtitle, typeNames, sc).trim());
                if (typeChoice < 0 || typeChoice >= typeNames.length) {
                    console.printResult(title, "Lựa chọn không hợp lệ", sc);
                }
            } while (typeChoice < 0 || typeChoice >= typeNames.length);

            String[] statusNames = { "Bản nháp", "Hoạt động", "Hết hạn", "Đã chấm dứt" };
            Contract.ContractStatus[] statuses = {
                    Contract.ContractStatus.DRAFT,
                    Contract.ContractStatus.ACTIVE,
                    Contract.ContractStatus.EXPIRED,
                    Contract.ContractStatus.TERMINATED };
            int statusChoice;
            do {
                statusChoice = Integer.parseInt(console.printUI(title, subtitle, statusNames, sc).trim());
                if (statusChoice < 0 || statusChoice >= statusNames.length) {
                    console.printResult(title, "Lựa chọn không hợp lệ", sc);
                }
            } while (statusChoice < 0 || statusChoice >= statusNames.length);

            contracts[index] = new Contract(startDate, endDate, types[typeChoice], statuses[statusChoice]);
            console.printResult(title, "Thêm hợp đồng thành công", sc);
        } catch (NumberFormatException e) {
            console.printResult(title, "Vui lòng nhập số cho lựa chọn!", sc);
        }
    }

    public void addPayroll(Employee[] employees, Scanner sc) {
        DataCheck checker = new DataCheck("^(\\d{2})/(\\d{4})$");
        ConsoleUI console = new ConsoleUI();
        String title = "Thêm bảng lương";

        Employee employee = selectEmployeePrompt(employees, title, sc);
        if (employee == null) {
            console.printResult(title, "Không tìm thấy nhân viên", sc);
            return;
        }

        Payroll[] payrolls = employee.getPayrolls();
        int index = -1;
        for (int i = 0; i < payrolls.length; i++) {
            if (payrolls[i] == null) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            console.printResult(title, "Danh sách bảng lương đã đầy", sc);
            return;
        }

        String subtitle = "Bảng lương của nhân viên " + employee.getId();
        String content = "";

        String input = "Tháng (MM/yyyy)";
        String monthStr = console.printUI(title, subtitle, content, input, sc).trim();
        while (!checker.isValid(monthStr)) {
            console.printResult(title, "Dữ liệu không hợp lệ", sc);
            monthStr = console.printUI(title, subtitle, content, input, sc).trim();
        }
        YearMonth month = YearMonth.parse(monthStr, yearMonthFormatter);

        try {
            for (int i = 0; i < payrolls.length; i++) {
                if (payrolls[i] != null && payrolls[i].getMonth().equals(month)) {
                    console.printResult(title, "Bảng lương tháng này đã tồn tại", sc);
                    return;
                }
            }

            double baseSalary = Double
                    .parseDouble(console.printUI(title, subtitle, content, "Lương cơ bản", sc).trim());
            double allowance = Double.parseDouble(console.printUI(title, subtitle, content, "Phụ cấp", sc).trim());
            double deduction = Double.parseDouble(console.printUI(title, subtitle, content, "Khấu trừ", sc).trim());
            double taxAmount = Double.parseDouble(console.printUI(title, subtitle, content, "Thuế", sc).trim());
            double insuranceAmount = Double
                    .parseDouble(console.printUI(title, subtitle, content, "Số tiền bảo hiểm", sc).trim());
            int overtimeHours = Integer
                    .parseInt(console.printUI(title, subtitle, content, "Số giờ tăng ca", sc).trim());

            if (baseSalary < 0 || allowance < 0 || deduction < 0 || taxAmount < 0 || insuranceAmount < 0
                    || overtimeHours < 0) {
                console.printResult(title, "Các khoản tiền không được âm", sc);
                return;
            }

            double totalHours = 0;
            Attendance[] attendances = employee.getAttendances();
            for (int i = 0; i < attendances.length; i++) {
                if (attendances[i] != null && attendances[i].getDate().getMonthValue() == month.getMonthValue()
                        && attendances[i].getDate().getYear() == month.getYear()) {
                    totalHours += attendances[i].calculateWorkedHours();
                }
            }
            int workedHours = (int) Math.round(totalHours);

            Evaluation latest = null;
            Evaluation[] evaluations = employee.getEvaluations();
            for (int i = 0; i < evaluations.length; i++) {
                if (evaluations[i] != null) {
                    latest = evaluations[i];
                }
            }

            Payroll newPayroll = new Payroll(month, baseSalary, 0, allowance, deduction, taxAmount,
                    insuranceAmount, workedHours, overtimeHours, null);
            if (latest != null) {
                newPayroll.setBonus(newPayroll.calculateBonus(latest));
            }

            payrolls[index] = newPayroll;
            console.printResult(title, "Payroll added successfully\nSố giờ làm việc: " + workedHours
                    + "\nThưởng: " + newPayroll.getBonus(), sc);
        } catch (NumberFormatException e) {
            console.printResult(title, "Vui lòng nhập số cho các trường số!", sc);
        }
    }

    public void addAttendance(Employee[] employees, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        String title = "Thêm chấm công";

        Employee employee = selectEmployeePrompt(employees, title, sc);
        if (employee == null) {
            console.printResult(title, "Không tìm thấy nhân viên", sc);
            return;
        }

        Attendance[] attendances = employee.getAttendances();
        int index = -1;
        for (int i = 0; i < attendances.length; i++) {
            if (attendances[i] == null) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            console.printResult(title, "Danh sách chấm công đã đầy", sc);
            return;
        }

        String subtitle = "Chấm công của nhân viên " + employee.getId();
        String content = "";
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        try {
            LocalDate date = console.printDateUI(title, subtitle, content, "Ngày (dd/MM/yyyy)", sc);

            LocalTime clockIn;
            LocalTime clockOut;
            do {
                clockIn = LocalTime.parse(console.printUI(title, subtitle, content, "Giờ vào (HH:mm)", sc).trim(),
                        timeFormatter);
                clockOut = LocalTime.parse(console.printUI(title, subtitle, content, "Giờ ra (HH:mm)", sc).trim(),
                        timeFormatter);

                if (!clockIn.isBefore(clockOut)) {
                    console.printResult(title, "Giờ vào phải trước giờ ra", sc);
                }
            } while (!clockIn.isBefore(clockOut));

            attendances[index] = new Attendance(date, clockIn, clockOut);
            console.printResult(title, "Thêm chấm công thành công", sc);
        } catch (DateTimeParseException e) {
            console.printResult(title, "Thời gian không hợp lệ! Dùng HH:mm", sc);
        }
    }

    public void addInsurance(Employee[] employees, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        String title = "Thêm bảo hiểm";

        Employee employee = selectEmployeePrompt(employees, title, sc);
        if (employee == null) {
            console.printResult(title, "Không tìm thấy nhân viên", sc);
            return;
        }

        Insurance[] insurances = employee.getInsurances();
        int index = -1;
        for (int i = 0; i < insurances.length; i++) {
            if (insurances[i] == null) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            console.printResult(title, "Danh sách bảo hiểm đã đầy", sc);
            return;
        }

        String subtitle = "Bảo hiểm cho nhân viên " + employee.getId();
        String content = "";

        try {
            String insuranceNumber = console.printUI(title, subtitle, content, "Số bảo hiểm", sc).trim();
            if (insuranceNumber.isEmpty()) {
                console.printResult(title, "Số bảo hiểm không được để trống", sc);
                return;
            }

            LocalDate startDate;
            LocalDate endDate;
            do {
                startDate = console.printDateUI(title, subtitle, content, "Ngày bắt đầu (dd/MM/yyyy)", sc);
                endDate = console.printDateUI(title, subtitle, content, "Ngày kết thúc (dd/MM/yyyy)", sc);

                if (startDate.isAfter(endDate)) {
                    console.printResult(title, "Ngày bắt đầu phải trước ngày kết thúc", sc);
                }
            } while (startDate.isAfter(endDate));

            double rate = Double.parseDouble(console.printUI(title, subtitle, content, "Mức", sc).trim());
            if (rate < 0) {
                console.printResult(title, "Mức không được âm", sc);
                return;
            }

            String[] typeNames = { "Nhân thọ", "Sức khỏe", "Xã hội", "Thất nghiệp" };
            Insurance.InsuranceType[] types = {
                    Insurance.InsuranceType.LIFE,
                    Insurance.InsuranceType.HEALTH,
                    Insurance.InsuranceType.SOCIAL,
                    Insurance.InsuranceType.UNEMPLOYMENT };
            int typeChoice;
            do {
                typeChoice = Integer.parseInt(console.printUI(title, subtitle, typeNames, sc).trim());
                if (typeChoice < 0 || typeChoice >= typeNames.length) {
                    console.printResult(title, "Lựa chọn không hợp lệ", sc);
                }
            } while (typeChoice < 0 || typeChoice >= typeNames.length);

            insurances[index] = new Insurance(insuranceNumber, types[typeChoice], startDate, endDate, rate);
            console.printResult(title, "Thêm bảo hiểm thành công", sc);
        } catch (NumberFormatException e) {
            console.printResult(title, "Vui lòng nhập số cho các trường số!", sc);
        }
    }

    public void printPayrolls(DataHandler data, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        SortData sort = new SortData();
        String title = "In bảng lương";
        String subtitle = "Chọn một tùy chọn";

        String id = askId(title, sc);
        int idx = getIndex(data, id);
        if (idx == -1) {
            console.printResult(title, "Nhân viên không tồn tại", sc);
            return;
        }

        Employee currEmployee = data.getEmployees()[idx];
        Payroll[] currPayrolls = currEmployee.getPayrolls();

        int sortBy;
        do {
            String[] options = { "Tháng", "Lương cơ bản", "Thưởng", "Phụ cấp", "Khấu trừ", "Thuế",
                    "Số tiền bảo hiểm", "Số giờ làm việc", "Số giờ tăng ca", "Tiền tăng ca", "Ngày tăng lương gần nhất",
                    "Lương thực nhận" };
            sortBy = validateInt(console.printUI(title, subtitle, options, sc));

            if (sortBy < 0 || 11 < sortBy) {
                console.printResult(title, "Lựa chọn không hợp lệ", sc);
            }
        } while (sortBy < 0 || 11 < sortBy);

        boolean isAscending = askSortOrder("Sắp xếp tăng dần", sc);

        switch (sortBy) {
            case 0:
                sort.sortByMonth(currPayrolls, isAscending);
                break;
            case 1:
                sort.sortByBaseSalary(currPayrolls, isAscending);
                break;
            case 2:
                sort.sortByBonus(currPayrolls, isAscending);
                break;
            case 3:
                sort.sortByAllowance(currPayrolls, isAscending);
                break;
            case 4:
                sort.sortByDeduction(currPayrolls, isAscending);
                break;
            case 5:
                sort.sortByTaxAmount(currPayrolls, isAscending);
                break;
            case 6:
                sort.sortByInsuranceAmount(currPayrolls, isAscending);
                break;
            case 7:
                sort.sortByWorkedHours(currPayrolls, isAscending);
                break;
            case 8:
                sort.sortByOvertimeHours(currPayrolls, isAscending);
                break;
            case 9:
                sort.sortByOvertimePay(currPayrolls, isAscending);
                break;
            case 10:
                sort.sortByLastSalaryIncreaseDate(currPayrolls, isAscending);
                break;
            case 11:
                sort.sortByNetSalary(currPayrolls, isAscending);
                break;

            default:
                break;
        }

        PrintTables.printPayrolls(currEmployee, id, sc);
    }

    public void printEvaluations(DataHandler data, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        SortData sort = new SortData();
        String title = "In đánh giá";
        String subtitle = "Chọn một tùy chọn";

        String id = askId(title, sc);
        int idx = getIndex(data, id);
        if (idx == -1) {
            console.printResult(title, "Nhân viên không tồn tại", sc);
            return;
        }

        Employee currEmployee = data.getEmployees()[idx];
        Evaluation[] currEvaluations = currEmployee.getEvaluations();

        int sortBy;
        do {
            String[] options = { "Tháng", "Điểm công việc", "Attitude score", "Điểm kỷ luật", "Chất lượng công việc",
                    "Năng suất", "Làm việc nhóm" };
            sortBy = validateInt(console.printUI(title, subtitle, options, sc));

            if (sortBy < 0 || 6 < sortBy) {
                console.printResult(title, "Lựa chọn không hợp lệ", sc);
            }
        } while (sortBy < 0 || 6 < sortBy);

        boolean isAscending = askSortOrder("Sắp xếp tăng dần", sc);

        switch (sortBy) {
            case 0:
                sort.sortByMonth(currEvaluations, isAscending);
                break;
            case 1:
                sort.sortByWorkScore(currEvaluations, isAscending);
                break;
            case 2:
                sort.sortByAttitudeScore(currEvaluations, isAscending);
                break;
            case 3:
                sort.sortByDisciplineScore(currEvaluations, isAscending);
                break;
            case 4:
                sort.sortByWorkQuality(currEvaluations, isAscending);
                break;
            case 5:
                sort.sortByProductivity(currEvaluations, isAscending);
                break;
            case 6:
                sort.sortByTeamWork(currEvaluations, isAscending);
                break;

            default:
                break;
        }

        PrintTables.printEvaluations(currEmployee, id, sc);
    }

    public void printContracts(DataHandler data, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        SortData sort = new SortData();
        String title = "In hợp đồng";
        String subtitle = "Chọn một tùy chọn";

        String id = askId(title, sc);
        int idx = getIndex(data, id);
        if (idx == -1) {
            console.printResult(title, "Nhân viên không tồn tại", sc);
            return;
        }

        Employee currEmployee = data.getEmployees()[idx];
        Contract[] currContracts = currEmployee.getContracts();

        int sortBy;
        do {
            String[] options = { "Ngày bắt đầu", "Ngày kết thúc", "Loại", "Trạng thái" };
            sortBy = validateInt(console.printUI(title, subtitle, options, sc));

            if (sortBy < 0 || 3 < sortBy) {
                console.printResult(title, "Lựa chọn không hợp lệ", sc);
            }
        } while (sortBy < 0 || 3 < sortBy);

        boolean isAscending = askSortOrder("Sắp xếp tăng dần", sc);

        switch (sortBy) {
            case 0:
                sort.sortByStartDate(currContracts, isAscending);
                break;
            case 1:
                sort.sortByEndDate(currContracts, isAscending);
                break;
            case 2:
                sort.sortByType(currContracts, isAscending);
                break;
            case 3:
                sort.sortByStatus(currContracts, isAscending);
                break;

            default:
                break;
        }

        PrintTables.printContracts(currEmployee, id, sc);
    }

    public void printAttendances(DataHandler data, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        SortData sort = new SortData();
        String title = "In chấm công";
        String subtitle = "Chọn một tùy chọn";

        String id = askId(title, sc);
        int idx = getIndex(data, id);
        if (idx == -1) {
            console.printResult(title, "Nhân viên không tồn tại", sc);
            return;
        }

        Employee currEmployee = data.getEmployees()[idx];
        Attendance[] currAttendances = currEmployee.getAttendances();

        int sortBy;
        do {
            String[] options = { "Ngày", "Giờ vào", "Giờ ra" };
            sortBy = validateInt(console.printUI(title, subtitle, options, sc));

            if (sortBy < 0 || 2 < sortBy) {
                console.printResult(title, "Lựa chọn không hợp lệ", sc);
            }
        } while (sortBy < 0 || 2 < sortBy);

        boolean isAscending = askSortOrder("Sắp xếp tăng dần", sc);

        switch (sortBy) {
            case 0:
                sort.sortByDate(currAttendances, isAscending);
                break;
            case 1:
                sort.sortByClockIn(currAttendances, isAscending);
                break;
            case 2:
                sort.sortByClockOut(currAttendances, isAscending);
                break;

            default:
                break;
        }

        PrintTables.printAttendances(currEmployee, id, sc);
    }

    public void printAbsences(DataHandler data, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        SortData sort = new SortData();
        String title = "In đơn nghỉ phép";
        String subtitle = "Chọn một tùy chọn";

        String id = askId(title, sc);
        int idx = getIndex(data, id);
        if (idx == -1) {
            console.printResult(title, "Nhân viên không tồn tại", sc);
            return;
        }

        Employee currEmployee = data.getEmployees()[idx];
        Absence[] currAbsences = currEmployee.getAbsences();

        int sortBy;
        do {
            String[] options = { "Lý do", "Từ ngày", "Đến ngày", "Trạng thái" };
            sortBy = validateInt(console.printUI(title, subtitle, options, sc));

            if (sortBy < 0 || 3 < sortBy) {
                console.printResult(title, "Lựa chọn không hợp lệ", sc);
            }
        } while (sortBy < 0 || 3 < sortBy);

        boolean isAscending = askSortOrder("Sắp xếp tăng dần", sc);

        switch (sortBy) {
            case 0:
                sort.sortByReason(currAbsences, isAscending);
                break;
            case 1:
                sort.sortByFromDate(currAbsences, isAscending);
                break;
            case 2:
                sort.sortByToDate(currAbsences, isAscending);
                break;
            case 3:
                sort.sortByStatus(currAbsences, isAscending);
                break;

            default:
                break;
        }

        PrintTables.printAbsences(currEmployee, id, sc);
    }

    public void printResignations(DataHandler data, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        SortData sort = new SortData();
        String title = "In đơn thôi việc";
        String subtitle = "Chọn một tùy chọn";

        String id = askId(title, sc);
        int idx = getIndex(data, id);
        if (idx == -1) {
            console.printResult(title, "Nhân viên không tồn tại", sc);
            return;
        }

        Employee currEmployee = data.getEmployees()[idx];
        Resignation[] currResignations = currEmployee.getResignations();

        int sortBy;
        do {
            String[] options = { "Lý do", "Ngày", "Trạng thái" };
            sortBy = validateInt(console.printUI(title, subtitle, options, sc));

            if (sortBy < 0 || 2 < sortBy) {
                console.printResult(title, "Lựa chọn không hợp lệ", sc);
            }
        } while (sortBy < 0 || 2 < sortBy);

        boolean isAscending = askSortOrder("Sắp xếp tăng dần", sc);

        switch (sortBy) {
            case 0:
                sort.sortByReason(currResignations, isAscending);
                break;
            case 1:
                sort.sortByDate(currResignations, isAscending);
                break;
            case 2:
                sort.sortByStatus(currResignations, isAscending);
                break;

            default:
                break;
        }

        PrintTables.printResignations(currEmployee, id, sc);
    }

    public void printInsurances(DataHandler data, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        SortData sort = new SortData();
        String title = "In bảo hiểm";
        String subtitle = "Chọn một tùy chọn";

        String id = askId(title, sc);
        int idx = getIndex(data, id);
        if (idx == -1) {
            console.printResult(title, "Nhân viên không tồn tại", sc);
            return;
        }

        Employee currEmployee = data.getEmployees()[idx];
        Insurance[] currInsurances = currEmployee.getInsurances();

        int sortBy;
        do {
            String[] options = { "Số bảo hiểm", "Loại", "Ngày bắt đầu", "Ngày kết thúc", "Mức" };
            sortBy = validateInt(console.printUI(title, subtitle, options, sc));

            if (sortBy < 0 || 4 < sortBy) {
                console.printResult(title, "Lựa chọn không hợp lệ", sc);
            }
        } while (sortBy < 0 || 4 < sortBy);

        boolean isAscending = askSortOrder("Sắp xếp tăng dần", sc);

        switch (sortBy) {
            case 0:
                sort.sortByInsuranceNumber(currInsurances, isAscending);
                break;
            case 1:
                sort.sortByType(currInsurances, isAscending);
                break;
            case 2:
                sort.sortByStartDate(currInsurances, isAscending);
                break;
            case 3:
                sort.sortByEndDate(currInsurances, isAscending);
                break;
            case 4:
                sort.sortByRate(currInsurances, isAscending);
                break;

            default:
                break;
        }

        PrintTables.printInsurances(currEmployee, id, sc);
    }

    public void editDepartment(Scanner sc, Department[] departments) {
        ConsoleUI console = new ConsoleUI();

        String title = "Sửa phòng ban";
        String subtitle = "Chọn phòng ban";

        boolean hasDepartment = false;
        if (departments != null) {
            for (int i = 0; i < departments.length; i++) {
                if (departments[i] != null) {
                    hasDepartment = true;
                    break;
                }
            }
        }
        if (!hasDepartment) {
            console.printResult(title, "Không có phòng ban nào", sc);
            return;
        }

        int choice;
        boolean valid;
        do {
            String content = getDepartments(departments) + "\nEnter 0 to go back\n";

            try {
                choice = Integer.parseInt(console.printUI(title, subtitle, content, sc).trim());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            if (choice == 0) {
                return;
            }

            valid = choice >= 1 && choice <= departments.length && departments[choice - 1] != null;
            if (!valid) {
                console.printResult(title, "Phòng ban không tồn tại", sc);
            }
        } while (!valid);

        Department department = departments[choice - 1];

        String[] options = { "Thoát", "Tên", "Điện thoại", "Chức vụ" };

        do {
            String content = "Tên: " + department.getName() + "\n"
                    + "Điện thoại: " + department.getPhone() + "\n"
                    + "Chức vụ: " + department.getPosition();

            try {
                choice = Integer.parseInt(console.printUI(title, "Chọn một tùy chọn", content, options, sc).trim());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 0:
                    break;

                case 1: {
                    String newName;
                    boolean duplicate;

                    do {
                        newName = console.printUI(title, "Đặt tên",
                                "Tên hiện tại: " + department.getName(), "Tên mới", sc).trim();

                        duplicate = false;

                        if (newName.isEmpty() || newName.length() > 30) {
                            console.printResult(title, "Tên phải có từ 1-30 ký tự", sc);
                            duplicate = true;
                        } else {
                            for (int i = 0; i < departments.length; i++) {
                                if (departments[i] != null && departments[i] != department
                                        && departments[i].getName().equalsIgnoreCase(newName)) {
                                    duplicate = true;
                                    console.printResult(title, "Tên phòng ban đã tồn tại", sc);
                                    break;
                                }
                            }
                        }

                    } while (duplicate);

                    department.setName(newName);
                    console.printResult(title, "Đặt tên thành công", sc);
                    break;
                }

                case 2: {
                    String newPhone;
                    do {
                        newPhone = console.printUI(title, "Đặt số điện thoại",
                                "Số điện thoại hiện tại: " + department.getPhone(), "Số điện thoại mới", sc).trim();

                        if (!newPhone.matches("[0-9]{1,11}")) {
                            console.printResult(title, "Số điện thoại phải có từ 1-11 chữ số", sc);
                        }
                    } while (!newPhone.matches("[0-9]{1,11}"));

                    department.setPhone(newPhone);
                    console.printResult(title, "Đặt số điện thoại thành công", sc);
                    break;
                }

                case 3: {
                    String newPosition;
                    do {
                        newPosition = console.printUI(title, "Đặt chức vụ",
                                "Chức vụ hiện tại: " + department.getPosition(), "Chức vụ mới", sc).trim();

                        if (newPosition.isEmpty() || newPosition.length() > 61) {
                            console.printResult(title, "Chức vụ phải có từ 1-61 ký tự", sc);
                        }
                    } while (newPosition.isEmpty() || newPosition.length() > 61);

                    department.setPosition(newPosition);
                    console.printResult(title, "Đặt chức vụ thành công", sc);
                    break;
                }

                default:
                    console.printResult(title, "Lựa chọn không hợp lệ", sc);
                    break;
            }

        } while (choice != 0);
    }

    public void editPosition(Scanner sc, Position[] positions) {
        ConsoleUI console = new ConsoleUI();

        String title = "Sửa chức vụ";
        String subtitle = "Chọn chức vụ";

        boolean hasPosition = false;
        if (positions != null) {
            for (int i = 0; i < positions.length; i++) {
                if (positions[i] != null) {
                    hasPosition = true;
                    break;
                }
            }
        }
        if (!hasPosition) {
            console.printResult(title, "Không có chức vụ nào", sc);
            return;
        }

        int choice;
        boolean valid;
        do {
            String content = getPositions(positions) + "\nEnter 0 to go back\n";

            try {
                choice = Integer.parseInt(console.printUI(title, subtitle, content, sc).trim());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            if (choice == 0) {
                return;
            }

            valid = choice >= 1 && choice <= positions.length && positions[choice - 1] != null;
            if (!valid) {
                console.printResult(title, "Chức vụ không tồn tại", sc);
            }
        } while (!valid);

        Position position = positions[choice - 1];

        String[] options = { "Thoát", "Tên", "Hệ số chức vụ", "Trách nhiệm" };

        do {
            String content = "Tên: " + position.getName() + "\n"
                    + "Hệ số chức vụ: " + position.getPositionCoefficient() + "\n"
                    + "Trách nhiệm: " + position.getResponsibilities();

            try {
                choice = Integer.parseInt(console.printUI(title, "Chọn một tùy chọn", content, options, sc).trim());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 0:
                    break;

                case 1: {
                    String newName;
                    boolean duplicate;

                    do {
                        newName = console.printUI(title, "Đặt tên",
                                "Tên hiện tại: " + position.getName(), "Tên mới", sc).trim();

                        duplicate = false;

                        if (newName.isEmpty() || newName.length() > 30) {
                            console.printResult(title, "Tên phải có từ 1-30 ký tự", sc);
                            duplicate = true;
                        } else {
                            for (int i = 0; i < positions.length; i++) {
                                if (positions[i] != null && positions[i] != position
                                        && positions[i].getName().equalsIgnoreCase(newName)) {
                                    duplicate = true;
                                    console.printResult(title, "Tên chức vụ đã tồn tại", sc);
                                    break;
                                }
                            }
                        }

                    } while (duplicate);

                    position.setName(newName);
                    console.printResult(title, "Đặt tên thành công", sc);
                    break;
                }

                case 2: {
                    double newCoefficient;
                    boolean validCoefficient;

                    do {
                        String text = console.printUI(title, "Đặt hệ số chức vụ",
                                "Hệ số hiện tại: " + position.getPositionCoefficient(),
                                "Hệ số mới", sc).trim();

                        try {
                            newCoefficient = Double.parseDouble(text);
                        } catch (NumberFormatException e) {
                            newCoefficient = -1;
                        }

                        validCoefficient = Double.isFinite(newCoefficient) && newCoefficient >= 0
                                && Double.toString(newCoefficient).length() <= 25;
                        if (!validCoefficient) {
                            console.printResult(title, "Hệ số phải là số hợp lệ >= 0", sc);
                        }
                    } while (!validCoefficient);

                    position.setPositionCoefficient(newCoefficient);
                    console.printResult(title, "Đặt hệ số chức vụ thành công", sc);
                    break;
                }

                case 3: {
                    String newResponsibilities;
                    do {
                        newResponsibilities = console.printUI(title, "Đặt trách nhiệm",
                                "Trách nhiệm hiện tại: " + position.getResponsibilities(),
                                "Trách nhiệm mới", sc).trim();

                        if (newResponsibilities.isEmpty() || newResponsibilities.length() > 47) {
                            console.printResult(title, "Trách nhiệm phải có từ 1-47 ký tự", sc);
                        }
                    } while (newResponsibilities.isEmpty() || newResponsibilities.length() > 47);

                    position.setResponsibilities(newResponsibilities);
                    console.printResult(title, "Đặt trách nhiệm thành công", sc);
                    break;
                }

                default:
                    console.printResult(title, "Lựa chọn không hợp lệ", sc);
                    break;
            }

        } while (choice != 0);
    }
}
