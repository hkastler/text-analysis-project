/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.hkstlr.twitter.boundary;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hkstlr.twitter.control.FileWR;
import com.hkstlr.twitter.control.TweetAnalyzer;

import twitter4j.TwitterException;

/**
 * @author milind see
 *         https://milindjagre.co/2016/08/26/twitter-sentiment-analysis-using-opennlp-java-api/
 *         see also https://github.com/technobium/opennlp-categorizer
 * @author henry.kastler
 */
public class TweetAnalysisMain {

	private static final Logger LOG = Logger.getLogger(TweetAnalysisMain.class.getName());
	private static final Level LOG_LEVEL = Level.INFO;

	private TweetAnalysisMain() {
		super();
	}

	public static void main(String[] args) throws IOException, TwitterException {
		
		TweetAnalyzer ta = new TweetAnalyzer(
				TweetAnalyzer.getTrainingDataFilepath(),
				"");
		
		
		String queryTerms = "chicago pizza";
		int numberOfTweetsToGet = 100;
		boolean writeToDesktop = true;

		if (args.length > 0) {
			queryTerms = Arrays.toString(args);
		}

		Object[] saResultObj = ta.getSentimentAnalysis(queryTerms, numberOfTweetsToGet);
		Map<String, Integer> results = (LinkedHashMap<String, Integer>) saResultObj[0];
		String probResults = (String) saResultObj[1];
		
		LOG.log(LOG_LEVEL, "{0}", new Object[] { probResults });
		LOG.log(LOG_LEVEL, "{0}", new Object[] { results.toString() });

		double total = (double) (Integer) results.get("total");
		results.remove("total");

		String lineSep = System.getProperty("line.separator");
		String openBracket = "{";
		String closeBracket = "}";
		String perc = "% ";
		StringBuilder messageSb = new StringBuilder(queryTerms)
		.append(lineSep)		
		.append("Results").append(lineSep);

		int i = 0;
		for (String key : results.keySet()) {
			messageSb.append(openBracket);
			messageSb.append(i);
			messageSb.append(closeBracket);
			messageSb.append(perc).append(key).append(lineSep);
			i++;
		}
		
		String message = messageSb.toString();		
		
		String strMsg = new MessageFormat(message).format(new Object[] { 
				(Integer) results.get("positive") / total * 100, (Integer) results.get("negative") / total * 100,
				(Integer) results.get("neutral") / total * 100 });
		
		LOG.log(LOG_LEVEL, strMsg);
		strMsg += results.toString();
		if (writeToDesktop) {
			writeTweets(FileWR.getDesktopFilePath("TweetAnalysis_" + queryTerms + "_", ".csv"), probResults);
			writeTweets(FileWR.getDesktopFilePath("TweetAnalysisResults_" + queryTerms + "_", ".txt"), strMsg);
		}
		
	}
	
	public static void writeTweets(String filePath, String tweets) {
		FileWR writer = new FileWR(filePath);

		try {
			writer.writeFile(tweets);
		} catch (IOException e) {
			LOG.log(LOG_LEVEL, "", e);
		}
	}

}
