package com.buylify.buylifyapp.notificationType;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notification-types")
@RequiredArgsConstructor
public class NotificationTypeController {

    private final NotificationTypeService notificationTypeService;

    @GetMapping
    public List<NotificationType> getAllNotificationTypes() {
        return notificationTypeService.getAllNotificationTypes();
    }
}
