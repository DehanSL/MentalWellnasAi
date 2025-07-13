package com.Mentalwellness.MentelWellnessApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class InsightsController {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private MoodEntryRepository moodEntryRepository;

    @GetMapping("/insights")
    public String insights(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        List<JournalEntry> journalEntries = journalEntryRepository.findByUserOrderByCreatedAtDesc(user);
        List<MoodEntry> moodEntries = moodEntryRepository.findByUserOrderByEntryDateDesc(user);

        // AI Insights
        long totalWords = journalEntries.stream().mapToLong(entry -> countWords(entry.getContent())).sum();
        model.addAttribute("totalJournalEntries", journalEntries.size());
        model.addAttribute("totalWords", totalWords);

        // Daily Reports
        model.addAttribute("moodEntries", moodEntries);

        return "insights";
    }

    private long countWords(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        String[] words = text.trim().split("\\s+");
        return words.length;
    }
}