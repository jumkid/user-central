package com.jumkid.usercentral.repository;

import com.jumkid.usercentral.model.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityEntity, String> {

    List<ActivityEntity> findByUserIdAndUnread(String userId, boolean unread);

    @Modifying
    @Query("update ActivityEntity set unread = ?1 where id = ?2 AND userId = ?3")
    Integer setUnreadForUserActivity(boolean unread, Long id, String userId);

}
