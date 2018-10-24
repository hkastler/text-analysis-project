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
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hkstlr.twitter.control.FileWR;
import com.hkstlr.twitter.control.TweetAnalyzer;


/**
 *  ref. https://milindjagre.co/2016/08/26/twitter-sentiment-analysis-using-opennlp-java-api/
 *  ref. also https://github.com/technobium/opennlp-categorizer
 * @author henry.kastler
 */
public class TweetAnalysisMain {

	private static final Logger LOG = Logger.getLogger(TweetAnalysisMain.class.getName());
	private static final Level LOG_LEVEL = Level.INFO;

	private TweetAnalysisMain() {
		super();
	}

	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {

		TweetAnalyzer ta = new TweetAnalyzer(TweetAnalyzer.getTrainingDataFilepath(), "");

		String queryTerms = "chicago pizza";
		int numberOfTweetsToGet = 100;
		String writeToDesktop = "no";

		if (args.length > 0) {
			queryTerms = Arrays.toString(args);
		}

		Object[] saResultObj = ta.getSentimentAnalysis(queryTerms, numberOfTweetsToGet);
		Map<String, Integer> results = (LinkedHashMap<String, Integer>) saResultObj[0];
		String probResults = (String) saResultObj[1];

		LOG.log(LOG_LEVEL, "{0}", new Object[] { probResults });
		LOG.log(LOG_LEVEL, "{0}", new Object[] { results.toString() });

		String totalKey = "total";
		double total = (double) (Integer) results.get(totalKey);

		String lineSep = System.getProperty("line.separator");
		String perc = "% ";
		StringBuilder messageSb = new StringBuilder(queryTerms).append(lineSep).append("Results").append(lineSep);

		for (Map.Entry<String, Integer> entry : results.entrySet()) {
			if (!totalKey.equals(entry.getKey())) {
				int percentResult = (int) Math.round(entry.getValue() / total * 100);
				messageSb.append(percentResult);
				messageSb.append(perc).append(entry.getKey()).append(lineSep);
			}
		}

		String strMsg = messageSb.toString();

		LOG.log(LOG_LEVEL, messageSb.toString());
		strMsg += results.toString();
		if ("yes".equals(writeToDesktop)) {
			writeTweets(FileWR.getDesktopFilePath("TweetAnalysis_" + queryTerms + "_", ".csv"), probResults);
			writeTweets(FileWR.getDesktopFilePath("TweetAnalysisResults_" + queryTerms + "_", ".txt"), strMsg);
		}

	}

	public static void writeTweets(String filePath, String tweets) 
                throws IOException, NullPointerException {
		
		FileWR writer = new FileWR(filePath);
		writer.writeFile(tweets);
		
	}

}
