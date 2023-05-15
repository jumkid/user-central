package com.jumkid.usercentral.service;

import com.jumkid.usercentral.controller.dto.Activity;
import com.jumkid.usercentral.exception.DataNotFoundException;
import com.jumkid.usercentral.model.ActivityEntity;

import java.util.List;

public interface UserService {

    Activity getUserActivity(Long id) throws DataNotFoundException;

    List<Activity> getActivities();

    Activity save(ActivityEntity entity);

    List<ActivityEntity> saveAll(List<ActivityEntity> activityEntities);

    Integer setActivityUnread(Long id, boolean unread);

    Integer deleteUserActivity(Long id);

    Integer deleteAll(List<ActivityEntity> activityEntities);

}
