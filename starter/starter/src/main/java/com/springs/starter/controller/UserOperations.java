package com.springs.starter.controller;

import com.springs.starter.entities.EventTable;
import com.springs.starter.entities.RegisteredEvents;
import com.springs.starter.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/userOper")
public class UserOperations {
    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/orgEvents")
    public ResponseEntity<EventTable> getPublicEvents(@RequestParam String org){
        EventTable event = eventRepository.findByOrganizationCode(org);
        return ResponseEntity.ok().body(event);
    }



}
