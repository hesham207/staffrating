package com.example.staffrating.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.staffrating.design.StaffProfileFactory;
import com.example.staffrating.model.RoleType;
import com.example.staffrating.model.StaffRating;
import com.example.staffrating.repository.StaffRatingRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ratings")
public class StaffRatingController {

    private final StaffRatingRepository staffRatingRepository;

    public StaffRatingController(StaffRatingRepository staffRatingRepository) {
        this.staffRatingRepository = staffRatingRepository;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("ratings", staffRatingRepository.findAll());
        return "ratings/index";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        StaffRating staffRating = staffRatingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rating id: " + id));

        String displayTitle = StaffProfileFactory
                .createProfile(staffRating.getRoleType())
                .displayTitle();

        model.addAttribute("rating", staffRating);
        model.addAttribute("displayTitle", displayTitle);

        return "ratings/detail";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("rating", new StaffRating());
        model.addAttribute("roleTypes", RoleType.values());
        model.addAttribute("formTitle", "Add Staff Rating");
        model.addAttribute("formAction", "/ratings");

        return "ratings/form";
    }

    @PostMapping
    public String create(
            @Valid @ModelAttribute("rating") StaffRating rating,
            BindingResult bindingResult,
            Model model) {

        if (staffRatingRepository.existsByEmail(rating.getEmail())) {
            bindingResult.rejectValue("email", "duplicate", "This email already has a rating.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("roleTypes", RoleType.values());
            model.addAttribute("formTitle", "Add Staff Rating");
            model.addAttribute("formAction", "/ratings");
            return "ratings/form";
        }

        staffRatingRepository.save(rating);
        return "redirect:/ratings";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        StaffRating staffRating = staffRatingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rating id: " + id));

        model.addAttribute("rating", staffRating);
        model.addAttribute("roleTypes", RoleType.values());
        model.addAttribute("formTitle", "Edit Staff Rating");
        model.addAttribute("formAction", "/ratings/" + id + "/edit");

        return "ratings/form";
    }

    @PostMapping("/{id}/edit")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("rating") StaffRating rating,
            BindingResult bindingResult,
            Model model) {

        if (staffRatingRepository.existsByEmailAndIdNot(rating.getEmail(), id)) {
            bindingResult.rejectValue("email", "duplicate", "This email already has a rating.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("roleTypes", RoleType.values());
            model.addAttribute("formTitle", "Edit Staff Rating");
            model.addAttribute("formAction", "/ratings/" + id + "/edit");
            return "ratings/form";
        }

        StaffRating existingRating = staffRatingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rating id: " + id));

        existingRating.setName(rating.getName());
        existingRating.setEmail(rating.getEmail());
        existingRating.setRoleType(rating.getRoleType());
        existingRating.setClarity(rating.getClarity());
        existingRating.setNiceness(rating.getNiceness());
        existingRating.setKnowledgeableScore(rating.getKnowledgeableScore());
        existingRating.setComment(rating.getComment());

        staffRatingRepository.save(existingRating);

        return "redirect:/ratings/" + id;
    }

    @GetMapping("/{id}/delete")
    public String deleteConfirm(@PathVariable Long id, Model model) {
        StaffRating staffRating = staffRatingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rating id: " + id));

        model.addAttribute("rating", staffRating);
        return "ratings/delete";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        staffRatingRepository.deleteById(id);
        return "redirect:/ratings";
    }

    @GetMapping("/")
    public String indexWithSlash() {
        return "redirect:/ratings";
    }
}