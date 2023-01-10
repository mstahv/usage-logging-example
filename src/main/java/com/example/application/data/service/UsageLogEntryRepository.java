package com.example.application.data.service;

import com.example.application.data.entity.UsageLogEntry;
import com.example.application.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface UsageLogEntryRepository extends JpaRepository<UsageLogEntry,Long> {


    List<UsageLogEntry> findAllByUserInAndPageVisitAfterAndPageVisitBefore(Set<User> users, LocalDateTime start, LocalDateTime end);

    List<UsageLogEntry> findAllByPageVisitAfterAndPageVisitBefore(LocalDateTime start, LocalDateTime end);
}
