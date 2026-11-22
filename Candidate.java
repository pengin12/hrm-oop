import java.time.LocalDate;
import java.util.Scanner;

public class Candidate extends Person {
    public Candidate() {
    }

    public Candidate(
            String firstName,
            String lastName,
            String email,
            String phone,
            LocalDate dateOfBirth,
            String gender,
            Address address) {
        super(firstName, lastName, email, phone, dateOfBirth, gender, address);
    }

    public void apply(Recruitment recruitment, String coverLetter,
            String resume, Scanner sc) {

        ConsoleUI console = new ConsoleUI();
        String title = "Nộp hồ sơ ứng tuyển";

        if (recruitment == null) {
            console.printResult(title, "Không có đợt tuyển dụng", sc);
            return;
        }

        if (recruitment.getStatus() != Recruitment.RecruitmentStatus.OPEN) {
            console.printResult(title, "Đợt tuyển dụng đã đóng!", sc);
            return;
        }

        if (recruitment.getDeadline() != null &&
                LocalDate.now().isAfter(recruitment.getDeadline())) {
            console.printResult(title, "Đã hết hạn ứng tuyển!", sc);
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

        console.printResult(title, "Ứng tuyển thành công!", sc);
    }
}
