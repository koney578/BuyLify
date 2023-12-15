package com.buylify.buylifyapp.notification;

import com.buylify.buylifyapp.authentication.User;
import com.buylify.buylifyapp.authentication.UserRepository;
import com.buylify.buylifyapp.mappers.NotificationMapper;
import com.buylify.buylifyapp.notificationType.NotificationType;
import com.buylify.buylifyapp.notificationType.NotificationTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationTypeRepository notificationTypeRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;

    public void addNotification(CreateNotificationDto createNotificationDto) {
        User user = userRepository.findById(createNotificationDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        NotificationType notificationType = notificationTypeRepository.findById(createNotificationDto.getNotificationTypeId()).orElseThrow(() -> new RuntimeException("Notification type not found"));

        Notification notification = notificationMapper.toEntity(createNotificationDto);
        notification.setUser(user);
        notification.setNotificationType(notificationType);
        notification.setChecked(false);

        notificationRepository.save(notification);
    }

    public List<NotificationDto> getNotificationsByLoggedUser(Long userId) {
        return notificationRepository.findNotificationsByUserId(userId).stream()
                .map(notificationMapper::toDto)
                .toList();
    }

    public void checkNotification(CheckDto checkDto, Long userId) {
        Notification notification = notificationRepository.findById(checkDto.getId()).orElseThrow(() -> new RuntimeException("Notification not found"));
        if (notification.getUser().getId().equals(userId)) {
            notification.setChecked(checkDto.getIsChecked());
            notificationRepository.save(notification);
        }
    }
}
