package com.jumkid.usercentral.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jumkid.share.event.ActivityEvent;
import com.jumkid.usercentral.model.ActivityNotificationEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class EventHelper {

    private final ObjectMapper objectMapper;

    public EventHelper() {
        this.objectMapper = new ObjectMapper();
    }

    public List<ActivityNotificationEntity> buildActivitiesForRelatedUsers(String message) {
        final List<ActivityNotificationEntity> activityEntities = new ArrayList<>();

        try {
            final ActivityEvent activityEvent = objectMapper.readValue(message, ActivityEvent.class);

            final String activityId = activityEvent.getActivityId().toString();
            final String payload = activityEvent.getPayload();
            final ObjectNode node = objectMapper.readValue(payload, ObjectNode.class);
            final String title = node.get(ActivityFields.NAME.value()).asText();

            String creatorId = node.get(ActivityFields.CREATED_BY.value()).asText();
            if (creatorId != null) {
                ActivityNotificationEntity creatorActivity = buildActivityEntity(creatorId, title, activityId, payload);
                activityEntities.add(creatorActivity);
            }

            JsonNode assigneesNode = node.get(ActivityFields.ACTIVITY_ASSIGNEES.value());
            if (assigneesNode.isArray()) {
                for (final JsonNode assigneeNode : assigneesNode) {
                    String assigneeId = assigneeNode.get(ActivityFields.ASSIGNEE_ID.value()).asText();
                    log.debug("Activity has assignee {}", assigneeId);
                    ActivityNotificationEntity assigneeActivity = buildActivityEntity(assigneeId, title, activityId, payload);
                    activityEntities.add(assigneeActivity);
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("Failed to map message to json object: {}", e.getMessage());
        }

        return activityEntities;
    }

    private ActivityNotificationEntity buildActivityEntity(String userId, String title, String entityId, String payload) {
        return ActivityNotificationEntity.builder()
                .userId(userId)
                .title(title)
                .entityId(entityId)
                .entityModule(SystemModules.ACTIVITY.value())
                .unread(true)
                .payload(payload)
                .build();
    }
}
