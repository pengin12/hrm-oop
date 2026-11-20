public class SortData {
  private <T> int compactNulls(T[] arr) {
    int count = 0;
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] != null) {
        arr[count++] = arr[i];
      }
    }
    for (int i = count; i < arr.length; i++) {
      arr[i] = null;
    }
    return count;
  }

  private <T extends Comparable<? super T>> boolean shouldSwap(T a, T b, boolean ascending) {
    if (a == null && b == null)
      return false;
    if (a == null)
      return true;
    if (b == null)
      return false;
    int cmp = a.compareTo(b);
    return ascending ? (cmp > 0) : (cmp < 0);
  }

  private boolean shouldSwapIgnoreCase(String a, String b, boolean ascending) {
    if (a == null && b == null)
      return false;
    if (a == null)
      return true;
    if (b == null)
      return false;
    int cmp = a.compareToIgnoreCase(b);
    return ascending ? (cmp > 0) : (cmp < 0);
  }

  public void sortById(Employee[] employees, boolean ascending) {
    if (employees == null)
      return;

    int n = compactNulls(employees);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwap(employees[j].getId(), employees[j + 1].getId(), ascending)) {
          Employee tmp = employees[j];
          employees[j] = employees[j + 1];
          employees[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByHireDate(Employee[] employees, boolean ascending) {
    if (employees == null)
      return;

    int n = compactNulls(employees);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwap(employees[j].getHireDate(), employees[j + 1].getHireDate(), ascending)) {
          Employee tmp = employees[j];
          employees[j] = employees[j + 1];
          employees[j + 1] = tmp;
        }
      }
    }
  }

  public void sortBySpecialization(Employee[] employees, boolean ascending) {
    if (employees == null)
      return;

    int n = compactNulls(employees);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwap(employees[j].getSpecialization(), employees[j + 1].getSpecialization(), ascending)) {
          Employee tmp = employees[j];
          employees[j] = employees[j + 1];
          employees[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByStatus(Employee[] employees, boolean ascending) {
    if (employees == null)
      return;

    int n = compactNulls(employees);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwap(String.valueOf(employees[j].getStatus()), String.valueOf(employees[j + 1].getStatus()),
            ascending)) {
          Employee tmp = employees[j];
          employees[j] = employees[j + 1];
          employees[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByTaxId(Employee[] employees, boolean ascending) {
    if (employees == null)
      return;

    int n = compactNulls(employees);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwap(employees[j].getTaxId(), employees[j + 1].getTaxId(), ascending)) {
          Employee tmp = employees[j];
          employees[j] = employees[j + 1];
          employees[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByPosition(Employee[] employees, boolean ascending) {
    if (employees == null)
      return;

    int n = compactNulls(employees);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwap(employees[j].getPosition().getName(), employees[j + 1].getPosition().getName(), ascending)) {
          Employee tmp = employees[j];
          employees[j] = employees[j + 1];
          employees[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByDepartment(Employee[] employees, boolean ascending) {
    if (employees == null)
      return;

    int n = compactNulls(employees);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwap(employees[j].getDepartment().getName(), employees[j + 1].getDepartment().getName(), ascending)) {
          Employee tmp = employees[j];
          employees[j] = employees[j + 1];
          employees[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByMonth(Payroll[] payrolls, boolean ascending) {
    if (payrolls == null)
      return;
    int n = compactNulls(payrolls);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwap(payrolls[j].getMonth(), payrolls[j + 1].getMonth(), ascending)) {
          Payroll tmp = payrolls[j];
          payrolls[j] = payrolls[j + 1];
          payrolls[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByBaseSalary(Payroll[] payrolls, boolean ascending) {
    if (payrolls == null)
      return;
    int n = compactNulls(payrolls);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        double s1 = payrolls[j].getBaseSalary();
        double s2 = payrolls[j + 1].getBaseSalary();
        if (ascending ? (s1 > s2) : (s1 < s2)) {
          Payroll tmp = payrolls[j];
          payrolls[j] = payrolls[j + 1];
          payrolls[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByBonus(Payroll[] payrolls, boolean ascending) {
    if (payrolls == null)
      return;
    int n = compactNulls(payrolls);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        double b1 = payrolls[j].getBonus();
        double b2 = payrolls[j + 1].getBonus();
        if (ascending ? (b1 > b2) : (b1 < b2)) {
          Payroll tmp = payrolls[j];
          payrolls[j] = payrolls[j + 1];
          payrolls[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByAllowance(Payroll[] payrolls, boolean ascending) {
    if (payrolls == null)
      return;
    int n = compactNulls(payrolls);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        double a1 = payrolls[j].getAllowance();
        double a2 = payrolls[j + 1].getAllowance();
        if (ascending ? (a1 > a2) : (a1 < a2)) {
          Payroll tmp = payrolls[j];
          payrolls[j] = payrolls[j + 1];
          payrolls[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByDeduction(Payroll[] payrolls, boolean ascending) {
    if (payrolls == null)
      return;
    int n = compactNulls(payrolls);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        double d1 = payrolls[j].getDeduction();
        double d2 = payrolls[j + 1].getDeduction();
        if (ascending ? (d1 > d2) : (d1 < d2)) {
          Payroll tmp = payrolls[j];
          payrolls[j] = payrolls[j + 1];
          payrolls[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByTaxAmount(Payroll[] payrolls, boolean ascending) {
    if (payrolls == null)
      return;
    int n = compactNulls(payrolls);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        double t1 = payrolls[j].getTaxAmount();
        double t2 = payrolls[j + 1].getTaxAmount();
        if (ascending ? (t1 > t2) : (t1 < t2)) {
          Payroll tmp = payrolls[j];
          payrolls[j] = payrolls[j + 1];
          payrolls[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByInsuranceAmount(Payroll[] payrolls, boolean ascending) {
    if (payrolls == null)
      return;
    int n = compactNulls(payrolls);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        double ins1 = payrolls[j].getInsuranceAmount();
        double ins2 = payrolls[j + 1].getInsuranceAmount();
        if (ascending ? (ins1 > ins2) : (ins1 < ins2)) {
          Payroll tmp = payrolls[j];
          payrolls[j] = payrolls[j + 1];
          payrolls[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByWorkedHours(Payroll[] payrolls, boolean ascending) {
    if (payrolls == null)
      return;
    int n = compactNulls(payrolls);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        int w1 = payrolls[j].getWorkedHours();
        int w2 = payrolls[j + 1].getWorkedHours();
        if (ascending ? (w1 > w2) : (w1 < w2)) {
          Payroll tmp = payrolls[j];
          payrolls[j] = payrolls[j + 1];
          payrolls[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByOvertimeHours(Payroll[] payrolls, boolean ascending) {
    if (payrolls == null)
      return;
    int n = compactNulls(payrolls);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        int o1 = payrolls[j].getOvertimeHours();
        int o2 = payrolls[j + 1].getOvertimeHours();
        if (ascending ? (o1 > o2) : (o1 < o2)) {
          Payroll tmp = payrolls[j];
          payrolls[j] = payrolls[j + 1];
          payrolls[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByOvertimePay(Payroll[] payrolls, boolean ascending) {
    if (payrolls == null)
      return;
    int n = compactNulls(payrolls);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        double op1 = payrolls[j].calculateOvertimePay();
        double op2 = payrolls[j + 1].calculateOvertimePay();
        if (ascending ? (op1 > op2) : (op1 < op2)) {
          Payroll tmp = payrolls[j];
          payrolls[j] = payrolls[j + 1];
          payrolls[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByLastSalaryIncreaseDate(Payroll[] payrolls, boolean ascending) {
    if (payrolls == null)
      return;
    int n = compactNulls(payrolls);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwap(payrolls[j].getLastSalaryIncreaseDate(), payrolls[j + 1].getLastSalaryIncreaseDate(),
            ascending)) {
          Payroll tmp = payrolls[j];
          payrolls[j] = payrolls[j + 1];
          payrolls[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByNetSalary(Payroll[] payrolls, boolean ascending) {
    if (payrolls == null)
      return;
    int n = compactNulls(payrolls);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        double n1 = payrolls[j].calculateNetSalary();
        double n2 = payrolls[j + 1].calculateNetSalary();
        if (ascending ? (n1 > n2) : (n1 < n2)) {
          Payroll tmp = payrolls[j];
          payrolls[j] = payrolls[j + 1];
          payrolls[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByMonth(Evaluation[] evaluations, boolean ascending) {
    if (evaluations == null)
      return;
    int n = compactNulls(evaluations);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwap(evaluations[j].getMonth(), evaluations[j + 1].getMonth(), ascending)) {
          Evaluation tmp = evaluations[j];
          evaluations[j] = evaluations[j + 1];
          evaluations[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByAverageScore(Evaluation[] evaluations, boolean ascending) {
    if (evaluations == null)
      return;
    int n = compactNulls(evaluations);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        double s1 = evaluations[j].calculateEvaluationScore();
        double s2 = evaluations[j + 1].calculateEvaluationScore();
        if (ascending ? (s1 > s2) : (s1 < s2)) {
          Evaluation tmp = evaluations[j];
          evaluations[j] = evaluations[j + 1];
          evaluations[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByWorkScore(Evaluation[] evaluations, boolean ascending) {
    if (evaluations == null)
      return;
    int n = compactNulls(evaluations);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        double s1 = evaluations[j].getWorkScore();
        double s2 = evaluations[j + 1].getWorkScore();
        if (ascending ? (s1 > s2) : (s1 < s2)) {
          Evaluation tmp = evaluations[j];
          evaluations[j] = evaluations[j + 1];
          evaluations[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByAttitudeScore(Evaluation[] evaluations, boolean ascending) {
    if (evaluations == null)
      return;
    int n = compactNulls(evaluations);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        double s1 = evaluations[j].getAttitudeScore();
        double s2 = evaluations[j + 1].getAttitudeScore();
        if (ascending ? (s1 > s2) : (s1 < s2)) {
          Evaluation tmp = evaluations[j];
          evaluations[j] = evaluations[j + 1];
          evaluations[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByDisciplineScore(Evaluation[] evaluations, boolean ascending) {
    if (evaluations == null)
      return;
    int n = compactNulls(evaluations);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        double s1 = evaluations[j].getDisciplineScore();
        double s2 = evaluations[j + 1].getDisciplineScore();
        if (ascending ? (s1 > s2) : (s1 < s2)) {
          Evaluation tmp = evaluations[j];
          evaluations[j] = evaluations[j + 1];
          evaluations[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByWorkQuality(Evaluation[] evaluations, boolean ascending) {
    if (evaluations == null)
      return;
    int n = compactNulls(evaluations);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        double s1 = evaluations[j].getWorkQuality();
        double s2 = evaluations[j + 1].getWorkQuality();
        if (ascending ? (s1 > s2) : (s1 < s2)) {
          Evaluation tmp = evaluations[j];
          evaluations[j] = evaluations[j + 1];
          evaluations[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByProductivity(Evaluation[] evaluations, boolean ascending) {
    if (evaluations == null)
      return;
    int n = compactNulls(evaluations);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        double s1 = evaluations[j].getProductivity();
        double s2 = evaluations[j + 1].getProductivity();
        if (ascending ? (s1 > s2) : (s1 < s2)) {
          Evaluation tmp = evaluations[j];
          evaluations[j] = evaluations[j + 1];
          evaluations[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByTeamWork(Evaluation[] evaluations, boolean ascending) {
    if (evaluations == null)
      return;
    int n = compactNulls(evaluations);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        double s1 = evaluations[j].getTeamWork();
        double s2 = evaluations[j + 1].getTeamWork();
        if (ascending ? (s1 > s2) : (s1 < s2)) {
          Evaluation tmp = evaluations[j];
          evaluations[j] = evaluations[j + 1];
          evaluations[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByStartDate(Contract[] contracts, boolean ascending) {
    if (contracts == null)
      return;
    int n = compactNulls(contracts);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwap(contracts[j].getStartDate(), contracts[j + 1].getStartDate(), ascending)) {
          Contract tmp = contracts[j];
          contracts[j] = contracts[j + 1];
          contracts[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByEndDate(Contract[] contracts, boolean ascending) {
    if (contracts == null)
      return;
    int n = compactNulls(contracts);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwap(contracts[j].getEndDate(), contracts[j + 1].getEndDate(), ascending)) {
          Contract tmp = contracts[j];
          contracts[j] = contracts[j + 1];
          contracts[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByType(Contract[] contracts, boolean ascending) {
    if (contracts == null)
      return;
    int n = compactNulls(contracts);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        String t1 = contracts[j].getType() != null ? contracts[j].getType().name() : null;
        String t2 = contracts[j + 1].getType() != null ? contracts[j + 1].getType().name() : null;
        if (shouldSwapIgnoreCase(t1, t2, ascending)) {
          Contract tmp = contracts[j];
          contracts[j] = contracts[j + 1];
          contracts[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByStatus(Contract[] contracts, boolean ascending) {
    if (contracts == null)
      return;
    int n = compactNulls(contracts);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        String s1 = contracts[j].getStatus() != null ? contracts[j].getStatus().name() : null;
        String s2 = contracts[j + 1].getStatus() != null ? contracts[j + 1].getStatus().name() : null;
        if (shouldSwapIgnoreCase(s1, s2, ascending)) {
          Contract tmp = contracts[j];
          contracts[j] = contracts[j + 1];
          contracts[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByDate(Attendance[] attendances, boolean ascending) {
    if (attendances == null)
      return;
    int n = compactNulls(attendances);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwap(attendances[j].getDate(), attendances[j + 1].getDate(), ascending)) {
          Attendance tmp = attendances[j];
          attendances[j] = attendances[j + 1];
          attendances[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByClockIn(Attendance[] attendances, boolean ascending) {
    if (attendances == null)
      return;
    int n = compactNulls(attendances);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwap(attendances[j].getClockIn(), attendances[j + 1].getClockIn(), ascending)) {
          Attendance tmp = attendances[j];
          attendances[j] = attendances[j + 1];
          attendances[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByClockOut(Attendance[] attendances, boolean ascending) {
    if (attendances == null)
      return;
    int n = compactNulls(attendances);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwap(attendances[j].getClockOut(), attendances[j + 1].getClockOut(), ascending)) {
          Attendance tmp = attendances[j];
          attendances[j] = attendances[j + 1];
          attendances[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByWorkedHours(Attendance[] attendances, boolean ascending) {
    if (attendances == null)
      return;
    int n = compactNulls(attendances);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        double h1 = attendances[j].calculateWorkedHours();
        double h2 = attendances[j + 1].calculateWorkedHours();
        if (ascending ? (h1 > h2) : (h1 < h2)) {
          Attendance tmp = attendances[j];
          attendances[j] = attendances[j + 1];
          attendances[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByFromDate(Absence[] absences, boolean ascending) {
    if (absences == null)
      return;
    int n = compactNulls(absences);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwap(absences[j].getFromDate(), absences[j + 1].getFromDate(), ascending)) {
          Absence tmp = absences[j];
          absences[j] = absences[j + 1];
          absences[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByToDate(Absence[] absences, boolean ascending) {
    if (absences == null)
      return;
    int n = compactNulls(absences);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwap(absences[j].getToDate(), absences[j + 1].getToDate(), ascending)) {
          Absence tmp = absences[j];
          absences[j] = absences[j + 1];
          absences[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByReason(Absence[] absences, boolean ascending) {
    if (absences == null)
      return;
    int n = compactNulls(absences);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwapIgnoreCase(absences[j].getReason(), absences[j + 1].getReason(), ascending)) {
          Absence tmp = absences[j];
          absences[j] = absences[j + 1];
          absences[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByStatus(Absence[] absences, boolean ascending) {
    if (absences == null)
      return;
    int n = compactNulls(absences);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        String s1 = absences[j].getStatus() != null ? absences[j].getStatus().name() : null;
        String s2 = absences[j + 1].getStatus() != null ? absences[j + 1].getStatus().name() : null;
        if (shouldSwapIgnoreCase(s1, s2, ascending)) {
          Absence tmp = absences[j];
          absences[j] = absences[j + 1];
          absences[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByDate(Resignation[] resignations, boolean ascending) {
    if (resignations == null)
      return;
    int n = compactNulls(resignations);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwap(resignations[j].getDate(), resignations[j + 1].getDate(), ascending)) {
          Resignation tmp = resignations[j];
          resignations[j] = resignations[j + 1];
          resignations[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByReason(Resignation[] resignations, boolean ascending) {
    if (resignations == null)
      return;
    int n = compactNulls(resignations);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwapIgnoreCase(resignations[j].getReason(), resignations[j + 1].getReason(), ascending)) {
          Resignation tmp = resignations[j];
          resignations[j] = resignations[j + 1];
          resignations[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByStatus(Resignation[] resignations, boolean ascending) {
    if (resignations == null)
      return;
    int n = compactNulls(resignations);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        String s1 = resignations[j].getStatus() != null ? resignations[j].getStatus().name() : null;
        String s2 = resignations[j + 1].getStatus() != null ? resignations[j + 1].getStatus().name() : null;
        if (shouldSwapIgnoreCase(s1, s2, ascending)) {
          Resignation tmp = resignations[j];
          resignations[j] = resignations[j + 1];
          resignations[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByInsuranceNumber(Insurance[] insurances, boolean ascending) {
    if (insurances == null)
      return;
    int n = compactNulls(insurances);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwapIgnoreCase(insurances[j].getInsuranceNumber(), insurances[j + 1].getInsuranceNumber(),
            ascending)) {
          Insurance tmp = insurances[j];
          insurances[j] = insurances[j + 1];
          insurances[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByType(Insurance[] insurances, boolean ascending) {
    if (insurances == null)
      return;
    int n = compactNulls(insurances);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        String t1 = insurances[j].getType() != null ? insurances[j].getType().name() : null;
        String t2 = insurances[j + 1].getType() != null ? insurances[j + 1].getType().name() : null;
        if (shouldSwapIgnoreCase(t1, t2, ascending)) {
          Insurance tmp = insurances[j];
          insurances[j] = insurances[j + 1];
          insurances[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByStartDate(Insurance[] insurances, boolean ascending) {
    if (insurances == null)
      return;
    int n = compactNulls(insurances);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwap(insurances[j].getStartDate(), insurances[j + 1].getStartDate(), ascending)) {
          Insurance tmp = insurances[j];
          insurances[j] = insurances[j + 1];
          insurances[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByEndDate(Insurance[] insurances, boolean ascending) {
    if (insurances == null)
      return;
    int n = compactNulls(insurances);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwap(insurances[j].getEndDate(), insurances[j + 1].getEndDate(), ascending)) {
          Insurance tmp = insurances[j];
          insurances[j] = insurances[j + 1];
          insurances[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByRate(Insurance[] insurances, boolean ascending) {
    if (insurances == null)
      return;
    int n = compactNulls(insurances);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        double r1 = insurances[j].getRate();
        double r2 = insurances[j + 1].getRate();
        if (ascending ? (r1 > r2) : (r1 < r2)) {
          Insurance tmp = insurances[j];
          insurances[j] = insurances[j + 1];
          insurances[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByName(Department[] departments, boolean ascending) {
    if (departments == null)
      return;
    int n = compactNulls(departments);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwapIgnoreCase(departments[j].getName(), departments[j + 1].getName(), ascending)) {
          Department tmp = departments[j];
          departments[j] = departments[j + 1];
          departments[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByPhone(Department[] departments, boolean ascending) {
    if (departments == null)
      return;
    int n = compactNulls(departments);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwapIgnoreCase(departments[j].getPhone(), departments[j + 1].getPhone(), ascending)) {
          Department tmp = departments[j];
          departments[j] = departments[j + 1];
          departments[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByPosition(Department[] departments, boolean ascending) {
    if (departments == null)
      return;
    int n = compactNulls(departments);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwapIgnoreCase(departments[j].getPosition(), departments[j + 1].getPosition(), ascending)) {
          Department tmp = departments[j];
          departments[j] = departments[j + 1];
          departments[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByName(Position[] positions, boolean ascending) {
    if (positions == null)
      return;
    int n = compactNulls(positions);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwapIgnoreCase(positions[j].getName(), positions[j + 1].getName(), ascending)) {
          Position tmp = positions[j];
          positions[j] = positions[j + 1];
          positions[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByCoefficient(Position[] positions, boolean ascending) {
    if (positions == null)
      return;
    int n = compactNulls(positions);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        double c1 = positions[j].getPositionCoefficient();
        double c2 = positions[j + 1].getPositionCoefficient();
        if (ascending ? (c1 > c2) : (c1 < c2)) {
          Position tmp = positions[j];
          positions[j] = positions[j + 1];
          positions[j + 1] = tmp;
        }
      }
    }
  }

  public void sortByResponsibilities(Position[] positions, boolean ascending) {
    if (positions == null)
      return;
    int n = compactNulls(positions);
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (shouldSwapIgnoreCase(positions[j].getResponsibilities(), positions[j + 1].getResponsibilities(),
            ascending)) {
          Position tmp = positions[j];
          positions[j] = positions[j + 1];
          positions[j + 1] = tmp;
        }
      }
    }
  }
}
