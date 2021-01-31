package com.starsky.backend.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class NotificationType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification-type-id-generator")
    @SequenceGenerator(name = "notification-type-id-generator", sequenceName = "notification_type_sequence", allocationSize = 1)
    private long id;

    @Column(unique = true)
    @NotNull
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

