package com.bankapp.notification.service;

import com.bankapp.notification.dto.NotificationDTO;
import com.bankapp.notification.model.NotificationType;
import com.bankapp.notification.model.NotificationStatus;

import java.util.List;

public interface NotificationService {
    NotificationDTO createNotification(Long userId, NotificationType type, String content);
    List<NotificationDTO> getUserNotifications(Long userId);
    List<NotificationDTO> getUserNotificationsByStatus(Long userId, NotificationStatus status);
    NotificationDTO markAsRead(Long notificationId);
    NotificationDTO markAsSent(Long notificationId);
    NotificationDTO markAsFailed(Long notificationId);
} 