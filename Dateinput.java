import java.time.LocalDate;
import java.util.Scanner;

public class Dateinput {
    public static LocalDate inputDate(Scanner sc) {
        Datacheck checker = new Datacheck("^(\\d{2})/(\\d{2})/(\\d{4})$");
        while (true) {
            System.out.print("inputdate (dd//MM//yyyy): ");
            String date = sc.nextLine();
            if (checker.check(date)) {
                int day = Integer.parseInt(date.substring(0, 2));
                int month = Integer.parseInt(date.substring(3, 5));
                int year = Integer.parseInt(date.substring(6, 10));
                return LocalDate.of(year, month, day);
            } else {
                System.out.println("Error: Invalid date format! Please enter again");
            }

        }
    }
}
