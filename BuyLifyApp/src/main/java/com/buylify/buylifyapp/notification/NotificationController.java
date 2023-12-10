package com.buylify.buylifyapp.notification;

import com.buylify.buylifyapp.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PreAuthorize("hasAuthority('administrator')")
    @PostMapping
    public void addNotification(@RequestBody CreateNotificationDto createNotificationDto) {
        notificationService.addNotification(createNotificationDto);
    }

    @GetMapping
    public List<NotificationDto> getNotificationsByLoggedUser(Authentication authentication) {
        Long userId = ((SecurityUser) authentication.getPrincipal()).getId();
        return notificationService.getNotificationsByLoggedUser(userId);
    }
}
