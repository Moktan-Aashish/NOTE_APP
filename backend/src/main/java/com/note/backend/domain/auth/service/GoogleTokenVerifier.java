package com.note.backend.domain.auth.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

public interface GoogleTokenVerifier {
    GoogleIdToken.Payload verifyToken(String idTokenString);
}
