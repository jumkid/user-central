package com.jumkid.usercentral.event;

import com.jumkid.usercentral.model.ActivityNotificationEntity;
import com.jumkid.usercentral.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ActivityNotifyConsumer {

    private final EventHelper eventHelper;

    private final UserService userService;

    public ActivityNotifyConsumer(EventHelper eventHelper, UserService userService) {
        this.eventHelper = eventHelper;
        this.userService = userService;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name.activity.notify}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listen(@Payload String message) {
        log.debug("kafka message received: {}", message);
        List<ActivityNotificationEntity> activityEntities = eventHelper.buildActivitiesForRelatedUsers(message);
        if (!activityEntities.isEmpty()) {
            userService.saveAll(activityEntities);
        }
    }
}
