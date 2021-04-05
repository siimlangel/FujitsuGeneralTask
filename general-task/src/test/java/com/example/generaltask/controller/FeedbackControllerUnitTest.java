package com.example.generaltask.controller;

import com.example.generaltask.AbstractTest;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FeedbackControllerUnitTest extends AbstractTest {

    String uri = "/api/feedback";

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void saveFeedBack_EmptyBodyPOST_Status400() throws Exception {
        MockHttpServletRequestBuilder postBuilder = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(postBuilder)
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void saveFeedBack_OnlyUsernameInBodyPOST_Status400() throws Exception {

        JSONObject jo = new JSONObject();

        jo.put("username", "username123");



        MockHttpServletRequestBuilder postBuilder = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jo.toString());

        mvc.perform(postBuilder)
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void saveFeedBack_OnlyValidEmailInBodyPOST_Status400() throws Exception {

        JSONObject jo = new JSONObject();

        jo.put("email", "user@gmail.com");



        MockHttpServletRequestBuilder postBuilder = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jo.toString());

        mvc.perform(postBuilder)
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void saveFeedBack_OnlyTextInBodyPOST_Status400() throws Exception {

        JSONObject jo = new JSONObject();

        jo.put("text", "TestText");



        MockHttpServletRequestBuilder postBuilder = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jo.toString());

        mvc.perform(postBuilder)
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void saveFeedBack_InvalidEmailOtherFieldsCorrectBodyPOST_Status400() throws Exception {

        JSONObject jo = new JSONObject();

        jo.put("text", "TestText");
        jo.put("username", "user1");
        jo.put("email", "incorrect@@.com");



        MockHttpServletRequestBuilder postBuilder = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jo.toString());

        mvc.perform(postBuilder)
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

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
