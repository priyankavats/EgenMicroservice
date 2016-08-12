
package com.priyankavats.controllers;

import java.util.List;

import org.mongodb.morphia.query.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.priyankavats.dao.Alert;

@RestController
@RequestMapping(value = "/alerts")
public class AlertController {

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public List<Alert> read() {
		final Query<Alert> query = Application.getDatastore().createQuery(Alert.class);
		final List<Alert> alerts = query.asList();
		return alerts;
	}

	@RequestMapping(value = "/readByTimeRange/{startTime}/{endTime}", method = RequestMethod.GET)
	public List<Alert> readByTimeRange(@PathVariable long startTime, @PathVariable long endTime) {

		final Query<Alert> query = Application.getDatastore().createQuery(Alert.class);
		query.and(query.criteria("timeStamp").greaterThanOrEq(startTime),
				query.criteria("timeStamp").lessThanOrEq(endTime));
		final List<Alert> alerts = query.asList();
		return alerts;
	}

}
