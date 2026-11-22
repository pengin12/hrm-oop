import java.time.LocalDate;
import java.util.Scanner;

public class RecruitmentMenu {

    private static ConsoleUI console = new ConsoleUI();

    private static int inputNumber(String title, String message, Scanner sc) {
        String input = console.printUI(
                title, "", "", message, sc);

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static void showRecruitments(Recruitment[] recruitments, Scanner sc) {
        String content = "";

        for (int i = 0; i < recruitments.length; i++) {
            Recruitment r = recruitments[i];

            if (r != null) {
                String department = "Chưa xác định";
                String position = "Chưa xác định";

                if (r.getDepartment() != null) {
                    department = r.getDepartment().getName();
                }

                if (r.getPosition() != null) {
                    position = r.getPosition().getName();
                }

                content += (i + 1) + ". " + position + "\n";
                content += "Phòng ban: " + department + "\n";
                content += "Số lượng tuyển: " + r.getNumberOfPositions() + "\n";
                content += "Hạn nộp hồ sơ: " + r.getDeadline() + "\n";
                content += "Trạng thái: " + r.getStatus() + "\n\n";
            }
        }

        if (content.isEmpty()) {
            content = "Chưa có đợt tuyển dụng nào.";
        }

        console.printResult("Danh sách tuyển dụng", content, sc);
    }

    public static void recruitmentMenu(Recruitment[] recruitments,
            Department[] departments,
            Position[] positions,
            Scanner sc) {
        while (true) {
            String[] options = {
                    "Quay lại",
                    "Xem danh sách tuyển dụng",
                    "Tạo đợt tuyển dụng",
                    "Đóng đợt tuyển dụng",
                    "Hủy đợt tuyển dụng",
                    "Xét duyệt hồ sơ ứng tuyển"
            };

            String input = console.printUI(
                    "Quản lý tuyển dụng", "", options, sc);

            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 0:
                    return;

                case 1:
                    showRecruitments(recruitments, sc);
                    break;

                case 2:
                    createRecruitment(
                            recruitments, departments, positions, sc);
                    break;

                case 3:
                    changeStatus(recruitments, true, sc);
                    break;

                case 4:
                    changeStatus(recruitments, false, sc);
                    break;

                case 5:
                    reviewApplications(recruitments, sc);
                    break;

                default:
                    console.printResult(
                            "Lỗi", "Lựa chọn không hợp lệ", sc);
            }
        }
    }

    private static void createRecruitment(
            Recruitment[] recruitments,
            Department[] departments,
            Position[] positions,
            Scanner sc) {

        int index = -1;

        for (int i = 0; i < recruitments.length; i++) {
            if (recruitments[i] == null) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            console.printResult(
                    "Tạo đợt tuyển dụng", "Danh sách tuyển dụng đã đầy.", sc);
            return;
        }

        String content = "";

        for (int i = 0; i < departments.length; i++) {
            if (departments[i] != null) {
                content += (i + 1) + ". "
                        + departments[i].getName() + "\n";
            }
        }

        int d = inputNumber(
                "Chọn phòng ban", content + "\nNhập số thứ tự phòng ban", sc) - 1;

        if (d < 0 || d >= departments.length) {
            console.printResult(
                    "Lỗi", "Phòng ban không hợp lệ.", sc);
            return;
        }

        if (departments[d] == null) {
            console.printResult(
                    "Lỗi", "Không tìm thấy phòng ban.", sc);
            return;
        }

        content = "";

        for (int i = 0; i < positions.length; i++) {
            if (positions[i] != null) {
                content += (i + 1) + ". "
                        + positions[i].getName() + "\n";
            }
        }

        int p = inputNumber(
                "Chọn vị trí tuyển dụng", content + "\nNhập số thứ tự vị trí", sc) - 1;

        if (p < 0 || p >= positions.length) {
            console.printResult(
                    "Lỗi", "Vị trí tuyển dụng không hợp lệ.", sc);
            return;
        }

        if (positions[p] == null) {
            console.printResult(
                    "Lỗi", "Không tìm thấy vị trí tuyển dụng.", sc);
            return;
        }

        int slots = inputNumber(
                "Tạo đợt tuyển dụng", "Số lượng nhân viên cần tuyển", sc);

        if (slots <= 0) {
            console.printResult(
                    "Lỗi", "Số lượng tuyển dụng không hợp lệ.", sc);
            return;
        }

        LocalDate deadline = console.printDateUI(
                "Tạo đợt tuyển dụng", "",
                "", "Hạn nộp hồ sơ (dd/MM/yyyy)", sc);

        if (deadline == null) {
            console.printResult(
                    "Lỗi", "Hạn nộp hồ sơ không hợp lệ.", sc);
            return;
        }

        if (deadline.isBefore(LocalDate.now())) {
            console.printResult(
                    "Lỗi", "Hạn nộp hồ sơ không được ở quá khứ.", sc);
            return;
        }

        Recruitment recruitment = new Recruitment(
                slots,
                deadline,
                Recruitment.RecruitmentStatus.OPEN,
                departments[d],
                positions[p]);

        recruitments[index] = recruitment;

        console.printResult(
                "Tạo đợt tuyển dụng",
                "Tạo đợt tuyển dụng thành công!", sc);
    }

