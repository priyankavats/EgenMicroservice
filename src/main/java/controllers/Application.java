package controllers;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	final static Morphia morphia = new Morphia();
	private static Datastore datastore = null;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);        
    }
    
    public static Datastore getDatastore() {
    	return datastore;
    }

}