import java.time.LocalDate;

public class Application {
	public enum ApplicationStatus {
		SUBMITTED,
		UNDER_REVIEW,
		SHORTLISTED,
		INTERVIEW,
		SELECTED,
		OFFERED,
		ACCEPTED,
		REJECTED,
		WITHDRAWN
	}

	private LocalDate applyDate;
	private String coverLetter;
	private String resume;
	private ApplicationStatus status;
	private Candidate candidate;

	public Application(
			LocalDate applyDate,
			String coverLetter,
			String resume,
			ApplicationStatus status,
			Candidate candidate) {
		this.applyDate = applyDate;
		this.coverLetter = coverLetter;
		this.resume = resume;
		this.status = status;
		this.candidate = candidate;
	}

	public LocalDate getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(LocalDate applyDate) {
		this.applyDate = applyDate;
	}

	public String getCoverLetter() {
		return coverLetter;
	}

	public void setCoverLetter(String coverLetter) {
		this.coverLetter = coverLetter;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public ApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
}
