package org.example.expert.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.expert.domain.user.enums.UserRole;

@Getter
@AllArgsConstructor
public class UserData {
    private Long id;
    private String email;
    private UserRole role; // 예: ADMIN, USER 등
}