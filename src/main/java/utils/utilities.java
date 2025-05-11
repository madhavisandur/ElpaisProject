package utils;

import java.util.HashMap;
import com.google.cloud.translate.Translate;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;



public class utilities {
	
	//Initializing Log4j
	private static final Logger logger = LogManager.getLogger(utilities.class);

	
	//Function to check duplicate words which are repeated more than twice
	// Input: Translated Text
	//Return value None
	//Output: prints the word with number of occurrences
	public void DuplicateWordCount(String text) {
		try  {



			// Step 2: Split the string into words
			String[] words = text.toLowerCase().split("\\s+");

			// Step 3: Store word counts
			Map<String, Integer> wordCountMap = new HashMap<>();
			for (String word : words) {
				wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
			}

			// Step 4: Identify and count duplicate words
			int duplicateWordCount = 0;
			logger.info("Duplicate words which are repeated more than 2 times:");
			for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
				if (entry.getValue() > 2) {
					duplicateWordCount++;
					logger.info("Word: "+entry.getKey() + ": " + "Count: "+entry.getValue());
				}
			}

			// Step 5: Display the number of duplicate words
			logger.info("Number of duplicate words: " + duplicateWordCount);
			logger.info(" ");

		}
		finally {
			
		}
	}
	
	//Google API Translate 
		 public static String translateText(String text, String sourceLanguage, String targetLanguage) {
		        // Creates a translation client
		        Translate translate = TranslateOptions.getDefaultInstance().getService();

		        // Translates the text
		        Translation translation = translate.translate(
		                text,
		                Translate.TranslateOption.sourceLanguage(sourceLanguage),
		                Translate.TranslateOption.targetLanguage(targetLanguage)
		        );

		        String translatedText = translation.getTranslatedText();
		        logger.info("Original Text: " + text);
		        logger.info("Translated Text: " + translatedText);
		        
		        logger.info("------------------------------------------");

		        return translatedText;
		    }

	
	
	
	}
	
	

