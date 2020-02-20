package entities;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
@NamedQueries({
	@NamedQuery(name = "Sentiment.findAll", query = "select o from Sentiment o")
})

@Entity
public class Sentiment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String data;
	private String sentimentAverage;
	public Sentiment() {
		
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getSentimentAverage() {
		return sentimentAverage;
	}

	public void setSentimentAverage(String sentimentAverage) {
		this.sentimentAverage = sentimentAverage;
	}

	public Sentiment( String data, String average) {
		super();
		this.data = data;
		this.sentimentAverage = average;
	}

	@Override
	public String toString() {
		return "Sentiment [id=" + id + ", data=" + data + ", sentimentAverage=" + sentimentAverage + "]";
	}

	

	
	

}
