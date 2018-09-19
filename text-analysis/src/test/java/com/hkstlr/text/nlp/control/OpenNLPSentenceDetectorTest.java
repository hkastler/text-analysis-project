/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * see https://github.com/technobium/opennlp-categorizer
 * 
 */
package com.hkstlr.text.nlp.control;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author henry.kastler
 */
public class OpenNLPSentenceDetectorTest {
	
	OpenNLPSentenceDetector cut;
	
	public OpenNLPSentenceDetectorTest() {
	}

	/**
	 * Test of init method, of class OpenNLPSentenceDetector.
	 */
	@Test
	public void testInit() {
		System.out.println("init");
		cut = new OpenNLPSentenceDetector();
		assertNotNull(cut.getSentenceDetector());
		
		cut = new OpenNLPSentenceDetector("/etc/config/models/en-sent.bin");
		assertNotNull(cut.getSentenceDetector());
	}
	

	@Test
	public void testDetection() {
		System.out.println("getSentenceDetector().sentDetect");
		cut = new OpenNLPSentenceDetector();
		String sentence = "Hi. How are you? Hello.";
		//Detecting the sentence 
		String sentences[] = cut.getSentenceDetector().sentDetect(sentence); 
		
		//Printing the sentences 
		for(String sent : sentences)
			System.out.println(sent);   
			
		//Getting the probabilities of the last decoded sequence   
		double[] probs = cut.getSentenceDetector().getSentenceProbabilities(); 
		
		System.out.println("  "); 
		  
		for(int i = 0; i<probs.length; i++) 
			System.out.println(probs[i]); 
		} 
}

