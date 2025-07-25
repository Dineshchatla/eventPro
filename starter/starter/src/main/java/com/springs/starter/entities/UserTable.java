package com.springs.starter.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_table")
public class UserTable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private int UserId;
    @Column(columnDefinition = "DEFAULT 'user' ")
    private String userType;
    private String firstName;
    private String lastName;
    private String email;
    private String organizationCode;
    private String password;

}
