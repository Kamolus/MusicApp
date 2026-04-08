package com.springmusicapp.domain.notification;

import com.springmusicapp.domain.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private User recipient;

    private String message;
    private LocalDateTime createdAt = LocalDateTime.now();
    private boolean isRead = false;

    @Enumerated(EnumType.STRING)
    private NotificationType type;
}
