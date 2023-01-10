package com.example.application.data.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class UsageLogEntry extends AbstractEntity {

    private LocalDateTime pageVisit;
    private String view;
    private String ip;
    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getPageVisit() {
        return pageVisit;
    }

    public void setPageVisit(LocalDateTime pageVisit) {
        this.pageVisit = pageVisit;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "UsageLogEntry{" +
                "pageVisit=" + pageVisit +
                ", view='" + view + '\'' +
                ", ip='" + ip + '\'' +
                ", user=" + (user == null ? "" :  user.getName()) +
                '}';
    }
}
