package com.priyankavats.models;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity("metrics")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Metric {
	
	@Id
    private long timeStamp;
	
    private int value;

    public Metric() {
    }

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Metric [timeStamp=" + timeStamp + ", value=" + value + "]";
	}

    
}