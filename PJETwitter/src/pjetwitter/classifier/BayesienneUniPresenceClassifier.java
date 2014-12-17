package pjetwitter.classifier;

import helper.Globals;
import helper.Utils;

import java.util.ArrayList;
import java.util.List;

import pjetwitter.TweetInfo;

public class BayesienneUniPresenceClassifier {
	private ArrayList<TweetInfo> positiveTweets;
	private ArrayList<TweetInfo> negativeTweets;
	private ArrayList<TweetInfo> neutralTweets;
	
	private ArrayList<String> positiveWords;
	private ArrayList<String> negativeWords;
	private ArrayList<String> neutralWords;
	
	public BayesienneUniPresenceClassifier(List<TweetInfo> references) {
		List<TweetInfo> listTweets = references;
		
		this.positiveTweets = new ArrayList<TweetInfo>();
		this.negativeTweets = new ArrayList<TweetInfo>();
		this.neutralTweets = new ArrayList<TweetInfo>();
		
		this.positiveWords = new ArrayList<String>();
		this.negativeWords = new ArrayList<String>();
		this.neutralWords = new ArrayList<String>();
		
		for(TweetInfo tweet : listTweets) {
			switch(tweet.getTweetPolarity()) {
				case Globals.NEUTRAL_TWEET:
					this.neutralTweets.add(tweet);
					this.addWordsToNeutralList(tweet.getTweetText());
					break;
				case Globals.POSITIVE_TWEET:
					this.positiveTweets.add(tweet);
					this.addWordsToPositiveList(tweet.getTweetText());
					break;
				case Globals.NEGATIVE_TWEET:
					this.negativeTweets.add(tweet);
					this.addWordsToNegativeList(tweet.getTweetText());
					break;
				default:
					break;
			}
		}
	}
	
	private void addWordsToNeutralList(String tweet) {
		String[] words = tweet.split(" ");
		
		for(String str : words)
		{
			if(str.length() >= 3) {
				this.neutralWords.add(str);
			}
		}
	}
	
	private void addWordsToNegativeList(String tweet) {
		String[] words = tweet.split(" ");
		
		for(String str : words)
		{
			if(str.length() >= 3) {
				this.negativeWords.add(str);
			}
		}
	}
	
	private void addWordsToPositiveList(String tweet) {
		String[] words = tweet.split(" ");
		
		for(String str : words)
		{
			if(str.length() >= 3) {
				this.positiveWords.add(str);
			}
		}
	}

	public int classify(String tweet) {
    	String[] words = tweet.split(" ");
    	
    	ArrayList<String> wordsRefreshed = new ArrayList<String>();
		
		for(String str : words)
		{
			if(str.length() >= 3) {
				wordsRefreshed.add(str);
			}
		}
    	
    	double probNegative = 0;
    	double probNeutral = 0;
    	double probPositive = 0;
    	
    	
    	//Negative
    	probNegative = (this.negativeTweets.size() / this.getNbTweets());
    	for(String word : wordsRefreshed) {
    		double num = 1;
    		double den = this.negativeWords.size() + this.getNbWords();
    		for(String negativeWord : this.negativeWords) {
    			if(word.equals(negativeWord)) {
    				num++;
    			}
    		}
    		probNegative *= (num/den);
    	}
    	
    	//Neutral
    	probNeutral = (this.neutralTweets.size() / this.getNbTweets());
    	for(String word : wordsRefreshed) {
    		double num = 1;
    		double den = this.neutralWords.size() + this.getNbWords();
    		for(String neutralWord : this.neutralWords) {
    			if(word.equals(neutralWord)) {
    				num++;
    			}
    		}
    		probNeutral *= (num/den);
    	}
    	
    	//Positive
    	probPositive = (this.positiveTweets.size() / this.getNbTweets());
    	for(String word : wordsRefreshed) {
    		double num = 1;
    		double den = this.positiveWords.size() + this.getNbWords();
    		for(String positiveWord : this.positiveWords) {
    			if(word.equals(positiveWord)) {
    				num++;
    			}
    		}
    		probPositive *= (num/den);
    	}
    	
    	
    	return Utils.probabilitiesToTweetPolarity(probNegative, probNeutral, probPositive, false);
		
    }
	
	private double getNbTweets() {
		return this.positiveTweets.size() + this.negativeTweets.size() + this.neutralTweets.size();
	}
	
	private double getNbWords() {
		return this.positiveWords.size() + this.negativeWords.size() + this.neutralWords.size();
	}
}
