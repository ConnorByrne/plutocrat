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

	public Main() {
		Document doc;
		
		try {
			doc= Jsoup.connect("https://www.nasdaq.com/market-activity/quotes/Nasdaq-100-Index-Components").get();
			   List<Company> companyList = new ArrayList<Company>();
	            
	
	    		Elements allRows = doc.getElementsByTag("tr");
	            
	            for(Element row: allRows) {
	            	try {
	            	Elements rowData = row.getAllElements();
	            	
	            	if(!rowData.get(1).text().equalsIgnoreCase("Company Name")) {
	            		Company c1 = new Company(rowData.get(1).text(),rowData.get(4).text());
	            		companyList.add(c1);
	            		//companyDAO.persistCompany(c1);
	            	}
	            	}catch(Exception e) {
	            		System.out.println("Error makeing company object");
	            	}
	            }
	            StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
	            for(Company company: companyList) {
	            	try {
	            		doc = Jsoup.connect("https://www.businessinsider.com/s?q="+company.getTickerSymbol()+"&vertical=&author=&contributed=1&sort=date&r=US&IR=T").get();
	            		String title = doc.title();
	            		System.out.println(title);
	            		Elements headlines = doc.getElementsByTag("a");
	            		for(Element headline: headlines) {
	            			try {
	            				String text = headline.text();
	                			CoreDocument coreDocument = new CoreDocument(text);
	                    		System.out.println(text);
	                    		stanfordCoreNLP.annotate(coreDocument);
	                    		List<CoreSentence> coreSentenceList = coreDocument.sentences();
//	                    		double positive = 0.0;
//	                    		double negative = 0.0;
//	                    		for(CoreSentence sentence: coreSentenceList) {
//	                    			try {
//	                    				String sentiment = sentence.sentiment();
//	                        			System.out.println(sentence+" = "+sentiment);
//	                        			if(sentiment.equalsIgnoreCase("positive")) {
//	                        				positive = positive+1;
//	                        			}
//	                        			else if(sentiment.equalsIgnoreCase("negative")) {
//	                        				negative= negative+1;
//	                        			}
//	                        			}catch(Exception e) {
//	                        				e.printStackTrace();
//	                        			
//	                    				}
//	                    		}
//	                    		String average = ((positive/negative)*(100/1)+"");
	                    		Sentiment sentiment = new Sentiment(text,coreSentenceList.get(0).sentiment());
	                    		company.addSentimentalData(sentiment);
	            				
	            			}catch(Exception e) {
	            				
	            			}
	            			
	            		}
	            		companyDAO.persistCompany(company);
	            		
	            	}catch(Exception e) {
	            		e.printStackTrace();
	            	}
	            }
	            
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
		
		
        	
//        Document twitter;
//        Document doc;
//        try {
//
//            doc = Jsoup.connect("https://www.nasdaq.com/market-activity/quotes/Nasdaq-100-Index-Components").get();
//
//            // get title of the page
//            //String title = doc.title();
//            List<Company> companyList = new ArrayList<Company>();
//            
//
//    		Elements allRows = doc.getElementsByTag("tr");
//            
//            for(Element row: allRows) {
//            	Elements rowData = row.getAllElements();
//            	
//            	if(!rowData.get(1).text().equalsIgnoreCase("Company Name")) {
//            		Company c1 = new Company(rowData.get(1).text(),rowData.get(4).text());
//            		companyList.add(c1);
//            		//companyDAO.persistCompany(c1);
//            	}
//            	
//            }
//            
//            //companyList = companyDAO.getAllCompanies();
//            StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
//            
////            for(Company c: companyList) {
////            	//System.out.println(c.toString());
////            	String companyName = c.getCompanyName();
////            	String tickerSymbol = c.getTickerSymbol();
////            	if(companyName.contains(" ")) {
////            		companyName = companyName.replaceAll(" ", "%20");
////            	}
//            for(Company c1: companyList) {
//            	try {
//            	twitter = Jsoup.connect("https://www.businessinsider.com/s?q="+c1.getTickerSymbol()+"&vertical=&author=&contributed=1&sort=date&r=US&IR=T").get();
//            	String title =twitter.title();
//            	System.out.println(title);
//            	Elements headlines = twitter.getElementsByTag("a");
//            	for(Element headline: headlines) {
//            		try {
//            			
//            		//System.out.println(headline.attr("href"));
////            		Document article = Jsoup.connect(headline.attr("href")).get();
////            		Elements paragraphs = article.getElementsByTag("p");
////            		for(Element paragraph: paragraphs) {
//            			String text = headline.text();
//            			CoreDocument coreDocument = new CoreDocument(text);
//                		System.out.println(text);
//                		stanfordCoreNLP.annotate(coreDocument);
//                		List<CoreSentence> coreSentenceList = coreDocument.sentences();
//                		
//                		double positive=0;
//                		double negative=0;
//                		for(CoreSentence s1 : coreSentenceList) {
//                			try {
//                			String sentiment = s1.sentiment();
//                			System.out.println(s1+" = "+sentiment);
//                			if(sentiment.equalsIgnoreCase("positive")) {
//                				positive++;
//                			}
//                			else if(sentiment.equalsIgnoreCase("negative")) {
//                				negative++;
//                			}
//                			}catch(Exception e) {
//                				e.printStackTrace();
//                			}
//                			
//                		}
//                		
//            		//}
//            		double average = (positive/negative)*(100/1);
//            		Sentiment sentiment = new Sentiment(text,average);
//            		c1.addSentimentalData(sentiment);
//            		}catch(Exception e) {
//            			e.printStackTrace();
//            		}
//            	}
////            	String companyName = c1.getCompanyName();
////            	if(companyName.contains(" ")) {
////            		companyName = companyName.replaceAll(" ", "%20");
////            	}
////            	twitter = Jsoup.connect("https://twitter.com/search?q="+companyName+"&src=typed_query&f=live").get();
////            	title =twitter.title();
////            	System.out.println(title);
////            	//System.out.println(twitter.outerHtml());
////            	headlines = twitter.getElementsByAttribute("a");
////            	for(Element div: headlines) {
//////            			System.out.println(div.text());
//////            			Sentiment sentiment = new Sentiment();
////            		String text = div.text();
////            		CoreDocument coreDocument = new CoreDocument(text);
////            		
////            		stanfordCoreNLP.annotate(coreDocument);
////            		List<CoreSentence> coreSentenceList = coreDocument.sentences();
////            		
////            		double positive=0;
////            		double negative=0;
////            		for(CoreSentence s1 : coreSentenceList) {
////            			String sentiment = s1.sentiment();
////            			System.out.println(s1.text()+" "+s1.sentiment());
////            			//System.out.println(s1+" = "+sentiment);
////            			if(sentiment.equalsIgnoreCase("positive")) {
////            				positive++;
////            			}
////            			else if(sentiment.equalsIgnoreCase("negative")) {
////            				negative++;
////            			}
////            			
////            		}
////            		double average = (positive/negative)*(100/1);
////            		Sentiment sentiment = new Sentiment(text,average);
////            		sentimentDAO.persistSentiment(sentiment);
////            		c1.addSentimentalData(sentiment);
////            	}
//            	companyDAO.persistCompany(c1);
//            	}catch(Exception e) {
//            		e.printStackTrace();
//            	}
//            }
//            	
//            	
////            	
//            //}
//            	
//            
//            
//            /*
//            //RTE ISEQ TABLE
//           //Get all the TABLE ROW elements
//           Elements allRows = doc.getElementsByTag("tr");
//           
//           //For each row
//           for(Element row :allRows) {
//        	   //get all the TABLE DATA cells in that row
//        	   Elements rowData = row.getAllElements();
//        	   //Starting at the second cell, get the next 2 cells
//        	   for(int i = 1; i <3 ; i ++) {
//        		   //get the cell at index 
//        		   Element cell  = rowData.get(i);
//        		   //print the value of the cell
//        		   System.out.println(cell.text());
//        	   }
//           }
//            
//            */
//            
//            /*
//           
//           
//           
//            
//            //Get company names from RTE ISEQ market page
//            Elements tableRows = doc.getElementsByTag("tr");
//            
//            for(Element row : tableRows) {
//            	Elements tableData = row.getAllElements();
//            	
//            	for(int i = 1; i<3 ; i++) {
//            		Element data = tableData.get(i);
//            		System.out.println(data.text());
//            	}
//            	
//            	
//            }*/
//            
//            
//            //Dailymail headlines
//            // get all links
////            Elements links = doc.getElementsByTag("a");
////            for (Element link : links) {
////
////                // get the value from itemprop attribute
////                if(link.attr("itemprop").equals("url")){
////                	if(link.text().contains("Boris"))
////                	System.out.println("Text : " + link.text());
////                	
////                }
////            }
//            
//
//            /*
//            //Ticketmaster span tags
//            Elements spans = doc.select("span");
//            for (Element tag : spans) {
//                	System.out.println("Text : " + tag.text());
//                }
//            */
//            
//            
//            //play around with tags after viewing source
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//}  
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new Main();
	}


}