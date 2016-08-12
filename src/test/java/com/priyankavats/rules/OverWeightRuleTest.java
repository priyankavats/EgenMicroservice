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

public class OverWeightRuleTest {
	
	@Mock MetricDao metricDaoMock;
	@Mock AlertDao alertDaoMock;
	
	private OverWeightRule overWeightRule;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		overWeightRule = new OverWeightRule(metricDaoMock, alertDaoMock);
	}
	
	@Test
	public void testWhenFirstAndCurrentMetricAreNullAndReturnFalse() {
		Metric metric = null;
		
		Mockito.when(metricDaoMock.getMetric("timeStamp")).thenReturn(metric);
		Mockito.when(metricDaoMock.getMetric("-timeStamp")).thenReturn(metric);
		
		Assert.assertFalse(overWeightRule.when());
	}
	
	@Test
	public void testWhenFirstAndCurrentMetricAreLessThan10PercentAndReturnFalse() {
		Metric metricFirst = new Metric();
		metricFirst.setValue(30);
		Metric metricCurrent = new Metric();
		metricCurrent.setValue(31);
		
		Mockito.when(metricDaoMock.getMetric("timeStamp")).thenReturn(metricFirst);
		Mockito.when(metricDaoMock.getMetric("-timeStamp")).thenReturn(metricCurrent);
		
		Assert.assertFalse(overWeightRule.when());
	}
	
	@Test
	public void testWhenFirstAndCurrentMetricAreGreaterThan10PercentAndReturnTrue() {
		Metric metricFirst = new Metric();
		metricFirst.setValue(30);
		Metric metricCurrent = new Metric();
		metricCurrent.setValue(35);
		
		Mockito.when(metricDaoMock.getMetric("timeStamp")).thenReturn(metricFirst);
		Mockito.when(metricDaoMock.getMetric("-timeStamp")).thenReturn(metricCurrent);
		
		Assert.assertTrue(overWeightRule.when());
	}

}
