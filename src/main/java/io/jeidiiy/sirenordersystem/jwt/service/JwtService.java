package io.jeidiiy.sirenordersystem.jwt.service;

import io.jeidiiy.sirenordersystem.jwt.model.JwtToken;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtService {

  private final SecretKey key;

  public JwtService(@Value("${jwt.secret-key}") String key) {
    this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
  }

  public String generateAccessToken(Date now, String subject) {
    var expiration = new Date(now.getTime() + (1000 * 60 * 60 * 3)); // 3시간
    return Jwts.builder()
        .subject(subject)
        .signWith(key)
        .issuedAt(now)
        .expiration(expiration)
        .compact();
  }

  public String generateRefreshToken(Date now, String subject) {
    var refreshTokenExpiration = new Date(now.getTime() + (1000L * 60 * 60 * 24 * 30)); // 30일
    return Jwts.builder()
        .subject(subject)
        .signWith(key)
        .issuedAt(now)
        .expiration(refreshTokenExpiration)
        .compact();
  }

  public JwtToken generateJwtToken(String subject) {
    Date now = new Date();
    String accessToken = generateAccessToken(now, subject);
    String refreshToken = generateRefreshToken(now, subject);

    return JwtToken.builder().accessToken(accessToken).refreshToken(refreshToken).build();
  }

  public boolean validateToken(String token) {
    try {
      getJwtParser().parseSignedClaims(token);
      return true;
    } catch (JwtException e) {
      log.error("Jwt Exception", e);
      return false;
    }
  }

  public String getUsername(String token) {
    return getSubject(token);
  }

  public Date getExpiration(String token) {
    try {
      return getJwtParser().parseSignedClaims(token).getPayload().getExpiration();
    } catch (JwtException e) {
      log.error("Jwt Exception", e);
      throw e;
    }
  }

  private String getSubject(String token) {
    try {
      return getJwtParser().parseSignedClaims(token).getPayload().getSubject();
    } catch (JwtException e) {
      log.error("Jwt Exception", e);
      throw e;
    }
  }

  private JwtParser getJwtParser() {
    return Jwts.parser().verifyWith(key).build();
  }
}
