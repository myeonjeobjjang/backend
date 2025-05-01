package org.myeonjeobjjang.config.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.myeonjeobjjang.exception.BaseException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CustomExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (BaseException exception) {
            response.setStatus(exception.getHttpStatus().value());
            response.setContentType("application/json");
            response.getWriter().write("{ \"error\" : \"" + exception.getErrorCode() + "\"}");
        }
    }
}
