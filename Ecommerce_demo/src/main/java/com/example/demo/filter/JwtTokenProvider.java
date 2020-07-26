package com.example.demo.filter;

import com.example.demo.dto.UserPrincipal;
import com.example.demo.util.DataUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {
    private String secretKey = "SpringBootTutorial";
    private Long expireHour = Long.valueOf("5");
    public String generateToken(Authentication authentication){
        UserPrincipal userPrinciple = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        return Jwts.builder().setId(UUID.randomUUID().toString())
                .claim("username", userPrinciple.getUsername())
//                .claim("role", userPrinciple.getAuthorities().stream().map(grantedAuthority -> ))
                .setSubject(String.valueOf(userPrinciple.getId()))
                .setIssuedAt(now).setExpiration(DataUtils.getExpirationTime(expireHour))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public Long getUserIdFromToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return Long.valueOf(claims.getSubject());
    }

    public Boolean isValidateToken(String token){
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
