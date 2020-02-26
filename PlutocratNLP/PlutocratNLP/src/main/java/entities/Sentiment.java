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
	private String date;
	public Sentiment() {
		
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Sentiment(int id, String data, String date) {
		super();
		this.id = id;
		this.data = data;
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Sentiment [id=" + id + ", data=" + data + ", date=" + date + "]";
	}
	
	

	

	
	

	
	

}
