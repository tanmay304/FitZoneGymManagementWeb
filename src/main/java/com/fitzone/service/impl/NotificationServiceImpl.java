package com.fitzone.service.impl;

import com.fitzone.dao.NotificationDAO;
import com.fitzone.dao.impl.NotificationDAOImpl;
import com.fitzone.model.Notification;
import com.fitzone.service.NotificationService;
import java.util.List;

public class NotificationServiceImpl implements NotificationService {
    private final NotificationDAO notificationDAO;

    public NotificationServiceImpl() {
        this.notificationDAO = new NotificationDAOImpl();
    }

    public NotificationServiceImpl(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationDAO.getAllNotifications();
    }

    @Override
    public boolean addNotification(Notification notification) {
        return notificationDAO.addNotification(notification);
    }

    @Override
    public boolean createNotification(String title, String message, Integer targetUserId, String type) {
        Notification n = new Notification();
        n.setTitle(title);
        n.setMessage(message);
        n.setTargetUserId(targetUserId);
        n.setType(type);
        return notificationDAO.addNotification(n);
    }

    @Override
    public boolean markAsRead(int notificationId) {
        return notificationDAO.markAsRead(notificationId);
    }
}
