package com.fitzone.controller;

import com.fitzone.model.Trainer;
import com.fitzone.service.TrainerService;
import com.fitzone.service.impl.TrainerServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/trainers")
public class WebTrainerController {

    private final TrainerService trainerService = new TrainerServiceImpl();

    @GetMapping
    public String listTrainers(Model model) {
        List<Trainer> trainers = trainerService.getAllTrainers();
        model.addAttribute("trainers", trainers);
        model.addAttribute("newTrainer", new Trainer());
        return "trainers";
    }

    @PostMapping("/add")
    public String addTrainer(@ModelAttribute Trainer trainer, RedirectAttributes redirectAttributes) {
        trainer.setJoiningDate(Date.valueOf(LocalDate.now()));
        trainer.setStatus("Active");
        if (trainerService.addTrainer(trainer)) {
            redirectAttributes.addFlashAttribute("successMessage", "Trainer registered successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add trainer.");
        }
        return "redirect:/trainers";
    }

    @PostMapping("/edit")
    public String editTrainer(@ModelAttribute Trainer trainer, RedirectAttributes redirectAttributes) {
        if (trainerService.updateTrainer(trainer)) {
            redirectAttributes.addFlashAttribute("successMessage", "Trainer updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update trainer.");
        }
        return "redirect:/trainers";
    }

    @GetMapping("/delete/{id}")
    public String deleteTrainer(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        if (trainerService.deleteTrainer(id)) {
            redirectAttributes.addFlashAttribute("successMessage", "Trainer deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete trainer.");
        }
        return "redirect:/trainers";
    }
}
