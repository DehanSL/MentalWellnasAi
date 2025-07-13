package com.Mentalwellness.MentelWellnessApplication;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MoodEntryRepository extends JpaRepository<MoodEntry, Long> {
    List<MoodEntry> findByUserOrderByEntryDateDesc(User user);
}