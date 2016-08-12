package com.priyankavats.dao;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.priyankavats.controllers.Application;
import com.priyankavats.models.Alert;

public class AlertDao {
	
	private Datastore datastore;
	
	public AlertDao() {
		datastore = Application.getDatastore();
	}
	
	public void create(Alert alert) {
		datastore.save(alert);
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
