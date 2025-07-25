package com.springs.starter.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class RegisteredEvents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Because DB uses auto_increment
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", nullable = false)
    private EventTable event;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private UserTable user;
}


