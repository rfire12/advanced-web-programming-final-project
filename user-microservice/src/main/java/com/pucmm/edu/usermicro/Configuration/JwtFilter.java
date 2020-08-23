package com.pucmm.edu.usermicro.Configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwtFilter extends OncePerRequestFilter {
    private String header = "Authorization";
    private String prefix = "Bearer ";
    @Value("ourbesttokenyet")
    private String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String myPath = request.getServletPath();

        if (myPath.startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (myPath.startsWith("/api/")) {
            if (jwtTokenExists(request, response)) {
                Claims claims = validateToken(request);
                if (claims.get("authorities") != null) {
                    setupSpringAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Without permissions");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private Claims validateToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(header).replace(prefix, "");
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJwt(jwtToken).getBody();
    }

    private void setupSpringAuthentication(Claims claims) {
        List<SimpleGrantedAuthority> authoritiesList = new ArrayList<>();
        authoritiesList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        authoritiesList.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
        authoritiesList.add(new SimpleGrantedAuthority("ROLE_CLIENT"));

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authoritiesList);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private boolean jwtTokenExists(HttpServletRequest request, HttpServletResponse response) {
        String authenticationHeader = request.getHeader(header);
        if (authenticationHeader == null || !authenticationHeader.startsWith(prefix))
            return false;
        return true;
    }
}
