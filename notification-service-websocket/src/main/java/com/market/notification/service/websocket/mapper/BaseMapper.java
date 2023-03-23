package com.market.notification.service.websocket.mapper;

public interface BaseMapper<Entity, Dto> {

  Entity toEntity(Dto notificationDto);

  Dto toDto(Entity entity);
}
