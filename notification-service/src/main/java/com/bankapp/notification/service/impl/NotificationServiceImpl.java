package com.bankapp.notification.service.impl;

import com.bankapp.notification.dto.NotificationDTO;
import com.bankapp.notification.model.Notification;
import com.bankapp.notification.model.NotificationStatus;
import com.bankapp.notification.model.NotificationType;
import com.bankapp.notification.repository.NotificationRepository;
import com.bankapp.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    @Transactional
    public NotificationDTO createNotification(Long userId, NotificationType type, String content) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setType(type);
        notification.setContent(content);
        notification.setStatus(NotificationStatus.PENDING);
        return convertToDTO(notificationRepository.save(notification));
    }

    @Override
    public List<NotificationDTO> getUserNotifications(Long userId) {
        return notificationRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDTO> getUserNotificationsByStatus(Long userId, NotificationStatus status) {
        return notificationRepository.findByUserIdAndStatus(userId, status)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NotificationDTO markAsRead(Long notificationId) {
        return updateNotificationStatus(notificationId, NotificationStatus.READ);
    }

    @Override
    @Transactional
    public NotificationDTO markAsSent(Long notificationId) {
        return updateNotificationStatus(notificationId, NotificationStatus.SENT);
    }

    @Override
    @Transactional
    public NotificationDTO markAsFailed(Long notificationId) {
        return updateNotificationStatus(notificationId, NotificationStatus.FAILED);
    }

    private NotificationDTO updateNotificationStatus(Long notificationId, NotificationStatus status) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setStatus(status);
        return convertToDTO(notificationRepository.save(notification));
    }

    private NotificationDTO convertToDTO(Notification notification) {
        return new NotificationDTO(
                notification.getId(),
                notification.getUserId(),
                notification.getType(),
                notification.getContent(),
                notification.getStatus(),
                notification.getCreatedAt()
        );
    }
} 