package com.priyankavats.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.priyankavats.dao.MetricDao;
import com.priyankavats.models.Metric;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MetricControllerTest {

	@InjectMocks
	private MetricController controller = new MetricController();

	@Mock
	MetricDao metricDaoMock;

	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getEmptyListWhenMetricDaoThrowsException() throws Exception {
		Mockito.when(metricDaoMock.readAll()).thenThrow(NullPointerException.class);
		mvc.perform(MockMvcRequestBuilders.get("/metrics/read").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(equalTo("[]")));
	}

	@Test
	public void getValidListWhenMetricDaoReturnsMetrics() throws Exception {
		Metric m = new Metric();
		m.setTimeStamp(131331331);
		m.setValue(150);
		List<Metric> mList = new ArrayList<Metric>();
		mList.add(m);

		Mockito.when(metricDaoMock.readAll()).thenReturn(mList);
		mvc.perform(MockMvcRequestBuilders.get("/metrics/read").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("[{\"timeStamp\":131331331,\"value\":150}]")));
	}

	@Test
	public void getValidListOfTimeRangeWhenMetricDaoReturnsMetrics() throws Exception {
		Metric m = new Metric();
		m.setTimeStamp(1471002713317l);
		m.setValue(150);

		Metric m2 = new Metric();
		m2.setTimeStamp(1471002713327l);
		m2.setValue(150);

		List<Metric> mList = new ArrayList<Metric>();
		mList.add(m);
		mList.add(m2);

		Mockito.when(metricDaoMock.readByTimeRange(1471002713316l, 1471003519705l)).thenReturn(mList);
		mvc.perform(MockMvcRequestBuilders.get("/metrics/readByTimeRange/1471002713316/1471003519705")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string(equalTo(
						"[{\"timeStamp\":1471002713317,\"value\":150},{\"timeStamp\":1471002713327,\"value\":150}]")));
	}
}