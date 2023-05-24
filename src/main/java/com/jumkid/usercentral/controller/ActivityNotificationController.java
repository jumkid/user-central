package com.jumkid.usercentral.controller;

import com.jumkid.share.controller.response.CommonResponse;
import com.jumkid.usercentral.controller.dto.ActivityNotification;
import com.jumkid.usercentral.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user/activityNotifications")
public class ActivityNotificationController {

    private final UserService userService;

    public ActivityNotificationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('USER_ROLE', 'ADMIN_ROLE')")
    public List<ActivityNotification> getUserActivities() {
        return userService.getActivityNotificationsForCurrentUser();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('USER_ROLE', 'ADMIN_ROLE')")
    public ActivityNotification getUserActivity(@PathVariable Long id) {
        return userService.getActivityNotification(id);
    }

    @PutMapping("/{id}/unread")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyAuthority('USER_ROLE', 'ADMIN_ROLE')")
    public CommonResponse setActivityNotificationUnread(@PathVariable Long id) {
        Integer updated = userService.setActivityNotificationUnread(id, false);
        boolean isUpdated = updated == 1;
        return CommonResponse.builder()
                .success(isUpdated)
                .msg(isUpdated ? "" : "Failed to update unread flag")
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('USER_ROLE', 'ADMIN_ROLE')")
    public CommonResponse delete(@PathVariable Long id) {
        Integer deleted = userService.deleteActivityNotification(id);
        boolean isDeleted = deleted == 1;
        return CommonResponse.builder()
                .success(isDeleted)
                .msg(isDeleted ? "" : "Failed to delete activity notification")
                .build();
    }

}
