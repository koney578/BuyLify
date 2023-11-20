package com.buylify.buylifyapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "notification_types")
public class NotificationType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_type_sequence")
    @SequenceGenerator(name = "notification_type_sequence", sequenceName = "notification_type_sequence", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max=30)
    private String name;
}
