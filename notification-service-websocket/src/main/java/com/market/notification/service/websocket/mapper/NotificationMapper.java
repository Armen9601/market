package com.market.notification.service.websocket.mapper;

import com.market.notification.service.websocket.dto.NotificationDto;
import com.market.notification.service.websocket.entity.Notification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationMapper implements
        BaseMapper<Notification, NotificationDto> {

    private final ModelMapper modelMapper;

    @Override
    public Notification toEntity(NotificationDto notificationDto) {
        return modelMapper.map(notificationDto, Notification.class);
    }

    @Override
    public NotificationDto toDto(Notification notification) {
        return modelMapper.map(notification, NotificationDto.class);
    }
}
