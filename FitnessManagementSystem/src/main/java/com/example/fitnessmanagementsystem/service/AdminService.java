package com.example.fitnessmanagementsystem.service;

import com.example.fitnessmanagementsystem.model.Member;
import com.example.fitnessmanagementsystem.model.Schedule;
import com.example.fitnessmanagementsystem.model.Trainer;
import com.example.fitnessmanagementsystem.repositories.MemberRepository;
import com.example.fitnessmanagementsystem.repositories.ScheduleRepository;
import com.example.fitnessmanagementsystem.repositories.TrainerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The service class for handling all admin related functions.
 * */
@Service
@Transactional
public class AdminService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * Adds a new member into the system.
     * @param username The username of the member.
     * @param password The password of the user, that will be encoded.
     * @param membershipType The type of membership the member has.
     * @param trainerId The ID of the trainer assigned to the member. (optional)
     * @return The saved member object.
     * */
    public Member addMember(String username, String password, String membershipType, Long trainerId) {
        Member member = new Member();
        member.setUsername(username);
        member.setPassword(passwordEncoder.encode(password));
        member.setRole("MEMBER");
        member.setMembershipType(membershipType);

        // Assign trainer if the ID is provided.
        if (trainerId != null) {
            Trainer trainer = trainerRepository.findById(trainerId)
                    .orElseThrow(() -> new RuntimeException("Trainer not found"));
            member.setAssignedTrainer(trainer);
        }
        return memberRepository.save(member);
    }

    /**
     * Deletes the member by ID.
     * @param id The ID of the member to be deleted.
     * @throws RuntimeException if the member doesn't exist.
     * */
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new RuntimeException("Member not found");
        }
        memberRepository.deleteById(id);
    }

    /**
     * Adds a new trainer to the system.
     * @param username The username of the trainer.
     * @param password The password of the trainer to be encoded.
     * @param specialization The specialty of the trainer.
     * @param shift The shift the trainer generally works.
     * @return The saved trainer object.
     * */
    public Trainer addTrainer(String username, String password, String specialization, String shift){
        Trainer trainer = new Trainer();
        trainer.setUsername(username);
        trainer.setPassword(passwordEncoder.encode(password));
        trainer.setRole("TRAINER");
        trainer.setSpecialization(specialization);
        trainer.setShift(shift);

        return trainerRepository.save(trainer);
    }
    /**
     * Deletes a trainer by ID, along with any schedules associated with that trainer.
     * @param id The ID of the trainer to be deleted.
     * @throws RuntimeException if the trainer doesn't exist.
     * */
    public void deleteTrainer(Long id){
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        scheduleRepository.deleteByTrainer(trainer);

        trainerRepository.deleteById(id);
    }

   /**
    * Adds a new schedule for a member and trainer.
    * @param memberId The ID of the member
    * @param trainerId The ID of the trainer.
    * @param startTime The time the activity starts.
    * @param endTime The time the activity ends.
    * @param activity The activity assigned in the schedule.
    * @return The saved schedule object.
    * */
    public Schedule addSchedule(Long memberId, Long trainerId, LocalDateTime startTime,
                                LocalDateTime endTime, String activity){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        Schedule schedule = new Schedule();
        schedule.setMember(member);
        schedule.setTrainer(trainer);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        schedule.setActivity(activity);

        return scheduleRepository.save(schedule);
    }

    /**
     * Retrieves all schedules in the system
     * @return A list of all schedules
     * */
    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }


    /**
     * Updates an existings members details.
     * @param id The ID of the member to be updated.
     * @param username The updated username.
     * @param password The updated passwork (optional).
     * @param membershipType The updated membership type.
     * @return The updated member object.
     * */
    public Member updateMember(Long id, String username, String password, String membershipType) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        member.setUsername(username);
        if (password != null) {
            member.setPassword(passwordEncoder.encode(password));
        }
        member.setMembershipType(membershipType);
        return memberRepository.save(member);
    }

    /**
     * Updates an existing trainers details.
     * @param id The ID of the trainer to be updated.
     * @param username The updated username.
     * @param password The updated password (optional).
     * @param specialization The updated specialization of the trainer.
     * @param shift The updated shift for the trainer.
     * @return THe updated trainer object.
     * */
    public Trainer updateTrainer(Long id, String username, String password, String specialization, String shift){
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        trainer.setUsername(username);
        if (password != null) {
            trainer.setPassword(passwordEncoder.encode(password));
        }
        trainer.setSpecialization(specialization);
        trainer.setShift(shift);

        return trainerRepository.save(trainer);
    }




}
