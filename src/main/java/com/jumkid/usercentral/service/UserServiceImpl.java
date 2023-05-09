package com.jumkid.usercentral.service;

import com.jumkid.share.user.UserProfile;
import com.jumkid.share.user.UserProfileManager;
import com.jumkid.usercentral.controller.dto.Activity;
import com.jumkid.usercentral.exception.DataNotFoundException;
import com.jumkid.usercentral.model.ActivityEntity;
import com.jumkid.usercentral.model.mapper.ActivityMapper;
import com.jumkid.usercentral.repository.ActivityRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    private final ActivityRepository activityRepository;

    private final UserProfileManager userProfileManager;

    private final ActivityMapper activityMapper;

    @Autowired
    public UserServiceImpl(ActivityRepository activityRepository,
                           UserProfileManager userProfileManager,
                           ActivityMapper activityMapper) {
        this.activityRepository = activityRepository;
        this.userProfileManager = userProfileManager;
        this.activityMapper = activityMapper;
    }

    @Override
    public Activity getUserActivity(Long id) throws DataNotFoundException{
        ActivityEntity entity = activityRepository.findByIdAndUserId(id, getCurrentUserId())
                .orElseThrow(() -> new DataNotFoundException(String.valueOf(id)));
        return activityMapper.entityToDTO(entity);
    }

    @Override
    public List<Activity> getActivities() {
        List<ActivityEntity> userActivityEntities = activityRepository.findByUserIdAndUnread(getCurrentUserId(), true);
        log.debug("Found total {} activities for current user", userActivityEntities.size());

        return activityMapper.entitiesToDTOs(userActivityEntities);
    }

    @Override
    public Activity save(ActivityEntity entity) {
        entity = activityRepository.save(entity);
        return activityMapper.entityToDTO(entity);
    }

    @Override
    public List<ActivityEntity> saveAll(List<ActivityEntity> activityEntities) {
        return activityRepository.saveAll(activityEntities);
    }

    @Override
    @Transactional
    public Integer setActivityUnread(Long id, boolean unread) {
        try {
            return activityRepository.updateUnread(unread, id, getCurrentUserId());
        } catch (Exception e) {
            return 0;
        }
    }

    private String getCurrentUserId() {
        UserProfile userProfile = userProfileManager.fetchUserProfile();
        return userProfile.getId();
    }
}
