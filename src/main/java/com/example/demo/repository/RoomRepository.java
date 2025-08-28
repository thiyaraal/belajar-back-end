package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.room.RoomEntity;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, String> {
}
