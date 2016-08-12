package com.priyankavats.rules;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

import com.priyankavats.dao.AlertDao;
import com.priyankavats.dao.MetricDao;
import com.priyankavats.models.Alert;
import com.priyankavats.models.Metric;

@Rule(name = "OverWeightRule")
public class OverWeightRule {
	
	private MetricDao metricDao;
	private AlertDao alertDao;

	private Metric firstMetric = null;
	private Metric currentMetric = null;
	
	public OverWeightRule() {
		metricDao = new MetricDao();
		alertDao = new AlertDao();
	}

	@Condition
	public boolean when() {

		// only need to get the first metric once
		if (firstMetric == null) {
			firstMetric = metricDao.getMetric("timeStamp");
		}

		currentMetric = metricDao.getMetric("-timeStamp");
		
		if (firstMetric == null || currentMetric == null) {
			return false;
		}

		return currentMetric.getValue() > ((float) firstMetric.getValue() * 1.1);
	}

	@Action
	public void then() {

		Alert newAlert = new Alert();
		newAlert.setTimeStamp(currentMetric.getTimeStamp());
		newAlert.setType("Over weight");
		newAlert.setValue(currentMetric.getValue());

		// Creating new alert
		alertDao.create(newAlert);

	}
}