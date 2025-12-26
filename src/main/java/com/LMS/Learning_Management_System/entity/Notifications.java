package com.LMS.Learning_Management_System.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "notifications")
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private int notificationsId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonIgnore
    private Users userId;

    @Column(nullable = false)
    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "created_at", nullable = false)
    private Date createdTime;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    // ✅ Constructeur vide (JPA)
    public Notifications() {
    }

    // ✅ Constructeur COMPLET (CORRIGÉ)
    public Notifications(int notificationsId, Users userId, String message, Date createdTime) {
        this.notificationsId = notificationsId;
        this.userId = userId;
        this.message = message;
        this.createdTime = createdTime; // ✅ FIX MAJEUR
        this.isRead = false;             // ✅ BONNE PRATIQUE
    }

    // Getters & Setters

    public int getNotificationsId() {
        return notificationsId;
    }

    public void setNotificationsId(int notificationsId) {
        this.notificationsId = notificationsId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public String toString() {
        return "Notifications{" +
                "notificationsId=" + notificationsId +
                ", message='" + message + '\'' +
                ", createdTime=" + createdTime +
                ", isRead=" + isRead +
                '}';
    }
}
