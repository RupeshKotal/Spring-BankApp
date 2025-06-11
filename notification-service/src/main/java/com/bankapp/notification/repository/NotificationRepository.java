package com.bankapp.notification.repository;

import com.bankapp.notification.model.Notification;
import com.bankapp.notification.model.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long userId);
    List<Notification> findByUserIdAndStatus(Long userId, NotificationStatus status);
} 