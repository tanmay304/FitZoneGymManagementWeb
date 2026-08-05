package com.fitzone.dao;

import com.fitzone.model.Notification;
import java.util.List;

public interface NotificationDAO {
    List<Notification> getAllNotifications();
    boolean addNotification(Notification notification);
    boolean markAsRead(int id);
}
