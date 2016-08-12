package com.priyankavats.rules;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.priyankavats.dao.AlertDao;
import com.priyankavats.dao.MetricDao;
import com.priyankavats.models.Metric;

public class UnderWeightRuleTest {
	
	@Mock
	MetricDao metricDaoMock;
	@Mock
	AlertDao alertDaoMock;

	private UnderWeightRule underWeightRule;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		underWeightRule = new UnderWeightRule(metricDaoMock, alertDaoMock);
	}

	@Test
	public void testWhenFirstAndCurrentMetricAreNullAndReturnFalse() {
		Metric metric = null;

		Mockito.when(metricDaoMock.getMetric("timeStamp")).thenReturn(metric);
		Mockito.when(metricDaoMock.getMetric("-timeStamp")).thenReturn(metric);

		Assert.assertFalse(underWeightRule.when());
	}

	@Test
	public void testWhenFirstAndCurrentMetricAreLessThan10PercentAndReturnFalse() {
		Metric metricFirst = new Metric();
		metricFirst.setValue(30);
		Metric metricCurrent = new Metric();
		metricCurrent.setValue(29);

		Mockito.when(metricDaoMock.getMetric("timeStamp")).thenReturn(metricFirst);
		Mockito.when(metricDaoMock.getMetric("-timeStamp")).thenReturn(metricCurrent);

		Assert.assertFalse(underWeightRule.when());
	}

	@Test
	public void testWhenFirstAndCurrentMetricAreGreaterThan10PercentAndReturnTruee() {
		Metric metricFirst = new Metric();
		metricFirst.setValue(30);
		Metric metricCurrent = new Metric();
		metricCurrent.setValue(26);

		Mockito.when(metricDaoMock.getMetric("timeStamp")).thenReturn(metricFirst);
		Mockito.when(metricDaoMock.getMetric("-timeStamp")).thenReturn(metricCurrent);

		Assert.assertTrue(underWeightRule.when());
	}
}
