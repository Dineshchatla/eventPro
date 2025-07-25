package com.springs.starter.controller;

import com.springs.starter.entities.RegisteredEvents;
import com.springs.starter.entities.UserTable;
import com.springs.starter.repositories.EventRepository;
import com.springs.starter.entities.EventTable;
import com.springs.starter.repositories.RegisteredEventRepository;
import com.springs.starter.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EventController {
//    private final EventRepository eventRepository;
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegisteredEventRepository registeredEventRepository;

    @PostMapping("/createEvent")
    public ResponseEntity<EventTable> loginUser(@RequestBody EventTable event) {
        System.out.println("Received: " + event);
        EventTable savedEvent = eventRepository.save(event);
        return ResponseEntity.ok(savedEvent);

    }

    @GetMapping("/events")
    public Iterable<EventTable> getAllEvents(){
        return eventRepository.findAll();
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<EventTable> getEventById(@PathVariable int id) {
        Optional<EventTable> event = eventRepository.findById(id);
        return event.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/event/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Integer id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return ResponseEntity.ok("Deleted event with ID " + id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found");
        }

    }

    @PatchMapping("/event/{id}")
    public ResponseEntity<EventTable> updateEventPartially(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        Optional<EventTable> optionalEvent = eventRepository.findById(id);

        if (optionalEvent.isPresent()) {
            EventTable event = optionalEvent.get();

            // Check and apply updates
            updates.forEach((key, value) -> {
                switch (key) {
                    case "eventName":
                        event.setEventName((String) value);
                        break;
                    case "date":
                        event.setStartDate(LocalDate.parse((String) value));
                        break;
                    case "time":
                        event.setStartTime(LocalTime.parse((String) value));
                        break;
                    case "status":
                        event.setEventStatus((String) value);
                        break;
                    case "organizationCode":
                        event.setOrganizationCode((String) value);
                        break;

                }
            });

            eventRepository.save(event);
            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.notFound().build();
        }




    }




    @GetMapping("/events/all")
    public List<EventTable> getAllEventsList() {
        return eventRepository.findAll();
    }

    @GetMapping("/eventStatus/{status}")
    public List<EventTable> getByStatus(@PathVariable String status) {
        return eventRepository.findByEventStatus(status);
    }

    @GetMapping("/eventdetail")
    public ResponseEntity<EventTable> getEventDetail(@RequestParam int id) {
        Optional<EventTable> optional = eventRepository.findById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("image") MultipartFile file) throws IOException {
        String folder = "C:/EventImages/";

        // Create folder if it doesn't exist
        File dir = new File(folder);
        if (!dir.exists()) dir.mkdirs();

        // Rename file to current timestamp + extension
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String newFileName = System.currentTimeMillis() + extension;
        String filePath = folder + newFileName;

        // Save file
        file.transferTo(new File(filePath));

        // Return response with image path or name
        Map<String, String> response = new HashMap<>();
        response.put("bannerUrl", newFileName);  // or full path
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registerEvent")
    public ResponseEntity<String> registerEvent(@RequestParam int eventId, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        Optional<UserTable> userOpt = userRepository.findById(userId);
        Optional<EventTable> eventOpt = eventRepository.findById(eventId);

        if (userOpt.isPresent() && eventOpt.isPresent()) {
            EventTable event = eventRepository.getReferenceById(eventId);
            int currentCount = event.getParticipants();
            event.setParticipants(currentCount + 1);

            // Save updated event
            eventRepository.save(event);
            RegisteredEvents registration = new RegisteredEvents();
            registration.setUser(userOpt.get());
            registration.setEvent(eventOpt.get());

            registeredEventRepository.save(registration);
            return ResponseEntity.ok("User registered successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid userId or eventId.");
        }
    }

    @GetMapping("/registeredEvents")
    public ResponseEntity<?> registeredEventsList(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in.");
        }
        Optional<UserTable> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + userId);
        }
        List<RegisteredEvents> registeredEvents = registeredEventRepository.findByUser(userOpt.get());
        return ResponseEntity.ok(registeredEvents);
    }

    @GetMapping("/orgEventsByStatus")
    public ResponseEntity<?> getOrgEventsByStatus(@RequestParam String status , HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        UserTable user = userRepository.getReferenceById(userId);

        List<EventTable> events = eventRepository.findByOrganizationCodeAndEventStatus(user.getOrganizationCode(), status);
        return ResponseEntity.ok(events);

    }


}
