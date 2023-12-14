package com.buylify.buylifyapp.notification;

import com.buylify.buylifyapp.notificationType.NotificationType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto {

    private Long id;

    private String message;

    private boolean isChecked;

    private LocalDateTime createdAt;

    private NotificationType notificationType;
}
