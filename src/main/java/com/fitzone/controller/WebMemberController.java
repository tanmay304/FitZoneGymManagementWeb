package com.fitzone.controller;

import com.fitzone.model.Member;
import com.fitzone.service.MemberService;
import com.fitzone.service.PdfService;
import com.fitzone.service.impl.MemberServiceImpl;
import com.fitzone.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/members")
public class WebMemberController {

    private static final Logger logger = LoggerFactory.getLogger(WebMemberController.class);
    private final MemberService memberService = new MemberServiceImpl();

    @GetMapping
    public String listMembers(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Member> members;
        if (search != null && !search.trim().isEmpty()) {
            members = memberService.searchMembers(search.trim());
            model.addAttribute("searchQuery", search.trim());
        } else {
            members = memberService.getAllMembers();
        }
        model.addAttribute("members", members);
        model.addAttribute("newMember", new Member());
        return "members";
    }

    @PostMapping("/add")
    public String addMember(@ModelAttribute Member member, RedirectAttributes redirectAttributes) {
        logger.info(">>> Processing Registration Request for: {} {}, Email: {}, Mobile: {}",
                member.getFname(), member.getLname(), member.getEmail(), member.getMobile());

        if (member.getFname() == null || member.getFname().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "First Name is required.");
            return "redirect:/members";
        }
        if (member.getEmail() == null || member.getEmail().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email address is required.");
            return "redirect:/members";
        }
        if (memberService.isEmailDuplicate(member.getEmail().trim(), 0)) {
            logger.warn("Registration rejected: Email duplicate ({})", member.getEmail());
            redirectAttributes.addFlashAttribute("errorMessage", "Registration Failed: Email address '" + member.getEmail() + "' is already registered!");
            return "redirect:/members";
        }
        if (member.getMobile() != null && !member.getMobile().trim().isEmpty() && memberService.isMobileDuplicate(member.getMobile().trim(), 0)) {
            logger.warn("Registration rejected: Mobile duplicate ({})", member.getMobile());
            redirectAttributes.addFlashAttribute("errorMessage", "Registration Failed: Mobile number '" + member.getMobile() + "' is already registered!");
            return "redirect:/members";
        }

        member.setPassword(PasswordUtil.hashPasswordMD5("123456"));
        boolean success = memberService.addMember(member);
        if (success) {
            logger.info("✅ Member registered successfully! ID: {}", member.getId());
            redirectAttributes.addFlashAttribute("successMessage", "Member registered successfully! Welcome " + member.getFname() + " " + (member.getLname() != null ? member.getLname() : ""));
        } else {
            logger.error("❌ Database insertion failed during addMember for {}", member.getEmail());
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save member in database. Please check MySQL logs.");
        }
        return "redirect:/members";
    }

    @PostMapping("/edit")
    public String editMember(@ModelAttribute Member member, RedirectAttributes redirectAttributes) {
        if (memberService.isEmailDuplicate(member.getEmail(), member.getId())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email already in use by another member!");
            return "redirect:/members";
        }
        if (memberService.isMobileDuplicate(member.getMobile(), member.getId())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Mobile number already in use by another member!");
            return "redirect:/members";
        }

        if (memberService.updateMember(member)) {
            redirectAttributes.addFlashAttribute("successMessage", "Member details updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update member in database.");
        }
        return "redirect:/members";
    }

    @GetMapping("/delete/{id}")
    public String deleteMember(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        if (memberService.deleteMember(id)) {
            redirectAttributes.addFlashAttribute("successMessage", "Member deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete member.");
        }
        return "redirect:/members";
    }

    @GetMapping("/card/{id}")
    public ResponseEntity<byte[]> downloadMemberCard(@PathVariable("id") int id) {
        try {
            Member member = memberService.getMemberById(id);
            if (member == null) return ResponseEntity.notFound().build();

            File tempFile = File.createTempFile("MemberCard_", ".pdf");
            if (PdfService.generateMemberCardPdf(member, tempFile)) {
                byte[] bytes = Files.readAllBytes(tempFile.toPath());
                tempFile.delete();
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=MemberCard_" + id + ".pdf")
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(bytes);
            }
        } catch (Exception e) {
            logger.error("Failed to generate member card PDF for ID " + id, e);
        }
        return ResponseEntity.internalServerError().build();
    }
}
