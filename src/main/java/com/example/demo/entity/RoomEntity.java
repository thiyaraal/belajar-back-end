package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonIgnoreType;

@JsonIgnoreType
@Entity
@Table(name = "rooms", uniqueConstraints = @UniqueConstraint(name = "uk_rooms_passcode", columnNames = "passcode"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RoomEntity extends BaseEntity {
    private String sessionToken;
    private String roomCode;
    private String roomName;
    private String roomType;
    private Integer roomCapacity;
    private String picUrl;
    private String picFileName;
    private String roomColorTag;
    private Boolean activeRoom;
    private String roomLocation;
    private String roomDimension;
    private Integer roomDuration;
    private String calendarId;
    @Column(nullable = false, length = 16)
    private String passcode;
    private Boolean inUsed;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionEntity> transactions;

}
