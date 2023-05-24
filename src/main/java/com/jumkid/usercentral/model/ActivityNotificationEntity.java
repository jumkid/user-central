package com.jumkid.usercentral.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "activity_notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"userId", "entityId", "entityModule"})
public class ActivityNotificationEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "entity_id")
    private String entityId;

    @Column(name = "entity_module")
    private String entityModule;

    @Column(name = "title")
    private String title;

    @Column(name = "unread")
    private boolean unread;

    @Column(name = "payload")
    private String payload;

}
