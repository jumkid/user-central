package com.jumkid.usercentral.service;

import com.jumkid.share.user.UserProfile;
import com.jumkid.share.user.UserProfileManager;
import com.jumkid.usercentral.controller.dto.ActivityNotification;
import com.jumkid.usercentral.exception.DataNotFoundException;
import com.jumkid.usercentral.model.ActivityNotificationEntity;
import com.jumkid.usercentral.model.mapper.ActivityNotificationMapper;
import com.jumkid.usercentral.repository.ActivityNotificationRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    private final ActivityNotificationRepository activityNotificationRepository;

    private final UserProfileManager userProfileManager;

    private final ActivityNotificationMapper activityNotificationMapper;

    @Autowired
    public UserServiceImpl(ActivityNotificationRepository activityNotificationRepository,
                           UserProfileManager userProfileManager,
                           ActivityNotificationMapper activityNotificationMapper) {
        this.activityNotificationRepository = activityNotificationRepository;
        this.userProfileManager = userProfileManager;
        this.activityNotificationMapper = activityNotificationMapper;
    }

    @Override
    public ActivityNotification getActivityNotification(Long id) throws DataNotFoundException{
        ActivityNotificationEntity entity = activityNotificationRepository.findByIdAndUserId(id, getCurrentUserId())
                .orElseThrow(() -> new DataNotFoundException(String.valueOf(id)));
        return activityNotificationMapper.entityToDTO(entity);
    }

    @Override
    public List<ActivityNotification> getActivityNotificationsForCurrentUser() {
        List<ActivityNotificationEntity> userActivityEntities = activityNotificationRepository.findByUserId(getCurrentUserId());
        log.debug("Found total {} activities for current user", userActivityEntities.size());

        return activityNotificationMapper.entitiesToDTOs(userActivityEntities);
    }

    @Override
    public ActivityNotification save(ActivityNotificationEntity entity) {
        entity = activityNotificationRepository.save(entity);
        return activityNotificationMapper.entityToDTO(entity);
    }

    @Override
    @Transactional
    public List<ActivityNotificationEntity> saveAll(List<ActivityNotificationEntity> activityEntities) {
        return activityNotificationRepository.saveAll(activityEntities);
    }

    @Override
    @Transactional
    public Integer setActivityNotificationUnread(Long id, boolean unread) {
        try {
            return activityNotificationRepository.updateUnread(unread, id, getCurrentUserId());
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Integer deleteActivityNotification(Long id) {
        try {
            activityNotificationRepository.deleteById(String.valueOf(id));
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            log.trace("Failed to delete user activity with id {}", id);
            return 0;
        }
    }

    @Override
    public Integer deleteAll(List<ActivityNotificationEntity> activityEntities) {
        int count = 0;
        try {
            for (ActivityNotificationEntity activityNotificationEntity : activityEntities) {
                String userId = activityNotificationEntity.getUserId();
                String entityId = activityNotificationEntity.getEntityId();
                String entityModule = activityNotificationEntity.getEntityModule();
                count += activityNotificationRepository.deleteByUserIdAndEntityIdAndEntityModule(userId, entityId, entityModule);
                log.trace("Deleted {} activity with userId:{}, entityId:{} entityModule:{}",
                        count, userId, entityId, entityModule);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    private String getCurrentUserId() {
        UserProfile userProfile = userProfileManager.fetchUserProfile();
        return userProfile.getId();
    }
}
