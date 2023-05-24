package com.jumkid.usercentral;

import com.jumkid.usercentral.model.ActivityNotificationEntity;

import java.util.List;

public class APITestsSetup {

    static List<ActivityNotificationEntity> buildActivityNotificationEntities() {
        return List.of(buildActivityNotificationEntity(1L), buildActivityNotificationEntity(2L));
    }

    static ActivityNotificationEntity buildActivityNotificationEntity(Long id) {
        return ActivityNotificationEntity.builder()
                .id(id)
                .userId("test")
                .title("test user activity notification")
                .entityModule("test")
                .entityId("1234-abed-"+id)
                .unread(true)
                .payload("<test>this is a payload for test</test>")
                .build();
    }

}
