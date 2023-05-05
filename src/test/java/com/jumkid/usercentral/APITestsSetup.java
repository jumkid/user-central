package com.jumkid.usercentral;

import com.jumkid.usercentral.model.ActivityEntity;

import java.util.List;

public class APITestsSetup {

    static List<ActivityEntity> buildActivityEntities() {
        return List.of(buildActivityEntity(1L), buildActivityEntity(2L));
    }

    static ActivityEntity buildActivityEntity(Long id) {
        return ActivityEntity.builder()
                .id(id)
                .userId("test")
                .title("test user activity")
                .entityModule("test")
                .entityId("1234-abed-"+id)
                .unread(true)
                .payload("<test>this is a payload for test</test>")
                .build();
    }

}
