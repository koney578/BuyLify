package com.buylify.buylifyapp.mappers;

import com.buylify.buylifyapp.notification.CreateNotificationDto;
import com.buylify.buylifyapp.notification.Notification;
import com.buylify.buylifyapp.notification.NotificationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface NotificationMapper {

    Notification toEntity(CreateNotificationDto dto);

    @Mapping(target = "id", source = "id")
    NotificationDto toDto(Notification notification);
}
