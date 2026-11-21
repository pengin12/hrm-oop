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

        String choice = console.printUI(title, "", new String[] {}, "Yes/No [Y/n]", sc);
        return !choice.equalsIgnoreCase("n");
    }

    public AdminAccount(String username, String password, AccountStatus status) {
        super(username, password, status);
    }

    @Override
    public void getInfo(Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        String result = "";
        result += "Username: " + this.getUsername() + "\n";
        result += "Status: " + this.getStatus() + "\n";

        console.printResult("Employee Information", result, sc);
    }

    public void addEmployee(Employee[] employees, Employee employee, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        String title = "Thêm nhân viên";

        for (int i = 0; i < employees.length; i++) {
            if (employees[i] == null) {
                employees[i] = employee;

                String result = "Da them nhan vien: " + employee.getId()
                        + " - " + employee.getFirstName() + " " + employee.getLastName();
                console.printResult(title, result, sc);
                return;
            }
        }

        console.printResult(title, "Mang nhan vien da day, khong them duoc.", sc);
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
        System.out.println("Danh sach tai khoan:");
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] != null) {
                System.out.print((i + 1) + ". ");
                accounts[i].getInfo(sc);
            }
        }
    }

    public void removeEmployee(Employee[] employees, String id, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Remove Employee";

        for (int i = 0; i < employees.length; i++) {
            if (employees[i] != null && employees[i].getId().equals(id)) {
                employees[i] = null;
                console.printResult(title, "Da xoa tai khoan: " + id, sc);
                return;
            }
        }

        console.printResult(title, "Khong tim thay nhan vien: " + id, sc);
    }

    public void removeAccount(Account[] accounts, String id, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Remove Account";

        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] != null && accounts[i].getUsername().equals(id)) {
                accounts[i] = null;
                console.printResult(title, "Da xoa tai khoan: " + id, sc);
                return;
            }
        }

        console.printResult(title, "Khong tim thay tai khoan: " + id, sc);
    }

    public void replyRequest(Account[] accounts, String username, String reply, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Reply Request";

        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] != null && accounts[i].getUsername().equals(username)) {
                console.printResult(title, "Admin phan hoi " + username + ": " + reply, sc);
                return;
            }
        }
        console.printResult(title, "Khong tim thay tai khoan: " + username, sc);
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
                " ".repeat(nameLeftPadding) + "Name" + " ".repeat(nameRightPadding) + " | " +
                " ".repeat(positionCoefficientLeftPadding) + "Position coefficient"
                + " ".repeat(positionCoefficientRightPadding)
                + " | " +
                " ".repeat(responsibilityLeftPadding) + "Responsibilities" + " ".repeat(responsibilityRightPadding)
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
                " ".repeat(nameLeftPadding) + "Name" + " ".repeat(nameRightPadding) + " | " +
                " ".repeat(phoneLeftPadding) + "Phone" + " ".repeat(phoneRightPadding) + " | " +
                " ".repeat(positionLeftPadding) + "Position" + " ".repeat(positionRightPadding)
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

        String title = "Edit Employee";
        String subtitle = "Edit Hire Date";
        String content = "Current hire date: " + employee.getHireDate();
        String input;

        input = "Hire date (dd/MM/yyyy)";
        LocalDate hireDate = console.printDateUI(title, subtitle, content, input, sc);
        employee.setHireDate(hireDate);

        console.printResult(title, "Set hire date successfully", sc);
    }

    private void editEmployeeSpecialization(Scanner sc, Employee employee) {
        ConsoleUI console = new ConsoleUI();

        String title = "Edit Employee";
        String subtitle = "Set Specialization";
        String content = "Current specialization: " + employee.getSpecialization();

        String input = "Specialization";
        employee.setSpecialization(console.printUI(title, subtitle, content, input, sc));

        console.printResult(title, "Set specialization successfully", sc);
    }

    private void editEmployeeStatus(Scanner sc, Employee employee) {
        ConsoleUI console = new ConsoleUI();

        String title = "Edit Employee";
        String subtitle = "Set Status";
        String content = "Current status: " + employee.getStatus();

        int statusNum = 0;
        do {
            String[] options = { "Working", "On leave", "Resigned" };
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

        console.printResult(title, "Set status successfully", sc);
    }

    private void editEmployeeTaxId(Scanner sc, Employee employee) {
        ConsoleUI console = new ConsoleUI();

        String title = "Edit Employee";
        String subtitle = "Set Tax Id";
        String content = "Current tax id: " + employee.getTaxId();

        String input = "Tax id";
        employee.setTaxId(console.printUI(title, subtitle, content, input, sc));

        console.printResult(title, "Set tax id successfully", sc);
    }

    private void editEmployeePosition(Scanner sc, Employee employee, Position[] positions) {
        ConsoleUI console = new ConsoleUI();

        String title = "Edit Employee";
        String subtitle = "Set Position";
        String content = "Current position: " + employee.getPosition().getName() + "\n";

        int choice;
        do {
            content += getPositions(positions);
            choice = Integer.parseInt(console.printUI(title, subtitle, content, sc));

            if (positions[choice - 1] == null) {
                console.printResult(title, "Position not exist", sc);
            }
        } while (positions[choice - 1] == null);

        console.printResult(title, "Set position successfully", sc);
    }

    private void editEmployeeDepartment(Scanner sc, Employee employee, Department[] departments) {
        ConsoleUI console = new ConsoleUI();

        String title = "Edit Employee";
        String subtitle = "Set Department";
        String content = "Current department: " + employee.getDepartment().getName() + "\n";

        int choice;
        do {
            content += getDepartments(departments);
            choice = Integer.parseInt(console.printUI(title, subtitle, content, sc));

            if (departments[choice - 1] == null) {
                console.printResult(title, "Department not exist", sc);
            }
        } while (departments[choice - 1] == null);

        console.printResult(title, "Set department successfully", sc);
    }

    public void editEmployee(Scanner sc, Employee[] employees, String id, Position[] positions,
            Department[] departments) {
        ConsoleUI console = new ConsoleUI();
        String title = "Edit Employee";
        String subtitle = "Choose an option";

        Employee employee = this.searchEmployee(employees, id);
        if (employee == null) {
            console.printResult(title, "Khong tim thay nhan vien: " + id, sc);
            return;
        }

        int setChoice;
        do {
            String[] options = { "Exit", "Hire date", "Specialization", "Status", "Tax id", "Position", "Department" };
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
                    console.printResult(title, "Invalid choice", sc);
                    break;
            }
        } while (setChoice != 0);

        console.printResult(title, "Da sua nhan vien: " + id, sc);
    }

    public void printEmployees(DataHandler data, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        SortData sort = new SortData();
        String title = "Print Employees";
        String subtitle = "Choose an option";

        Employee[] currEmployees = data.getEmployees();

        int sortBy;
        do {
            String[] options = { "Id", "Hire date", "Specialization", "Status", "Tax id", "Position", "Department" };
            sortBy = validateInt(console.printUI(title, subtitle, options, sc));

            if (sortBy < 0 || 6 < sortBy) {
                console.printResult(title, "Invalid choice", sc);
            }
        } while (sortBy < 0 || 6 < sortBy);

        boolean isAscending = askSortOrder("Ascending Sort", sc);

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
        String title = "Print Departments";
        String subtitle = "Choose an option";

        Department[] currDepartments = data.getDepartments();

        int sortBy;
        do {
            String[] options = { "Name", "Phone", "Position" };
            sortBy = validateInt(console.printUI(title, subtitle, options, sc));

            if (sortBy < 0 || 2 < sortBy) {
                console.printResult(title, "Invalid choice", sc);
            }
        } while (sortBy < 0 || 2 < sortBy);

        boolean isAscending = askSortOrder("Ascending Sort", sc);

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
        String title = "Print Positions";
        String subtitle = "Choose an option";

        Position[] currPositions = data.getPositions();

        int sortBy;
        do {
            String[] options = { "Name", "Phone", "Position" };
            sortBy = validateInt(console.printUI(title, subtitle, options, sc));

            if (sortBy < 0 || 2 < sortBy) {
                console.printResult(title, "Invalid choice", sc);
            }
        } while (sortBy < 0 || 2 < sortBy);

        boolean isAscending = askSortOrder("Ascending Sort", sc);

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
        String title = "Search Employee";
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
            console.printResult(title, "Employee not exists", sc);
        }
    }

    public void searchPosition(Position[] positions, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        String title = "Search Position";
        String input = "Position name";

        String name = console.printUI(title, "", "", input, sc).trim();
        String content = "";
        boolean found = false;

        for (int i = 0; i < positions.length; i++) {
            Position position = positions[i];

            if (position != null && position.getName() != null
                    && position.getName().equalsIgnoreCase(name)) {
                content += "Position name: " + position.getName() + "\n";
                content += "Position coefficient: "
                        + position.getPositionCoefficient() + "\n";
                content += "Responsibilities: "
                        + position.getResponsibilities() + "\n";
                found = true;
                break;
            }
        }

        if (found) {
            console.printResult(title, content, sc);
        } else {
            console.printResult(title, "Position not exists", sc);
        }
    }

    public void searchDepartment(Department[] departments, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        String title = "Search departments";
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
            console.printResult(title, "Department not exists", sc);
        }
    }

    public Account createAccount(Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        String title = "Create Account";
        String subtitle = "";
        String content = "";
        String input;

        input = "Username";
        String username = console.printUI(title, subtitle, content, input, sc);

        input = "Password";
        String password = console.printUI(title, subtitle, content, input, sc);

        int statusNum = 0;
        Account.AccountStatus status = Account.AccountStatus.ACTIVE;
        do {
            String[] options = { "Active", "Inactive", "Suspended", "Pending", "Locked", "Terminated" };
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
            String[] options = { "Admin", "Employee" };
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
        String title = "Create Department";
        String subtitle = "";
        String content = "";

        String input = "Name";
        String name = console.printUI(title, subtitle, subtitle, input, sc);
        content += "Name: " + name + "\n";

        input = "Phone";
        String phone = console.printUI(title, subtitle, content, input, sc);
        content += "Phone: " + phone + "\n";

        input = "Position";
        String position = console.printUI(title, subtitle, content, input, sc);
        content += "Position: " + position + "\n";

        console.printResult(title, content, sc);

        return new Department(name, phone, position);
    }

    public Position createPosition(Scanner sc, Position[] positions) {
        ConsoleUI console = new ConsoleUI();
        String title = "Create Position";
        String subtitle = "";
        String content = "";

        String input = "Name";
        String name = console.printUI(title, subtitle, subtitle, input, sc);
        content += "Name: " + name + "\n";

        input = "Position coefficient";
        double positionCoefficient = Double.parseDouble(console.printUI(title, subtitle, content, input, sc));
        content += "Position coefficient: " + positionCoefficient + "\n";

        input = "Responsibilities";
        String responsibilities = console.printUI(title, subtitle, content, input, sc);
        content += "Responsibilities: " + responsibilities + "\n";

        console.printResult(title, content, sc);

        return new Position(responsibilities, name, positionCoefficient);
    }

    public Address createAddress(Scanner sc) {
        ConsoleUI console = new ConsoleUI();

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

    public Evaluation createEvaluation(Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Create Employee";
        String subtitle = "Create Evaluation";
        String[] options = {};
        String input;

        input = "Month (MM/yyyy)";
        YearMonth month = YearMonth.parse(
                console.printUI(title, subtitle, options, input, sc).trim(),
                yearMonthFormatter);

        input = "Comment";
        String comment = console.printUI(title, subtitle, options, input, sc);

        input = "Work score";
        double workScore = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

        input = "Attitude score";
        double attitudeScore = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

        input = "Discipline score";
        double disciplineScore = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

        input = "Work quality";
        double workQuality = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

        input = "Productivity";
        double productivity = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

        input = "Team work";
        double teamWork = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

        return new Evaluation(month, comment, workScore, attitudeScore, disciplineScore, workQuality, productivity,
                teamWork);
    }

    public Contract createContract(Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Create Employee";
        String subtitle = "Create Contracts";
        String[] options = {};
        String input;

        LocalDate startDate;
        LocalDate endDate;
        do {
            input = "Start date: ";
            startDate = LocalDate.parse(console.printUI(title, subtitle, options, input, sc), dateFormatter);

            input = "End date: ";
            endDate = LocalDate.parse(console.printUI(title, subtitle, options, input, sc), dateFormatter);

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

    public Payroll createPayroll(Scanner sc) {
        DataCheck checker = new DataCheck("^(\\d{2})/(\\d{4})$");
        ConsoleUI console = new ConsoleUI();

        String title = "Create Employee";
        String subtitle = "Create Payrolls";
        String content = "";
        String[] options = {};
        String input;

        input = "Month (MM/yyyy)";
        String monthStr = console.printUI(title, subtitle, content, input, sc).trim();
        while (!checker.isValid(monthStr)) {
            console.printResult(title, "Invalid input", sc);
            monthStr = console.printUI(title, subtitle, content, input, sc).trim();
        }
        YearMonth month = YearMonth.parse(monthStr, yearMonthFormatter);

        input = "Base Salary";
        double baseSalary = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

        input = "Bonus";
        double bonus = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

        input = "Allowance";
        double allowance = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

        input = "Deduction";
        double deduction = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

        input = "Tax amount";
        double taxAmount = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

        input = "Insurance amount";
        double insuranceAmount = Double.parseDouble(console.printUI(title, subtitle, options, input, sc));

        input = "Worked hours";
        int workedHours = validateInt(console.printUI(title, subtitle, options, input, sc));

        input = "Overtime hours";
        int overtimeHours = validateInt(console.printUI(title, subtitle, options, input, sc));

        input = "Last salary increase (dd/MM/yyyy)";
        LocalDate lastSalaryIncreaseDate = console.printDateUI(title, subtitle, options, input, sc);

        return new Payroll(month, baseSalary, bonus, allowance, deduction, taxAmount, insuranceAmount,
                workedHours,
                overtimeHours, lastSalaryIncreaseDate);
    }

    public Attendance createAttendance(Scanner sc) {
        ConsoleUI console = new ConsoleUI();

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

    public Insurance createInsurance(Scanner sc) {
        ConsoleUI console = new ConsoleUI();

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

    public Employee.EmployeeStatus chooseEmployeeStatus(Scanner sc) {
        ConsoleUI console = new ConsoleUI();

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

    public Evaluation[] createEvaluations(LimitHandler limits, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

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

    public Contract[] createContracts(LimitHandler limits, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

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

    public Payroll[] createPayrolls(LimitHandler limits, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

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

    public Attendance[] createAttendances(LimitHandler limits, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

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

    public Resignation createResignation(Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Create Employee";
        String subtitle = "Create Resignation";
        String[] options = {};
        String content = "";
        String input;

        input = "Reason: ";
        String reason = console.printUI(title, subtitle, content, input, sc);

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

    public Resignation[] createResignations(LimitHandler limits, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

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

    public Insurance[] createInsurances(LimitHandler limits, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

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

    public Position choosePosition(DataHandler data, LimitHandler limits, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Create Employee";
        String subtitle = "Choose Position";
        String[] options = { "Exit", "Choose a position" };

        int positionsOccupied = 0;
        for (int i = 0; i < data.getPositions().length; i++) {
            if (data.getPositions()[i] != null) {
                positionsOccupied++;
            }
        }

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

    public Department chooseDepartment(DataHandler data, LimitHandler limits, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Create Employee";
        String subtitle = "Choose Department";
        String[] options = { "Exit", "Choose a department" };

        int departmentsOccupied = 0;
        for (int i = 0; i < data.getDepartments().length; i++) {
            if (data.getDepartments()[i] != null) {
                departmentsOccupied++;
            }
        }

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

    public Employee createEmployee(DataHandler data, LimitHandler limits, Scanner sc) {
        ConsoleUI console = new ConsoleUI();

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

        input = "Specialization";
        String specialization = console.printUI(title, subtitle, options, input, sc);

        Employee.EmployeeStatus status = chooseEmployeeStatus(sc);

        Evaluation[] evaluations = createEvaluations(limits, sc);
        Contract[] contracts = createContracts(limits, sc);
        Payroll[] payrolls = createPayrolls(limits, sc);
        Attendance[] attendances = createAttendances(limits, sc);
        Resignation[] resignations = createResignations(limits, sc);

        input = "Tax id";
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
        String content = "Employees list\n";

        for (int i = 0; i < employees.length; i++) {
            if (employees[i] != null) {
                content += "- ID: " + employees[i].getId() + " | "
                        + employees[i].getFirstName() + " "
                        + employees[i].getLastName() + "\n";
            }
        }

        if (content.equals("List Employee:\n")) {
            console.printResult(title, "Employee list is empty!", sc);
            return null;
        }

        String inputField = "Enter Employee (ID)";
        String employeeId = console.printUI(title, "Hired employees", content, inputField, sc);

        Employee employee = searchEmployee(employees, employeeId);
        return employee;
    }

    public void addEvaluation(Employee[] employees, Scanner sc) {
        DataCheck checker = new DataCheck("^(\\d{2})/(\\d{4})$");
        ConsoleUI console = new ConsoleUI();
        String title = "Add Evaluation";

        Employee employee = selectEmployeePrompt(employees, title, sc);
        if (employee == null) {
            console.printResult(title, "Employee not found!", sc);
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
            console.printResult(title, "Evaluation list is full!", sc);
            return;
        }

        String subtitle = "Enter evaluation information for employee " + employee.getId();
        String content = "";

        String monthStr = console.printUI(title, subtitle, content, "Evaluation month (MM/yyyy)", sc).trim();

        if (!checker.isValid(monthStr)) {
            console.printResult(title, "Invalid input", sc);
            return;
        }

        YearMonth month = YearMonth.parse(monthStr, yearMonthFormatter);

        String comment = console.printUI(title, subtitle, content, "Evaluation comment", sc).trim();

        String[] scoreNames = { "Work score (0-10)", "Attitude score (0-10)", "Discipline score (0-10)",
                "Work quality (0-10)", "Productivity (0-10)", "Team work (0-10)" };
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
                    console.printResult(title, "Score must be a number between 0 and 10!", sc);
                }
            } while (!(scores[i] >= 0 && scores[i] <= 10));
        }

        evals[index] = new Evaluation(month, comment, scores[0], scores[1], scores[2], scores[3], scores[4],
                scores[5]);
        console.printResult(title, "Evaluation added successfully!", sc);
    }

    public void addContract(Employee[] employees, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        String title = "Add Contract";

        Employee employee = selectEmployeePrompt(employees, title, sc);
        if (employee == null) {
            console.printResult(title, "Employee not found", sc);
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
            console.printResult(title, "Contract storage is full", sc);
            return;
        }

        String subtitle = "Contract for employee " + employee.getId();
        String content = "";

        try {
            LocalDate startDate;
            LocalDate endDate;
            do {
                startDate = console.printDateUI(title, subtitle, content, "Start date (dd/MM/yyyy)", sc);
                endDate = console.printDateUI(title, subtitle, content, "End date (dd/MM/yyyy)", sc);

                if (startDate.isAfter(endDate)) {
                    console.printResult(title, "Start date must be before end date", sc);
                }
            } while (startDate.isAfter(endDate));

            String[] typeNames = { "Probation", "Full time", "Part time", "Temporary", "Fixed term", "Permanent" };
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
                    console.printResult(title, "Invalid choice", sc);
                }
            } while (typeChoice < 0 || typeChoice >= typeNames.length);

            String[] statusNames = { "Draft", "Active", "Expired", "Terminated" };
            Contract.ContractStatus[] statuses = {
                    Contract.ContractStatus.DRAFT,
                    Contract.ContractStatus.ACTIVE,
                    Contract.ContractStatus.EXPIRED,
                    Contract.ContractStatus.TERMINATED };
            int statusChoice;
            do {
                statusChoice = Integer.parseInt(console.printUI(title, subtitle, statusNames, sc).trim());
                if (statusChoice < 0 || statusChoice >= statusNames.length) {
                    console.printResult(title, "Invalid choice", sc);
                }
            } while (statusChoice < 0 || statusChoice >= statusNames.length);

            contracts[index] = new Contract(startDate, endDate, types[typeChoice], statuses[statusChoice]);
            console.printResult(title, "Contract added successfully", sc);
        } catch (NumberFormatException e) {
            console.printResult(title, "Please enter a number for the choice!", sc);
        }
    }

    public void addPayroll(Employee[] employees, Scanner sc) {
        DataCheck checker = new DataCheck("^(\\d{2})/(\\d{4})$");
        ConsoleUI console = new ConsoleUI();
        String title = "Add Payroll";

        Employee employee = selectEmployeePrompt(employees, title, sc);
        if (employee == null) {
            console.printResult(title, "Employee not found", sc);
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
            console.printResult(title, "Payroll storage is full", sc);
            return;
        }

        String subtitle = "Payroll for employee " + employee.getId();
        String content = "";

        String input = "Month (MM/yyyy)";
        String monthStr = console.printUI(title, subtitle, content, input, sc).trim();
        while (!checker.isValid(monthStr)) {
            console.printResult(title, "Invalid input", sc);
            monthStr = console.printUI(title, subtitle, content, input, sc).trim();
        }
        YearMonth month = YearMonth.parse(monthStr, yearMonthFormatter);

        try {
            for (int i = 0; i < payrolls.length; i++) {
                if (payrolls[i] != null && payrolls[i].getMonth().equals(month)) {
                    console.printResult(title, "Payroll for this month already exists", sc);
                    return;
                }
            }

            double baseSalary = Double
                    .parseDouble(console.printUI(title, subtitle, content, "Base salary", sc).trim());
            double allowance = Double.parseDouble(console.printUI(title, subtitle, content, "Allowance", sc).trim());
            double deduction = Double.parseDouble(console.printUI(title, subtitle, content, "Deduction", sc).trim());
            double taxAmount = Double.parseDouble(console.printUI(title, subtitle, content, "Tax amount", sc).trim());
            double insuranceAmount = Double
                    .parseDouble(console.printUI(title, subtitle, content, "Insurance amount", sc).trim());
            int overtimeHours = Integer
                    .parseInt(console.printUI(title, subtitle, content, "Overtime hours", sc).trim());

            if (baseSalary < 0 || allowance < 0 || deduction < 0 || taxAmount < 0 || insuranceAmount < 0
                    || overtimeHours < 0) {
                console.printResult(title, "Amounts cannot be negative", sc);
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
            console.printResult(title, "Payroll added successfully\nWorked hours: " + workedHours
                    + "\nBonus: " + newPayroll.getBonus(), sc);
        } catch (NumberFormatException e) {
            console.printResult(title, "Please enter numbers for the number fields!", sc);
        }
    }

    public void addAttendance(Employee[] employees, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        String title = "Add Attendance";

        Employee employee = selectEmployeePrompt(employees, title, sc);
        if (employee == null) {
            console.printResult(title, "Employee not found", sc);
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
            console.printResult(title, "Attendance storage is full", sc);
            return;
        }

        String subtitle = "Attendance for employee " + employee.getId();
        String content = "";
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        try {
            LocalDate date = console.printDateUI(title, subtitle, content, "Date (dd/MM/yyyy)", sc);

            LocalTime clockIn;
            LocalTime clockOut;
            do {
                clockIn = LocalTime.parse(console.printUI(title, subtitle, content, "Clock in (HH:mm)", sc).trim(),
                        timeFormatter);
                clockOut = LocalTime.parse(console.printUI(title, subtitle, content, "Clock out (HH:mm)", sc).trim(),
                        timeFormatter);

                if (!clockIn.isBefore(clockOut)) {
                    console.printResult(title, "Clock in time must be before clock out time", sc);
                }
            } while (!clockIn.isBefore(clockOut));

            attendances[index] = new Attendance(date, clockIn, clockOut);
            console.printResult(title, "Attendance added successfully", sc);
        } catch (DateTimeParseException e) {
            console.printResult(title, "Invalid time! Use HH:mm", sc);
        }
    }

    public void addInsurance(Employee[] employees, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        String title = "Add Insurance";

        Employee employee = selectEmployeePrompt(employees, title, sc);
        if (employee == null) {
            console.printResult(title, "Employee not found", sc);
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
            console.printResult(title, "Insurance storage is full", sc);
            return;
        }

        String subtitle = "Insurance for employee " + employee.getId();
        String content = "";

        try {
            String insuranceNumber = console.printUI(title, subtitle, content, "Insurance number", sc).trim();
            if (insuranceNumber.isEmpty()) {
                console.printResult(title, "Insurance number cannot be empty", sc);
                return;
            }

            LocalDate startDate;
            LocalDate endDate;
            do {
                startDate = console.printDateUI(title, subtitle, content, "Start date (dd/MM/yyyy)", sc);
                endDate = console.printDateUI(title, subtitle, content, "End date (dd/MM/yyyy)", sc);

                if (startDate.isAfter(endDate)) {
                    console.printResult(title, "Start date must be before end date", sc);
                }
            } while (startDate.isAfter(endDate));

            double rate = Double.parseDouble(console.printUI(title, subtitle, content, "Rate", sc).trim());
            if (rate < 0) {
                console.printResult(title, "Rate cannot be negative", sc);
                return;
            }

            String[] typeNames = { "Life", "Health", "Social", "Unemployment" };
            Insurance.InsuranceType[] types = {
                    Insurance.InsuranceType.LIFE,
                    Insurance.InsuranceType.HEALTH,
                    Insurance.InsuranceType.SOCIAL,
                    Insurance.InsuranceType.UNEMPLOYMENT };
            int typeChoice;
            do {
                typeChoice = Integer.parseInt(console.printUI(title, subtitle, typeNames, sc).trim());
                if (typeChoice < 0 || typeChoice >= typeNames.length) {
                    console.printResult(title, "Invalid choice", sc);
                }
            } while (typeChoice < 0 || typeChoice >= typeNames.length);

            insurances[index] = new Insurance(insuranceNumber, types[typeChoice], startDate, endDate, rate);
            console.printResult(title, "Insurance added successfully", sc);
        } catch (NumberFormatException e) {
            console.printResult(title, "Please enter numbers for the number fields!", sc);
        }
    }

    public void printPayrolls(DataHandler data, Scanner sc) {
        ConsoleUI console = new ConsoleUI();
        SortData sort = new SortData();
        String title = "Print Payrolls";
        String subtitle = "Choose an option";

        String id = askId(title, sc);
        int idx = getIndex(data, id);
        if (idx == -1) {
            console.printResult(title, "Employee not exists", sc);
            return;
        }

        Employee currEmployee = data.getEmployees()[idx];
        Payroll[] currPayrolls = currEmployee.getPayrolls();

        int sortBy;
        do {
            String[] options = { "Month", "Base salary", "Bonus", "Allowance", "Deduction", "Tax amount",
                    "Insurance amount", "Worked hours", "Overtime hours", "Overtime pay", "Last salary increase date",
                    "Net salary" };
            sortBy = validateInt(console.printUI(title, subtitle, options, sc));

            if (sortBy < 0 || 11 < sortBy) {
                console.printResult(title, "Invalid choice", sc);
            }
        } while (sortBy < 0 || 11 < sortBy);

        boolean isAscending = askSortOrder("Ascending Sort", sc);

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
        String title = "Print Evaluations";
        String subtitle = "Choose an option";

        String id = askId(title, sc);
        int idx = getIndex(data, id);
        if (idx == -1) {
            console.printResult(title, "Employee not exists", sc);
            return;
        }

        Employee currEmployee = data.getEmployees()[idx];
        Evaluation[] currEvaluations = currEmployee.getEvaluations();

        int sortBy;
        do {
            String[] options = { "Month", "Work score", "Attitude score", "Discipline score", "Work quality",
                    "Productivity", "Team work" };
            sortBy = validateInt(console.printUI(title, subtitle, options, sc));

            if (sortBy < 0 || 6 < sortBy) {
                console.printResult(title, "Invalid choice", sc);
            }
        } while (sortBy < 0 || 6 < sortBy);

        boolean isAscending = askSortOrder("Ascending Sort", sc);

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
        String title = "Print Contracts";
        String subtitle = "Choose an option";

        String id = askId(title, sc);
        int idx = getIndex(data, id);
        if (idx == -1) {
            console.printResult(title, "Employee not exists", sc);
            return;
        }

        Employee currEmployee = data.getEmployees()[idx];
        Contract[] currContracts = currEmployee.getContracts();

        int sortBy;
        do {
            String[] options = { "Start date", "End date", "Type", "Status" };
            sortBy = validateInt(console.printUI(title, subtitle, options, sc));

            if (sortBy < 0 || 3 < sortBy) {
                console.printResult(title, "Invalid choice", sc);
            }
        } while (sortBy < 0 || 3 < sortBy);

        boolean isAscending = askSortOrder("Ascending Sort", sc);

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
        String title = "Print Attendances";
        String subtitle = "Choose an option";

        String id = askId(title, sc);
        int idx = getIndex(data, id);
        if (idx == -1) {
            console.printResult(title, "Employee not exists", sc);
            return;
        }

        Employee currEmployee = data.getEmployees()[idx];
        Attendance[] currAttendances = currEmployee.getAttendances();

        int sortBy;
        do {
            String[] options = { "Date", "Clock in", "Clock out" };
            sortBy = validateInt(console.printUI(title, subtitle, options, sc));

            if (sortBy < 0 || 2 < sortBy) {
                console.printResult(title, "Invalid choice", sc);
            }
        } while (sortBy < 0 || 2 < sortBy);

        boolean isAscending = askSortOrder("Ascending Sort", sc);

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
        String title = "Print Absences";
        String subtitle = "Choose an option";

        String id = askId(title, sc);
        int idx = getIndex(data, id);
        if (idx == -1) {
            console.printResult(title, "Employee not exists", sc);
            return;
        }

        Employee currEmployee = data.getEmployees()[idx];
        Absence[] currAbsences = currEmployee.getAbsences();

        int sortBy;
        do {
            String[] options = { "Reason", "From date", "To date", "Status" };
            sortBy = validateInt(console.printUI(title, subtitle, options, sc));

            if (sortBy < 0 || 3 < sortBy) {
                console.printResult(title, "Invalid choice", sc);
            }
        } while (sortBy < 0 || 3 < sortBy);

        boolean isAscending = askSortOrder("Ascending Sort", sc);

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
        String title = "Print Resignations";
        String subtitle = "Choose an option";

        String id = askId(title, sc);
        int idx = getIndex(data, id);
        if (idx == -1) {
            console.printResult(title, "Employee not exists", sc);
            return;
        }

        Employee currEmployee = data.getEmployees()[idx];
        Resignation[] currResignations = currEmployee.getResignations();

        int sortBy;
        do {
            String[] options = { "Reason", "Date", "Status" };
            sortBy = validateInt(console.printUI(title, subtitle, options, sc));

            if (sortBy < 0 || 2 < sortBy) {
                console.printResult(title, "Invalid choice", sc);
            }
        } while (sortBy < 0 || 2 < sortBy);

        boolean isAscending = askSortOrder("Ascending Sort", sc);

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
        String title = "Print Insurances";
        String subtitle = "Choose an option";

        String id = askId(title, sc);
        int idx = getIndex(data, id);
        if (idx == -1) {
            console.printResult(title, "Employee not exists", sc);
            return;
        }

        Employee currEmployee = data.getEmployees()[idx];
        Insurance[] currInsurances = currEmployee.getInsurances();

        int sortBy;
        do {
            String[] options = { "Insurance number", "Type", "Start date", "End date", "Rate" };
            sortBy = validateInt(console.printUI(title, subtitle, options, sc));

            if (sortBy < 0 || 4 < sortBy) {
                console.printResult(title, "Invalid choice", sc);
            }
        } while (sortBy < 0 || 4 < sortBy);

        boolean isAscending = askSortOrder("Ascending Sort", sc);

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

        String title = "Edit Department";
        String subtitle = "Choose a department";

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
            console.printResult(title, "No departments available", sc);
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
                console.printResult(title, "Department not exist", sc);
            }
        } while (!valid);

        Department department = departments[choice - 1];

        String[] options = { "Exit", "Name", "Phone", "Position" };

        do {
            String content = "Name: " + department.getName() + "\n"
                    + "Phone: " + department.getPhone() + "\n"
                    + "Position: " + department.getPosition();

            try {
                choice = Integer.parseInt(console.printUI(title, "Choose an option", content, options, sc).trim());
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
                        newName = console.printUI(title, "Set Name",
                                "Current name: " + department.getName(), "New name", sc).trim();

                        duplicate = false;

                        if (newName.isEmpty() || newName.length() > 30) {
                            console.printResult(title, "Name must be 1-30 characters", sc);
                            duplicate = true;
                        } else {
                            for (int i = 0; i < departments.length; i++) {
                                if (departments[i] != null && departments[i] != department
                                        && departments[i].getName().equalsIgnoreCase(newName)) {
                                    duplicate = true;
                                    console.printResult(title, "Department name already exists", sc);
                                    break;
                                }
                            }
                        }

                    } while (duplicate);

                    department.setName(newName);
                    console.printResult(title, "Set name successfully", sc);
                    break;
                }

                case 2: {
                    String newPhone;
                    do {
                        newPhone = console.printUI(title, "Set Phone",
                                "Current phone: " + department.getPhone(), "New phone", sc).trim();

                        if (!newPhone.matches("[0-9]{1,11}")) {
                            console.printResult(title, "Phone must be 1-11 digits", sc);
                        }
                    } while (!newPhone.matches("[0-9]{1,11}"));

                    department.setPhone(newPhone);
                    console.printResult(title, "Set phone successfully", sc);
                    break;
                }

                case 3: {
                    String newPosition;
                    do {
                        newPosition = console.printUI(title, "Set Position",
                                "Current position: " + department.getPosition(), "New position", sc).trim();

                        if (newPosition.isEmpty() || newPosition.length() > 61) {
                            console.printResult(title, "Position must be 1-61 characters", sc);
                        }
                    } while (newPosition.isEmpty() || newPosition.length() > 61);

                    department.setPosition(newPosition);
                    console.printResult(title, "Set position successfully", sc);
                    break;
                }

                default:
                    console.printResult(title, "Invalid choice", sc);
                    break;
            }

        } while (choice != 0);
    }

    public void editPosition(Scanner sc, Position[] positions) {
        ConsoleUI console = new ConsoleUI();

        String title = "Edit Position";
        String subtitle = "Choose a position";

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
            console.printResult(title, "No positions available", sc);
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
                console.printResult(title, "Position not exist", sc);
            }
        } while (!valid);

        Position position = positions[choice - 1];

        String[] options = { "Exit", "Name", "Position coefficient", "Responsibilities" };

        do {
            String content = "Name: " + position.getName() + "\n"
                    + "Position coefficient: " + position.getPositionCoefficient() + "\n"
                    + "Responsibilities: " + position.getResponsibilities();

            try {
                choice = Integer.parseInt(console.printUI(title, "Choose an option", content, options, sc).trim());
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
                        newName = console.printUI(title, "Set Name",
                                "Current name: " + position.getName(), "New name", sc).trim();

                        duplicate = false;

                        if (newName.isEmpty() || newName.length() > 30) {
                            console.printResult(title, "Name must be 1-30 characters", sc);
                            duplicate = true;
                        } else {
                            for (int i = 0; i < positions.length; i++) {
                                if (positions[i] != null && positions[i] != position
                                        && positions[i].getName().equalsIgnoreCase(newName)) {
                                    duplicate = true;
                                    console.printResult(title, "Position name already exists", sc);
                                    break;
                                }
                            }
                        }

                    } while (duplicate);

                    position.setName(newName);
                    console.printResult(title, "Set name successfully", sc);
                    break;
                }

                case 2: {
                    double newCoefficient;
                    boolean validCoefficient;

                    do {
                        String text = console.printUI(title, "Set Position coefficient",
                                "Current coefficient: " + position.getPositionCoefficient(),
                                "New coefficient", sc).trim();

                        try {
                            newCoefficient = Double.parseDouble(text);
                        } catch (NumberFormatException e) {
                            newCoefficient = -1;
                        }

                        validCoefficient = Double.isFinite(newCoefficient) && newCoefficient >= 0
                                && Double.toString(newCoefficient).length() <= 25;
                        if (!validCoefficient) {
                            console.printResult(title, "Coefficient must be a valid number >= 0", sc);
                        }
                    } while (!validCoefficient);

                    position.setPositionCoefficient(newCoefficient);
                    console.printResult(title, "Set position coefficient successfully", sc);
                    break;
                }

                case 3: {
                    String newResponsibilities;
                    do {
                        newResponsibilities = console.printUI(title, "Set Responsibilities",
                                "Current responsibilities: " + position.getResponsibilities(),
                                "New responsibilities", sc).trim();

                        if (newResponsibilities.isEmpty() || newResponsibilities.length() > 47) {
                            console.printResult(title, "Responsibilities must be 1-47 characters", sc);
                        }
                    } while (newResponsibilities.isEmpty() || newResponsibilities.length() > 47);

                    position.setResponsibilities(newResponsibilities);
                    console.printResult(title, "Set responsibilities successfully", sc);
                    break;
                }

                default:
                    console.printResult(title, "Invalid choice", sc);
                    break;
            }

        } while (choice != 0);
    }
}
