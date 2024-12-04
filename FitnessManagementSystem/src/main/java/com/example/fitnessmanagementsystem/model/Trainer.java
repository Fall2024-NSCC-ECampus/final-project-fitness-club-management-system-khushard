package com.example.fitnessmanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Trainer extends User {

    private String specialization;
    private String shift;

    @OneToMany(mappedBy = "assignedTrainer")
    private List<Member> members;



}
