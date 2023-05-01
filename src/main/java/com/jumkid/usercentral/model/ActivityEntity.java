package com.jumkid.usercentral.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "activity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityEntity {

    @Id
    @Column(name = "activity_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "entity_id")
    private String entityId;

    @Column(name = "module")
    private String entityModule;

    @Column(name = "title")
    private String title;

    @Column(name = "unread")
    private boolean unread;

}
