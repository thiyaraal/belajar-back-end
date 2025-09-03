package com.example.demo.dto.participant;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantSimpleResponse {
    private String id;
    private String companyCode;
    private String name;
    private String email;
    private String handphone;
}