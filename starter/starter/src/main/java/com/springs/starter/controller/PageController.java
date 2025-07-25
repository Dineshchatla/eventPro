package com.springs.starter.controller;

import com.springs.starter.entities.RegisteredEvents;
import com.springs.starter.repositories.EventRepository;
import com.springs.starter.repositories.UserRepository;
import com.springs.starter.entities.EventTable;
import com.springs.starter.entities.UserTable;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
public class PageController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventRepository eventRepo;

    @GetMapping("/")
    public String showLandingpage() {
        return "Landingpage";
    }

    @GetMapping("/signup")
    public String showRegisterPage() {
        return "UserRegistrationPage";
    }

    @GetMapping("/createEventPage")
    public String homePage() {
        return "Admin-CreateEvent";
    }

    @GetMapping("/home")
    public String createEvent(@RequestParam String firstName, Model model){
        UserTable userData = userRepository.findByFirstName(firstName);
        model.addAttribute("username", userData.getFirstName());
        return "User-Homepage";

    }

    @GetMapping("/SingleEvent")
    public String SingleEvent(@RequestParam Integer id, Model model){
        EventTable eventData = eventRepo.getReferenceById(id);
        model.addAttribute("eventData",eventData);
        System.out.println("Event Name: " + eventData.getEventName());
        return "Admin-SingleEventDetail";
    }



    @GetMapping("/loginpage")
    public String showLoginPage(){
        return "Main-LoginPage";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("userId");
        return "redirect:/loginpage";
    }

    @GetMapping("/userManage")
    public String showUserManage(){
        return "Admin-UserManagmentPage";
    }


    // Admin pages
//    @Autowired
//    private UserRepository userRepository;

    @GetMapping("/adminDash")
    public String showAdminDash(Model model, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId != null) {
            UserTable usertable = userRepository.getReferenceById(userId);
            System.out.println(userId+" "+usertable);
            model.addAttribute("username", usertable.getFirstName());
            model.addAttribute("totalEvents",eventRepo.count());
            model.addAttribute("upcomingEvents", eventRepo.countByEventStatus("upcoming"));
            return "admindashboard";
        } else {
            return "redirect:/loginpage";
        }
    }

    @GetMapping("/create-events")
    public String createEventPage(HttpSession session) {
        Integer id = (Integer) session.getAttribute("userId");
        if(id != null) {
            UserTable user = userRepository.getReferenceById(id);
            if (Objects.equals(user.getUserType(), "admin")) {
                return "Admin-CreateEvent";
            }
            return "redirect:/loginpage";
        }return "redirect:/loginpage";
    }

    @GetMapping("/manage-events")
    public String manageEventsPage(HttpSession session) {
        Integer id = (Integer) session.getAttribute("userId");
        if(id != null) {
            UserTable user = userRepository.getReferenceById(id);
            if (Objects.equals(user.getUserType(), "admin")) {
                return "Admin-ManageAllEvents";
            }
            return "redirect:/loginpage";
        }return "redirect:/loginpage";

    }

    @GetMapping("/user-management")
    public String userManagementPage() {
        return "Admin-UserManagmentPage";
    }

    @GetMapping("/getDash")
    public String getDashboard(HttpSession session) {

        Integer id = (Integer) session.getAttribute("userId");
        if(id != null) {
            UserTable user = userRepository.getReferenceById(id);
            if (Objects.equals(user.getUserType(), "admin")) {
                return "redirect:/adminDash";
            } else if (Objects.equals(user.getUserType(), "")) {
                return "redirect:/userDash";
            }else{
                return "redirect:/userDash";
            }
        }else {
            return "redirect:/loginpage";
        }
    }

    @GetMapping("editEventPage")
    public String getEventEditPage(@RequestParam int id, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        UserTable user = userRepository.getReferenceById(userId);
        if(Objects.equals(user.getUserType(), "admin")){
            return "Admin-EditEvent";
        }else {
            return "redirect:/loginpage";
        }

    }


    // UserPages

    @GetMapping("/userDash")
    public String getUserDashboard(Model model, HttpSession session){
        Integer user_id = (Integer) session.getAttribute("userId");
        if(user_id != null){
        String username = userRepository.getReferenceById(user_id).getFirstName();
        model.addAttribute("username",username);
        return "User-HomePage";}else{
            return "redirect:/loginpage";
        }
    }

    @GetMapping("/registeredEventsPage")
    public String getRegisteredevents(HttpSession session){
        Integer user_id = (Integer) session.getAttribute("userId");
        return "User-registredEventsPage";
    }

    @GetMapping("/userTicket")
    public String getUserTicket(@RequestParam int eventId, Model model, HttpSession session){
        Integer user_id = (Integer) session.getAttribute("userId");
        UserTable user = userRepository.getReferenceById(user_id);
        EventTable event = eventRepo.getReferenceById(eventId);
        model.addAttribute("eventName",event.getEventName());
        model.addAttribute("eventDate", event.getStartDate());
        model.addAttribute("eventPlatform", event.getPlatform());
        model.addAttribute("eventBanner", event.getEventBanner());
        model.addAttribute("userName", user.getFirstName());
        model.addAttribute("userId",user_id);
        model.addAttribute("eventId", eventId );
        model.addAttribute("meetingId",event.getMeetingId());
        model.addAttribute("meetingPassword", event.getMeetingPassword());
        return "User-Ticket";

    }








}
