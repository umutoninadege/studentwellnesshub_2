package com.wellnesshub.appointment;

import com.wellnesshub.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	private User student;

	@ManyToOne(optional = false)
	private User counselor;

	@NotNull
	@Future
	private LocalDateTime startTime;

	@NotNull
	private LocalDateTime endTime;

	@Column(length = 500)
	private String notes;

	public Long getId() { return id; }
	public User getStudent() { return student; }
	public void setStudent(User student) { this.student = student; }
	public User getCounselor() { return counselor; }
	public void setCounselor(User counselor) { this.counselor = counselor; }
	public LocalDateTime getStartTime() { return startTime; }
	public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
	public LocalDateTime getEndTime() { return endTime; }
	public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
	public String getNotes() { return notes; }
	public void setNotes(String notes) { this.notes = notes; }

	@PrePersist
	@PreUpdate
	void validateDuration() {
		if (startTime == null || endTime == null) return;
		if (!endTime.isAfter(startTime)) {
			throw new IllegalArgumentException("End time must be after start time");
		}
		Duration d = Duration.between(startTime, endTime);
		if (d.toMinutes() < 15 || d.toHours() > 3) {
			throw new IllegalArgumentException("Appointments must be 15-180 minutes long");
		}
	}
}