    private static void changeStatus(Recruitment[] recruitments,
            boolean close,
            Scanner sc) {

        showRecruitments(recruitments, sc);

        int index = inputNumber(
                "Quản lý đợt tuyển dụng", "Nhập số thứ tự đợt tuyển dụng", sc) - 1;

        if (index < 0 || index >= recruitments.length) {
            console.printResult(
                    "Lỗi", "Đợt tuyển dụng không hợp lệ.", sc);
            return;
        }

        if (recruitments[index] == null) {
            console.printResult(
                    "Lỗi", "Không tìm thấy đợt tuyển dụng.", sc);
            return;
        }

        if (close) {
            recruitments[index].close();
        } else {
            recruitments[index].setStatus(
                    Recruitment.RecruitmentStatus.CANCELLED);
        }

        console.printResult(
                "Quản lý đợt tuyển dụng",
                "Cập nhật trạng thái thành công!", sc);
    }

    public static void candidateMenu(Candidate candidate,
            Recruitment[] recruitments,
            Scanner sc) {

        if (candidate == null) {
            console.printResult(
                    "Ứng viên", "Không tìm thấy thông tin ứng viên.", sc);
            return;
        }

        while (true) {
            String[] options = {
                    "Quay lại",
                    "Xem danh sách tuyển dụng",
                    "Ứng tuyển"
            };

            String input = console.printUI(
                    "Tuyển dụng dành cho ứng viên", "", options, sc);

            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 0:
                    return;

                case 1:
                    showRecruitments(recruitments, sc);
                    break;

                case 2:
                    applyRecruitment(candidate, recruitments, sc);
                    break;

                default:
                    console.printResult(
                            "Lỗi", "Lựa chọn không hợp lệ", sc);
            }
        }
    }

    private static void applyRecruitment(
            Candidate candidate,
            Recruitment[] recruitments,
            Scanner sc) {

        showRecruitments(recruitments, sc);

        int index = inputNumber(
                "Ứng tuyển", "Nhập số thứ tự đợt tuyển dụng", sc) - 1;

        if (index < 0 || index >= recruitments.length) {
            console.printResult(
                    "Lỗi", "Đợt tuyển dụng không hợp lệ.", sc);
            return;
        }

        Recruitment recruitment = recruitments[index];

        if (recruitment == null) {
            console.printResult(
                    "Lỗi", "Không tìm thấy đợt tuyển dụng.", sc);
            return;
        }

        if (recruitment.getStatus() != Recruitment.RecruitmentStatus.OPEN) {
            console.printResult(
                    "Lỗi", "Đợt tuyển dụng hiện không mở.", sc);
            return;
        }

        String coverLetter = console.printUI(
                "Ứng tuyển", "", "", "Thư xin việc", sc);

        String resume = console.printUI(
                "Ứng tuyển", "", "", "Tên tệp hoặc đường dẫn CV", sc);

        if (coverLetter.trim().isEmpty()
                || resume.trim().isEmpty()) {
            console.printResult(
                    "Lỗi", "Vui lòng nhập đầy đủ thông tin.", sc);
            return;
        }

        candidate.apply(recruitment, coverLetter, resume, sc);
    }

    private static void reviewApplications(
            Recruitment[] recruitments,
            Scanner sc) {

        showRecruitments(recruitments, sc);

        int index = inputNumber(
                "Xét duyệt hồ sơ", "Nhập số thứ tự đợt tuyển dụng", sc) - 1;

        if (index < 0 || index >= recruitments.length) {
            console.printResult(
                    "Lỗi", "Đợt tuyển dụng không hợp lệ.", sc);
            return;
        }

        Recruitment recruitment = recruitments[index];

        if (recruitment == null) {
            console.printResult(
                    "Lỗi", "Không tìm thấy đợt tuyển dụng.", sc);
            return;
        }

        while (true) {
            Application[] applications = recruitment.getApplications();

            int count = recruitment.getApplicationCount();

            if (count == 0) {
                console.printResult(
                        "Hồ sơ ứng tuyển", "Chưa có hồ sơ ứng tuyển nào.", sc);
                return;
            }

            String content = "";

            for (int i = 0; i < count; i++) {
                Application a = applications[i];

                if (a != null) {
                    content += (i + 1) + ". Hồ sơ ứng tuyển\n";
                    content += "Ngày ứng tuyển: "
                            + a.getApplyDate() + "\n";
                    content += "Thư xin việc: "
                            + a.getCoverLetter() + "\n";
                    content += "CV: "
                            + a.getResume() + "\n";
                    content += "Trạng thái: "
                            + a.getStatus() + "\n\n";
                }
            }

            console.printResult(
                    "Danh sách hồ sơ ứng tuyển", content, sc);

            int number = inputNumber(
                    "Xét duyệt hồ sơ",
                    "Nhập số thứ tự hồ sơ (0 để quay lại)", sc);

            if (number == 0) {
                return;
            }

            int aIndex = number - 1;

            if (aIndex < 0 || aIndex >= count) {
                console.printResult(
                        "Lỗi", "Hồ sơ ứng tuyển không hợp lệ.", sc);
                continue;
            }

            Application selected = applications[aIndex];

            if (selected == null) {
                console.printResult(
                        "Lỗi", "Không tìm thấy hồ sơ ứng tuyển.", sc);
                continue;
            }

            String[] options = {
                    "Quay lại",
                    "Đang xem xét",
                    "Đưa vào danh sách rút gọn",
                    "Phỏng vấn",
                    "Đã được chọn",
                    "Đã gửi thư mời làm việc",
                    "Đã chấp nhận",
                    "Từ chối"
            };

            String input = console.printUI(
                    "Cập nhật trạng thái hồ sơ", "", options, sc);

            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 0:
                    break;

                case 1:
                    selected.setStatus(
                            Application.ApplicationStatus.UNDER_REVIEW);
                    break;

                case 2:
                    selected.setStatus(
                            Application.ApplicationStatus.SHORTLISTED);
                    break;

                case 3:
                    selected.setStatus(
                            Application.ApplicationStatus.INTERVIEW);
                    break;

                case 4:
                    selected.setStatus(
                            Application.ApplicationStatus.SELECTED);
                    break;

                case 5:
                    selected.setStatus(
                            Application.ApplicationStatus.OFFERED);
                    break;

                case 6:
                    selected.setStatus(
                            Application.ApplicationStatus.ACCEPTED);
                    break;

                case 7:
                    selected.setStatus(
                            Application.ApplicationStatus.REJECTED);
                    break;

                default:
                    console.printResult(
                            "Lỗi", "Lựa chọn không hợp lệ", sc);
                    continue;
            }

            if (choice >= 1 && choice <= 7) {
                console.printResult(
                        "Xét duyệt hồ sơ",
                        "Cập nhật trạng thái hồ sơ thành công!", sc);
            }
        }
    }
}
