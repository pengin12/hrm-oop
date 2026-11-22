
import java.time.LocalDate;
import java.util.Scanner;

public class Candidate extends Person {

    public void apply(Recruitment recruitment, String coverLetter,
            String resume, Scanner sc) {

        ConsoleUI console = new ConsoleUI();
        String title = "Apply Recruitment";

        if (recruitment == null) {
            console.printResult(title, "Khong co dot tuyen dung", sc);
            return;
        }

        if (recruitment.getStatus() != Recruitment.RecruitmentStatus.OPEN) {
            console.printResult(title, "Dot tuyen dung da dong!", sc);
            return;
        }

        if (recruitment.getDeadline() != null &&
                LocalDate.now().isAfter(recruitment.getDeadline())) {
            console.printResult(title, "Da het han ung tuyen!", sc);
            return;
        }

        Application application = new Application(
                LocalDate.now(),
                coverLetter,
                resume,
                Application.ApplicationStatus.SUBMITTED,
                this);

        application.setCandidate(this);
        recruitment.addApplication(application);

        console.printResult(title, "Ung tuyen thanh cong!", sc);
    }
}
