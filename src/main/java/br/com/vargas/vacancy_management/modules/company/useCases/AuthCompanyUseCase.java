package br.com.vargas.vacancy_management.modules.company.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.vargas.vacancy_management.exceptions.CompanyNotFountException;
import br.com.vargas.vacancy_management.modules.company.dto.AuthCompanyRequestDTO;
import br.com.vargas.vacancy_management.modules.company.dto.AuthCompanyResponseDTO;
import br.com.vargas.vacancy_management.modules.company.entities.CompanyEntity;
import br.com.vargas.vacancy_management.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret.company}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public AuthCompanyResponseDTO execute(AuthCompanyRequestDTO authCompanyRequestDTO)
        throws AuthenticationException {

        CompanyEntity company = this.companyRepository.findByUsername(authCompanyRequestDTO.getUsername())
            .orElseThrow(() -> {
                throw new CompanyNotFountException();
            });
        //Verificar se as senhas são iguais
        boolean passwordMatches = this.passwordEncoder.matches(
            authCompanyRequestDTO.getPassword(), 
            company.getPassword());

        //Se não for igual subir exceção
        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        Instant expiresIn = Instant.now().plus(Duration.ofHours(2));
        //Se for igual, gerar token
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        String token = JWT.create().withIssuer("java_jobs")
            .withExpiresAt(expiresIn)
            .withSubject(company.getId().toString())
            .withClaim("roles", Arrays.asList("COMPANY"))
            .sign(algorithm);
            
        return AuthCompanyResponseDTO.builder()
            .access_token(token)
            .expires_in(expiresIn.toEpochMilli())
            .build();
    }
}
