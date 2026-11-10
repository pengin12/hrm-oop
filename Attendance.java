import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Attendance {
    private LocalDate date;
    private LocalTime clockIn;
    private LocalTime clockOut;

    public Attendance(LocalDate date, LocalTime clockIn, LocalTime clockOut) {
        this.date = date;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
    }

    public double calculateWorkedHours() {
        long workedMinutes = Duration.between(clockIn, clockOut).toMinutes();

        if (workedMinutes < 0) {
            workedMinutes += 24 * 60;
        }

        return workedMinutes / 60.0;
    }

    public void displayAttendance() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        System.out.println("Ngay: " + date.format(dateFormatter));
        System.out.println("Gio vao: " + clockIn.format(timeFormatter));
        System.out.println("Gio ra: " + clockOut.format(timeFormatter));
        System.out.println("So gio lam: " + calculateWorkedHours());
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getClockIn() {
        return clockIn;
    }

    public void setClockIn(LocalTime clockIn) {
        this.clockIn = clockIn;
    }

    public LocalTime getClockOut() {
        return clockOut;
    }

    public void setClockOut(LocalTime clockOut) {
        this.clockOut = clockOut;
    }
}
