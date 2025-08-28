package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.RoomEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, String> {
    boolean existsByPasscode(String passcode);
    Optional<RoomEntity> findByPasscode(String passcode);
}
