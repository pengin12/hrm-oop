import java.io.DataInput;
import java.time.LocalDate;
import java.util.Scanner;

public class ConsoleUI {
  private static final int CONTENT_WIDTH = 120;

  private void printCentered(String content) {
    int leftPadding = (CONTENT_WIDTH - content.length()) / 2;
    int rightPadding = CONTENT_WIDTH - content.length() - leftPadding;

    System.out.printf("║%" + leftPadding + "s" +
        "\u001B[1m" + "%s" + "\u001B[0m" +
        "%" + rightPadding + "s║%n",
        "", content, "");
  }

  private void printHeader(String title) {
    System.out.print("\033[H\033[2J");
    System.out.println("╔" + "═".repeat(CONTENT_WIDTH) + "╗");
    printCentered(title);
    System.out.println("╠" + "═".repeat(CONTENT_WIDTH) + "╣");
  }

  private void printSubtitle(String subtitle) {
    int rightPadding = CONTENT_WIDTH - 1 - subtitle.length();
    System.out.printf("║ " +
        "\u001B[3m" + "%s" + "\u001B[0m" +
        " ".repeat(rightPadding) + "║%n", subtitle);
  }

  private void printContent(String content) {
    int rightPadding = CONTENT_WIDTH - 1 - content.length();
    System.out.printf("║ %s" + " ".repeat(rightPadding) + "║%n", content);
  }

  private String printChoice(Scanner sc) {
    int rightPadding = CONTENT_WIDTH - 1 - 8;
    System.out.printf("║ Choice: " + " ".repeat(rightPadding) + "║%n");
    System.out.println("╚" + "═".repeat(CONTENT_WIDTH) + "╝");

    System.out.print("\u001B[1A");
    System.out.print("\u001B[1A");
    System.out.print("\u001B[10C");
    System.out.flush();

    return sc.nextLine();
  }

  private String printChoice(String input, Scanner sc) {
    int rightPadding = CONTENT_WIDTH - 1 - input.length() - 2;
    System.out.printf("║ %s: " + " ".repeat(rightPadding) + "║%n", input);
    System.out.println("╚" + "═".repeat(CONTENT_WIDTH) + "╝");

    System.out.print("\u001B[1A");
    System.out.print("\u001B[1A");
    System.out.printf("\u001B[" + (input.length() + 4) + "C");
    System.out.flush();

    return sc.nextLine();
  }

  private String printMenu(String subtitle, String[] options, Scanner sc) {
    if (!subtitle.isEmpty()) {
      System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
      printSubtitle(subtitle);
    }

    if (options.length != 0) {
      System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
      for (int i = 0; i < options.length; i++) {
        String line = "[" + i + "] " + options[i];
        printContent(line);
      }
      System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
      System.out.println("╠" + "═".repeat(CONTENT_WIDTH) + "╣");
    }

    return printChoice(sc);
  }

  private String printMenu(String subtitle, String[] options, String input, Scanner sc) {
    if (!subtitle.isEmpty()) {
      System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
      printSubtitle(subtitle);
      System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
    }

    if (options.length != 0) {
      for (int i = 0; i < options.length; i++) {
        String line = "[" + i + "] " + options[i];
        printContent(line);
      }
      System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
      System.out.println("╠" + "═".repeat(CONTENT_WIDTH) + "╣");
    }

    return printChoice(input, sc);
  }

  public String printMenu(String subtitle, String content, Scanner sc) {
    if (!subtitle.isEmpty()) {
      System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
      printSubtitle(subtitle);
      System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
    }

    String[] lines = content.split("\n");
    if (lines.length != 0) {
      for (int i = 0; i < lines.length; i++) {
        printContent(lines[i]);
      }
      System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
      System.out.println("╠" + "═".repeat(CONTENT_WIDTH) + "╣");
    }

    return printChoice(sc);
  }

