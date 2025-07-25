package com.springs.starter.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
public class EventTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Integer eventId;

    private String eventName;

    private String eventType;
    private String eventBanner;

    private String platform;
    private String meetingUrl;
    private String meetingId;
    private String meetingPassword;

    private LocalDate startDate;
    private LocalTime startTime;
    private String duration;

    private String timezone;
    private String currency;

    @Column(columnDefinition = "default 'public'")
    private String organizationCode;

    private String category;

    @Column(columnDefinition = "VARCHAR(100) DEFAULT 'Upcoming'")
    private String eventStatus = "Upcoming";

    private int participants;
    





}
