package com.priyankavats.rules;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

import com.priyankavats.dao.AlertDao;
import com.priyankavats.dao.MetricDao;
import com.priyankavats.models.Alert;
import com.priyankavats.models.Metric;

/**
 * The Class UnderWeightRule checks the first metric in MongoDB sorted
 * by timestamp and the most recent one when the rule is fired. If 
 * the recent timestamp is less than 10% then it creates a new alert
 * using the EasyRules API
 * 
 * @author Priyanka Vats
 */
@Rule(name = "UnderWeightRule")
public class UnderWeightRule {
	
	private MetricDao metricDao;
	
	private AlertDao alertDao;
	
	private Metric firstMetric;
	
	private Metric currentMetric;
	
	/**
	 * Instantiates a new under weight rule.
	 *
	 * @param metricDao the metric dao
	 * @param alertDao the alert dao
	 */
	public UnderWeightRule(MetricDao metricDao, AlertDao alertDao) {
		this.metricDao = metricDao;
		this.alertDao = alertDao;
	}

	/**
	 * EasyRule when condition. Validates the metrics objects and then
	 * does the comparison for percentage.
	 *
	 * @return true, if successful
	 */
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

		return currentMetric.getValue() < ((float) firstMetric.getValue() * 0.9);
	}

	/**
	 * EasyRule when condition. Creates an alert object and saves it
	 * to MongoDB.
	 */
	@Action
	public void then() {

		Alert newAlert = new Alert();
		newAlert.setTimeStamp(currentMetric.getTimeStamp());
		newAlert.setType("Under weight");
		newAlert.setValue(currentMetric.getValue());

		alertDao.create(newAlert);

	}
}
