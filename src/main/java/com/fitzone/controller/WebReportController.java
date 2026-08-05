package com.fitzone.controller;

import com.fitzone.model.Member;
import com.fitzone.model.Payment;
import com.fitzone.service.ExcelExportService;
import com.fitzone.service.MemberService;
import com.fitzone.service.PaymentService;
import com.fitzone.service.PdfService;
import com.fitzone.service.impl.MemberServiceImpl;
import com.fitzone.service.impl.PaymentServiceImpl;
import com.fitzone.util.CSVExporter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Controller
@RequestMapping("/reports")
public class WebReportController {

    private final PaymentService paymentService = new PaymentServiceImpl();
    private final MemberService memberService = new MemberServiceImpl();

    @GetMapping
    public String viewReports(Model model) {
        model.addAttribute("totalRevenue", String.format("%.2f", paymentService.getTotalRevenue()));
        model.addAttribute("totalMembers", memberService.getTotalMembersCount());
        return "reports";
    }

    @GetMapping("/excel/members")
    public ResponseEntity<byte[]> exportMembersExcel() {
        try {
            List<Member> members = memberService.getAllMembers();
            File tempFile = File.createTempFile("Members_Report_", ".xlsx");
            if (ExcelExportService.exportMembersToExcel(members, tempFile)) {
                byte[] bytes = Files.readAllBytes(tempFile.toPath());
                tempFile.delete();
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Members_Report.xlsx")
                        .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                        .body(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/excel/payments")
    public ResponseEntity<byte[]> exportPaymentsExcel() {
        try {
            List<Payment> payments = paymentService.getAllPayments();
            File tempFile = File.createTempFile("Payments_Report_", ".xlsx");
            if (ExcelExportService.exportPaymentsToExcel(payments, tempFile)) {
                byte[] bytes = Files.readAllBytes(tempFile.toPath());
                tempFile.delete();
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Payments_Report.xlsx")
                        .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                        .body(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping("/csv/members")
    public ResponseEntity<byte[]> exportMembersCSV() {
        try {
            List<Member> members = memberService.getAllMembers();
            File tempFile = File.createTempFile("Members_Report_", ".csv");
            if (CSVExporter.exportMembersToCSV(members, tempFile)) {
                byte[] bytes = Files.readAllBytes(tempFile.toPath());
                tempFile.delete();
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Members_Report.csv")
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }
}
