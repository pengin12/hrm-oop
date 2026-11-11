import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AdminAccount extends Account {
    public AdminAccount(String username, String password, AccountStatus status) {
        super(username, password, status);
    }

    public void addEmployee(Employee[] employees, Employee employee) {
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] == null) {
                employees[i] = employee;
                System.out.println("Da them nhan vien: " + employee.getId()
                        + " - " + employee.getFirstName() + " " + employee.getLastName());
                return;
            }
        }
        System.out.println("Mang nhan vien da day, khong them duoc.");
    }

    public void listEmployee(Account[] accounts) {
        System.out.println("Danh sach tai khoan:");
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] != null) {
                System.out.print((i + 1) + ". ");
                accounts[i].getInfo();
            }
        }
    }

    public void removeEmployee(Account[] accounts, String username) {
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] != null && accounts[i].getUsername().equals(username)) {
                accounts[i] = null;
                System.out.println("Da xoa tai khoan: " + username);
                return;
            }
        }
        System.out.println("Khong tim thay tai khoan: " + username);
    }

    public void replyRequest(Account[] accounts, String username, String reply) {
        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] != null && accounts[i].getUsername().equals(username)) {
                System.out.println("Admin phan hoi " + username + ": " + reply);
                return;
            }
        }
        System.out.println("Khong tim thay tai khoan: " + username);
    }

    public Employee searchEmployee(Employee[] employees, String id) {
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] != null && employees[i].getId().equals(id)) {
                return employees[i];
            }
        }
        return null;
    }

    public void printPositions(Position[] positions) {
        System.out.println("Danh sach vi tri:");
        for (int i = 0; i < positions.length; i++) {
            if (positions[i] != null) {
                System.out.println((i + 1) + ". " + positions[i].getName()
                        + " | Position Coefficient: " + positions[i].getPositionCoefficient()
                        + " | Responsibilities: " + positions[i].getResponsibilities());
            }
        }
    }

    public void printDepartments(Department[] departments) {
        System.out.println("Danh sach phong ban:");
        for (int i = 0; i < departments.length; i++) {
            if (departments[i] != null) {
                System.out.println((i + 1) + ". " + departments[i].getName()
                        + " | SDT: " + departments[i].getPhone()
                        + " | Vi tri: " + departments[i].getPosition());
            }
        }
    }

    private void editEmployeeHireDate(Scanner sc, Employee employee) {
        System.out.println("Set Hire Date");

        System.out.println("Current hire date: " + employee.getHireDate());

        System.out.print("Hire date (dd/MM/yyyy): ");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate hireDate = LocalDate.parse(sc.nextLine(), dateFormatter);
        employee.setHireDate(hireDate);

        System.out.print("\033[H\033[2J");
        System.out.println("Set hire date successfully");
    }

    private void editEmployeeSpecialization(Scanner sc, Employee employee) {
        System.out.println("Set Specialization");

        System.out.println("Current specialization: " + employee.getSpecialization());

        System.out.print("Specialization: ");
        employee.setSpecialization(sc.nextLine());

        System.out.print("\033[H\033[2J");
        System.out.println("Set specialization successfully");
    }

    private void editEmployeeStatus(Scanner sc, Employee employee) {
        System.out.println("Set Employee Status");

        System.out.println("Current employee status: " + employee.getStatus());

        int statusNum = 0;
        do {
            System.out.println("[1] Working");
            System.out.println("[2] On leave");
            System.out.println("[3] Resigned");
            System.out.print("Status:");

            statusNum = Integer.parseInt(sc.nextLine());
        } while (statusNum < 1 || 3 < statusNum);

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

        System.out.print("\033[H\033[2J");
        System.out.println("Set status successfully");
    }

    private void editEmployeeTaxId(Scanner sc, Employee employee) {
        System.out.println("Set Tax Id");

        System.out.println("Current tax id: " + employee.getTaxId());

        System.out.print("Tax id: ");
        employee.setTaxId(sc.nextLine());

        System.out.print("\033[H\033[2J");
        System.out.println("Set tax id successfully");
    }

    private void editEmployeePosition(Scanner sc, Employee employee, Position[] positions) {
        System.out.println("Set Position");

        System.out.println("Current position: " + employee.getPosition().getName());

        int choice;
        do {
            printPositions(positions);
            System.out.print("Position number: ");
            choice = Integer.parseInt(sc.nextLine());

            if (positions[choice] == null) {
                System.out.print("\033[H\033[2J");
                System.out.println("Position not exist");
            }
        } while (positions[choice] == null);

        System.out.print("\033[H\033[2J");
        System.out.println("Set position successfully");
    }

    private void editEmployeeDepartment(Scanner sc, Employee employee, Department[] departments) {
        System.out.println("Set Department");

        System.out.println("Current department: " + employee.getDepartment().getName());

        int choice;
        do {
            printDepartments(departments);
            System.out.print("Department number: ");
            choice = Integer.parseInt(sc.nextLine());

            if (departments[choice] == null) {
                System.out.print("\033[H\033[2J");
                System.out.println("Department not exist");
            }
        } while (departments[choice] == null);

        System.out.print("\033[H\033[2J");
        System.out.println("Set department successfully");
    }

    public void editEmployee(Scanner sc, Employee[] employees, String id, Position[] positions,
            Department[] departments) {
        Employee employee = this.searchEmployee(employees, id);
        if (employee == null) {
            System.out.println("Khong tim thay nhan vien: " + id);
            return;
        }

        int setChoice;
        do {
            System.out.println("[0] Exit");
            System.out.println("[1] Hire date");
            System.out.println("[2] Specialization");
            System.out.println("[3] Status");
            System.out.println("[4] Tax id");
            System.out.println("[5] Position");
            System.out.println("[6] Department");
            System.out.print("Choice: ");
            setChoice = Integer.parseInt(sc.nextLine());

            System.out.print("\033[H\033[2J");

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
                    System.out.print("\033[H\033[2J");
                    System.out.println("Wrong input");
                    break;
            }
        } while (setChoice != 0);

        System.out.println("Da sua nhan vien: " + id);
    }

    public void printEmployees(Employee[] employees) {
        System.out.println("Danh sach nhan vien:");
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] != null) {
                System.out.println((i + 1) + ". " + employees[i].getId()
                        + " | " + employees[i].getFirstName() + " " + employees[i].getLastName()
                        + " | " + employees[i].getDepartment().getName()
                        + " | " + employees[i].getStatus());
            }
        }
    }

    public void printPayrolls(Employee[] employees, String id) {
        Employee employee = this.searchEmployee(employees, id);
        if (employee == null) {
            System.out.println("Khong tim thay nhan vien: " + id);
            return;
        }
        System.out.println("Bang luong cua " + employee.getId() + ":");
        Payroll[] payrolls = employee.getPayrolls();
        boolean found = false;
        for (int i = 0; i < payrolls.length; i++) {
            if (payrolls[i] != null) {
                payrolls[i].displayPayroll();
                found = true;
            }
        }
        if (!found) {
            System.out.println("Chua co bang luong.");
        }
    }

    public void searchEmployee(Employee[] employees) {
        System.out.println("Search employee");
    }

    public void searchDepartment(Department[] departments) {
        System.out.println("Search departments");
    }

    public Account createAccount(Scanner sc) {
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
}
