package br.com.vargas.vacancy_management.modules.candidate.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.vargas.vacancy_management.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.vargas.vacancy_management.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.vargas.vacancy_management.modules.candidate.repositories.CandidateRepository;

@Service
public class AuthCandidateUseCase {

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) 
        throws AuthenticationException {
            
        var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
            .orElseThrow(() -> {
                throw new BadCredentialsException("Bad credentials");
            });

        boolean passwordMatches = this.passwordEncoder.matches(
            authCandidateRequestDTO.password(), 
            candidate.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        Instant expiresIn = Instant.now().plus(Duration.ofHours(2));
        Algorithm algoritm = Algorithm.HMAC256(secretKey);

        String token = JWT.create()
            .withIssuer("java_jobs")
            .withExpiresAt(expiresIn)
            .withSubject(candidate.getId().toString())
            .withClaim("roles", Arrays.asList("CANDIDATE"))
            .sign(algoritm);
        
        return AuthCandidateResponseDTO.builder()
            .access_token(token)
            .expires_in(expiresIn.toEpochMilli())
            .build();
    }
}
