import java.time.LocalDate;
import java.util.Scanner;

public class RecruitmentMenu {

    private static ConsoleUI console = new ConsoleUI();

    private static int validateInt(String str) {
        if (str.matches("[0-9]+")) {
            return Integer.parseInt(str);
        }

        return -1;
    }

    private static int getOccupied(Object[] objArr) {
        int occupied = 0;
        for (Object obj : objArr) {
            if (obj != null) {
                occupied++;
            }
        }

        return occupied;
    }

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
        console.printResult(
                "Danh sách tuyển dụng",
                PrintTables.getRecruitments(recruitments),
                sc);
    }

    public static void recruitmentMenu(DataHandler data,
            Scanner sc) {
        String title = "Quản lý tuyển dụng";
        String subtitle = "";

        while (true) {
            String[] options = {
                    "Quay lại",
                    "Xem danh sách tuyển dụng",
                    "Tạo đợt tuyển dụng",
                    "Đóng đợt tuyển dụng",
                    "Hủy đợt tuyển dụng",
                    "Xét duyệt hồ sơ ứng tuyển"
            };

            int choice = validateInt(console.printUI(title, subtitle, options, sc));

            switch (choice) {
                case 0:
                    return;

                case 1:
                    showRecruitments(data.getRecruitments(), sc);
                    break;

                case 2:
                    createRecruitment(
                            data.getRecruitments(),
                            data.getDepartments(),
                            data.getPositions(),
                            sc);
                    break;

                case 3:
                    changeStatus(data.getRecruitments(), true, sc);
                    break;

                case 4:
                    changeStatus(data.getRecruitments(), false, sc);
                    break;

                case 5:
                    reviewApplications(data.getRecruitments(), sc);
                    break;

                default:
                    console.printResult(
                            "Lỗi", "Lựa chọn không hợp lệ", sc);
                    break;
            }
        }
    }

    private static void createRecruitment(
            Recruitment[] recruitments,
            Department[] departments,
            Position[] positions,
            Scanner sc) {
        String title = "Tạo đợt tuyển dụng";
        String subtitle = "";

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

        int departmentsOccupied = getOccupied(departments);
        String[] departmentOpts = new String[departmentsOccupied];
        int idx = 0;
        for (int i = 0; i < departmentsOccupied; i++) {
            if (departments[i] != null && departments[i].getName() != null) {
                departmentOpts[idx] = departments[i].getName();
                idx++;
            }
        }

        subtitle = "Hãy chọn phòng ban";
        int d = validateInt(console.printUI(title, subtitle, departmentOpts, sc));
        if (d < 0 || d >= departments.length) {
            console.printResult(title, "Phòng ban không hợp lệ.", sc);
            return;
        }

        if (departments[d] == null) {
            console.printResult(title, "Không tìm thấy phòng ban.", sc);
            return;
        }

        int positionsOccupied = getOccupied(positions);
        String[] positionOpts = new String[positionsOccupied];
        idx = 0;
        for (int i = 0; i < positionsOccupied; i++) {
            if (positions[i] != null && positions[i].getName() != null) {
                positionOpts[idx] = positions[i].getName();
                idx++;
            }
        }

        subtitle = "Hãy chọn chức vụ";
        int p = validateInt(console.printUI(title, subtitle, positionOpts, sc));
        if (p < 0 || p >= positions.length) {
            console.printResult(title, "Vị trí tuyển dụng không hợp lệ.", sc);
            return;
        }

        if (positions[p] == null) {
            console.printResult(title, "Không tìm thấy vị trí tuyển dụng.", sc);
            return;
        }

        subtitle = "";
        int slots = validateInt(console.printUI(
                title,
                subtitle,
                content,
                "Số lượng nhân viên cần tuyển",
                sc));
        if (slots <= 0) {
            console.printResult(title, "Số lượng tuyển dụng không hợp lệ.", sc);
            return;
        }

        LocalDate deadline = console.printDateUI(
                "Tạo đợt tuyển dụng", "",
                "", "Hạn nộp hồ sơ (dd/MM/yyyy)", sc);

        if (deadline == null) {
            console.printResult(title, "Hạn nộp hồ sơ không hợp lệ.", sc);
            return;
        }

        if (deadline.isBefore(LocalDate.now())) {
            console.printResult(title, "Hạn nộp hồ sơ không được ở quá khứ.", sc);
            return;
        }

        Recruitment recruitment = new Recruitment(
                slots,
                deadline,
                Recruitment.RecruitmentStatus.OPEN,
                departments[d],
                positions[p]);

        recruitments[index] = recruitment;

        console.printResult(title, "Tạo đợt tuyển dụng thành công!", sc);
    }

    private static void changeStatus(Recruitment[] recruitments,
            boolean close,
            Scanner sc) {
        String title = "Quản lý đợt tuyển dụng";

        int index = validateInt(console.printUI(
                title,
                "",
                "\n" + PrintTables.getRecruitments(recruitments),
                sc)) - 1;

        if (index < 0 || index >= recruitments.length) {
            console.printResult(
                    title, "Đợt tuyển dụng không hợp lệ.", sc);
            return;
        }

        if (recruitments[index] == null) {
            console.printResult(
                    title, "Không tìm thấy đợt tuyển dụng.", sc);
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
        int index = validateInt(console.printUI(
                "Xét duyệt hồ sơ",
                "",
                "\n" + PrintTables.getRecruitments(recruitments),
                sc)) - 1;

        if (index < 0 || index >= recruitments.length) {
            console.printResult(
                    "Xét duyệt hồ sơ", "Đợt tuyển dụng không hợp lệ.", sc);
            return;
        }

        Recruitment recruitment = recruitments[index];

        if (recruitment == null) {
            console.printResult(
                    "Xét duyệt hồ sơ", "Không tìm thấy đợt tuyển dụng.", sc);
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
