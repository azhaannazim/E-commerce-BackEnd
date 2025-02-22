package com.ecommerce.project.sbECom.security.jwt;

import com.ecommerce.project.sbECom.security.services.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

//contains utility methods for generating, parsing, and validating JWTs.
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${spring.app.jwtSecrets}") //fetching
    private String jwtSecret; //used for signing the tokens

    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs; //milliseconds

    @Value("${spring.ecom.app.jwtCookieName}")
    private String jwtCookie;

    //Authorization using bearer token
//    public String getJwtFromHeader(HttpServletRequest request){
//        String bearerToken = request.getHeader("Authorization");
//        logger.debug("Authorization Header : {}",bearerToken);
//        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
    public String getJwtFromCookies(HttpServletRequest request){
        Cookie cookie = WebUtils.getCookie(request , jwtCookie);
        if(cookie != null){
            return cookie.getValue();
        }
        return null;
    }
    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal){
        String jwt = generateTokenFromUserName(userPrincipal.getUsername());
        ResponseCookie cookie = ResponseCookie.from(jwtCookie ,jwt)
                                                .path("/api")
                                                .maxAge(24*60*60)
                                                .httpOnly(false)
                .secure(false)
                                                .build();
        return cookie;
    }
    public ResponseCookie getCleanJwtCookie(){
        ResponseCookie cookie = ResponseCookie.from(jwtCookie , null)
                .path("/api")
                .build();
        return cookie;
    }

    //auth using bearer token
//    public String generateTokenFromUserName(UserDetails userDetails){
//        String userName = userDetails.getUsername();
//        return Jwts.builder()
//                .subject(userName)
//                .issuedAt(new Date())
//                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
//                .signWith(key())
//                .compact();
//    }
    public String generateTokenFromUserName(String userName){
        return Jwts.builder()
                .subject(userName)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }
    public String getUserNameFromJwtToken(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    public boolean validateJwtToken(String authToken){
        try{
            System.out.println("Validate :");
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        }
        catch (MalformedJwtException e){
            logger.error("Invalid JWT token : {}", e.getMessage());
        }
        catch (ExpiredJwtException e){
            logger.error("Expired JWT token : {}", e.getMessage());
        }
        catch (UnsupportedJwtException e){
            logger.error("JWT token is unsupported : {}", e.getMessage());
        }
        catch (IllegalArgumentException e){
            logger.error("JWT claims string is empty : {}", e.getMessage());
        }
        return false;
    }


}
