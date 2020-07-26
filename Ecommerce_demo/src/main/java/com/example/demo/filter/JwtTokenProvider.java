package com.example.demo.filter;

import com.example.demo.dto.UserPrincipal;
import com.example.demo.util.DataUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.UUID;

@Configuration
public class JwtTokenProvider {
    private String secretKey = "SpringBoot_E-commerce";
    private Long expireHour = Long.valueOf("5");
    public String generateToken(Authentication authentication) {
        //ekhon authentication theke UserPrincipal take ber kore nibo
        UserPrincipal userPrinciple = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        return Jwts.builder().setId(UUID.randomUUID().toString())
                .claim("username", userPrinciple.getUsername())
                //.claim("role",userPrinciple.getAuthorities().stream().map(grantedAuthority -> ))
                .setSubject(String.valueOf(userPrinciple.getId()))
                .setIssuedAt(now)//mane ei token ta kokhon make kora hoise
                .setExpiration(DataUtils.getExpirationTime(expireHour))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return Long.valueOf(claims.getSubject());
    }

    public Boolean isValidateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
