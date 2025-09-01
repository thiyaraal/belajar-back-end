package com.example.demo.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasscodeLogoutRequest {
    @NotBlank
    private String passcode;

}
