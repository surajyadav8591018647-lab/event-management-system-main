package com.example.demo.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
     throws ServletException, IOException{
        String authHeader = req.getHeader("Authorization");
        if(authHeader == null || ! authHeader.startsWith("Bearer ")){
            filterChain.doFilter((req), res);
            return;
        }

        String token = authHeader.substring(7);
            try{
                Claims claim = Jwts.parser().verifyWith(jwtService.getSecretKey()).build().parseSignedClaims(token).getPayload();

                String email = claim.getSubject();

                String role = claim.get("role", String.class);

                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

                UsernamePasswordAuthenticationToken  authentication = new UsernamePasswordAuthenticationToken(email,null, java.util.List.of(authority));

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch(Exception e){

                SecurityContextHolder.clearContext();

            }

            filterChain.doFilter(req, res);

    }

    @Override
protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

    String path = request.getServletPath();

    return path.equals("/api/users/register")
            || path.equals("/api/users/login");
}

}
