package com.example.generaltask.service;

import com.example.generaltask.model.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IFeedbackService {
    List<Feedback> findAll();
    Page<Feedback> findAll(Pageable pageable);
    Feedback saveOne(Feedback feedbackToSave);
}
