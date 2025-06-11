package org.example.expert.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class SignupResponse {

    private final long id;

    private final String bearerToken;

    public SignupResponse(long id, String bearerToken) {
        this.id = id;
        this.bearerToken = bearerToken;
    }
}
