package com.hims.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;
import com.hims.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtConfig {
	private static final String SECRETKEY = "asdddsaaassdddsa";
	
	//Create Jwt Token
	public String generateToken(User user) {
		Map<String, Object> claims=new HashMap<>();
		return createToken(claims,user.getEmail());
	}

	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userName)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
				.signWith(SignatureAlgorithm.HS256, SECRETKEY)
				.compact();
	}
	
	//Extract Claims
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();
	}
	
	//Extract Username
	public String extractUserName(String token) {
		//System.out.println("this "+token+"\n "+extractClaim(token, Claims::getSubject));
		return extractClaim(token, Claims::getSubject);
	}
	
	//Token Expiration
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	//Validate token
	public Boolean validateToken(String token, User user) {
		System.out.println("This is Validate");
		System.out.println("Token: "+token);
		final String userName=extractUserName(token);
		return (userName.equals(user.getEmail())&&!isTokenExpired(token));
		
	}


}
