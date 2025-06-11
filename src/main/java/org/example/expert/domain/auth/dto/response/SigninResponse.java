package org.example.expert.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SigninResponse {
    private String accessToken;
    private String refreshToken;
    private UserData user;
}
