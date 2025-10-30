package com.wellnesshub.appointment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

	private final AppointmentService appointmentService;

	public AppointmentController(AppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}

	@PostMapping
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<Appointment> schedule(@RequestBody @Valid ScheduleRequest request) {
		Appointment a = appointmentService.scheduleAppointment(request.studentId(), request.counselorId(), request.start(), request.end(), request.notes());
		return ResponseEntity.ok(a);
	}

	@GetMapping("/student/{studentId}")
	@PreAuthorize("hasRole('STUDENT')")
	public List<Appointment> studentAppointments(@PathVariable Long studentId) {
		return appointmentService.getAppointmentsForStudent(studentId);
	}

	@GetMapping("/counselor/{counselorId}")
	@PreAuthorize("hasRole('COUNSELOR')")
	public List<Appointment> counselorAppointments(@PathVariable Long counselorId) {
		return appointmentService.getAppointmentsForCounselor(counselorId);
	}

	public record ScheduleRequest(@NotNull Long studentId, @NotNull Long counselorId, @NotNull @Future LocalDateTime start, @NotNull @Future LocalDateTime end, String notes) {}
}







