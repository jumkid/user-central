package com.jumkid.usercentral.service;

import com.jumkid.usercentral.controller.dto.ActivityNotification;
import com.jumkid.usercentral.exception.DataNotFoundException;
import com.jumkid.usercentral.model.ActivityNotificationEntity;

import java.util.List;

public interface UserService {

    ActivityNotification getActivityNotification(Long id) throws DataNotFoundException;

    List<ActivityNotification> getActivityNotificationsForCurrentUser();

    ActivityNotification save(ActivityNotificationEntity entity);

    List<ActivityNotificationEntity> saveAll(List<ActivityNotificationEntity> entities);

    Integer setActivityNotificationUnread(Long id, boolean unread);

    Integer deleteActivityNotification(Long id);

    Integer deleteAll(List<ActivityNotificationEntity> entities);

}
