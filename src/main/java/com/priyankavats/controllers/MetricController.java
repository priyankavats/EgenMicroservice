package com.priyankavats.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.priyankavats.dao.MetricDao;
import com.priyankavats.models.Metric;

@RestController
@RequestMapping(value="/metrics")
public class MetricController {
	
	private MetricDao metricDao = new MetricDao();
	
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(@RequestBody Metric metric) {
    	metricDao.create(metric);
        Application.getRulesEngine().fireRules();
    }
    
    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public List<Metric> read() {
    	try {
    		return metricDao.readAll();
    	} catch (Exception e) {
    		return new ArrayList<Metric>();
    	}
    }
    
    @RequestMapping(value = "/readByTimeRange/{startTime}/{endTime}", method = RequestMethod.GET)
    public List<Metric> readByTimeRange(@PathVariable long startTime, @PathVariable long endTime) {
    	try {
    		return metricDao.readByTimeRange(startTime, endTime);
    	} catch (Exception e) {
    		return new ArrayList<Metric>();
    	}    		  
    }
        

}