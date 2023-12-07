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

    public void addNotificationType(NotificationType notificationType) {
        notificationTypeRepository.save(notificationType);
    }

    public void deleteNotificationType(Long id) {
        notificationTypeRepository.deleteById(id);
    }


    public void editNotificationType(Long id, NotificationType notificationTypeToEdit) {
        NotificationType notificationType = notificationTypeRepository.findById(id).orElseThrow();
        notificationType.setName(notificationTypeToEdit.getName());
        notificationTypeRepository.save(notificationType);
    }
}
