package com.cdut.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Map;

/**
 * JWT token生成解析工具类
 * Created by king on 2017/9/22.
 */
public class JWTUtil {

    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, "cdut")
                .compact();
    }

    public static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey("cdut")
                    .parseClaimsJws(token)
                    .getBody();

        } catch (Exception e) {

            return null;
        }
        return claims;
    }

    public static Long getUserId(String token) {
        Long userId = null;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey("cdut")
                    .parseClaimsJws(token)
                    .getBody();
          userId =  Long.parseLong(claims.get("ID").toString());
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
        return userId;
    }
}
