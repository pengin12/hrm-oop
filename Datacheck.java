import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class DataCheck {
    private Pattern pattern;

    public DataCheck(String regex) {
        pattern = Pattern.compile(regex);
    }

    public boolean check(String date) {
        Matcher matcher = pattern.matcher(date);
        if (!matcher.matches()) {
            return false;
        }
        int day = Integer.parseInt(matcher.group(1));
        int month = Integer.parseInt(matcher.group(2));
        int year = Integer.parseInt(matcher.group(3));
        if (day < 1 || month < 1 || month > 12 || year < 1) {
            return false;
        }
        int maxDay = 31;
        switch (month) {
            case 4:
            case 6:
            case 9:
            case 11:
                maxDay = 30;
                break;

            case 2:
                if (year % 400 == 0 ||
                        (year % 4 == 0 && year % 100 != 0)) {
                    maxDay = 29;
                } else {
                    maxDay = 28;
                }
                break;
        }

        return day <= maxDay;
    }

}
