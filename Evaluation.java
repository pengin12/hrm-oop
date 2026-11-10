public class Evaluation {
    private String month;
    private String comment;
    private double workScore;
    private double attitudeScore;
    private double disciplineScore;
    private double workQuality;
    private double productivity;
    private double teamWork;

    public Evaluation(String month, String comment,
            double workScore,
            double attitudeScore,
            double disciplineScore,
            double workQuality,
            double productivity,
            double teamWork) {
        this.month = month;
        this.comment = comment;
        this.workScore = workScore;
        this.attitudeScore = attitudeScore;
        this.disciplineScore = disciplineScore;
        this.workQuality = workQuality;
        this.productivity = productivity;
        this.teamWork = teamWork;
    }

    public double getWorkQuality() {
        return workQuality;
    }

    public double getProductivity() {
        return productivity;
    }

    public double getTeamWork() {
        return teamWork;
    }

    public double calculateEvaluationScore() {
        return (workScore + attitudeScore + disciplineScore
                + workQuality + productivity + teamWork) / 6;
    }

    public double getBonusRate() {
        double score = calculateEvaluationScore();
        if (score >= 9)
            return 0.30;
        if (score >= 8)
            return 0.20;
        if (score >= 7)
            return 0.10;
        if (score >= 5)
            return 0.05;
        return 0.0;
    }

    public void displayEvaluation() {
        System.out.println("Thang danh gia: " + month);
        System.out.println("Nhan xet: " + comment);
        System.out.println("Diem cong viec: " + workScore);
        System.out.println("Diem thai do: " + attitudeScore);
        System.out.println("Diem ky luat: " + disciplineScore);
        System.out.println("Chat luong cong viec (workQuality): " + workQuality);
        System.out.println("Nang suat (productivity): " + productivity);
        System.out.println("Lam viec nhom (teamWork): " + teamWork);
        System.out.println("Diem trung binh: " + calculateEvaluationScore());
        System.out.println("He so thuong: " + getBonusRate());
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getWorkScore() {
        return workScore;
    }

    public void setWorkScore(double workScore) {
        this.workScore = workScore;
    }

    public double getAttitudeScore() {
        return attitudeScore;
    }

    public void setAttitudeScore(double attitudeScore) {
        this.attitudeScore = attitudeScore;
    }

    public double getDisciplineScore() {
        return disciplineScore;
    }

    public void setDisciplineScore(double disciplineScore) {
        this.disciplineScore = disciplineScore;
    }

    public void setWorkQuality(double workQuality) {
        this.workQuality = workQuality;
    }

    public void setProductivity(double productivity) {
        this.productivity = productivity;
    }

    public void setTeamWork(double teamWork) {
        this.teamWork = teamWork;
    }
}
