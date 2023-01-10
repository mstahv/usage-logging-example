package com.example.application.data.service;

import com.example.application.data.entity.UsageLogEntry;
import com.example.application.data.entity.User;
import com.vaadin.flow.component.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class UsageLogService {

    private final UsageLogEntryRepository repository;

    public UsageLogService(UsageLogEntryRepository repository) {
        this.repository = repository;
    }

    public void logEntry(Component view, String ip, User user) {
        UsageLogEntry entry = new UsageLogEntry();
        entry.setView(view.getClass().getSimpleName());
        entry.setIp(ip);
        entry.setUser(user);
        entry.setPageVisit(LocalDateTime.now());
        repository.save(entry);
        System.out.println("Logged visit:" + entry);
    }

    public List<UsageLogEntry> listEntries(LocalDateTime start, LocalDateTime end, Set<User> users) {
        if(!users.isEmpty()) {
            return repository.findAllByUserInAndPageVisitAfterAndPageVisitBefore(users, start, end);
        }
        return repository.findAllByPageVisitAfterAndPageVisitBefore(start,end);
    }
}
