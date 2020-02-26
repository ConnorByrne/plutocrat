package main;

import java.io.IOException;


import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import dao.CompanyDAO;
import dao.SentimentDAO;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import entities.Company;
import entities.Sentiment;

public class Main {
	CompanyDAO companyDAO = new CompanyDAO();
	SentimentDAO sentimentDAO = new SentimentDAO();
	Main(){

		Document doc;
		ArrayList<Company> companies = new ArrayList<Company>();
		try {
			doc= Jsoup.connect("https://www.slickcharts.com/nasdaq100").get();
			Elements allRows = doc.getElementsByTag("tr");
			
			for(Element row: allRows) {
				Elements rowData = row.getAllElements();
				if(!rowData.get(2).text().equalsIgnoreCase("Company")) {
					Company c1 = new Company();
					c1.setCompanyName(rowData.get(2).text());
					c1.setTickerSymbol(rowData.get(4).text());
					companies.add(c1);
					//companyDAO.persistCompany(c1);
				}
			}
			for(Company company: companies) {
				System.out.println(company.getCompanyName());
				doc = Jsoup.connect("https://www.businessinsider.com/s?q="+company.getTickerSymbol()+"&vertical=&author=&contributed=1&sort=date&r=US&IR=T").get();
				Elements allHeadlines = doc.getElementsByTag("h3");
				for(Element headline:allHeadlines) {
					Elements headlines = headline.getElementsByTag("a");
					Elements time = doc.getElementsByClass("river-post__date");
					int num = 1;
					for(Element hl:headlines) {
						Sentiment s1 = new Sentiment();
						s1.setData(hl.text());
						System.out.println(headline.text());
						try {
						Element t1 = time.get(num);
						num++;
						s1.setDate(normaliseDate(t1.text()));
						company.addSentimentalData(s1);
						
						//System.out.println(normaliseDate(t1.text()));
						}catch(Exception e) {
							
						}
					}
				}
				companyDAO.persistCompany(company);
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
		
	}
	
//	CompanyDAO companyDAO = new CompanyDAO();
//	SentimentDAO sentimentDAO = new SentimentDAO();
//
//	public Main() {
//		Document doc;
//		
//		try {
//			doc= Jsoup.connect("https://www.nasdaq.com/market-activity/quotes/Nasdaq-100-Index-Components").get();
//			   List<Company> companyList = new ArrayList<Company>();
//	            
//	
//	    		Elements allRows = doc.getElementsByTag("tr");
//	            
//	            for(Element row: allRows) {
//	            	try {
//	            	Elements rowData = row.getAllElements();
//	            	
//	            	if(!rowData.get(1).text().equalsIgnoreCase("Company Name")) {
//	            		Company c1 = new Company(rowData.get(1).text(),rowData.get(4).text());
//	            		companyList.add(c1);
//	            		//companyDAO.persistCompany(c1);
//	            	}
//	            	}catch(Exception e) {
//	            		System.out.println("Error makeing company object");
//	            	}
//	            }
//	            StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
//	            for(Company company: companyList) {
//	            	try {
//	            		doc = Jsoup.connect("https://www.businessinsider.com/s?q="+company.getTickerSymbol()+"&vertical=&author=&contributed=1&sort=date&r=US&IR=T").get();
//	            		String title = doc.title();
//	            		System.out.println(title);
//	            		Elements headlines = doc.getElementsByTag("a");
//	            		for(Element headline: headlines) {
//	            			try {
//	            				String text = headline.text();
//	                			CoreDocument coreDocument = new CoreDocument(text);
//	                    		System.out.println(text);
//	                    		stanfordCoreNLP.annotate(coreDocument);
//	                    		List<CoreSentence> coreSentenceList = coreDocument.sentences();
////	                    		double positive = 0.0;
////	                    		double negative = 0.0;
////	                    		for(CoreSentence sentence: coreSentenceList) {
////	                    			try {
////	                    				String sentiment = sentence.sentiment();
////	                        			System.out.println(sentence+" = "+sentiment);
////	                        			if(sentiment.equalsIgnoreCase("positive")) {
////	                        				positive = positive+1;
////	                        			}
////	                        			else if(sentiment.equalsIgnoreCase("negative")) {
////	                        				negative= negative+1;
////	                        			}
////	                        			}catch(Exception e) {
////	                        				e.printStackTrace();
////	                        			
////	                    				}
////	                    		}
////	                    		String average = ((positive/negative)*(100/1)+"");
//	                    		Sentiment sentiment = new Sentiment(text,coreSentenceList.get(0).sentiment());
//	                    		company.addSentimentalData(sentiment);
//	            				
//	            			}catch(Exception e) {
//	            				
//	            			}
//	            			
//	            		}
//	            		companyDAO.persistCompany(company);
//	            		
//	            	}catch(Exception e) {
//	            		e.printStackTrace();
//	            	}
//	            }
//	            
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
		
		
        	

	

	private String normaliseDate(String text) {
		// TODO Auto-generated method stub
		String year;
		String month = "00";
		String day;
		year = text.substring(9, 13);
		day = text.substring(5,7);
		switch((text.substring(0, 3)+"").toLowerCase()) {
		case "jan":
			month="01";
			break;
		case "feb":
			month="02";
			break;
		case "mar":
			month="03";
			break;
		case "apr":
			month="04";
			break;
		case "may":
			month="05";
			break;
		case "jun":
			month="06";
			break;
		case "jul":
			month="07";
			break;
		case "aug":
			month="08";
			break;
		case "sep":
			month="09";
			break;
		case "oct":
			month="10";
			break;
		case "nov":
			month="11";
			break;
		case "dec":
			month="12";
			break;
		
		}
		return year+"-"+month+"-"+day;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new Main();
	}


}