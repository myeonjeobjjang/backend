package org.myeonjeobjjang.config.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.config.security.PrincipalDetails;
import org.myeonjeobjjang.config.security.PrincipalDetailsService;
import org.myeonjeobjjang.exception.BaseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.myeonjeobjjang.common.errorCode.SecurityErrorCode.*;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private SecretKey refreshSecretKey;
    private SecretKey accessSecretKey;
    private @Value("${jwt.issuer}") String issuer;
    private @Value("${jwt.refresh_jwt_secret}") String refreshJwtSecret;
    private @Value("${jwt.access_jwt_secret}") String accessJwtSecret;
    private @Value("${jwt.refresh_expired_time}") Long refreshExpiredTime;
    private @Value("${jwt.access_expired_time}") Long accessExpiredTime;

    private final PrincipalDetailsService principalDetailsService;

    @PostConstruct
    protected void init() {
        refreshSecretKey = new SecretKeySpec(refreshJwtSecret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS512.key().build().getAlgorithm());
        accessSecretKey = new SecretKeySpec(accessJwtSecret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS512.key().build().getAlgorithm());
    }

    public String createJwt(String memberId, JwtType tokenType, Long currentTimeMillis) {
        if (tokenType == JwtType.REFRESH_TOKEN)
            return getRefreshJwt(memberId, currentTimeMillis);
        else if (tokenType == JwtType.ACCESS_TOKEN)
            return getAccessJwt(memberId, currentTimeMillis);
        else
            throw new BaseException(UNSUPPORTED_TOKEN_TYPE);
    }

    private String getRefreshJwt(String memberId, Long currentTimeMillis) {
        return Jwts.builder()
            .issuer(issuer)
            .subject(memberId)
            .claim("tokenType", JwtType.REFRESH_TOKEN.getData())
            .issuedAt(new Date(currentTimeMillis))
            .expiration(new Date(currentTimeMillis + refreshExpiredTime))
            .signWith(refreshSecretKey).compact();
    }

    private String getAccessJwt(String memberId, Long currentTimeMillis) {
        return Jwts.builder()
            .issuer(issuer)
            .subject(memberId)
            .claim("tokenType", JwtType.ACCESS_TOKEN.getData())
            .issuedAt(new Date(currentTimeMillis))
            .expiration(new Date(currentTimeMillis + accessExpiredTime))
            .signWith(accessSecretKey).compact();
    }

    public Authentication getAuthentication(String token, JwtType jwtType) {
        try {
            PrincipalDetails principalDetails
                = principalDetailsService.loadUserByUsername(getMemberId(token, jwtType));
            return new UsernamePasswordAuthenticationToken(principalDetails,
                "", principalDetails.getAuthorities());
        } catch (UsernameNotFoundException exception) {
            throw new BaseException(UNSUPPORTED_JWT);
        }
    }

    public String getMemberId(String token, JwtType jwtType) {
        if(jwtType == JwtType.REFRESH_TOKEN)
            return Jwts.parser().verifyWith(refreshSecretKey).build().parseSignedClaims(token).getPayload().getSubject();
        else if(jwtType == JwtType.ACCESS_TOKEN)
            return Jwts.parser().verifyWith(accessSecretKey).build().parseSignedClaims(token).getPayload().getSubject();
        else
            throw new BaseException(UNSUPPORTED_TOKEN_TYPE);
    }

    public boolean checkJwt(String token, JwtType jwtType) {
        SecretKey secretKey = null;
        if(jwtType == JwtType.REFRESH_TOKEN) {
            secretKey = refreshSecretKey;
        }
        else if(jwtType == JwtType.ACCESS_TOKEN) {
            secretKey = accessSecretKey;
        }
        else {
            throw new BaseException(UNSUPPORTED_TOKEN_TYPE);
        }
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
        } catch (ExpiredJwtException e) {
            throw new BaseException(EXPIRED_TOKEN);
        } catch (SecurityException | MalformedJwtException e) {
            throw new BaseException(INVALID_JWT);
        } catch (UnsupportedJwtException e) {
            throw new BaseException(UNSUPPORTED_JWT);
        }
        return true;
    }
}
