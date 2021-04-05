package com.example.generaltask.controller;

import com.example.generaltask.AbstractTest;
import com.example.generaltask.model.Category;
import com.example.generaltask.model.Feedback;
import com.example.generaltask.repository.CategoryRepository;
import com.example.generaltask.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class FeedbackControllerIntegrationTest extends AbstractTest {
    String uri = "/api/feedback";

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }


    @Test
    public void saveFeedBack_ValidCategoryInBody_FeedbackWithOneCategory() throws Exception {
        Category cat = new Category(null, "test");
        Category saved = categoryRepository.save(cat);
        Feedback feedback = new Feedback(
                null,
                "testText",
                "Testuser",
                "TestUser@gmail.com",
                Set.of(saved)
        );

        JSONObject jsonObject = super.FeedbackToJson(feedback);

        MockHttpServletRequestBuilder postBuilder = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString());

        MvcResult mvcResult = mvc.perform(postBuilder)
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<Category> responseCategories = JsonPath.parse(contentAsString).read("$.categories");
        assertEquals(feedback.getCategories().size(), responseCategories.size());
    }

    @Test
    public void saveFeedBack_ValidCategoriesInBody_FeedbackWithManyCategories() throws Exception {
        Category cat1 = new Category(null, "test1");
        Category cat2 = new Category(null, "test2");
        Category cat3 = new Category(null, "test3");

        Category saved1 = categoryRepository.save(cat1);
        Category saved2 = categoryRepository.save(cat2);
        Category saved3 = categoryRepository.save(cat3);

        Feedback feedback = new Feedback(
                null,
                "testText",
                "Testuser",
                "TestUser@gmail.com",
                Set.of(saved1, saved2, saved3)
        );

        JSONObject jsonObject = super.FeedbackToJson(feedback);

        MockHttpServletRequestBuilder postBuilder = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString());

        MvcResult mvcResult = mvc.perform(postBuilder)
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<Category> responseCategories = JsonPath.parse(contentAsString).read("$.categories");

        assertEquals(feedback.getCategories().size(), responseCategories.size());
    }

    @Test
    public void saveFeedBack_OnlyInvalidCategoryInBody_Status400() throws Exception {
        Category cat = new Category(null, "test");
        categoryRepository.save(cat);

        Category invalidCat = new Category(null, "notTest");
        Feedback feedback = new Feedback(
                null,
                "testText",
                "Testuser",
                "TestUser@gmail.com",
                Set.of(invalidCat)
        );

        JSONObject jsonObject = super.FeedbackToJson(feedback);

        MockHttpServletRequestBuilder postBuilder = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString());

        mvc.perform(postBuilder)
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void saveFeedBack_InvalidAndValidCategoryInBody_Status400() throws Exception {
        Category cat = new Category(null, "test");
        Category validCat = categoryRepository.save(cat);

        Category invalidCat = new Category(null, "notTest");
        Feedback feedback = new Feedback(
                null,
                "testText",
                "Testuser",
                "TestUser@gmail.com",
                Set.of(validCat, invalidCat)
        );

        JSONObject jsonObject = super.FeedbackToJson(feedback);

        MockHttpServletRequestBuilder postBuilder = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString());

        mvc.perform(postBuilder)
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

    }




    @Test
    public void getFeedbackList_GET_FeedbacksExact() throws Exception {
        List<Feedback> feedbacks = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Feedback f = new Feedback(null,
                    "text " + i,
                    "user " + i,
                    "user" + i + "@gmail.com",
                    new HashSet<>()
            );
            feedbacks.add(f);
        }
        ObjectMapper mapper = new ObjectMapper();
        for (Feedback feedback : feedbacks) {
            String jsonBody = super.FeedbackToJson(feedback).toString();
            MockHttpServletRequestBuilder postBuilder = MockMvcRequestBuilders.post(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonBody);

            mvc.perform(postBuilder)
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        }


        MockHttpServletRequestBuilder getBuilder = MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE);
        MvcResult mvcResult = mvc.perform(getBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        List<Feedback> feedbackList = JsonPath.parse(content).read("$.content");


        assertEquals(3, feedbackList.size());
    }


}