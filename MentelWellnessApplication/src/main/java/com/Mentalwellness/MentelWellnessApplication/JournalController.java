package com.Mentalwellness.MentelWellnessApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JournalController {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @GetMapping("/journal")
    public String journal(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        model.addAttribute("entries", journalEntryRepository.findByUserOrderByCreatedAtDesc(userDetails.getUser()));
        return "journal";
    }

    @PostMapping("/journal")
    public String saveJournalEntry(@RequestParam String content, @AuthenticationPrincipal CustomUserDetails userDetails) {
        JournalEntry newEntry = new JournalEntry();
        newEntry.setContent(content);
        newEntry.setUser(userDetails.getUser());
        journalEntryRepository.save(newEntry);
        return "redirect:/journal";
    }
}