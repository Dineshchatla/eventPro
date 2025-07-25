package com.springs.starter.controller;

import com.springs.starter.entities.Question;
import com.springs.starter.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("questions")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping("allQues")
    public List<Question> allQuestion(){
        return questionService.getAllQuestions();
    }
}
