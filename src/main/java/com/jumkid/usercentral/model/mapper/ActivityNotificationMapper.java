package com.jumkid.usercentral.model.mapper;

import com.jumkid.usercentral.controller.dto.ActivityNotification;
import com.jumkid.usercentral.model.ActivityNotificationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel="spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ActivityNotificationMapper {

    ActivityNotification entityToDTO(ActivityNotificationEntity entity);

    ActivityNotificationEntity dtoToEntity(ActivityNotification dto);

    List<ActivityNotification> entitiesToDTOs(List<ActivityNotificationEntity> entities);

    List<ActivityNotificationEntity> dtosToEntities(List<ActivityNotification> dtos);

}
