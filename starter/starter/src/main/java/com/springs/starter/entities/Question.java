package com.springs.starter.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String quesTitle;
    private String option1;
    private String option2;

    @Entity
    public static class CurrentSession {

        @Id
        @Column(columnDefinition = "VARCHAR(100) DEFAULT 'current'")
        private String userNow = "current";

        private int userId;
        private String userName;
        private String userType;


    }
}
;