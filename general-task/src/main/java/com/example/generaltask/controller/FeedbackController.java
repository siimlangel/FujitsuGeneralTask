package com.example.generaltask.controller;

import com.example.generaltask.model.Feedback;
import com.example.generaltask.service.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@Validated
@RestController
@RequestMapping("/api")
public class FeedbackController {

    @Autowired
    private IFeedbackService feedbackService;

    /**
     * Paginated request for feedbacks
     * @param pageable how many results from which page to return. /?page=x&size=y in request params.
     * @return s feedbacks from page p, where s and p are the size and page number specified in the request params.
     */
    @GetMapping("/feedback")
    Page<Feedback> getAllFeedbacks(Pageable pageable) {
        return feedbackService.findAll(pageable);
    }

    /**
     * Save a feedback to the database.
     * @param feedbackToSave Feedback sent from front-end.
     * @return Saved feedback instance.
     */
    @PostMapping("/feedback")
    Feedback saveOneFeedback(@RequestBody @Valid @NotNull Feedback feedbackToSave, Errors errors) throws Exception {
        // Convey all constraint violations to com.example.generaltask.exception.RestExceptionHandler class
        if (errors.hasErrors()) {
            List<ConstraintViolation<?>> violations = new ArrayList<>();
            for (ObjectError e: errors.getAllErrors()) {
                violations.add(e.unwrap(ConstraintViolation.class));
            }

            String message = "";

            for (ConstraintViolation<?> violation : violations) {
                message += violation.getPropertyPath() + " " + violation.getMessage();
            }

            throw new Exception(String.format("Request input is invalid:\n%s", message));
        }



        return feedbackService.saveOne(feedbackToSave);
    }

}
