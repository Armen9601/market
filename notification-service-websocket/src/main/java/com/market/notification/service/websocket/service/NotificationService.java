package com.market.notification.service.websocket.service;

import com.market.notification.service.websocket.dto.NotificationDto;

import java.util.List;

public interface NotificationService {

    void add(NotificationDto notificationDto);

    NotificationDto findById(String id);

    NotificationDto updateNotification(String id, NotificationDto notificationDto);

    void deleteNotificationById(String id);

    List<NotificationDto> findByFrom(String from);

    List<NotificationDto> findByTo(String to);

    List<NotificationDto> updateNotificationStatus(List<String> ids);
}
