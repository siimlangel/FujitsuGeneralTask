package com.example.generaltask.controller;

import com.example.generaltask.AbstractTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryControllerUnitTest extends AbstractTest {

    String uri = "/api/category";

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void unallowedMethod_GET_Status200() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

        int status = mvcResult.getResponse().getStatus();

        assertEquals(200, status);
    }

    @Test
    public void unallowedMethod_POST_Status405() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)).andReturn();

        int status = mvcResult.getResponse().getStatus();

        assertEquals(405, status);
    }

    @Test
    public void unallowedMethod_PUT_Status405() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)).andReturn();

        int status = mvcResult.getResponse().getStatus();

        assertEquals(405, status);
    }

    @Test
    public void unallowedMethod_DELETE_Status405() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();

        int status = mvcResult.getResponse().getStatus();

        assertEquals(405, status);
    }
}
