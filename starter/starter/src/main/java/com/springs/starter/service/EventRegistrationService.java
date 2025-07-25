package com.springs.starter.service;


import com.springs.starter.repositories.EventRepository;
import com.springs.starter.repositories.RegisteredEventRepository;
import com.springs.starter.repositories.UserRepository;
import com.springs.starter.entities.EventTable;
import com.springs.starter.entities.RegisteredEvents;
import com.springs.starter.entities.UserTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventRegistrationService {

        @Autowired
        private RegisteredEventRepository registeredEventsRepo;

        @Autowired
        private EventRepository eventRepo;

        @Autowired
        private UserRepository userRepo;

        public void registerEvent(Integer eventId, Integer userId) {
            RegisteredEvents reg = new RegisteredEvents();

            EventTable event = eventRepo.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
            UserTable user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

            reg.setEvent(event);
            reg.setUser(user);

            registeredEventsRepo.save(reg);
        }
    }


