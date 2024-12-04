package com.example.fitnessmanagementsystem.repositories;

import com.example.fitnessmanagementsystem.model.Member;
import com.example.fitnessmanagementsystem.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByAssignedTrainer(Trainer trainer);
}
