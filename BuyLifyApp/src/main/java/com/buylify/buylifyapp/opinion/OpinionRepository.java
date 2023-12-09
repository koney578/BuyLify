package com.buylify.buylifyapp.opinion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OpinionRepository extends JpaRepository<Opinion, Long> {

    @Query("SELECT o FROM Opinion o " +
            "WHERE o.userSender.id = :userIdSender AND o.userReceiver.id = :userIdReceiver")
    Optional<Opinion> findByUserSenderAndUserReceiver(@Param("userIdSender") Long userIdSender,
                                                      @Param("userIdReceiver") Long userIdReceiver);
}
