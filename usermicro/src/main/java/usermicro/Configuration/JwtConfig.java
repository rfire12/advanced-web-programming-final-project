package usermicro.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtConfig extends OncePerRequestFilter {
    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    @Value("${token}")
    String SECRET;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!request.getServletPath().equals("/hello") && !request.getServletPath().equals("/auth")
                && !request.getServletPath().equals("/dbconsole")) {
            if (JWTExists(request, response)) {
                Claims claims = validateToken(request);
                if (claims.get("authorities") != null) {
                    setUpSpringAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
                filterChain.doFilter(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Not enough permissions");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private Claims validateToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
        return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
    }

    private void setUpSpringAuthentication(Claims claims) {
        List authorities = (List) claims.get("roles");
        List<SimpleGrantedAuthority> listaAuto = new ArrayList<>();
        listaAuto.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        listaAuto.add(new SimpleGrantedAuthority("ROLE_EMPLEADO"));
        listaAuto.add(new SimpleGrantedAuthority("ROLE_CLIENTE"));

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                listaAuto);
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    private boolean JWTExists(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader(HEADER);
        if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
            return false;
        return true;
    }
}