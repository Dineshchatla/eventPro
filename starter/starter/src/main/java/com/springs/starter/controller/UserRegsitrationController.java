package com.springs.starter.controller;

import com.springs.starter.repositories.EventRepository;
import com.springs.starter.repositories.RegisteredEventRepository;
import com.springs.starter.repositories.UserRepository;
import com.springs.starter.entities.UserTable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/main")
public class UserRegsitrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private RegisteredEventRepository registeredEventRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> loginUser(@RequestBody UserTable user) {
        System.out.println("Received: " + user);
        userRepository.save(user);  // store in DB
        return ResponseEntity.ok("User saved successfully");
    }

    @GetMapping("/allUsers")
    public List<UserTable> getUsers(){
        return userRepository.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserTable> deleteUser(@PathVariable int id){
        if (userRepository.existsById(id)){
            UserTable user = userRepository.getReferenceById(id);
            userRepository.deleteById(id);
            return ResponseEntity.ok(user);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PatchMapping("/edituser/{id}")
    public ResponseEntity<UserTable> updateUserPartially(
            @PathVariable int id,
            @RequestBody Map<String, Object> updates) {

        Optional<UserTable> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            UserTable user = optionalUser.get();

            updates.forEach((key, value) -> {
                switch (key) {
                    case "firstName": user.setFirstName((String) value); break;
                    case "lastName": user.setLastName((String) value); break;
                    case "email": user.setEmail((String) value); break;
                    case "organizationCode": user.setOrganizationCode((String) value); break;
                    case "password": user.setPassword((String) value); break;
                    // Add more fields as needed
                }
            });

            userRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/verifyLogin")
    public ResponseEntity<UserTable> verifyLogin(@RequestBody Map<String, String> payload, HttpServletRequest request) {
        String email = payload.get("email");
        String password = payload.get("password");

        UserTable userDetails = userRepository.findByEmail(email);
        System.out.println(email+" "+password+" "+userDetails);
        if (userDetails != null && Objects.equals(userDetails.getPassword(), password)) {
            HttpSession sess = request.getSession();
            sess.setAttribute("userId",userDetails.getUserId());
            return ResponseEntity.ok(userDetails);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userDetails);
        }
    }







    }
