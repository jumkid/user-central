package com.jumkid.usercentral.controller;

import com.jumkid.share.controller.response.CommonResponse;
import com.jumkid.usercentral.controller.dto.Activity;
import com.jumkid.usercentral.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/activities")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('USER_ROLE', 'ADMIN_ROLE')")
    public List<Activity> getUserActivities() {
        return userService.getActivities();
    }

    @GetMapping("/activities/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('USER_ROLE', 'ADMIN_ROLE')")
    public Activity getUserActivity(@PathVariable Long id) {
        return userService.getUserActivity(id);
    }

    @PutMapping("/activities/{id}/unread")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyAuthority('USER_ROLE', 'ADMIN_ROLE')")
    public CommonResponse setActivityUnread(@PathVariable Long id) {
        Integer updated = userService.setActivityUnread(id, false);
        boolean isUpdated = updated == 1;
        return CommonResponse.builder()
                .success(isUpdated)
                .msg(isUpdated ? "" : "Failed to update unread flag")
                .build();
    }

}
