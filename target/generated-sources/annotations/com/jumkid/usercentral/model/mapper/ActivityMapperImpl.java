package com.jumkid.usercentral.model.mapper;

import com.jumkid.usercentral.controller.dto.Activity;
import com.jumkid.usercentral.model.ActivityEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-01T12:11:26-0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class ActivityMapperImpl implements ActivityMapper {

    @Override
    public Activity entityToDTO(ActivityEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Activity.ActivityBuilder activity = Activity.builder();

        activity.id( entity.getId() );
        activity.userId( entity.getUserId() );
        activity.entityId( entity.getEntityId() );
        activity.entityModule( entity.getEntityModule() );
        activity.title( entity.getTitle() );
        activity.unread( entity.isUnread() );

        return activity.build();
    }

    @Override
    public ActivityEntity dtoToEntity(Activity dto) {
        if ( dto == null ) {
            return null;
        }

        ActivityEntity.ActivityEntityBuilder activityEntity = ActivityEntity.builder();

        activityEntity.id( dto.getId() );
        activityEntity.userId( dto.getUserId() );
        activityEntity.entityId( dto.getEntityId() );
        activityEntity.entityModule( dto.getEntityModule() );
        activityEntity.title( dto.getTitle() );
        activityEntity.unread( dto.isUnread() );

        return activityEntity.build();
    }
}
