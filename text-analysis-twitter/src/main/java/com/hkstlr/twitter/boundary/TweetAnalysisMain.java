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

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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

	public static void main(String[] args) throws IOException, TwitterException {

		TweetAnalyzer ta = new TweetAnalyzer("/etc/config/twitter_sentiment_training_data.train",
				"/etc/config/twitter_sa_project_model.bin");

		String queryTerms = "chicago pizza";
		int numberOfTweetsToGet = 100;
		boolean writeToDesktop = false;

		if (args.length > 0) {
			queryTerms = Arrays.toString(args);
		}

		Object[] saResultObj = (Object[]) ta.getSAAnalysis(queryTerms, numberOfTweetsToGet);
		Object saResult = saResultObj[0];
		String probResults = (String) saResultObj[1];
		if(writeToDesktop) {
			ta.writeTweets(getFilePath("TweetAnalysis_",".csv"), probResults);
		}
		LOG.log(LOG_LEVEL, "{0}", new Object[] { probResults });
		LOG.log(LOG_LEVEL, "{0}", new Object[] { saResult.toString() });
		
		
		
		Map<?, ?> results = (HashMap<?, ?>) saResult;
		String message = String.format(queryTerms +"%nResults%n{0}%% positive%n{1}%% negative%n{2}%% neutral");
		
		String strMsg = new MessageFormat(message).format(
						new Object[] { 
						(double) (int) results.get("positive") / (int) results.get("total") * 100,
						(double) (int) results.get("negative") / (int) results.get("total") * 100,
						(double) (int) results.get("neutral") / (int) results.get("total") * 100 
				});
		
		LOG.log(LOG_LEVEL, strMsg);
				
		if(writeToDesktop) {
			ta.writeTweets(getFilePath("TweetAnalysisResults_",".txt"), strMsg);
		}
	}
	private static String getFilePath(String fileNameBase, String fileExtension) {
		StringBuilder filePath = new StringBuilder(System.getProperty("user.home"))
				.append(File.separator)
				.append("Desktop")
				.append(File.separator)
				.append(fileNameBase)
				.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))
				.append(fileExtension);
		return filePath.toString();
		
	}

}
