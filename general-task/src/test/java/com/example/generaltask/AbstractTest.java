package com.example.generaltask;

import com.example.generaltask.model.Category;
import com.example.generaltask.model.Feedback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public abstract class AbstractTest {

	protected MockMvc mvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	protected void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	protected JSONObject FeedbackToJson(Feedback feedback) throws JSONException {

		List<JSONObject> categories = new ArrayList<>();
		if (feedback.getCategories() != null) {
			for (Category category : feedback.getCategories()) {
				JSONObject jsonCat = new JSONObject();
				jsonCat.put("type", category.getType());
				categories.add(jsonCat);
			}
		}

		JSONArray ja = new JSONArray(categories);

		JSONObject jo = new JSONObject();
		jo.put("text", feedback.getText());
		jo.put("username", feedback.getUsername());
		jo.put("email", feedback.getEmail());
		jo.put("categories", ja);

		return jo;
	}

}
