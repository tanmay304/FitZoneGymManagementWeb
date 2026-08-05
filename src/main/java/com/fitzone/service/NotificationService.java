package com.fitzone.service;

import com.fitzone.model.Notification;
import java.util.List;

public interface NotificationService {
    List<Notification> getAllNotifications();
    boolean addNotification(Notification notification);
    boolean createNotification(String title, String message, Integer targetUserId, String type);
    boolean markAsRead(int notificationId);
}
