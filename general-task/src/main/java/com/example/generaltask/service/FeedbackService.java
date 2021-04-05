package com.example.generaltask.service;

import com.example.generaltask.model.Category;
import com.example.generaltask.model.Feedback;
import com.example.generaltask.repository.CategoryRepository;
import com.example.generaltask.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FeedbackService implements IFeedbackService{

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Find all feedbacks given
     * @return List of all feedbacks
     */
    @Override
    public List<Feedback> findAll() {
        var feedbacks = (List<Feedback>) feedbackRepository.findAll();
        return feedbacks;
    }

    /**
     *
     * @param pageable get paging params from request to control the number and page of results returned.
     * @return Paged results from the feedback repository.
     */
    @Override
    public Page<Feedback> findAll(Pageable pageable) {
        return feedbackRepository.findAll(pageable);
    }

    /**
     * Takes a feedback to save to the database.
     * Save categories given in body only if they are actual categories in the db
     * @param feedbackToSave
     * @return saved feedback
     */
    @Override
    public Feedback saveOne(Feedback feedbackToSave) {
        Set<Category> categories = new HashSet<>();

        // Only add categories that are already in the db.
        // Don't let frontend create new categories.
        for (Category cat : feedbackToSave.getCategories()) {
            Category categoryToSave = categoryRepository.findByType(cat.getType());

            // Don't let feedbacks with invalid categories be saved by just omitting invalid categories
            if (categoryToSave == null) {
                throw new DataIntegrityViolationException("Invalid category type " + cat.getType());
            }

            categories.add(categoryToSave);
        }
        feedbackToSave.setCategories(categories);
        return feedbackRepository.save(feedbackToSave);
    }


}
