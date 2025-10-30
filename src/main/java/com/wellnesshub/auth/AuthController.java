package com.wellnesshub.auth;

import com.wellnesshub.security.JwtService;
import com.wellnesshub.user.Role;
import com.wellnesshub.user.User;
import com.wellnesshub.user.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
		User user = userRepository.findByEmail(request.email()).orElseThrow();
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", user.getRole().name());
		String token = jwtService.generateToken(user.getEmail(), claims);
		return ResponseEntity.ok(new TokenResponse(token));
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
		if (userRepository.existsByEmail(request.email())) {
			return ResponseEntity.badRequest().body(Map.of("message", "Email already registered"));
		}
		User user = new User();
		user.setEmail(request.email());
		user.setPasswordHash(passwordEncoder.encode(request.password()));
		user.setFullName(request.fullName());
		user.setRole(request.role());
		userRepository.save(user);
		return ResponseEntity.ok(Map.of("message", "Registered"));
	}

	public record LoginRequest(@Email @NotBlank String email, @NotBlank String password) {}
	public record RegisterRequest(@Email @NotBlank String email, @NotBlank @Size(min = 8) String password, @NotBlank String fullName, Role role) {}
	public record TokenResponse(String token) {}
}







