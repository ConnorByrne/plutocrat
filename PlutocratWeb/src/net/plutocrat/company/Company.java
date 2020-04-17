package net.plutocrat.company;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;



@Entity
public class Company {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String companyName;
	private String tickerSymbol;
//	@OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
//	private List<Sentiment> sentimentData = new ArrayList<Sentiment>();

	public Company() {
		super();
	}

	

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTickerSymbol() {
		return tickerSymbol;
	}



	public void setTickerSymbol(String tickerSymbol) {
		this.tickerSymbol = tickerSymbol;
	}



//	public List<Sentiment> getSentimentData() {
//		return sentimentData;
//	}
//
//
//
//	public void setSentimentData(List<Sentiment> sentimentData) {
//		this.sentimentData = sentimentData;
//	}
//	
//	public void addSentimentalData(Sentiment s1) {
//		this.sentimentData.add(s1);
//	}



	public Company(String companyName,String tickerSymbol) {
		super();
		this.companyName = companyName;
		this.tickerSymbol = tickerSymbol;
		
		
	}



//	public Company( String companyName, String tickerSymbol, List<Sentiment> sentimentData) {
//		super();
//		this.companyName = companyName;
//		this.tickerSymbol = tickerSymbol;
//		this.sentimentData = sentimentData;
//	}
	
	

	
	
	

	


}
