package com.buylify.buylifyapp.mappers;

import com.buylify.buylifyapp.notification.CreateNotificationDto;
import com.buylify.buylifyapp.notification.Notification;
import com.buylify.buylifyapp.notification.NotificationDto;
import org.mapstruct.Mapper;

@Mapper
public interface NotificationMapper {

    Notification toEntity(CreateNotificationDto dto);

    NotificationDto toDto(Notification notification);
}
