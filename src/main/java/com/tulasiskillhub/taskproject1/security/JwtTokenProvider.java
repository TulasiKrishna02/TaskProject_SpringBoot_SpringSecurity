package com.tulasiskillhub.taskproject1.security;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	
	public String generateToken(Authentication authentication) {
		
		String email=authentication.getName();
		Date currentDate =new Date();
		Date expireDate=new Date(currentDate.getTime()+3600000);//milliseconds
		
		
		String token=Jwts.builder()
				.setSubject(email)
				.setIssuedAt(currentDate)
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512,"JWTSecretKey")
				.compact();
		
		return token;
		
	}
	
	
	public String getEmailFromToken(String token) {
		
		Claims claims = Jwts.parser()
				.setSigningKey("JWTSecretKey")
				.parseClaimsJwt(token)
				.getBody();
				
				

		
		
		return claims.getSubject();
		
	}

}
