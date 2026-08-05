package com.fitzone.controller;

import com.fitzone.model.GymPackage;
import com.fitzone.service.PackageService;
import com.fitzone.service.impl.PackageServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/packages")
public class WebPackageController {

    private final PackageService packageService = new PackageServiceImpl();

    @GetMapping
    public String listPackages(Model model) {
        List<GymPackage> packages = packageService.getAllPackages();
        model.addAttribute("packages", packages);
        model.addAttribute("newPackage", new GymPackage());
        return "packages";
    }

    @PostMapping("/add")
    public String addPackage(@ModelAttribute GymPackage gymPackage, RedirectAttributes redirectAttributes) {
        gymPackage.setPackageType("1");
        if (packageService.addPackage(gymPackage)) {
            redirectAttributes.addFlashAttribute("successMessage", "Package created successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create package.");
        }
        return "redirect:/packages";
    }

    @PostMapping("/edit")
    public String editPackage(@ModelAttribute GymPackage gymPackage, RedirectAttributes redirectAttributes) {
        if (packageService.updatePackage(gymPackage)) {
            redirectAttributes.addFlashAttribute("successMessage", "Package updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update package.");
        }
        return "redirect:/packages";
    }

    @GetMapping("/delete/{id}")
    public String deletePackage(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        if (packageService.deletePackage(id)) {
            redirectAttributes.addFlashAttribute("successMessage", "Package deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete package.");
        }
        return "redirect:/packages";
    }
}
