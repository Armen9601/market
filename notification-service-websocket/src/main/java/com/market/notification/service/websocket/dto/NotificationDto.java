package com.market.notification.service.websocket.dto;


import com.market.notification.service.websocket.entity.Status;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {

    private String from;

    private String to;

    private LocalDate date;

    private String message;

    private String name;

    private Status status;

}
