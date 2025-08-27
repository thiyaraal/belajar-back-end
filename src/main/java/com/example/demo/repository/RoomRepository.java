package com.example.demo.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.RoomEntity;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, String> {
}
