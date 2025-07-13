package com.Mentalwellness.MentelWellnessApplication;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {
    List<JournalEntry> findByUserOrderByCreatedAtDesc(User user);
}