package com.springs.starter.service;

import com.springs.starter.entities.Question;
import com.springs.starter.repositories.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;
    public List<Question> getAllQuestions() {
       return questionDao.findAll();
    }
}
