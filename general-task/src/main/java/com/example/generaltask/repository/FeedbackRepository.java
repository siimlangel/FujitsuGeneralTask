package com.example.generaltask.repository;

import com.example.generaltask.model.Feedback;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends PagingAndSortingRepository<Feedback, Long> {

}
