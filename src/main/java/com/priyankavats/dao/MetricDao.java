package com.priyankavats.dao;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.priyankavats.controllers.Application;
import com.priyankavats.models.Metric;

public class MetricDao {

	private Datastore datastore;
	
	public MetricDao() {
		datastore = Application.getDatastore();
	}

	public void create(Metric metric) {
		datastore.save(metric);
	}

	public List<Metric> readAll() {
		final Query<Metric> query = datastore.createQuery(Metric.class);
		final List<Metric> metrics = query.asList();
		return metrics;
	}

	public List<Metric> readByTimeRange(long startTime, long endTime) {

		final Query<Metric> query = datastore.createQuery(Metric.class);
		query.and(query.criteria("timeStamp").greaterThanOrEq(startTime),
				query.criteria("timeStamp").lessThanOrEq(endTime));
		final List<Metric> metrics = query.asList();
		return metrics;
	}
	
	public Metric getMetric(String sort) {
		final Query<Metric> query = datastore.createQuery(Metric.class);
		return query.order(sort).get();
	}

}
