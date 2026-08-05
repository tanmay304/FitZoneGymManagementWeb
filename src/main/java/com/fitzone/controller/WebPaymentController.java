package com.fitzone.controller;

import com.fitzone.model.Booking;
import com.fitzone.model.Payment;
import com.fitzone.service.BookingService;
import com.fitzone.service.PaymentService;
import com.fitzone.service.PdfService;
import com.fitzone.service.impl.BookingServiceImpl;
import com.fitzone.service.impl.PaymentServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Controller
@RequestMapping("/payments")
public class WebPaymentController {

    private final PaymentService paymentService = new PaymentServiceImpl();
    private final BookingService bookingService = new BookingServiceImpl();

    @GetMapping
    public String listPayments(Model model) {
        List<Payment> payments = paymentService.getAllPayments();
        List<Booking> bookings = bookingService.getAllBookings();

        model.addAttribute("payments", payments);
        model.addAttribute("bookings", bookings);
        model.addAttribute("newPayment", new Payment());
        return "payments";
    }

    @PostMapping("/add")
    public String addPayment(@ModelAttribute Payment payment, RedirectAttributes redirectAttributes) {
        payment.setStatus("Paid");
        payment.setReceiptNo("REC-" + (System.currentTimeMillis() % 1000000));
        payment.setTransactionId("TXN-" + System.currentTimeMillis());

        if (paymentService.addPayment(payment)) {
            redirectAttributes.addFlashAttribute("successMessage", "Payment recorded successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to record payment.");
        }
        return "redirect:/payments";
    }

    @GetMapping("/receipt/{id}")
    public ResponseEntity<byte[]> downloadReceipt(@PathVariable("id") int id) {
        try {
            Payment payment = paymentService.getPaymentById(id);
            if (payment == null) return ResponseEntity.notFound().build();

            File tempFile = File.createTempFile("Receipt_", ".pdf");
            if (PdfService.generatePaymentReceiptPdf(payment, tempFile)) {
                byte[] bytes = Files.readAllBytes(tempFile.toPath());
                tempFile.delete();
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Receipt_" + id + ".pdf")
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }
}
