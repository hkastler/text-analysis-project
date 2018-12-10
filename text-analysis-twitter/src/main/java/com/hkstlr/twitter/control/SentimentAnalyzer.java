package com.hkstlr.twitter.control;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.hkstlr.text.opennlp.control.DocumentCategorizerManager;

public class SentimentAnalyzer {

	DocumentCategorizerManager cat;
    	
    String delimiter = "~";
    String newLine = System.getProperty("line.separator");
    String[] headers  = {};
    String[] categories = {};
    Map<String, Integer> categoryCount = new LinkedHashMap<>();
    StringBuilder dsv = new StringBuilder();
    Object[] analysis = new Object[2];

    public SentimentAnalyzer() {
        super();
    }

    public SentimentAnalyzer(DocumentCategorizerManager cat) {
        super();
        setCat(cat);
    }
    
    void init() {
        
        int numOfCats = cat.getDoccat().getNumberOfCategories();
        categories = new String[numOfCats];
        for(int i=0; i < numOfCats; i++) {
        	categories[i] = cat.getDoccat().getCategory(i);
        	categoryCount.put(categories[i], 0);
        }
        Arrays.sort(categories, Collections.reverseOrder());
        
        String[] headersStart = { "sentiment", "tweet" };
        headers = Stream.concat(Arrays.stream(headersStart), Arrays.stream(categories))
                .toArray(String[]::new);
        String dsvTemplate = getDsvTemplate(headers.length, delimiter, newLine);
        dsv = new StringBuilder(MessageFormat.format(dsvTemplate, (Object[]) headers));
    }
    
    
	public void analyzeText(String text) {
		
    	// the probabilities of the categories
        Map<String, Double> probMap = cat.getDoccat().scoreMap(cat.getTokenize(text));
        // the category, in this use case, sentiment
        String sentiment = probMap.entrySet().stream().max(Map.Entry.comparingByValue())
        		.map(Map.Entry::getKey)
                .orElse("");
        
        categoryCountAdd(sentiment);
        
        text = text.replaceAll(delimiter, "&tilde;").replace("\"", "&quot;");
        dsvAdd(sentiment, text, probMap);
    }
	
	public void categoryCountAdd(String category) {
		categoryCount.put(category,  categoryCount.get(category) + 1);
	}
	
	public void dsvAdd(String sentiment, String text, Map<String, Double> probMap) {
		String dsvTemplate = getDsvTemplate(headers.length, delimiter, newLine);
        String dsvRow = MessageFormat.format(dsvTemplate, sentiment, text, 
        		probMap.get(categories[0]),probMap.get(categories[1]), probMap.get(categories[2]));
        dsv.append(dsvRow);
	}
	
    public Object[] getSentimentAnalysis() {
        analysis[0] = results();
        analysis[1] = dsv.toString();
        return analysis;
    }
    
    Map<String, Integer> results(){
    	Map<String, Integer> results = new LinkedHashMap<>();
    	Integer total = categoryCount.values().stream().reduce(0, Integer::sum);
    	
        results.put("total", total);
        Arrays.stream(categories).forEach(c -> results.put(c, categoryCount.get(c)));
        return results;
    }

    public String getDsvTemplate(int cols, String delimiter, String newLine) {
        StringBuilder msgSb = new StringBuilder();
        for (int x = 0; x <= cols - 1; x++) {
            msgSb.append("{").append(Integer.toString(x)).append("}");
            if (x < cols - 1) {
                msgSb.append(delimiter);
            }

        }
        msgSb.append(newLine);

        return msgSb.toString();
    }

	public DocumentCategorizerManager getCat() {
		return cat;
	}

	public void setCat(DocumentCategorizerManager cat) {
		this.cat = cat;
	}

}