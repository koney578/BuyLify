package com.buylify.buylifyapp.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.user.id = :userId")
    List<Notification> findNotificationsByUserId(@Param("userId") Long userId);
}
