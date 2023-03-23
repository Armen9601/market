package com.market.notification.service.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan({"com.market.notification.service"})
@EnableMongoRepositories(basePackages = {"com.market.notification.service.websocket.repository"})
@EntityScan({"com.energizeglobal.notification.service.core.*"})
public class NotificationServiceWebsocketApplication {

  public static void main(String[] args) {
    SpringApplication.run(NotificationServiceWebsocketApplication.class, args);
  }

}