  public String printMenu(String subtitle, String content, String input, Scanner sc) {
    if (!subtitle.isEmpty()) {
      System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
      printSubtitle(subtitle);
    }

    String[] lines = content.split("\n");
    System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
    if (lines.length != 0) {
      for (int i = 0; i < lines.length; i++) {
        printContent(lines[i]);
      }
      System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
      System.out.println("╠" + "═".repeat(CONTENT_WIDTH) + "╣");
    }

    return printChoice(input, sc);
  }

  public String printMenu(String subtitle, String content, String[] options, Scanner sc) {
    if (!subtitle.isEmpty()) {
      System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
      printSubtitle(subtitle);
    }

    String[] lines = content.split("\n");
    System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
    if (lines.length != 0) {
      for (int i = 0; i < lines.length; i++) {
        printContent(lines[i]);
      }
      System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
      System.out.println("╠" + "═".repeat(CONTENT_WIDTH) + "╣");
    }

    if (options.length != 0) {
      System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
      for (int i = 0; i < options.length; i++) {
        String line = "[" + i + "] " + options[i];
        printContent(line);
      }
      System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
      System.out.println("╠" + "═".repeat(CONTENT_WIDTH) + "╣");
    }

    return printChoice(sc);
  }

  public String printMenu(String subtitle, String content, String[] options, String input, Scanner sc) {
    if (!subtitle.isEmpty()) {
      System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
      printSubtitle(subtitle);
      System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
    }

    String[] lines = content.split("\n");
    if (lines.length != 0) {
      for (int i = 0; i < lines.length; i++) {
        printContent(lines[i]);
      }
      System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
      System.out.println("╠" + "═".repeat(CONTENT_WIDTH) + "╣");
    }

    if (options.length != 0) {
      for (int i = 0; i < options.length; i++) {
        String line = "[" + i + "] " + options[i];
        printContent(line);
      }
      System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
      System.out.println("╠" + "═".repeat(CONTENT_WIDTH) + "╣");
    }

    return printChoice(input, sc);
  }

  public String printUI(String title, String subtitle, String[] options, Scanner sc) {
    printHeader(title);
    return printMenu(subtitle, options, sc);
  }

  public String printUI(String title, String subtitle, String[] options, String input, Scanner sc) {
    printHeader(title);
    return printMenu(subtitle, options, input, sc);
  }

  public String printUI(String title, String subtitle, String content, Scanner sc) {
    printHeader(title);
    return printMenu(subtitle, content, sc);
  }

  public String printUI(String title, String subtitle, String content, String input, Scanner sc) {
    printHeader(title);
    return printMenu(subtitle, content, input, sc);
  }

  public String printUI(String title, String subtitle, String content, String[] options, Scanner sc) {
    printHeader(title);
    return printMenu(subtitle, content, options, sc);
  }

  public String printUI(String title, String subtitle, String content, String[] options, String input, Scanner sc) {
    printHeader(title);
    return printMenu(subtitle, content, options, input, sc);
  }

  public void printResult(String title, String result, Scanner sc) {
    printHeader(title);

    String[] lines = result.split("\n");

    System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
    for (int i = 0; i < lines.length; i++) {
      printContent(lines[i]);
    }
    System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
    printContent("Press Enter to go back");
    System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
    System.out.println("╚" + "═".repeat(CONTENT_WIDTH) + "╝");

    sc.nextLine();
  }

  public LocalDate printDate(String title, String subtitle, String[] options, String input, Scanner sc) {
    printHeader(subtitle);
    if (!subtitle.isEmpty()) {
      System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
      printSubtitle(subtitle);
    }
    if (options.length != 0) {
      System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
      for (int i = 0; i < options.length; i++) {
        printContent("[" + i + "] " + options[i]);
      }
      System.out.println("║" + " ".repeat(CONTENT_WIDTH) + "║");
      System.out.println("╚" + "═".repeat(CONTENT_WIDTH) + "╝");
    }
    return Dateinput.inputDate(sc);
  }

}
