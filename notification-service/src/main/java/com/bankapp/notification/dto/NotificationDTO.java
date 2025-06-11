package com.bankapp.notification.dto;

import com.bankapp.notification.model.NotificationType;
import com.bankapp.notification.model.NotificationStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Long id;
    private Long userId;
    private NotificationType type;
    private String content;
    private NotificationStatus status;
    private LocalDateTime createdAt;
} 