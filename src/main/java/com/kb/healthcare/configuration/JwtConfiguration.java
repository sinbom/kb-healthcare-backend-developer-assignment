package com.kb.healthcare.configuration;

import com.kb.healthcare.user.adapter.out.jwt.JwsTokenProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static java.time.Duration.ofHours;

@Configuration
@EnableConfigurationProperties(value = JwtProperty.class)
class JwtConfiguration {

    @Bean
    JwsTokenProvider jwtTokenProvider(JwtProperty jwtProperty) {
        return JwsTokenProvider.builder()
                .tokenIssuer("kb-healthcare-application")
                .tokenTimeToLive(ofHours(1L))
                .publicKey(createPublicKey(jwtProperty.publicKey()))
                .privateKey(createPrivateKey(jwtProperty.privateKey()))
                .build();
    }

    private PublicKey createPublicKey(String encodedPublicKey) {
        byte[] decode = Base64.getDecoder()
                .decode(encodedPublicKey);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return keyFactory.generatePublic(new X509EncodedKeySpec(decode));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    private PrivateKey createPrivateKey(String encodedPrivateKey) {
        byte[] decode = Base64.getDecoder()
                .decode(encodedPrivateKey);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decode));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

}
