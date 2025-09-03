package com.example.demo.dto.participant;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantCreateRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String handPhone;
    @NotBlank
    private String companyCode;

}
