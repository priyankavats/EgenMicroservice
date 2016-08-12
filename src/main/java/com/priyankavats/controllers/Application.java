package com.priyankavats.controllers;

import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;

import org.easyrules.api.RulesEngine;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mongodb.MongoClient;
import com.priyankavats.dao.AlertDao;
import com.priyankavats.dao.MetricDao;
import com.priyankavats.rules.OverWeightRule;
import com.priyankavats.rules.UnderWeightRule;

/**
 * SpringBootApplication which initiliazes our beans as well as the
 * DataStore. It also initializes the RulesEngine.
 * 
 * @author Priyanka Vats
 */
@SpringBootApplication
public class Application {

	/** The Constant morphia. */
	final static Morphia morphia = new Morphia();
	
	/** The datastore. */
	private static Datastore datastore = null;
	
	/** The rules engine. */
	private static RulesEngine rulesEngine;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		morphia.mapPackage("com.priyankavats.models");
		datastore = morphia.createDatastore(new MongoClient(), "EgenMicroservice");
		datastore.ensureIndexes();
		rulesEngine = aNewRulesEngine().build();
		MetricDao metricDao = new MetricDao();
		AlertDao alertDao = new AlertDao();
        rulesEngine.registerRule(new UnderWeightRule(metricDao, alertDao));
        rulesEngine.registerRule(new OverWeightRule(metricDao, alertDao));
		SpringApplication.run(Application.class, args);

	}
	
	/**
	 * Metric dao.
	 *
	 * @return the metric dao
	 */
	@Bean
	public MetricDao metricDao() {
	   final MetricDao metricDao = new MetricDao();
	   return metricDao;
	}
	
	/**
	 * Alert dao.
	 *
	 * @return the alert dao
	 */
	@Bean
	public AlertDao alertDao() {
	   final AlertDao alertDao = new AlertDao();
	   return alertDao;
	}

	/**
	 * Gets the datastore.
	 *
	 * @return the datastore
	 */
	public static Datastore getDatastore() {
		return datastore;
	}
	
	/**
	 * Gets the rules engine.
	 *
	 * @return the rules engine
	 */
	public static RulesEngine getRulesEngine() {
		return rulesEngine;
	}

}