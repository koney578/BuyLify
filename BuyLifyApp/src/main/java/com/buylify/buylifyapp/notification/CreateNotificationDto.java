package com.buylify.buylifyapp.notification;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateNotificationDto {

    @NotNull
    private String message;

    @NotNull
    private Long userId;

    @NotNull
    private Long notificationTypeId;
}
