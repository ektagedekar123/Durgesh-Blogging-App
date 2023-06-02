package com.codewithdurgesh.blog.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHelper {
	
	
	public static final long JWT_TOKEN_VALIDITY= 5 * 60 * 60;
	
	@Value("${jwt.secrete}")
	private String jwtSecrete;
	
	//retriving username from Jwt Token
	public String getUsernamefromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	//retrive Expiration Date from Jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims= getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	// for retriving any information from Token we will need the secrete key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecrete).parseClaimsJws(token).getBody();
	}

	// Check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	// Generate Token for User
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims=new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}
	
	// While creating the token
	//1. Defining Claims of the token like Issuer, Expiration, Subject, the Id
	//2. Sign the JWT using HSS12 algorithm & secrete key
	//3. According to JWS compact serialization (http://tools.letf.org/html/draft-letf-jose-
	//  compaction of the jwt to aURL safe String
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())) 
		         .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)).signWith(SignatureAlgorithm.HS512, jwtSecrete).compact(); 
	}
	
	
	//Validate Token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String userName = getUsernamefromToken(token);
		return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}
	
	
	
	
}
