package com.example.fitnessmanagementsystem.controller;

import com.example.fitnessmanagementsystem.model.Member;
import com.example.fitnessmanagementsystem.repositories.MemberRepository;
import com.example.fitnessmanagementsystem.repositories.ScheduleRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for managing member related functions.
 * Handles member dashboard and schedule viewing.
 * */
@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private MemberRepository memberRepository;


    /**
     * Display the member's dashboard.
     * Shows the member schedule info relevant to them.
     *
     * @param session The HTTP session to retrieve user details.
     * @param model   The model object used to pass data to the view.
     * @return Redirects to the dashboard view, or login page if the session isn't valid.
     */
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        // Get the memberId from the session
        Long memberId = (Long) session.getAttribute("userId");

        // Redirects to login if session userId is null.
        if (memberId == null) {
            System.out.println("userId is null in session");
            return "redirect:/login";
        }
        // Retrieve the member object by ID
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        // Add the member's schedule to the model.
        model.addAttribute("schedules", scheduleRepository.findByMember(member));
        return "member/dashboard";
    }

}



