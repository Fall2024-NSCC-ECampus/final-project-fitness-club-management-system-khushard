package com.example.fitnessmanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member extends User {

    private String membershipType;

   @ManyToOne
    private Trainer assignedTrainer;
}
