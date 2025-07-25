package com.springs.starter.repositories;

import com.springs.starter.entities.RegisteredEvents;
import com.springs.starter.entities.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegisteredEventRepository extends JpaRepository<RegisteredEvents, Integer> {
    List<RegisteredEvents> findByUser(UserTable user);

}
