package br.com.vargas.vacancy_management.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.vargas.vacancy_management.enums.UserType;
import br.com.vargas.vacancy_management.providers.JWTCandidateProvider;
import br.com.vargas.vacancy_management.providers.JWTCompanyProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class InternalFilter {

    @Autowired
    private JWTCandidateProvider jwtCandidateProvider;

    @Autowired
    private JWTCompanyProvider jwtCompanyProvider;
    
    public void execute(HttpServletRequest request, HttpServletResponse response, UserType userType) {
        SecurityContextHolder.getContext().setAuthentication(null);
        String header = request.getHeader("Authorization");

        if (header != null) {
            DecodedJWT token = null;
            
            if (userType.equals(UserType.CANDIDATE)) {
                token = jwtCandidateProvider.validateToken(header);
            }
            
            if (userType.equals(UserType.COMPANY)) {
                token = jwtCompanyProvider.validateToken(header);
            } 

            if (token == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            request.setAttribute(userType.getDescription(), token.getSubject());
            List<Object> roles = token.getClaim("roles").asList(Object.class);
            List<SimpleGrantedAuthority> grants = roles
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString()))
                .toList();
            
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                token.getSubject(), null, grants); 
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }
}
