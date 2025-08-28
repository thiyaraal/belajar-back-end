package com.example.demo.controller.participant;

import com.example.demo.api.ApiResponse;
import com.example.demo.dto.participant.ParticipantCreateRequest;
import com.example.demo.dto.participant.ParticipantResponse;
import com.example.demo.entity.participant.ParticipantEntity;
import com.example.demo.mapper.ParticipantMapper;
import com.example.demo.repository.ParticipantRepository;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    private final ParticipantRepository participantRepository;

    public ParticipantController(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    @GetMapping("/find-all-participants")
    public ResponseEntity<List<ParticipantResponse>> getAllParticipants() {
        List<ParticipantResponse> result = participantRepository.findAll()
                .stream().map(ParticipantMapper::toResponse).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/find-participant/{id}")
    public ResponseEntity<ApiResponse<ParticipantResponse>> getParticipantById(@PathVariable String id) {
        ParticipantEntity participant = participantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Participant not found: " + id));
        return ResponseEntity.ok(ApiResponse.ok("Participant found", ParticipantMapper.toResponse(participant)));
    }

    @PostMapping("/create-participant")
    public ResponseEntity<ApiResponse<ParticipantResponse>> createParticipant(
            @Valid @RequestBody ParticipantCreateRequest req,
            UriComponentsBuilder uri) {

        ParticipantEntity p = ParticipantEntity.builder()
                .name(req.getName())
                .email(req.getEmail())
                .handphone(req.getPhoneNumber())
                .companyCode(req.getCompanyCode())
                .build();

        ParticipantEntity saved = participantRepository.save(p);
        var body = ParticipantMapper.toResponse(saved);

        return ResponseEntity
                .created(uri.path("/participants/{id}")
                        .buildAndExpand(saved.getId())
                        .toUri())
                .body(ApiResponse.created("Participant successfully added", body));

    }

    @PutMapping("/update-participant/{id}")
    public ResponseEntity<ApiResponse<ParticipantResponse>> updateParticipant(@PathVariable String id,
            @RequestBody ParticipantEntity payload) {
        ParticipantEntity p = participantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Participant not found: " + id));

        p.setCompanyCode(payload.getCompanyCode());
        p.setName(payload.getName());
        p.setEmail(payload.getEmail());
        p.setHandphone(payload.getHandphone());
        ParticipantEntity saved = participantRepository.save(p);
        return ResponseEntity
                .ok(ApiResponse.ok("Participant successfully updated", ParticipantMapper.toResponse(saved)));
    }

    @DeleteMapping("/delete-participant/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteParticipant(@PathVariable String id) {
        participantRepository.deleteById(id);
        return ResponseEntity
                .ok(ApiResponse.ok("Participant successfully deleted", null));
    }
}