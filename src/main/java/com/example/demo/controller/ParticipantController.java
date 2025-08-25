    package com.example.demo.controller;

    import com.example.demo.entity.Participant;
    import com.example.demo.repository.ParticipantRepository;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/participants")
    public class ParticipantController {

        private final ParticipantRepository participantRepository;

        public ParticipantController(ParticipantRepository participantRepository) {
            this.participantRepository = participantRepository;
        }

        @GetMapping(value="/find-all-participants")
        public List<Participant> getAllParticipants() {
            return participantRepository.findAll();
        }

        @PostMapping(value="/create-participant")
        public Participant createParticipant(@RequestBody Participant participant) {
            return participantRepository.save(participant);
        }

        @PutMapping(value="/update-participant/{id}")
        public Participant updateParticipant(@PathVariable Long id, @RequestBody Participant participant) {
            participant.setId(id);
            return participantRepository.save(participant);
        }

        @DeleteMapping(value="/delete-participant/{id}")
        public void deleteParticipant(@PathVariable Long id) {
            participantRepository.deleteById(id);
        }
    }