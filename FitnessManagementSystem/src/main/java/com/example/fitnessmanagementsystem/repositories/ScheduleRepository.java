package com.example.fitnessmanagementsystem.repositories;

import com.example.fitnessmanagementsystem.model.Member;
import com.example.fitnessmanagementsystem.model.Schedule;
import com.example.fitnessmanagementsystem.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByMember(Member member);
    List<Schedule> findByTrainer(Trainer trainer);

    void deleteByTrainer(Trainer trainer);
    void deleteByMember(Member member);

}
