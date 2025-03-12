package org.myeonjeobjjang.common.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;
    private String AUTHORIZATION_HEADER = "Authorization";
    private String REFRESH_HEADER = "refreshToken";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = null;

        String refreshToken = removeBearer(((HttpServletRequest) request).getHeader(REFRESH_HEADER));
        String accessToken = removeBearer(((HttpServletRequest) request).getHeader(AUTHORIZATION_HEADER));
        if (refreshToken != null) {
            jwtTokenProvider.checkJwt(refreshToken, JwtType.REFRESH_TOKEN);
            authentication = jwtTokenProvider.getAuthentication(refreshToken, JwtType.REFRESH_TOKEN);
        } else if (accessToken != null) {
            jwtTokenProvider.checkJwt(accessToken, JwtType.ACCESS_TOKEN);
            authentication = jwtTokenProvider.getAuthentication(accessToken, JwtType.ACCESS_TOKEN);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private String removeBearer(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return token;
    }
}
