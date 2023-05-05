package com.jumkid.usercentral.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jumkid.share.event.ActivityEvent;
import com.jumkid.usercentral.model.ActivityEntity;
import com.jumkid.usercentral.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ActivityNotifyConsumer {

    private final ObjectMapper objectMapper;

    private final UserService userService;

    public ActivityNotifyConsumer(UserService userService) {
        this.userService = userService;
        this.objectMapper = new ObjectMapper();
    }

    @KafkaListener(topics = "${spring.kafka.topic.name.activity.notify}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listen(@Payload String message) {
        log.debug("kafka message received: {}", message);
        try {
            final ActivityEvent event = objectMapper.readValue(message, ActivityEvent.class);
            final String payload = event.getPayload();
            final ObjectNode node = objectMapper.readValue(payload, ObjectNode.class);
            final List<ActivityEntity> activityEntities = new ArrayList<>();
            final String title = node.get(ActivityFields.NAME.value()).asText();

            String creatorId = node.get(ActivityFields.CREATED_BY.value()).asText();
            if (creatorId != null) {
                ActivityEntity creatorActivity = buildActivityEntityByUser(creatorId, title, event, payload);
                activityEntities.add(creatorActivity);
            }

            JsonNode assigneesNode = node.get(ActivityFields.ACTIVITY_ASSIGNEES.value());
            if (assigneesNode.isArray()) {
                for (final JsonNode assigneeNode : assigneesNode) {
                    String assigneeId = assigneeNode.get(ActivityFields.ASSIGNEE_ID.value()).asText();
                    log.debug("Activity has assignee {}", assigneeId);
                    activityEntities.add(buildActivityEntityByUser(assigneeId, title, event, payload));
                }
            }

            userService.saveAll(activityEntities);
        } catch (JsonMappingException jme) {
            jme.printStackTrace();
            log.error("failed to map message to json object: {}", jme.getMessage());
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
            log.error("failed to map process json message: {}", jpe.getMessage());
        }
    }

    private ActivityEntity buildActivityEntityByUser(String userId, String title, ActivityEvent event, String payload) {
        return ActivityEntity.builder()
                .userId(userId)
                .title(title)
                .entityId(event.getActivityId().toString())
                .entityModule(SystemModules.ACTIVITY.value())
                .unread(true)
                .payload(payload)
                .build();
    }
}
