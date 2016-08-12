package controllers;

import java.util.List;

import org.mongodb.morphia.query.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dao.Metric;

@RestController
@RequestMapping(value="/metrics")
public class MetricController {

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(@RequestBody Metric metric) {
        System.out.println(metric);        
        Application.getDatastore().save(metric);        
    }
    
    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public List<Metric> read() {
    	
    	final Query<Metric> query = Application.getDatastore().createQuery(Metric.class);
    	final List<Metric> metrics = query.asList();
    	return metrics;  
    }
    
    @RequestMapping(value = "/readByTimeRange/{startTime}/{endTime}", method = RequestMethod.GET)
    public List<Metric> readByTimeRange(@PathVariable long startTime, @PathVariable long endTime) {
    	
    	final Query<Metric> query = Application.getDatastore().createQuery(Metric.class);
		query
			.and(
				query.criteria("timeStamp").greaterThanOrEq(startTime),
				query.criteria("timeStamp").lessThanOrEq(endTime)
				);
    	final List<Metric> metrics = query.asList();
    	return metrics;  
    }
        

}