package com.epam.ta.reportportal.ws.controller;

import com.epam.ta.reportportal.ws.BaseMvcTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author <a href="mailto:ihar_kahadouski@epam.com">Ihar Kahadouski</a>
 */
@Sql("classpath:db/activity/activity-fill.sql")
@Ignore
public class ActivityControllerTest extends BaseMvcTest {

	@Test
	public void getActivityByWrongTestItemId() throws Exception {
		mockMvc.perform(get(DEFAULT_PROJECT_BASE_URL + "/activity/1111").with(token(oAuthHelper.getDefaultToken())))
				.andExpect(status().is(404));
	}

	@Test
	public void getActivityByWrongProjectName() throws Exception {
		mockMvc.perform(get("/wrong_project/activity/1").with(token(oAuthHelper.getDefaultToken()))).andExpect(status().is(403));
	}

	@Test
	public void getTestItemActivitiesByWrongTestItem() throws Exception {
		mockMvc.perform(get(DEFAULT_PROJECT_BASE_URL + "/activity/item/1111").with(token(oAuthHelper.getDefaultToken())))
				.andExpect(status().is(404));
	}

	@Test
	public void getTestItemActivitiesPositive() throws Exception {
		mockMvc.perform(get(DEFAULT_PROJECT_BASE_URL + "/activity/item/1").with(token(oAuthHelper.getDefaultToken())))
				.andExpect(status().is(200));
	}

	@Test
	public void getActivityPositive() throws Exception {
		mockMvc.perform(get(DEFAULT_PROJECT_BASE_URL + "/activity/1").with(token(oAuthHelper.getDefaultToken())))
				.andExpect(status().is(200));
	}

	@Test
	public void getActivitiesForProject() throws Exception {
		mockMvc.perform(get(DEFAULT_PROJECT_BASE_URL + "/activity").with(token(oAuthHelper.getDefaultToken()))).andExpect(status().is(200));
	}
}