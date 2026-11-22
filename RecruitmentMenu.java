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

    private static Address createAddress(Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Tạo nhân viên";
        String subtitle = "Tạo địa chỉ";
        String contents = "";
        String input;

        input = "Số";
        String number = console.printUI(title, subtitle, contents, input, sc);

        input = "Đường";
        String street = console.printUI(title, subtitle, contents, input, sc);

        input = "Phường";
        String ward = console.printUI(title, subtitle, contents, input, sc);

        input = "Quận/Huyện";
        String district = console.printUI(title, subtitle, contents, input, sc);

        input = "Thành phố";
        String city = console.printUI(title, subtitle, contents, input, sc);

        return new Address(number, street, ward, district, city);
    }

    private static Candidate createCandidate(Scanner sc) {
        ConsoleUI console = new ConsoleUI();

        String title = "Tạo hồ sơ";
        String subtitle = "";
        String[] options = {};
        String input;

        input = "Tên";
        String firstName = console.printUI(title, subtitle, options, input, sc);

        input = "Họ";
        String lastName = console.printUI(title, subtitle, options, input, sc);

        input = "Email";
        String email = console.printUI(title, subtitle, options, input, sc);

        input = "Số điện thoại";
        String phone = console.printUI(title, subtitle, options, input, sc);

        input = "Ngày sinh (dd/MM/yyyy)";
        LocalDate dateOfBirth = console.printDateUI(title, subtitle, options, input, sc);

        input = "Giới tính";
        String gender = console.printUI(title, subtitle, options, input, sc);

        input = "Địa chỉ";
        Address address = createAddress(sc);

        return new Candidate(
                firstName,
                lastName,
                email,
                phone,
                dateOfBirth,
                gender,
                address);
    }

    public static void candidateMenu(Recruitment[] recruitments, Scanner sc) {
        Candidate candidate = new Candidate();

        while (true) {
            String[] options = {
                    "Quay lại",
                    "Xem danh sách tuyển dụng",
                    "Ứng tuyển",
                    "Tạo hồ sơ ứng viên"
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

                case 3:
                    candidate = createCandidate(sc);
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
        String title = "Ứng tuyển";
        String subtitle = "";

        if (candidate == null || candidate.getFirstName() == null) {
            console.printResult(title, "Chưa tạo hồ sơ ứng viên", sc);
            return;
        }

        int index = validateInt(console.printUI(
                title,
                subtitle,
                PrintTables.getRecruitments(recruitments),
                "Nhập số thứ tự đợt tuyển dụng",
                sc)) - 1;

        if (index < 0 || index >= recruitments.length) {
            console.printResult(title, "Đợt tuyển dụng không hợp lệ.", sc);
            return;
        }

        Recruitment recruitment = recruitments[index];

        if (recruitment == null) {
            console.printResult(title, "Không tìm thấy đợt tuyển dụng.", sc);
            return;
        }

        if (recruitment.getStatus() != Recruitment.RecruitmentStatus.OPEN) {
            console.printResult(
                    title, "Đợt tuyển dụng hiện không mở.", sc);
            return;
        }

        String coverLetter = console.printUI(title, "", "", "Thư xin việc", sc);

        String resume = console.printUI(
                title, "", "", "Tên tệp hoặc đường dẫn CV", sc);

        if (coverLetter.trim().isEmpty()
                || resume.trim().isEmpty()) {
            console.printResult(title, "Vui lòng nhập đầy đủ thông tin.", sc);
            return;
        }

        candidate.apply(recruitment, coverLetter, resume, sc);
    }

    private static void reviewApplications(
            Recruitment[] recruitments,
            Scanner sc) {
        String title = "Xét duyệt hồ sơ";
        String subtitle = "";
        String input = "";

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

        Recruitment recruitment = recruitments[index];

        if (recruitment == null) {
            console.printResult(
                    title, "Không tìm thấy đợt tuyển dụng.", sc);
            return;
        }

        while (true) {
            Application[] applications = recruitment.getApplications();

            int count = recruitment.getApplicationCount();

            if (count == 0) {
                console.printResult(
                        title, "Chưa có hồ sơ ứng tuyển nào.", sc);
                return;
            }

            String content = "";

            for (int i = 0; i < count; i++) {
                Application a = applications[i];

                if (a != null) {
                    content += "[" + (i + 1) + "]" + " Hồ sơ ứng tuyển của " +
                            a.getCandidate().getLastName() + " " +
                            a.getCandidate().getFirstName() + "\n";
                    content += "Ngày ứng tuyển: " + a.getApplyDate() + "\n";
                    content += "Thư xin việc: " + a.getCoverLetter() + "\n";
                    content += "CV: " + a.getResume() + "\n";
                    content += "Trạng thái: " + a.getStatus() + "\n\n";
                }
            }

            input = "Nhập số thứ tự hồ sơ (0 để quay lại)";
            int number = validateInt(console.printUI(
                    title,
                    subtitle,
                    content,
                    input,
                    sc));

            if (number == 0) {
                return;
            }

            int aIndex = number - 1;

            if (aIndex < 0 || aIndex >= count) {
                console.printResult(
                        title, "Hồ sơ ứng tuyển không hợp lệ.", sc);
                continue;
            }

            Application selected = applications[aIndex];

            if (selected == null) {
                console.printResult(
                        title, "Không tìm thấy hồ sơ ứng tuyển.", sc);
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

            subtitle = "Cập nhật trạng thái hồ sơ";
            input = console.printUI(
                    title, subtitle, options, sc);

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
                            title, "Lựa chọn không hợp lệ", sc);
                    continue;
            }

            if (choice >= 1 && choice <= 7) {
                console.printResult(
                        title,
                        "Cập nhật trạng thái hồ sơ thành công!", sc);
            }
        }
    }
}
