package com.example.demo.controller;

import com.example.demo.dto.ParticipantResponse;
import com.example.demo.entity.ParticipantEntity;
import com.example.demo.mapper.ParticipantMapper;
import com.example.demo.repository.ParticipantRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/create-participant")
    public ResponseEntity<ParticipantResponse> createParticipant(@RequestBody ParticipantEntity participant) {

        ParticipantEntity saved = participantRepository.save(participant);
        return ResponseEntity.ok(ParticipantMapper.toResponse(saved));
    }

    @PutMapping("/update-participant/{id}")
    public ResponseEntity<ParticipantResponse> updateParticipant(@PathVariable String id,
            @RequestBody ParticipantEntity payload) {
        ParticipantEntity p = participantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Participant not found: " + id));

        p.setCompanyCode(payload.getCompanyCode());
        p.setName(payload.getName());
        p.setEmail(payload.getEmail());
        p.setHandphone(payload.getHandphone());
        ParticipantEntity saved = participantRepository.save(p);
        return ResponseEntity.ok(ParticipantMapper.toResponse(saved));
    }

    @DeleteMapping("/delete-participant/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable String id) {
        participantRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}