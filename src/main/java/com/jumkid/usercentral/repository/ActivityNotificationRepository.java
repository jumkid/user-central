package com.jumkid.usercentral.repository;

import com.jumkid.usercentral.model.ActivityNotificationEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityNotificationRepository extends JpaRepository<ActivityNotificationEntity, String> {

    Optional<ActivityNotificationEntity> findByIdAndUserId(Long id, String userId);

    List<ActivityNotificationEntity> findByUserId(String userId);

    @Modifying
    @Query("update ActivityNotificationEntity a set a.unread = :unread where a.id = :id AND a.userId = :userId")
    Integer updateUnread(@Param(value = "unread") boolean unread,
                         @Param(value = "id") Long id,
                         @Param(value = "userId") String userId);

    @Transactional
    Integer deleteByUserIdAndEntityIdAndEntityModule(String userId, String entityId, String entityModule);
}
