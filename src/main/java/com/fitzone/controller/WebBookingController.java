package com.fitzone.controller;

import com.fitzone.model.Booking;
import com.fitzone.model.GymPackage;
import com.fitzone.model.Member;
import com.fitzone.service.BookingService;
import com.fitzone.service.MemberService;
import com.fitzone.service.PackageService;
import com.fitzone.service.impl.BookingServiceImpl;
import com.fitzone.service.impl.MemberServiceImpl;
import com.fitzone.service.impl.PackageServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/bookings")
public class WebBookingController {

    private final BookingService bookingService = new BookingServiceImpl();
    private final MemberService memberService = new MemberServiceImpl();
    private final PackageService packageService = new PackageServiceImpl();

    @GetMapping
    public String listBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        List<Member> members = memberService.getAllMembers();
        List<GymPackage> packages = packageService.getAllPackages();

        model.addAttribute("bookings", bookings);
        model.addAttribute("members", members);
        model.addAttribute("packages", packages);
        model.addAttribute("newBooking", new Booking());
        return "bookings";
    }

    @PostMapping("/add")
    public String addBooking(@ModelAttribute Booking booking, RedirectAttributes redirectAttributes) {
        booking.setStatus("Active");
        booking.setExpiryDate(new Date(System.currentTimeMillis() + 90L * 24 * 60 * 60 * 1000));
        if (bookingService.addBooking(booking)) {
            redirectAttributes.addFlashAttribute("successMessage", "Booking created successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create booking.");
        }
        return "redirect:/bookings";
    }

    @GetMapping("/renew/{id}")
    public String renewBooking(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        Date newExpiry = new Date(System.currentTimeMillis() + 90L * 24 * 60 * 60 * 1000);
        if (bookingService.renewBooking(id, newExpiry)) {
            redirectAttributes.addFlashAttribute("successMessage", "Membership renewed successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to renew membership.");
        }
        return "redirect:/bookings";
    }

    @GetMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        if (bookingService.deleteBooking(id)) {
            redirectAttributes.addFlashAttribute("successMessage", "Booking cancelled successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to cancel booking.");
        }
        return "redirect:/bookings";
    }
}
