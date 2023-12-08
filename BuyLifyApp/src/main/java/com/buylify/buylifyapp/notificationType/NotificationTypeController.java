package com.buylify.buylifyapp.notificationType;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasAuthority('administrator')")
    @PostMapping
    public void addNotificationType(@RequestBody NotificationType notificationType) {
        notificationTypeService.addNotificationType(notificationType);
    }

    @PreAuthorize("hasAuthority('administrator')")
    @DeleteMapping("/{id}")
    public void deleteNotificationType(@PathVariable("id") Long id) {
        notificationTypeService.deleteNotificationType(id);
    }

    @PreAuthorize("hasAuthority('administrator')")
    @PutMapping("/{id}")
    public void editNotificationType(@PathVariable("id") Long id, @RequestBody NotificationType notificationTypeToEdit) {
        notificationTypeService.editNotificationType(id, notificationTypeToEdit);
    }
}
