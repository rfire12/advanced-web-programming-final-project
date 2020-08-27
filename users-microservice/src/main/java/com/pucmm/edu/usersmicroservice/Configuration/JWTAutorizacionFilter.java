package com.pucmm.edu.usersmicroservice.Configuration;

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

public class JWTAutorizacionFilter extends OncePerRequestFilter {

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    @Value("${token_jwt}")
    String SECRET = "secret_key_here_asdasd_adadsasdas_asdasdasdas_1231231_asdasdsa_asdasdasdasd";

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
            final FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (request.getServletPath().startsWith("/api/")) {
            if (existeJWTToken(request, response)) {
                final Claims claims = validateToken(request);
                if (claims.get("authorities") != null) {
                    setUpSpringAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "Sin permisos");
                return;
            }

        }
        filterChain.doFilter(request, response);
    }

    private Claims validateToken(final HttpServletRequest request) {
        final String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
        return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
    }

    private void setUpSpringAuthentication(final Claims claims) {

        final List authorities = (List) claims.get("roles");
        final List<SimpleGrantedAuthority> listaAuto = new ArrayList<>();
        listaAuto.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(),
                null, listaAuto);
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    private boolean existeJWTToken(final HttpServletRequest request, final HttpServletResponse res) {
        final String authenticationHeader = request.getHeader(HEADER);
        if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
            return false;
        return true;
    }

}
