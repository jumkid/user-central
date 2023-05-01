package com.jumkid.usercentral.service;

import com.jumkid.usercentral.repository.ActivityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final ActivityRepository activityRepository;

    @Autowired
    public UserService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

}
