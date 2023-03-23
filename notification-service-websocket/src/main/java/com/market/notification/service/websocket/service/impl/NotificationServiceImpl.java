package com.market.notification.service.websocket.service.impl;

import com.market.notification.service.websocket.dto.NotificationDto;
import com.market.notification.service.websocket.entity.Notification;
import com.market.notification.service.websocket.entity.Status;
import com.market.notification.service.websocket.exception.EntityNotFoundException;
import com.market.notification.service.websocket.mapper.NotificationMapper;
import com.market.notification.service.websocket.repository.NotificationRepository;
import com.market.notification.service.websocket.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public void add(NotificationDto notificationRequest) {
        Notification notification = notificationMapper.toEntity(notificationRequest);
        notificationRepository.save(notification);
    }

    @Override
    public NotificationDto findById(String id) {
        Notification byId = notificationRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return notificationMapper.toDto(byId);
    }

    @Override
    public NotificationDto updateNotification(String id,
                                              NotificationDto notificationRequest) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        notification.setDate(notificationRequest.getDate());
        notification.setFrom(notificationRequest.getFrom());
        notification.setTo(notificationRequest.getTo());
        notification.setMessage(notificationRequest.getMessage());
        return notificationMapper.toDto(notificationRepository.save(notification));
    }

    @Override
    public void deleteNotificationById(String id) {
        boolean isExist = notificationRepository.existsById(id);
        if (!isExist) {
            throw new EntityNotFoundException();
        }
        notificationRepository.deleteById(id);
    }

    @Override
    public List<NotificationDto> findByFrom(String from) {
        return notificationRepository.findByFrom(from)
                .stream().map(notificationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDto> findByTo(String to) {
        return notificationRepository.findByTo(to)
                .stream()
                .filter(notification -> !notification.getStatus().equals(Status.RESOLVED))
                .map(notificationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDto> updateNotificationStatus(List<String> ids) {
        return notificationRepository.findAllByIdIsIn(ids).stream()
                .peek(notification -> {
                    notification.setStatus(Status.READ);
                    notificationRepository.save(notification);
                })
                .map(notificationMapper::toDto)
                .collect(Collectors.toList());
    }
}
