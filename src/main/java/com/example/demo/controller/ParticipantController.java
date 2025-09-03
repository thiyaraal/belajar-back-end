package com.example.demo.controller;

import com.example.demo.dto.common.ApiResponse;
import com.example.demo.dto.participant.ParticipantCreateRequest;
import com.example.demo.dto.participant.ParticipantResponse;
import com.example.demo.dto.participant.ParticipantSimpleResponse;
import com.example.demo.dto.participant.ParticipantUpdateRequest;
import com.example.demo.repository.ParticipantRepository;
import com.example.demo.service.ParticipantService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

        private final ParticipantService participantService;

        public ParticipantController(ParticipantRepository participantRepository,
                        ParticipantService participantService) {
                this.participantService = participantService;
        }

        @GetMapping("/find-all")
        public ResponseEntity<List<ParticipantSimpleResponse>> getAllParticipants() {
                List<ParticipantSimpleResponse> result = participantService.getAllParticipants();
                return ResponseEntity.ok(result);
        }

        @GetMapping("/find-participant/{id}")
        public ResponseEntity<ApiResponse<ParticipantResponse>> getParticipantById(@PathVariable String id) {
                ParticipantResponse response = participantService.getById(id);
                return ResponseEntity.ok(ApiResponse.ok("Participant found", response));
        }

        @PostMapping("/create-participant")
        public ResponseEntity<ApiResponse<ParticipantResponse>> createParticipant(
                        @Valid @RequestBody ParticipantCreateRequest req,
                        UriComponentsBuilder uri) {
                ParticipantResponse saved = participantService.createParticipant(req);
                URI location = uri.path("/participants/{id}").buildAndExpand(saved.getId()).toUri();
                return ResponseEntity.created(location)
                                .body(ApiResponse.created("Participant successfully added", saved));

        }

        @PutMapping("update-participant/{id}")
        public ResponseEntity<ApiResponse<ParticipantResponse>> putMethodName(@PathVariable String id,
                        @RequestBody ParticipantUpdateRequest p) {

                return ResponseEntity.ok(ApiResponse.ok("Participant successfully updated",
                                participantService.updateParticipant(id, p)));
        }

        @DeleteMapping("/delete-participant/{id}")
        public ResponseEntity<ApiResponse<Void>> deleteParticipant(@PathVariable String id) {
                participantService.deleteParticipant(id);
                return ResponseEntity.ok(ApiResponse.ok("Participant successfully deleted", null));
        }
}