package com.example.generaltask.controller;

import com.example.generaltask.AbstractTest;
import com.example.generaltask.model.Category;

import com.example.generaltask.repository.CategoryRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@Transactional
public class CategoryControllerIntegrationTest extends AbstractTest {

    String uri = "/api/category";

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }


    @Test
    public void getAllCategories_GET_ListOfAllCategories() throws Exception {
        Category cat1 = new Category(null, "Cat1");
        Category cat2 = new Category(null, "Cat2");
        Category cat3 = new Category(null, "Cat3");

        categoryRepository.save(cat1);
        categoryRepository.save(cat2);
        categoryRepository.save(cat3);

        MockHttpServletRequestBuilder getBuilder = MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE);
        MvcResult mvcResult = mvc.perform(getBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        List<Category> categoryList = JsonPath.parse(content).read("$");


        assertEquals(3, categoryList.size());

    }

}
