package security_package.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil { //Make sure to have this in gitignore to not expose secret :)

    //Secret key to generate tokens
    @Value("${jwt.secret}")
    private String jwtSecret;

    //Expiration time of token in milliseconds
    @Value("${jwt.expiration}")
    private int jwtExpiration;

    //create encrypted key based on these secret values
    private Key getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //generate token
    public String generateToken(UserDetails userDetails) {
        return io.jsonwebtoken.Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //validate token
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
            return  (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    //extract username from token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
}

    //check if token is expired
    private boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    //extract all claims from token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build().parseClaimsJws(token)
                .getBody();
    }
}