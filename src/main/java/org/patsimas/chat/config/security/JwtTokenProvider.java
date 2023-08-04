package org.patsimas.chat.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.patsimas.chat.dto.users.ValidTokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.milliseconds}")
    private int jwtExpirationInMs;

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){

        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    public String generateToken(Map<String, Object> claims, String subject){

        return createToken(claims, subject, jwtExpirationInMs);
    }

    public ValidTokenDto validateToken(String token) {

        boolean isValid;

        String extractUsername = extractUsername(token);

        isValid = !isTokenExpired(token);

        return ValidTokenDto.builder()
                .isValid(isValid)
                .extractedTokenUsername(extractUsername)
                .build();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private String createToken(Map<String, Object> claims, String subject, int jwtExpiration){
// subject the person who has succesfully authenticated(username)
// expiration
// sign the token by using an algorithm, secret key must sth more complicated in a prod application
// compact -> end of build pattern
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .setIssuer(getServerName())
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    private String getServerName() {
        InetAddress ip;
        String hostname = "";
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostname;
    }
}
