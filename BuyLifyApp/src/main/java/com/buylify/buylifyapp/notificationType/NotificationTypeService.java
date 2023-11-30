package com.buylify.buylifyapp.notificationType;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationTypeService {

    private final NotificationTypeRepository notificationTypeRepository;

    public List<NotificationType> getAllNotificationTypes() {
        return notificationTypeRepository.findAll();
    }
}
