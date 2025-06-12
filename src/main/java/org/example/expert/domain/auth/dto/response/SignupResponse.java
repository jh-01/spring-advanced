package org.example.expert.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.expert.domain.user.dto.response.UserResponse;

@Getter
@AllArgsConstructor
public class SignupResponse {
    private Long id;
    private String accessToken;
    private String refreshToken;
    private UserResponse user;
}
