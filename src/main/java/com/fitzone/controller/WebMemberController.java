package com.fitzone.controller;

import com.fitzone.model.Member;
import com.fitzone.service.MemberService;
import com.fitzone.service.PdfService;
import com.fitzone.service.impl.MemberServiceImpl;
import com.fitzone.util.PasswordUtil;
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
        if (memberService.isEmailDuplicate(member.getEmail(), 0)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email already registered!");
            return "redirect:/members";
        }
        if (memberService.isMobileDuplicate(member.getMobile(), 0)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Mobile number already registered!");
            return "redirect:/members";
        }

        member.setPassword(PasswordUtil.hashPasswordMD5("123456"));
        if (memberService.addMember(member)) {
            redirectAttributes.addFlashAttribute("successMessage", "Member registered successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to register member.");
        }
        return "redirect:/members";
    }

    @PostMapping("/edit")
    public String editMember(@ModelAttribute Member member, RedirectAttributes redirectAttributes) {
        if (memberService.isEmailDuplicate(member.getEmail(), member.getId())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email already in use!");
            return "redirect:/members";
        }
        if (memberService.isMobileDuplicate(member.getMobile(), member.getId())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Mobile number already in use!");
            return "redirect:/members";
        }

        if (memberService.updateMember(member)) {
            redirectAttributes.addFlashAttribute("successMessage", "Member updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update member.");
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
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }
}
