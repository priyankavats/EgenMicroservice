package rules;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;
import org.mongodb.morphia.query.Query;

import controllers.Application;
import dao.Alert;
import dao.Metric;

@Rule(name = "UnderWeightRule")
public class UnderWeightRule {
	
	private Metric currentMetric;
	private Metric firstMetric;

	@Condition
	public boolean when() {
	
		final Query<Metric> query = Application.getDatastore().createQuery(Metric.class);
		currentMetric = query.order("-timeStamp").get();
		firstMetric = query.order("timeStamp").get();

		float difference = (float)firstMetric.getValue() - currentMetric.getValue();
		
		return (difference > (float) firstMetric.getValue()/10);
	}

	@Action
	public void then() {
		
		Alert newAlert = new Alert();
		newAlert.setTimeStamp(currentMetric.getTimeStamp());
		newAlert.setType("Under weight");
		newAlert.setValue(currentMetric.getValue());
		
	}
}
