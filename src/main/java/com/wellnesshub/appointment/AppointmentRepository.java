package com.wellnesshub.appointment;

import com.wellnesshub.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	List<Appointment> findByCounselorAndEndTimeAfterAndStartTimeBefore(User counselor, LocalDateTime start, LocalDateTime end);
	List<Appointment> findByStudent(User student);
	List<Appointment> findByCounselor(User counselor);
}







