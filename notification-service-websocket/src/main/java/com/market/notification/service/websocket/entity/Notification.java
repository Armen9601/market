package com.market.notification.service.websocket.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "notification")
public class Notification {

    @Id
    private String id;

    private String from;

    private String name;

    private String to;

    private LocalDate date;

    private String message;

    private Status status;

}
