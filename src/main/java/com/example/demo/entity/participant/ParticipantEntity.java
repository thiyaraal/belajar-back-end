package com.example.demo.entity.participant;

import com.example.demo.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "participants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantEntity extends BaseEntity {

  private String companyCode;
  private String name;
  private String email;
  private String handphone;
}