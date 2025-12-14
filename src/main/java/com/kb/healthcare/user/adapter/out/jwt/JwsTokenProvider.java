package com.kb.healthcare.user.adapter.out.jwt;

import com.kb.healthcare.user.application.port.out.SignJwtPort;
import com.kb.healthcare.user.application.port.out.VerifyJwtPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.Builder;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Date.from;
import static java.util.UUID.randomUUID;
import static org.springframework.util.Assert.notNull;
import static org.springframework.util.StringUtils.hasText;

public class JwsTokenProvider implements SignJwtPort, VerifyJwtPort {

    private final String tokenIssuer;

    private final Duration tokenTimeToLive;

    private final PrivateKey privateKey;

    private final JwtParser jwtParser;

    @Builder
    private JwsTokenProvider(
            String tokenIssuer,
            Duration tokenTimeToLive,
            PublicKey publicKey,
            PrivateKey privateKey
    ) {
        notNull(tokenIssuer, "Token issuer must not be null");
        notNull(tokenTimeToLive, "Token time to live must not be null");
        notNull(publicKey, "Public key must not be null");
        notNull(privateKey, "Private key must not be null");

        this.tokenIssuer = tokenIssuer;
        this.tokenTimeToLive = tokenTimeToLive;
        this.privateKey = privateKey;
        this.jwtParser = Jwts.parser()
                .verifyWith(publicKey)
                .build();
    }

    @Override
    public String sign(String userId) {
        Date issuedAt = calculateIssuedAt();

        return Jwts.builder()
                .id(randomUUID().toString())
                .subject(userId)
                .issuer(tokenIssuer)
                .audience()
                .add(tokenIssuer)
                .and()
                .issuedAt(issuedAt)
                .notBefore(issuedAt)
                .expiration(calculateExpiration(issuedAt))
                .signWith(privateKey)
                .compact();
    }

    @Override
    public boolean verify(String token) {
        if (!hasText(token)) {
            return false;
        }

        try {
            return extractExpiration(token)
                    .after(calculateIssuedAt());
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String extractSubject(String token) {
        return extractClaims(token)
                .getSubject();
    }

    private Date extractExpiration(String token) {
        return extractClaims(token)
                .getExpiration();
    }

    private Date calculateIssuedAt() {
        return new Date();
    }

    private Date calculateExpiration(Date now) {
        Instant issuedAt = now.toInstant()
                .truncatedTo(MINUTES);

        return from(issuedAt.plus(tokenTimeToLive));
    }

    private Claims extractClaims(String token) {
        return jwtParser.parseSignedClaims(token)
                .getPayload();
    }

}
