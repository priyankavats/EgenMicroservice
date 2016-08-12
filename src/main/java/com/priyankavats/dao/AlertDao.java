package com.priyankavats.dao;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.stereotype.Component;

import com.priyankavats.controllers.Application;
import com.priyankavats.models.Alert;
import com.priyankavats.models.Metric;

@Component
public class AlertDao {
	
	private Datastore datastore;
	
	public AlertDao() {
		datastore = Application.getDatastore();
	}
	
	public void create(Metric metric) {
        Application.getDatastore().save(metric);
	}
	
	public List<Alert> readAll() {
		final Query<Alert> query = datastore.createQuery(Alert.class);
		final List<Alert> alert = query.asList();
		return alert;
	}
	
	public List<Alert> readByTimeRange(long startTime, long endTime) {

		final Query<Alert> query = datastore.createQuery(Alert.class);
		query.and(query.criteria("timeStamp").greaterThanOrEq(startTime),
				query.criteria("timeStamp").lessThanOrEq(endTime));
		final List<Alert> alerts = query.asList();
		return alerts;
	}

}
