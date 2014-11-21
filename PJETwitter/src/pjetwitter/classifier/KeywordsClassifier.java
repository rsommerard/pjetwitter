package pjetwitter.classifier;

import helper.Globals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class KeywordsClassifier {	
    private List<String> positiveWords;
    private List<String> negativeWords;
    
    public KeywordsClassifier() {
    	this.positiveWords = new ArrayList<String>();
    	this.negativeWords = new ArrayList<String>();
    	
    	try
    	{
    		this.loadWords(Globals.POSITIVE_FILE_PATH);
        	this.loadWords(Globals.NEGATIVE_FILE_PATH);
    	}
    	catch(Exception e) { }
    }
    
    private void loadWords(String filePath) throws Exception {
    	BufferedReader br = new BufferedReader(new FileReader(filePath));
    	
    	String line = br.readLine();
    	
    	while(line != null) {
    		String[] words = line.split(", ");
    		
    		for(String word : words) {
    			if(filePath.contains("positive")) {
    				this.positiveWords.add(word.trim());
    			}
    			else {
    				this.negativeWords.add(word.trim());
    			}
    		}
    		
    		line = br.readLine();
    	}
    	
    	br.close();
    }
    
    public int classify(String tweet) {
    	String[] words = tweet.split(" ");
    	int count = 0;
    	
    	for(String word : words) {
    		if(this.positiveWords.contains(word)) {
    			count++;
    		}
    		else if(this.negativeWords.contains(word)) {
    			count--;
    		}
    	}
    	
    	if(count < 0) {
    		return Globals.NEGATIVE_TWEET;
    	}
    	
    	if(count > 0) {
    		return Globals.POSITIVE_TWEET;
    	}
    	
    	return Globals.NEUTRAL_TWEET;
    }
    
    public static void main(String[] args) {
    	String tweet1 = "This is a tweet.";
    	String tweet2 = "This is a second good tweet.";
    	String tweet3 = "This is a second bad tweet.";
    	
    	System.out.println("NON_ANNOTATED_TWEET = 1, NEGATIVE_TWEET = 0, NEUTRAL_TWEET = 2, POSITIVE_TWEET = 4");
    	System.out.println("tweet1: \"" + tweet1 + "\" = " + new KeywordsClassifier().classify(tweet1));
    	System.out.println("tweet2: \"" + tweet2 + "\" = " + new KeywordsClassifier().classify(tweet2));
    	System.out.println("tweet3: \"" + tweet3 + "\" = " + new KeywordsClassifier().classify(tweet3));
    }
}
