package com.priyankavats.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.priyankavats.dao.AlertDao;
import com.priyankavats.models.Alert;

public class AlertControllerTest {

	@InjectMocks
	private AlertController controller = new AlertController();

	@Mock
	AlertDao alertDaoMock;

	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getEmptyListWhenMetricDaoThrowsException() throws Exception {
		Mockito.when(alertDaoMock.readAll()).thenThrow(NullPointerException.class);
		mvc.perform(MockMvcRequestBuilders.get("/alerts/read").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(equalTo("[]")));
	}

	@Test
	public void getValidListWhenMetricDaoReturnsMetrics() throws Exception {
		Alert a = new Alert();
		a.setTimeStamp(131331331);
		a.setValue(150);
		a.setType("Over weight");
		List<Alert> aList = new ArrayList<Alert>();
		aList.add(a);

		Mockito.when(alertDaoMock.readAll()).thenReturn(aList);
		mvc.perform(MockMvcRequestBuilders.get("/alerts/read").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content()
						.string(equalTo("[{\"timeStamp\":131331331,\"value\":150,\"type\":\"Over weight\"}]")));
	}

	@Test
	public void getValidListOfTimeRangeWhenMetricDaoReturnsMetrics() throws Exception {
		Alert a = new Alert();
		a.setTimeStamp(1471002713317l);
		a.setValue(150);
		a.setType("Over weight");

		Alert a2 = new Alert();
		a2.setTimeStamp(1471002713327l);
		a2.setValue(150);
		a2.setType("Under weight");

		List<Alert> aList = new ArrayList<Alert>();
		aList.add(a);
		aList.add(a2);

		Mockito.when(alertDaoMock.readByTimeRange(1471002713316l, 1471003519705l)).thenReturn(aList);
		mvc.perform(MockMvcRequestBuilders.get("/alerts/readByTimeRange/1471002713316/1471003519705")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string(equalTo(
						"[{\"timeStamp\":1471002713317,\"value\":150,\"type\":\"Over weight\"},{\"timeStamp\":1471002713327,\"value\":150,\"type\":\"Under weight\"}]")));
	}

}
