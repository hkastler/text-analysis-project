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
package com.hkstlr.text.opennlp.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 *
 * @author henry.kastler
 */
public class OpenNLPSentenceDetectorTest {
	
	SentenceDetectorManager cut;
	
	public OpenNLPSentenceDetectorTest() {
	}

	/**
	 * Test of init method, of class OpenNLPSentenceDetector.
	 */
	@Test
	public void testInit() {
		System.out.println("init");
		cut = new SentenceDetectorManager();
		assertNotNull(cut.getSentenceDetector());
		
		cut = new SentenceDetectorManager("/etc/opt/text-analysis-project/text-analysis/models/en-sent.bin");
		assertNotNull(cut.getSentenceDetector());
	}
	

	@Test
	public void testDetection() {
		System.out.println("getSentenceDetector().sentDetect");
		cut = new SentenceDetectorManager();
		String sentence = "Hi. How are you? Hello.";
		//Detecting the sentence 
		String sentences[] = cut.getSentenceDetector().sentDetect(sentence); 
		
		//Printing the sentences 
		for(String sent : sentences)
			System.out.println(sent);   
			
		
	}
        
        @Test
        public void testModelFile(){
            cut = new SentenceDetectorManager();
            String modelFilePath = "/dev/null/model.bin";
            cut.setModelFile(modelFilePath);
            assertEquals(modelFilePath, cut.getModelFile());
        }
        
}

