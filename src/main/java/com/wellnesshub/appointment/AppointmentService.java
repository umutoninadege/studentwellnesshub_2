package com.wellnesshub.appointment;

import com.wellnesshub.user.Role;
import com.wellnesshub.user.User;
import com.wellnesshub.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

	private final AppointmentRepository appointmentRepository;
	private final UserRepository userRepository;

	public AppointmentService(AppointmentRepository appointmentRepository, UserRepository userRepository) {
		this.appointmentRepository = appointmentRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public Appointment scheduleAppointment(Long studentId, Long counselorId, LocalDateTime start, LocalDateTime end, String notes) {
		User student = userRepository.findById(studentId).orElseThrow();
		User counselor = userRepository.findById(counselorId).orElseThrow();
		if (student.getRole() != Role.STUDENT) throw new IllegalArgumentException("Student id must be a STUDENT");
		if (counselor.getRole() != Role.COUNSELOR) throw new IllegalArgumentException("Counselor id must be a COUNSELOR");

		boolean hasConflict = !appointmentRepository.findByCounselorAndEndTimeAfterAndStartTimeBefore(counselor, start, end).isEmpty();
		if (hasConflict) throw new IllegalStateException("Counselor has a conflicting appointment");

		Appointment appt = new Appointment();
		appt.setStudent(student);
		appt.setCounselor(counselor);
		appt.setStartTime(start);
		appt.setEndTime(end);
		appt.setNotes(notes);
		return appointmentRepository.save(appt);
	}

	public List<Appointment> getAppointmentsForStudent(Long studentId) {
		User student = userRepository.findById(studentId).orElseThrow();
		return appointmentRepository.findByStudent(student);
	}

	public List<Appointment> getAppointmentsForCounselor(Long counselorId) {
		User counselor = userRepository.findById(counselorId).orElseThrow();
		return appointmentRepository.findByCounselor(counselor);
	}
}







