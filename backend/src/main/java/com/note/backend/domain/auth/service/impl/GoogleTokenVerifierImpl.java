package com.note.backend.domain.auth.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import com.note.backend.core.exception.custom.auth.TokenInvalidException;
import com.note.backend.domain.auth.service.GoogleTokenVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoogleTokenVerifierImpl implements GoogleTokenVerifier {
    @Value("${google.client-id}")
    private String googleClientId;

    @Override
    public GoogleIdToken.Payload verifyToken(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    GsonFactory.getDefaultInstance()
            ).setAudience(List.of(googleClientId)).build();

            GoogleIdToken idToken = verifier.verify(idTokenString);

            if (idToken == null) {
                throw new TokenInvalidException("Invalid Google ID Token");
            }

            return idToken.getPayload();
        } catch (Exception e) {
            throw new RuntimeException("Google token verification failed", e);
        }
    }
}
