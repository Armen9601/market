package com.market.notification.service.websocket.repository;

import com.market.notification.service.websocket.entity.Notification;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {

  List<Notification> findByFrom(String from);

  List<Notification> findByTo(String to);

  List<Notification> findAllByIdIsIn(List<String> ids);

}
