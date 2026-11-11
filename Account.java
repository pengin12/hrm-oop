import java.util.Scanner;

public abstract class Account {
    public enum AccountStatus {
        ACTIVE,
        INACTIVE,
        SUSPENDED,
        PENDING,
        LOCKED,
        TERMINATED
    }

    private String username;
    private String password;
    private AccountStatus status;

    public Account(String username, String password, AccountStatus status) {
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public void login() {
        System.out.println(this.username + " login...");
    }

    public void logout() {
        System.out.println(this.username + " logout");
    }

    public void resetPassword(Scanner sc) {
        System.out.println("Reset Password");
        String oldPassword;
        do {
            System.out.print("Old password: ");
            oldPassword = sc.nextLine();

            if (!getPassword().equals(oldPassword)) {
                System.out.println("Wrong password");
            }
        } while (!getPassword().equals(oldPassword));

        System.out.print("New password: ");
        setPassword(sc.nextLine());

        System.out.print("\033[H\033[2J");
        System.out.println("Reset password successfully");
    }

    public void getInfo() {
        System.out.println("Username: " + this.username + " | Status: " + this.status);
    }

    public void setInfo(String username, AccountStatus status) {
        this.username = username;
        this.status = status;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountStatus getStatus() {
        return this.status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}
