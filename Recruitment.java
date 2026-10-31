import java.time.LocalDate;

public class Recruitment {
	public enum RecruitmentStatus {
		OPEN,
		CLOSED,
		CANCELLED
	}

	private int numberOfPositions;
	private LocalDate deadline;
	private RecruitmentStatus status;
	private Department department;
	private Position position;

	public Recruitment() {
	}

	public Recruitment(int numberOfPositions,
			LocalDate deadline,
			RecruitmentStatus status,
			Department department,
			Position position) {
		this.numberOfPositions = numberOfPositions;
		this.deadline = deadline;
		this.status = status;
		this.department = department;
		this.position = position;
	}

	public void close() {
		this.status = RecruitmentStatus.CLOSED;
	}

	public int getNumberOfPositions() {
		return numberOfPositions;
	}

	public void setNumberOfPositions(int numberOfPositions) {
		this.numberOfPositions = numberOfPositions;
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public RecruitmentStatus getStatus() {
		return status;
	}

	public void setStatus(RecruitmentStatus status) {
		this.status = status;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}
