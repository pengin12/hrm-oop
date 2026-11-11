public class AdminAccount extends Account {
    public AdminAccount(String username, String password, AccountStatus status) {
        super(username, password, status);
    }

    public void addEmployee(Employee[] employees, Employee employee) {
        for (int i = 0; i < employees.length; i++) {
            if (employees[i] == null) {
                employees[i] = employee;
                System.out.println("Da them tai khoan: " + employee.getFirstName());
                return;
            }
        }
        System.out.println("Mang tai khoan da day, khong them duoc.");
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

    public void editEmployee(Account[] accounts, String username) {
        System.out.println("Edit employee");
    }

    public void printEmployee(Employee[] employees) {
        System.out.println("Print Employee");
    }

    public void printDepartments(Department[] departments) {
        System.out.println("Print Departments");
    }

    public void printPayrolls() {
        System.out.println("Print Payroll");
    }

    public void searchEmployee(Employee[] employees) {
        System.out.println("Search employee");
    }

    public void searchDepartment(Department[] departments) {
        System.out.println("Search departments");
    }
}
