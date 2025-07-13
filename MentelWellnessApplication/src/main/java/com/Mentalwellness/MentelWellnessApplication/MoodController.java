package com.Mentalwellness.MentelWellnessApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MoodController {

    @Autowired
    private MoodEntryRepository moodEntryRepository;

    @GetMapping("/mood")
    public String mood(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        model.addAttribute("entries", moodEntryRepository.findByUserOrderByEntryDateDesc(userDetails.getUser()));
        return "mood";
    }

    @PostMapping("/mood")
    public String saveMoodEntry(@RequestParam int moodLevel, @AuthenticationPrincipal CustomUserDetails userDetails) {
        MoodEntry newEntry = new MoodEntry();
        newEntry.setMoodLevel(moodLevel);
        newEntry.setUser(userDetails.getUser());
        moodEntryRepository.save(newEntry);
        return "redirect:/mood";
    }
}