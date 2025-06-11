package com.bankapp.notification.controller;

import com.bankapp.notification.dto.NotificationDTO;
import com.bankapp.notification.model.NotificationStatus;
import com.bankapp.notification.model.NotificationType;
import com.bankapp.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notification Controller", description = "APIs for managing notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new notification")
    public ResponseEntity<NotificationDTO> createNotification(
            @RequestParam Long userId,
            @RequestParam NotificationType type,
            @RequestParam String content) {
        return ResponseEntity.ok(notificationService.createNotification(userId, type, content));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get all notifications for a user")
    public ResponseEntity<List<NotificationDTO>> getUserNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getUserNotifications(userId));
    }

    @GetMapping("/user/{userId}/status/{status}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Get notifications by status for a user")
    public ResponseEntity<List<NotificationDTO>> getUserNotificationsByStatus(
            @PathVariable Long userId,
            @PathVariable NotificationStatus status) {
        return ResponseEntity.ok(notificationService.getUserNotificationsByStatus(userId, status));
    }

    @PutMapping("/{id}/read")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @Operation(summary = "Mark a notification as read")
    public ResponseEntity<NotificationDTO> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }

    @PutMapping("/{id}/sent")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Mark a notification as sent")
    public ResponseEntity<NotificationDTO> markAsSent(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsSent(id));
    }

    @PutMapping("/{id}/failed")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Mark a notification as failed")
    public ResponseEntity<NotificationDTO> markAsFailed(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsFailed(id));
    }
} 