package com.example.fitnessmanagementsystem.controller;

import com.example.fitnessmanagementsystem.model.Member;
import com.example.fitnessmanagementsystem.model.Schedule;
import com.example.fitnessmanagementsystem.model.Trainer;
import com.example.fitnessmanagementsystem.repositories.MemberRepository;
import com.example.fitnessmanagementsystem.repositories.ScheduleRepository;
import com.example.fitnessmanagementsystem.repositories.TrainerRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller for handling all trainer related functions.
 * Handles displaying the trainers dash board and viewing their schedules.
 * */
@Controller
@RequestMapping("/trainer")
public class TrainerController {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private TrainerRepository trainerRepository;

    /**
     * Displays the trainers dashboard.
     * Shows list of members assigned to the trainer and their schedules.
     * @param session The HTTP session to retrieve trainer details.
     * @param model The Model object that passes data to the view.
     * @return The view name for the trainers dashboard.
     * */
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        // Retrieve the trainerId from session
        Long trainerId = (Long) session.getAttribute("userId");

        // Retrieve the trainer object by ID.
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        // Retrieve the list of members assigned to the trainer.
        List<Member> assignedMembers = memberRepository.findByAssignedTrainer(trainer);
        model.addAttribute("assignedMembers", assignedMembers);

        //Retrieve the list of schedules associated with the trainer.
        List<Schedule> schedules = scheduleRepository.findByTrainer(trainer);
        model.addAttribute("schedules", schedules);


        return "trainer/dashboard";

    }




}
