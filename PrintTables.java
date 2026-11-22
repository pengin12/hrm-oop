import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class PrintTables {
  private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  private static String fit(String value, int width) {
    String text = value == null ? "" : value;
    if (text.length() > width)
      text = text.substring(0, width);
    return String.format("%-" + width + "s", text);
  }

  private static String center(String value, int width) {
    if (value.length() > width) {
      return fit(value, width);
    }

    int leftPad = (width - value.length()) / 2;
    int rightPad = width - value.length() - leftPad;

    return String.format("%s%s%s", " ".repeat(leftPad), value, " ".repeat(rightPad));
  }

  private static void appendHeader(StringBuilder out, int[] widths, String... values) {
    out.append("|");
    for (int i = 0; i < widths.length; i++) {
      String value = i < values.length ? values[i] : "";
      out.append(" ").append(center(value, widths[i])).append(" |");
    }
    out.append('\n');
  }

  private static void appendRow(StringBuilder out, int[] widths, String... values) {
    out.append("|");
    for (int i = 0; i < widths.length; i++) {
      String value = i < values.length ? values[i] : "";
      out.append(" ").append(fit(value, widths[i])).append(" |");
    }
    out.append('\n');
  }

  private static void appendSeparator(StringBuilder out, int[] widths) {
    out.append("|");
    for (int width : widths) {
      out.append(" ");
      out.append("-".repeat(width)).append(" |");
    }
    out.append("\n");
  }

  private static void show(String title, String content, Scanner sc) {
    ConsoleUI console = new ConsoleUI();
    console.printResult(title, content, sc);
  }

  public static String getRecruitments(Recruitment[] recruitments) {
    StringBuilder content = new StringBuilder();
    if (recruitments == null) {
      content.append("Không tìm thấy danh sách tuyển dụng: ");
    } else {
      int[] w = { 5, 20, 30, 15, 12, 10 };
      appendHeader(content, w, "STT", "Chức vụ", "Phòng ban", "Số lượng", "Hạn nộp", "Trạng thái");
      appendSeparator(content, w);
      boolean found = false;
      int idx = 1;
      for (Recruitment e : recruitments) {
        if (e != null) {
          appendRow(content, w, String.valueOf(idx),
              String.format("%s", e.getPosition().getName()),
              String.format("%s", e.getDepartment().getName()),
              String.format("%s", e.getNumberOfPositions()),
              String.format("%s", e.getDeadline().format(dateFormatter)),
              String.format("%s", e.getStatus()));
          found = true;
          idx++;
        }
      }

      if (!found)
        content.append("Không tìm thấy dữ liệu tuyển dụng.\n");
    }

    return content.toString();
  }

  public static void printEmployees(Employee[] employees, Scanner sc) {
    StringBuilder content = new StringBuilder();
    if (employees == null) {
      content.append("Không tìm thấy danh sách nhân viên: ").append(sc);
    } else {
      int[] w = { 6, 10, 20, 10, 6, 20, 24 };
      appendHeader(content, w, "Mã NV", "Ngày vào", "Chuyên môn", "Trạng thái", "Mã thuế", "Chức vụ", "Phòng ban");
      appendSeparator(content, w);
      boolean found = false;
      for (Employee e : employees) {
        if (e != null) {
          appendRow(content, w, String.valueOf(e.getId()),
              String.format("%s", e.getHireDate().format(dateFormatter)),
              String.format("%s", e.getSpecialization()),
              String.format("%s", e.getStatus()),
              String.format("%s", e.getTaxId()),
              String.format("%s", e.getPosition().getName()),
              String.format("%s", e.getDepartment().getName()));
          found = true;
        }
      }

      if (!found)
        content.append("Không tìm thấy dữ liệu nhân viên.\n");
    }
    show("Danh sách nhân viên", content.toString(), sc);
  }

  public static void printDepartments(Department[] employees, Scanner sc) {
    StringBuilder content = new StringBuilder();
    if (employees == null) {
      content.append("Không tìm thấy danh sách phòng ban: ").append(sc);
    } else {
      int[] w = { 50, 20, 30 };
      appendHeader(content, w, "Tên phòng ban", "Điện thoại", "Chức vụ");
      appendSeparator(content, w);
      boolean found = false;
      for (Department e : employees) {
        if (e != null) {
          appendRow(content, w, String.valueOf(e.getName()),
              String.format("%s", e.getPhone()),
              String.format("%s", e.getPosition()));
          found = true;
        }
      }

      if (!found)
        content.append("Không tìm thấy dữ liệu phòng ban.\n");
    }
    show("Danh sách phòng ban", content.toString(), sc);
  }

  public static void printPositions(Position[] employees, Scanner sc) {
    StringBuilder content = new StringBuilder();
    if (employees == null) {
      content.append("Không tìm thấy danh sách chức vụ: ").append(sc);
    } else {
      int[] w = { 40, 8, 40 };
      appendHeader(content, w, "Tên chức vụ", "Hệ số", "Trách nhiệm");
      appendSeparator(content, w);
      boolean found = false;
      for (Position e : employees) {
        if (e != null) {
          appendRow(content, w, String.valueOf(e.getName()),
              String.format("%s", e.getPositionCoefficient()),
              String.format("%s", e.getResponsibilities()));
          found = true;
        }
      }

      if (!found)
        content.append("Không tìm thấy dữ liệu chức vụ.\n");
    }
    show("Danh sách chức vụ", content.toString(), sc);
  }

  public static void printEvaluations(Employee employee, String id, Scanner sc) {
    StringBuilder content = new StringBuilder();
    if (employee == null) {
      content.append("Không tìm thấy nhân viên: ").append(id);
    } else {
      int[] w = { 12, 8, 10, 12, 9, 14, 10, 10 };
      appendHeader(content, w, "Tháng", "Công việc", "Thái độ", "Kỷ luật",
          "Chất lượng", "Năng suất", "Làm nhóm", "Trung bình");
      appendSeparator(content, w);
      Evaluation[] rows = employee.getEvaluations();
      boolean found = false;
      if (rows != null)
        for (Evaluation e : rows) {
          if (e != null) {
            appendRow(content, w, String.valueOf(e.getMonth()),
                String.format("%.1f", e.getWorkScore()),
                String.format("%.1f", e.getAttitudeScore()),
                String.format("%.1f", e.getDisciplineScore()),
                String.format("%.1f", e.getWorkQuality()),
                String.format("%.1f", e.getProductivity()),
                String.format("%.1f", e.getTeamWork()),
                String.format("%.2f", e.calculateEvaluationScore()));
            found = true;
          }
        }

      if (!found)
        content.append("Không tìm thấy dữ liệu đánh giá.\n");
    }
    show("BẢNG ĐÁNH GIÁ - " + id, content.toString(), sc);
  }

  public static void printContracts(Employee employee, String id, Scanner sc) {
    StringBuilder content = new StringBuilder();
    if (employee == null) {
      content.append("Không tìm thấy nhân viên: ").append(id);
    } else {
      int[] w = { 18, 18, 25, 20 };
      appendHeader(content, w, "Ngày bắt đầu", "Ngày kết thúc", "Loại hợp đồng", "Trạng thái");
      appendSeparator(content, w);
      Contract[] rows = employee.getContracts();
      boolean found = false;
      if (rows != null)
        for (Contract c : rows) {
          if (c != null) {
            appendRow(content, w, String.valueOf(c.getStartDate()),
                String.valueOf(c.getEndDate()), String.valueOf(c.getType()),
                String.valueOf(c.getStatus()));
            found = true;
          }
        }

      if (!found)
        content.append("Không tìm thấy dữ liệu hợp đồng.\n");
    }
    show("BẢNG HỢP ĐỒNG - " + id, content.toString(), sc);
  }

  public static void printPayrolls(Employee employee, String id, Scanner sc) {
    StringBuilder content = new StringBuilder();
    if (employee == null) {
      content.append("Không tìm thấy nhân viên: ").append(id);
    } else {
      int[] w = { 12, 14, 12, 12, 12, 14, 14 };
      appendHeader(content, w, "Tháng/Năm", "Lương cơ bản", "Thưởng",
          "Phụ cấp", "Khấu trừ", "Lương tăng ca", "Thực nhận");
      appendSeparator(content, w);
      Payroll[] rows = employee.getPayrolls();
      boolean found = false;
      if (rows != null)
        for (Payroll p : rows) {
          if (p != null) {
            appendRow(content, w, p.getMonth().getMonthValue() + "/" + p.getMonth().getYear(),
                String.format("%.2f", p.getBaseSalary()),
                String.format("%.2f", p.getBonus()),
                String.format("%.2f", p.getAllowance()),
                String.format("%.2f", p.getDeduction()),
                String.format("%.2f", p.calculateOvertimePay()),
                String.format("%.2f", p.calculateNetSalary()));
            found = true;
          }
        }

      if (!found)
        content.append("Không tìm thấy dữ liệu bảng lương.\n");
    }
    show("BẢNG LƯƠNG - " + id, content.toString(), sc);
  }

  public static void printAttendances(Employee employee, String id, Scanner sc) {
    StringBuilder content = new StringBuilder();
    if (employee == null) {
      content.append("Không tìm thấy nhân viên: ").append(id);
    } else {
      int[] w = { 20, 20, 20, 16 };
      appendHeader(content, w, "Ngày", "Giờ vào", "Giờ ra", "Số giờ làm");
      appendSeparator(content, w);
      Attendance[] rows = employee.getAttendances();
      boolean found = false;
      if (rows != null)
        for (Attendance a : rows) {
          if (a != null) {
            appendRow(content, w, String.valueOf(a.getDate()),
                String.valueOf(a.getClockIn()), String.valueOf(a.getClockOut()),
                String.format("%.2f", a.calculateWorkedHours()));
            found = true;
          }
        }

      if (!found)
        content.append("Không tìm thấy dữ liệu chấm công.\n");
    }

    show("BẢNG CHẤM CÔNG - " + id, content.toString(), sc);
  }

  public static void printAbsences(Employee employee, String id, Scanner sc) {
    StringBuilder content = new StringBuilder();
    if (employee == null) {
      content.append("Không tìm thấy nhân viên: ").append(id);
    } else {
      int[] w = { 35, 18, 18, 16 };
      appendHeader(content, w, "Lý do", "Từ ngày", "Đến ngày", "Trạng thái");
      appendSeparator(content, w);
      Absence[] rows = employee.getAbsences();
      boolean found = false;
      if (rows != null)
        for (Absence a : rows) {
          if (a != null) {
            appendRow(content, w, String.valueOf(a.getReason()),
                String.valueOf(a.getFromDate()), String.valueOf(a.getToDate()),
                String.valueOf(a.getStatus()));
            found = true;
          }
        }

      if (!found)
        content.append("Không tìm thấy dữ liệu nghỉ phép.\n");
    }
    show("BẢNG NGHỈ PHÉP - " + id, content.toString(), sc);
  }

  public static void printResignations(Employee employee, String id, Scanner sc) {
    StringBuilder content = new StringBuilder();
    if (employee == null) {
      content.append("Không tìm thấy nhân viên: ").append(id);
    } else {
      int[] w = { 45, 20, 18 };
      appendHeader(content, w, "Lý do", "Ngày", "Trạng thái");
      appendSeparator(content, w);
      Resignation[] rows = employee.getResignations();
      boolean found = false;
      if (rows != null)
        for (Resignation r : rows) {
          if (r != null) {
            appendRow(content, w, String.valueOf(r.getReason()),
                String.valueOf(r.getDate()), String.valueOf(r.getStatus()));
            found = true;
          }
        }

      if (!found)
        content.append("Không tìm thấy dữ liệu thôi việc.\n");
    }
    show("BẢNG THÔI VIỆC - " + id, content.toString(), sc);
  }

  public static void printInsurances(Employee employee, String id, Scanner sc) {
    StringBuilder content = new StringBuilder();
    if (employee == null) {
      content.append("Không tìm thấy nhân viên: ").append(id);
    } else {
      int[] w = { 22, 20, 18, 18, 12 };
      appendHeader(content, w, "Mã bảo hiểm", "Loại", "Ngày bắt đầu", "Ngày kết thúc", "Tỷ lệ");
      appendSeparator(content, w);
      Insurance[] rows = employee.getInsurances();
      boolean found = false;
      if (rows != null)
        for (Insurance i : rows) {
          if (i != null) {
            appendRow(content, w, String.valueOf(i.getInsuranceNumber()),
                String.valueOf(i.getType()), String.valueOf(i.getStartDate()),
                String.valueOf(i.getEndDate()), String.format("%.2f", i.getRate()));
            found = true;
          }
        }

      if (!found)
        content.append("Không tìm thấy dữ liệu bảo hiểm.\n");
    }
    show("BẢNG BẢO HIỂM - " + id, content.toString(), sc);
  }
}
