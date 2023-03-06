package com.example.opentalk.Security;

import com.example.opentalk.entity.RefreshToken;
import com.example.opentalk.model.Status;
import com.example.opentalk.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Date;



@Service
//@Profile("dev")
public class JwtToken {
    @Value("${jwt.token.secret}")
    private String secret;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private final long jwtExp = 5 * 1000 * 3600;


    public String generateToken(String username) throws Status {
        System.out.println(secret);
        String token = Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + jwtExp))
                    .signWith(SignatureAlgorithm.HS512, secret)
                    .compact();
        return token;
    }

    public RefreshToken generateRefreshToken(String username) throws Status {
        RefreshToken refreshToken = new RefreshToken();
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        refreshToken.setToken(token);
        refreshToken.setCreatedDate(new Date());
        return refreshTokenRepository.save(refreshToken);
    }

    public boolean validateToken(String jwt) {
        Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt);
        return true;
    }

    public String getUsernameFromJWT(String token) {
        return getBodyJwt(token).getSubject();
    }

    public Claims getBodyJwt(String jwt){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
