package com.priyankavats.controllers;

import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;

import org.easyrules.api.RulesEngine;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mongodb.MongoClient;
import com.priyankavats.dao.AlertDao;
import com.priyankavats.dao.MetricDao;
import com.priyankavats.rules.OverWeightRule;
import com.priyankavats.rules.UnderWeightRule;

@SpringBootApplication
public class Application {

	final static Morphia morphia = new Morphia();
	private static Datastore datastore = null;
	private static RulesEngine rulesEngine;

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

	public static Datastore getDatastore() {
		return datastore;
	}
	
	public static RulesEngine getRulesEngine() {
		return rulesEngine;
	}

}