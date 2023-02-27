package com.parking.security.microservice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	// @Value("${app.secret}")
	private String secret = "Klaus";

	// 7. validate token user name and request user also expDate
	public boolean validateToken(String token, String username) {
		String usernameInToken = getUsername(token);
		return (usernameInToken.equals(username) && !isTokenExpired(token));
	}

	// 6. Check Current and Exp Date
	public boolean isTokenExpired(String token) {
		final Date expiration = getExpDate(token);
		return expiration.before(new Date());
	}

	// 5. Generate Token with Empty Claims
	public String generateToken(com.parking.commons.model.User user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("name", user.getUsername());
		claims.put("id", user.getId());
		// System.out.println("Checkinh========== " + p.getLoginUser().getRole());
		claims.put("role", user.getRole());
		return generateToken(claims, user.getUsername());
	}

	// 4.Read username
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}

	public Integer getId(String token) {
		return (Integer) getClaims(token).get("id");
	}

	public String getRole(String token) {
		return (String) getClaims(token).get("role");
	}

	// 3. read ExpDate
	public Date getExpDate(String token) {
		return getClaims(token).getExpiration();
	}

	// 2. Read Claim
	private Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	// 1. generate token
	private String generateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuer("KHAN")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

}