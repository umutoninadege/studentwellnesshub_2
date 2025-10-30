package com.wellnesshub.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

	@Value("${app.security.jwt.secret}")
	private String secret;

	@Value("${app.security.jwt.issuer}")
	private String issuer;

	@Value("${app.security.jwt.expirationMinutes}")
	private long expirationMinutes;

	public String generateToken(String subject, Map<String, Object> claims) {
		Instant now = Instant.now();
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuer(issuer)
				.setIssuedAt(Date.from(now))
				.setExpiration(Date.from(now.plus(expirationMinutes, ChronoUnit.MINUTES)))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	public boolean isTokenValid(String token, String subject) {
		String extracted = extractClaim(token, Claims::getSubject);
		return extracted != null && extracted.equals(subject) && !isTokenExpired(token);
	}

	public boolean isTokenExpired(String token) {
		Date exp = extractClaim(token, Claims::getExpiration);
		return exp.before(new Date());
	}

	public <T> T extractClaim(String token, Function<Claims, T> resolver) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		return resolver.apply(claims);
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}





