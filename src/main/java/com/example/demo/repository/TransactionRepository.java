package com.example.demo.repository;

import com.example.demo.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {

  List<TransactionEntity> findByRoomIdAndBookingDateStartOrderByStartTimeAsc(String roomId, LocalDate date);

  @Query("""
      select case when count(t) > 0 then true else false end
      from TransactionEntity t
      where t.room.id = :roomId
        and t.bookingDateStart = :date
        and t.startTime < :endTime
        and t.endTime > :startTime
      """)
  boolean existsOverlap(String roomId, LocalDate date, LocalTime startTime, LocalTime endTime);

  @Query("SELECT t FROM TransactionEntity t WHERE t.room.id = :roomId AND t.bookingDateStart = CURRENT_DATE")
  List<TransactionEntity> findTodayTransactionsByRoomId(@Param("roomId") String roomId);

}