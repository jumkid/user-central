package com.jumkid.usercentral.model.mapper;

import com.jumkid.usercentral.controller.dto.Activity;
import com.jumkid.usercentral.model.ActivityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel="spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ActivityMapper {

    Activity entityToDTO(ActivityEntity entity);

    ActivityEntity dtoToEntity(Activity dto);

}
