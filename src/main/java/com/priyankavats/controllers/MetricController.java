package com.priyankavats.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.priyankavats.dao.MetricDao;
import com.priyankavats.models.Metric;

/**
 * The Class MetricController. Used for our api endpoint /metrics
 * It allows these endpoints: 
 * 
 * POST /metrics/create
 * GET /metrics/read
 * GET /metrics/readByTimeRange/{startTime}/{endTime}
 * 
 * @author Priyanka Vats
 */
@RestController
@RequestMapping(value="/metrics")
public class MetricController {
	
	@Autowired
	MetricDao metricDao;
	
    /**
     * Consumes the sensor and creates the metrics.
     *
     * @param metric the metric json object
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(@RequestBody Metric metric) {
    	metricDao.create(metric);
        Application.getRulesEngine().fireRules();
    }
    
    /**
     * Gets all the metrics available and sends the entire list of
     * metrics as a JSONArray
     *
     * @return json list of metrics
     */
    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public List<Metric> read() {
    	try {
    		return metricDao.readAll();
    	} catch (Exception e) {
    		return new ArrayList<Metric>();
    	}
    }
    
    /**
     * Gets all the metrics between a given start and endtime
     *
     * @param startTime the start timestamp
     * @param endTime the end timestamp
     * @return json list of metrics
     */
    @RequestMapping(value = "/readByTimeRange/{startTime}/{endTime}", method = RequestMethod.GET)
    public List<Metric> readByTimeRange(@PathVariable long startTime, @PathVariable long endTime) {
    	try {
    		return metricDao.readByTimeRange(startTime, endTime);
    	} catch (Exception e) {
    		return new ArrayList<Metric>();
    	}    		  
    }
        

}