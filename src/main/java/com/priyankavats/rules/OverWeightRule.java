package com.priyankavats.rules;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

import com.priyankavats.dao.AlertDao;
import com.priyankavats.dao.MetricDao;
import com.priyankavats.models.Alert;
import com.priyankavats.models.Metric;

/**
 * The Class OverWeightRule checks the first metric in MongoDB sorted
 * by timestamp and the most recent one when the rule is fired. If 
 * the recent timestamp is greater than 10% then it creates a new alert
 * using the EasyRules API
 * 
 * @author Priyanka Vats
 */
@Rule(name = "OverWeightRule")
public class OverWeightRule {
	
	/** The metric dao. */
	private MetricDao metricDao;
	
	/** The alert dao. */
	private AlertDao alertDao;

	/** The first metric. */
	private Metric firstMetric = null;
	
	/** The current metric. */
	private Metric currentMetric = null;
	
	/**
	 * Instantiates a new over weight rule.
	 *
	 * @param metricDao the metric dao
	 * @param alertDao the alert dao
	 */
	public OverWeightRule(MetricDao metricDao, AlertDao alertDao) {
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

		return currentMetric.getValue() > ((float) firstMetric.getValue() * 1.1);
	}

	/**
	 * EasyRule when condition. Creates an alert object and saves it
	 * to MongoDB.
	 */
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