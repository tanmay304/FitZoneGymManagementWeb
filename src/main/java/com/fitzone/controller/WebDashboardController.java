package com.fitzone.controller;

import com.fitzone.model.Booking;
import com.fitzone.model.GymPackage;
import com.fitzone.model.Payment;
import com.fitzone.service.BookingService;
import com.fitzone.service.MemberService;
import com.fitzone.service.PackageService;
import com.fitzone.service.PaymentService;
import com.fitzone.service.impl.BookingServiceImpl;
import com.fitzone.service.impl.MemberServiceImpl;
import com.fitzone.service.impl.PackageServiceImpl;
import com.fitzone.service.impl.PaymentServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class WebDashboardController {

    private final MemberService memberService = new MemberServiceImpl();
    private final PaymentService paymentService = new PaymentServiceImpl();
    private final BookingService bookingService = new BookingServiceImpl();
    private final PackageService packageService = new PackageServiceImpl();

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        int totalMembers = memberService.getTotalMembersCount();
        List<Booking> bookings = bookingService.getAllBookings();
        double todayRev = paymentService.getTodayRevenue();
        double totalRev = paymentService.getTotalRevenue();
        List<Payment> payments = paymentService.getAllPayments();
        List<GymPackage> packages = packageService.getAllPackages();

        model.addAttribute("totalMembers", totalMembers);
        model.addAttribute("activeMembers", bookings.size());
        model.addAttribute("todayRevenue", String.format("%.2f", todayRev));
        model.addAttribute("totalRevenue", String.format("%.2f", totalRev));
        model.addAttribute("recentPayments", payments.size() > 10 ? payments.subList(0, 10) : payments);
        model.addAttribute("packages", packages);

        return "dashboard";
    }
}
