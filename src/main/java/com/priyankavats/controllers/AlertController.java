package com.priyankavats.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.priyankavats.dao.AlertDao;
import com.priyankavats.models.Alert;

/**
 * The Class AlertController. Used for our api endpoint /alerts It allows these
 * endpoints:
 * 
 * GET /alerts/read 
 * GET /alerts/readByTimeRange/{startTime}/{endTime}
 * 
 * @author Priyanka Vats
 */
@RestController
@RequestMapping(value = "/alerts")
public class AlertController {

	@Autowired
	AlertDao alertDao;

	/**
	 * Gets all the alerts available and sends the entire list of metrics as a
	 * JSONArray
	 *
	 * @return json list of metrics
	 */
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public List<Alert> read() {
		try {
			return alertDao.readAll();
		} catch (Exception e) {
			return new ArrayList<Alert>();
		}
	}

	/**
	 * Gets all the alerts between a given start and endtime
	 *
	 * @param startTime the start timestamp
	 * @param endTime the end timestamp
	 * @return json list of metrics
	 */
	@RequestMapping(value = "/readByTimeRange/{startTime}/{endTime}", method = RequestMethod.GET)
	public List<Alert> readByTimeRange(@PathVariable long startTime, @PathVariable long endTime) {
		try {
			return alertDao.readByTimeRange(startTime, endTime);
		} catch (Exception e) {
			return new ArrayList<Alert>();
		}
	}

}
