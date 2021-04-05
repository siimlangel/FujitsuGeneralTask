package com.example.generaltask.repository;

import com.example.generaltask.AbstractTest;
import com.example.generaltask.Application;
import com.example.generaltask.model.Category;
import com.example.generaltask.model.Feedback;
import com.example.generaltask.repository.FeedbackRepository;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class FeedbackRepositoryUnitTest {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Test
    public void addNewFeedback_IdGiven_NoException() throws Exception {
        Feedback saved = feedbackRepository.save(
                new Feedback(1L, "Test", "testuser", "testuser@email.com", new HashSet<Category>())
        );

        Optional<Feedback> feedbackOptional = feedbackRepository.findById(saved.getId());
        assertTrue(feedbackOptional.isPresent());

        Feedback feedback1 = feedbackOptional.get();

        assertNotNull(feedback1);
        assertEquals(saved.getUsername(), feedback1.getUsername());
    }

    @Test
    public void addNewFeedback_IdNotGiven_NoException() throws Exception {
        Feedback saved = feedbackRepository.save(
                new Feedback(null, "Test", "testuser", "testuser@email.com", new HashSet<Category>())
        );

        Optional<Feedback> feedbackOptional = feedbackRepository.findById(saved.getId());
        assertTrue(feedbackOptional.isPresent());

        Feedback feedback1 = feedbackOptional.get();

        assertNotNull(feedback1);
        assertEquals(saved.getUsername(), feedback1.getUsername());
    }

    @Test
    public void addNewFeedback_NoCategoriesGiven_NoException() throws Exception {
        Feedback saved = feedbackRepository.save(
                new Feedback(1L, "Test", "testuser", "testuser@email.com", null)
        );

        Optional<Feedback> feedbackOptional = feedbackRepository.findById(saved.getId());
        assertTrue(feedbackOptional.isPresent());

        Feedback feedback1 = feedbackOptional.get();


        assertNotNull(feedback1);
        assertEquals(saved.getUsername(), feedback1.getUsername());
    }

    @Test
    public void addNewFeedback_NoCategoriesOrIdGiven_NoException() throws Exception {
        Feedback saved = feedbackRepository.save(
                new Feedback(null, "Test", "testuser", "testuser@email.com", null)
        );

        Optional<Feedback> feedbackOptional = feedbackRepository.findById(saved.getId());
        assertTrue(feedbackOptional.isPresent());

        Feedback feedback1 = feedbackOptional.get();


        assertNotNull(feedback1);
        assertEquals(saved.getUsername(), feedback1.getUsername());
    }

    @Test
    public void feedbackIdAutoincrement_NoIdOrCategoriesProvided_NoException() {
        Feedback saved1 = feedbackRepository.save(
                new Feedback(null, "Test1", "testuser1", "testuser1@email.com", null)
        );
        Feedback saved2 = feedbackRepository.save(
                new Feedback(null, "Test2", "testuser2", "testuser2@email.com", null)
        );
        Feedback saved3 = feedbackRepository.save(
                new Feedback(null, "Test3", "testuser3", "testuser3@email.com", null)
        );

        List<Feedback> feedbacks = List.of(saved1, saved2, saved3);

        List<Optional<Feedback>> optionals = new ArrayList<>();



        for (int i = 0; i < 3; i++) {
            Optional<Feedback> feedback = feedbackRepository.findById(feedbacks.get(i).getId());
            assertTrue(feedback.isPresent());
            optionals.add(feedback);
        }

        assertEquals(saved1.getId(), optionals.get(0).get().getId());
        assertEquals(saved2.getId(), optionals.get(1).get().getId());
        assertEquals(saved3.getId(), optionals.get(2).get().getId());

    }


    @Test
    public void feedbackColumnConstraints_NullText_ConstraintViolationException() {
        Feedback feedback = new Feedback(null, null, "user", "user@gmail.com", Set.of());

        assertThrows(ConstraintViolationException.class, () -> {
           feedbackRepository.save(feedback);
        });
    }

    @Test
    public void feedbackColumnConstraints_NullUsername_ConstraintViolationException() {
        Feedback feedback = new Feedback(null, "text", null, "user@gmail.com", Set.of());

        assertThrows(ConstraintViolationException.class, () -> {
            feedbackRepository.save(feedback);
        });
    }

    @Test
    public void feedbackColumnConstraints_NullEmail_ConstraintViolationException() {
        Feedback feedback = new Feedback(null, "Text", "user", null, Set.of());

        assertThrows(ConstraintViolationException.class, () -> {
            feedbackRepository.save(feedback);
        });
    }

    @Test
    public void feedbackDateAuditable_CreatedDate_CreationTime2AfterCreationTime1() throws InterruptedException {
        Feedback feedback1 = new Feedback(null, "Text1", "User1", "user1@gmail.com", Set.of());
        Feedback feedback2 = new Feedback(null, "Text2", "User2", "user2@gmail.com", Set.of());

        Feedback saved1 = feedbackRepository.save(feedback1);
        Thread.sleep(1000);
        Feedback saved2 = feedbackRepository.save(feedback2);

        assertTrue(saved2.getCreatedDate().after(saved1.getCreatedDate()));

    }


}