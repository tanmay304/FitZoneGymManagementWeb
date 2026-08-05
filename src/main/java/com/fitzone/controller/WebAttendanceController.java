package com.fitzone.controller;

import com.fitzone.model.Attendance;
import com.fitzone.service.AttendanceService;
import com.fitzone.service.impl.AttendanceServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/attendance")
public class WebAttendanceController {

    private final AttendanceService attendanceService = new AttendanceServiceImpl();

    @GetMapping
    public String listAttendance(@RequestParam(value = "date", required = false) String dateStr, Model model) {
        Date selectedDate = (dateStr != null && !dateStr.isEmpty()) ? Date.valueOf(dateStr) : Date.valueOf(LocalDate.now());
        List<Attendance> log = attendanceService.getDailyAttendance(selectedDate);

        model.addAttribute("attendanceLog", log);
        model.addAttribute("selectedDate", selectedDate.toString());
        return "attendance";
    }

    @PostMapping("/checkin")
    public String checkIn(@RequestParam("userId") int userId, @RequestParam("method") String method, RedirectAttributes redirectAttributes) {
        if (attendanceService.checkInMember(userId, method)) {
            redirectAttributes.addFlashAttribute("successMessage", "Member #" + userId + " checked in successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to check in member.");
        }
        return "redirect:/attendance";
    }

    @GetMapping("/checkout/{id}")
    public String checkOut(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        if (attendanceService.checkOutMember(id)) {
            redirectAttributes.addFlashAttribute("successMessage", "Member checked out successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to check out member.");
        }
        return "redirect:/attendance";
    }
}
