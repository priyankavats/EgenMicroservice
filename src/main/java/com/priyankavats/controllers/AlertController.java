
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

@RestController
@RequestMapping(value = "/alerts")
public class AlertController {

	@Autowired
	AlertDao alertDao;

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public List<Alert> read() {
		try {
			return alertDao.readAll();
		} catch (Exception e) {
			return new ArrayList<Alert>();
		}
	}

	@RequestMapping(value = "/readByTimeRange/{startTime}/{endTime}", method = RequestMethod.GET)
	public List<Alert> readByTimeRange(@PathVariable long startTime, @PathVariable long endTime) {
		try {
			return alertDao.readByTimeRange(startTime, endTime);
		} catch (Exception e) {
			return new ArrayList<Alert>();
		}
	}

}
