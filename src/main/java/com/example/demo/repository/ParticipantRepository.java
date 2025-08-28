package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ParticipantEntity;


@Repository
public interface ParticipantRepository extends JpaRepository<ParticipantEntity, String> {
}
