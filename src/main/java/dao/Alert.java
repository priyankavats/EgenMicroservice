package dao;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity("alerts")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Alert {
	
	@Id
    private long timeStamp;
    private int value;
    private String type;
    
    public Alert() {
    }

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Alert [timeStamp=" + timeStamp + ", value=" + value + "]";
	}

    
}