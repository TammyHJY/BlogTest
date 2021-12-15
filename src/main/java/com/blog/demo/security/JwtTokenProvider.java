package com.blog.demo.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.blog.demo.exception.BlogAPIException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

	//將application.properties中寫得設置利用@Value導入
	@Value("${app.jwt-secret}")
	private String jwtSecrete;
	
	@Value("${app.jwt-expiration-milliseconds}")
	private int jwtExpirationInMs;
	
	// generate token
	public String generateToken(Authentication authentication) {
		
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime()+jwtExpirationInMs);
		
		String token = Jwts.builder()
				.setSubject(username)
				.setIssuedAt(currentDate)
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecrete)
				.compact();
		return token;		
	}
	
	
	//get the username from token after login
	public String getUsernameFromJWT(String token) {
		
		Claims claims = Jwts.parser()
				.setSigningKey(jwtSecrete)
				.parseClaimsJws(token)
				.getBody();		
		return claims.getSubject(); //上面 generate token 時用.setSubject(username), 所以這裡用 claims.getSubject()取得username		
	}
	
	
	
	// validate JWT token
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecrete).parseClaimsJws(token);
			return true;
		}catch (SignatureException ex){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
        }
	}
	
}
