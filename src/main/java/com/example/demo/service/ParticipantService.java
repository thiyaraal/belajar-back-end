package com.example.demo.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.demo.dto.participant.ParticipantCreateRequest;
import com.example.demo.dto.participant.ParticipantResponse;
import com.example.demo.dto.participant.ParticipantSimpleResponse;
import com.example.demo.dto.participant.ParticipantUpdateRequest;
import com.example.demo.entity.ParticipantEntity;
import com.example.demo.mapper.ParticipantMapper;
import com.example.demo.repository.ParticipantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    public final ParticipantRepository participantRepository;

    public List<ParticipantSimpleResponse> getAllParticipants() {
        return participantRepository.findAll()
                .stream()
                .map(ParticipantMapper::toSimpleResponse)
                .toList();
    }

    public ParticipantResponse getById(String id) {
        ParticipantEntity participant = participantRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant not found: " + id));
        return ParticipantMapper.toResponse(participant);
    }

    public ParticipantResponse createParticipant(ParticipantCreateRequest req) {
        ParticipantEntity participant = ParticipantEntity.builder()
                .name(req.getName())
                .email(req.getEmail())
                .handphone(req.getHandPhone())
                .companyCode(req.getCompanyCode())
                .build();
        ParticipantEntity saved = participantRepository.save(participant);
        return ParticipantMapper.toResponse(saved);
    }

    public ParticipantResponse updateParticipant(String id, ParticipantUpdateRequest p) {
        ParticipantEntity participant = participantRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant not found: " + id));

        participant.setName(p.getName());
        participant.setEmail(p.getEmail());
        participant.setHandphone(p.getHandphone());
        participant.setCompanyCode(p.getCompanyCode());

        ParticipantEntity updated = participantRepository.save(participant);
        return ParticipantMapper.toResponse(updated);
    }

    public void deleteParticipant(String id) {
        ParticipantEntity participant = participantRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Participant not found: " + id));
        participantRepository.delete(participant);
    }
}