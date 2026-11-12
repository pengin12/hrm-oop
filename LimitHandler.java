import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LimitHandler {
  private int maxAccounts;
  private int maxEmployees;
  private int maxDepartments;
  private int maxPositions;
  private int maxEvaluations;
  private int maxContracts;
  private int maxPayrolls;
  private int maxAttendances;
  private int maxAbsences;
  private int maxInsurances;
  private int maxResignations;

  private String currentDirectory = System.getProperty("user.dir");
  private String directoryPath = currentDirectory + File.separator + "data";
  private String limitPath = directoryPath + File.separator + "limit.txt";

  public LimitHandler readLimit() {
    try (Scanner sc = new Scanner(new File(limitPath))) {
      this.setMaxAccounts(Integer.parseInt(sc.nextLine()));
      this.setMaxEmployees(Integer.parseInt(sc.nextLine()));
      this.setMaxDepartments(Integer.parseInt(sc.nextLine()));
      this.setMaxPositions(Integer.parseInt(sc.nextLine()));
      this.setMaxEvaluations(Integer.parseInt(sc.nextLine()));
      this.setMaxContracts(Integer.parseInt(sc.nextLine()));
      this.setMaxPayrolls(Integer.parseInt(sc.nextLine()));
      this.setMaxAttendances(Integer.parseInt(sc.nextLine()));
      this.setMaxAbsences(Integer.parseInt(sc.nextLine()));
      this.setMaxInsurances(Integer.parseInt(sc.nextLine()));
      this.setMaxResignations(Integer.parseInt(sc.nextLine()));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return this;
  }

  public void writeLimit() {
    try {
      FileWriter limitFileWriter = new FileWriter(limitPath);

      limitFileWriter.write(maxAccounts + "\n");
      limitFileWriter.write(maxEmployees + "\n");
      limitFileWriter.write(maxDepartments + "\n");
      limitFileWriter.write(maxPositions + "\n");
      limitFileWriter.write(maxEvaluations + "\n");
      limitFileWriter.write(maxContracts + "\n");
      limitFileWriter.write(maxPayrolls + "\n");
      limitFileWriter.write(maxAttendances + "\n");
      limitFileWriter.write(maxAbsences + "\n");
      limitFileWriter.write(maxInsurances + "\n");
      limitFileWriter.write(maxResignations + "\n");

      limitFileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public int getMaxAccounts() {
    return maxAccounts;
  }

  public void setMaxAccounts(int maxAccounts) {
    this.maxAccounts = maxAccounts;
  }

  public int getMaxEmployees() {
    return maxEmployees;
  }

  public void setMaxEmployees(int maxEmployees) {
    this.maxEmployees = maxEmployees;
  }

  public int getMaxDepartments() {
    return maxDepartments;
  }

  public void setMaxDepartments(int maxDepartments) {
    this.maxDepartments = maxDepartments;
  }

  public int getMaxPositions() {
    return maxPositions;
  }

  public void setMaxPositions(int maxPositions) {
    this.maxPositions = maxPositions;
  }

  public int getMaxEvaluations() {
    return maxEvaluations;
  }

  public void setMaxEvaluations(int maxEvaluations) {
    this.maxEvaluations = maxEvaluations;
  }

  public int getMaxContracts() {
    return maxContracts;
  }

  public void setMaxContracts(int maxContracts) {
    this.maxContracts = maxContracts;
  }

  public int getMaxPayrolls() {
    return maxPayrolls;
  }

  public void setMaxPayrolls(int maxPayrolls) {
    this.maxPayrolls = maxPayrolls;
  }

  public int getMaxAttendances() {
    return maxAttendances;
  }

  public void setMaxAttendances(int maxAttendances) {
    this.maxAttendances = maxAttendances;
  }

  public int getMaxAbsences() {
    return maxAbsences;
  }

  public void setMaxAbsences(int maxAbsences) {
    this.maxAbsences = maxAbsences;
  }

  public int getMaxInsurances() {
    return maxInsurances;
  }

  public void setMaxInsurances(int maxInsurances) {
    this.maxInsurances = maxInsurances;
  }

  public int getMaxResignations() {
    return maxResignations;
  }

  public void setMaxResignations(int maxResignations) {
    this.maxResignations = maxResignations;
  }
}
