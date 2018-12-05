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
    String trainingDataFile = "/etc/opt/text-analysis-project/text-analysis-twitter/twitter_sentiment_training_data.train";
    String modelOutFile = "/etc/opt/text-analysis-project/text-analysis-twitter/twitter_sa_model.bin";
	
    String delimiter = "~";
    String newLine = System.getProperty("line.separator");
    String[] headers;
    String[] categories;
    Map<String, Integer> categoryCount = new LinkedHashMap<>();
    StringBuilder dsv;
    Object[] analysis = new Object[2];

    public SentimentAnalyzer() {
        super();
    }
    
    void init() {
        Config analyzerConfig = new Config("tweetAnalyzer.properties");
        setTrainingDataFile(analyzerConfig.getProps().getProperty("trainingDataFilePath", getTrainingDataFile()));
        setModelOutFile(analyzerConfig.getProps().getProperty("modelOutFile", getModelOutFile()));
        
        setCat();

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
    
    public void setCat() {
        if (null == this.cat) {
            this.cat = new DocumentCategorizerManager(getTrainingDataFile(), getModelOutFile());
        }

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

	public String getTrainingDataFile() {
		return trainingDataFile;
	}

	public void setTrainingDataFile(String trainingDataFile) {
		this.trainingDataFile = trainingDataFile;
	}

	public String getModelOutFile() {
		return modelOutFile;
	}

	public void setModelOutFile(String modelOutFile) {
		this.modelOutFile = modelOutFile;
	}
    
    
}