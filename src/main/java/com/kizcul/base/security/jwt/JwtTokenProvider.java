package com.kizcul.base.security.jwt;

import com.kizcul.entity.user.UserEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final SecurityProperty prop;

    public String getUserIdx(String header) throws Exception{
        String token = header.substring(7);

        byte[] signinKey = getSigninKey();

        Jws<Claims> parsedToken = Jwts.parser()
                .setSigningKey(signinKey)
                .parseClaimsJws(token);

        return parsedToken.getBody().getSubject();
    }

    public String createToken(String userIdx, String userId, String role) {
        byte[] signinKey = getSigninKey();

        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, Keys.hmacShaKeyFor(signinKey))
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .claim("udx", userIdx)
                .claim("uid", userId)
                .claim("rol", role)
                .compact();

        return token;
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
        if(isnotEmpty(tokenHeader)){
            try {
                byte[] signinKey = getSigninKey();

                Jws<Claims> parsedToken = Jwts.parser()
                        .setSigningKey(signinKey)
                        .parseClaimsJws(tokenHeader.replace("Bearer ",""));

                Claims claims = parsedToken.getBody();

                String userIdx = (String)claims.get("udx");
                String userId = (String)claims.get("uid");

                if(isnotEmpty(userId)){
                    UserEntity user = new UserEntity();
                    user.setUserIdx(userIdx);
                    user.setUserId(userId);
                    user.setPassword("");

                    UserDetails details = new CustomUser(user);

                    return new UsernamePasswordAuthenticationToken(details, null, null);
                }
            }  catch (ExpiredJwtException exception) {
                log.warn("Request to parse expired JWT : {} failed : {}", tokenHeader, exception.getMessage());
            } catch (UnsupportedJwtException exception) {
                log.warn("Request to parse unsupported JWT : {} failed : {}", tokenHeader, exception.getMessage());
            } catch (MalformedJwtException exception) {
                log.warn("Request to parse invalid JWT : {} failed : {}", tokenHeader, exception.getMessage());
            } catch (IllegalArgumentException exception) {
                log.warn("Request to parse empty or null JWT : {} failed : {}", tokenHeader, exception.getMessage());
            }
        }
        return null;
    }

    private boolean isnotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    private boolean isEmpty(CharSequence cs) {
        return cs == null || cs.isEmpty();
    }

    private byte[] getSigninKey() {
        return prop.getSecretkey().getBytes();
    }

    public Boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(prop.getSecretkey()).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException exception) {
            log.error("Token expired");
            return false;
        } catch (JwtException exception) {
            log.error("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            log.error("Token is null");
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
