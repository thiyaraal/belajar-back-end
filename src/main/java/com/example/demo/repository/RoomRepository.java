package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.RoomEntity;
import com.example.demo.entity.TransactionEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, String> {

    boolean existsByPasscode(String passcode);

    Optional<RoomEntity> findByPasscode(String passcode);

    @EntityGraph(attributePaths = "transactions")
    Optional<RoomEntity> findWithTransactionsById(String id);

    @Query("""
              SELECT t FROM TransactionEntity t
              WHERE t.room.id = :roomId AND t.bookingDateStart = :today
            """)
    List<TransactionEntity> findByRoomIdAndBookingDateStart(@Param("roomId") String roomId,
            @Param("today") LocalDate today);

}
