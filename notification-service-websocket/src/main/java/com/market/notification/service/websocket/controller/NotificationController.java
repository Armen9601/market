package com.market.notification.service.websocket.controller;

import com.market.notification.service.websocket.dto.NotificationDto;
import com.market.notification.service.websocket.service.NotificationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    private final SimpMessagingTemplate template;

    private final NotificationService notificationService;

    @MessageMapping("/hello")
    @SendTo("/user/queue/notification")
    public List<NotificationDto> processMessageFromClient(@Payload String message) {
        return notificationService.findByTo(message);
    }


    @PostMapping("/")
    @ApiOperation(value = "add Notification")
    public ResponseEntity<?> sendNotification(@RequestBody NotificationDto notificationDto) {
        notificationService.add(notificationDto);
        template.convertAndSend("/user/queue/notification", notificationDto);
        System.out.println("notification sent");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * This method find notification by id
     *
     * @param id
     * @return ResponseEntity<NotificationDto>
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "find Notification byId")
    public ResponseEntity<NotificationDto> findById(@PathVariable String id) {
        return ResponseEntity.ok(notificationService.findById(id));
    }


    @PutMapping("/{id}")
    @ApiOperation(value = "update Notification")
    public ResponseEntity<NotificationDto> update(@PathVariable String id,
                                                  @RequestBody NotificationDto notificationDto) {
        NotificationDto notification = notificationService
                .updateNotification(id, notificationDto);
        return ResponseEntity.ok(notification);
    }

    /**
     * This method delete notification
     *
     * @param id
     * @return ResponseEntity<HttpStatus>
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete Notification")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
        notificationService.deleteNotificationById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * This method get notification by from
     *
     * @param from
     * @return ResponseEntity<List < NotificationResponse>>
     */
    @GetMapping("/get/{from}")
    @ApiOperation(value = "find Notification byFrom")
    public ResponseEntity<List<NotificationDto>> findByFrom(@PathVariable String from) {
        List<NotificationDto> byFrom = notificationService.findByFrom(from);
        return ResponseEntity.ok().body(byFrom);
    }

    /**
     * This method get notification by to
     *
     * @param to
     * @return ResponseEntity<List < NotificationResponse>>
     */
    @GetMapping("/get/{to}")
    @ApiOperation(value = "find Notification byTo")
    public ResponseEntity<List<NotificationDto>> findByTo(@PathVariable String to) {
        List<NotificationDto> byTo = notificationService.findByTo(to);
        return ResponseEntity.ok().body(byTo);
    }

//  @PutMapping(BY_ID+STATUS)
//  @ApiOperation(value = "update Notification status")
//  public ResponseEntity<NotificationResponse> updateStatus(@PathVariable String id) {
//    NotificationResponse notificationResponse = notificationService
//        .updateNotificationStatus(id);
//    return ResponseEntity.ok(notificationResponse);
//  }

    @PutMapping("/ids")
    @ApiOperation(value = "update Notifications status")
    public ResponseEntity<List<NotificationDto>> updateStatuses(
            @RequestParam("ids") List<String> notificationIds) {
        return ResponseEntity.ok(notificationService.updateNotificationStatus(notificationIds));
    }

}
