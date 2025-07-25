package com.springs.starter.repositories;

import com.springs.starter.entities.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserTable, Integer> {
    UserTable findByFirstName(String firstName);
    UserTable findByEmail(String email);
}
