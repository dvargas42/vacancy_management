package br.com.vargas.vacancy_management.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JWTCandidateProvider {

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    public DecodedJWT validateToken(String token) {
        token = token.replace("Bearer ", "");

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        DecodedJWT tokenDecoded;

        try {
            tokenDecoded = JWT.require(algorithm)
                .build()
                .verify(token);
            return tokenDecoded;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
