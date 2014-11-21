package pjetwitter.classifier;

import helper.Globals;

import java.util.ArrayList;

import pjetwitter.TweetInfo;

public class BayesienneClassifier {
	private ArrayList<TweetInfo> positiveTweets;
	private ArrayList<TweetInfo> negativeTweets;
	private ArrayList<TweetInfo> neutralTweets;
	
	private ArrayList<String> positiveWords;
	private ArrayList<String> negativeWords;
	private ArrayList<String> neutralWords;
	
	public BayesienneClassifier() {
		ArrayList<TweetInfo> listTweets = new ArrayList<TweetInfo>(helper.csv.CsvSingletons.getInstance().referenceCsv.readAll());
		
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
		for(String word : words) {
			this.neutralWords.add(word);
		}
	}
	
	private void addWordsToNegativeList(String tweet) {
		String[] words = tweet.split(" ");
		for(String word : words) {
			this.negativeWords.add(word);
		}
	}
	
	private void addWordsToPositiveList(String tweet) {
		String[] words = tweet.split(" ");
		for(String word : words) {
			this.positiveWords.add(word);
		}
	}

	public int classify(String tweet) {
    	String[] words = tweet.split(" ");
    	double probNegative = 0;
    	double probNeutral = 0;
    	double probPositive = 0;
    	
    	
    	//Negative
    	probNegative = (this.negativeTweets.size() / this.getNbTweets());
    	for(String word : words) {
    		double num = 1;
    		double den = this.negativeTweets.size() + this.getNbWords();
    		for(String negativeWord : this.negativeWords) {
    			if(word.equals(negativeWord)) {
    				num++;
    			}
    		}
    		probNegative *= (num/den);
    	}
    	
    	//Neutral
    	probNeutral = (this.neutralTweets.size() / this.getNbTweets());
    	for(String word : words) {
    		double num = 1;
    		double den = this.neutralTweets.size() + this.getNbWords();
    		for(String neutralWord : this.neutralWords) {
    			if(word.equals(neutralWord)) {
    				num++;
    			}
    		}
    		probNeutral *= (num/den);
    	}
    	
    	//Positive
    	probPositive = (this.positiveTweets.size() / this.getNbTweets());
    	for(String word : words) {
    		double num = 1;
    		double den = this.positiveTweets.size() + this.getNbWords();
    		for(String positiveWord : this.positiveWords) {
    			if(word.equals(positiveWord)) {
    				num++;
    			}
    		}
    		probPositive *= (num/den);
    	}
    	
    	System.out.println("probPositive: " + probPositive);
    	System.out.println("probNeutral: " + probNeutral);
    	System.out.println("probNegative: " + probNegative);
    	
    	if(probNegative > probNeutral) {
    		if(probNegative > probPositive) {
    			return Globals.NEGATIVE_TWEET;
    		}
    	}
    	
    	if(probNeutral > probNegative) {
    		if(probNeutral > probPositive) {
    			return Globals.NEUTRAL_TWEET;
    		}
    	}
    	
    	if(probPositive > probNegative) {
    		if(probPositive > probNeutral) {
    			return Globals.POSITIVE_TWEET;
    		}
    	}
    	
    	return Globals.NON_ANNOTATED_TWEET;
    }
    
    public static void main(String[] args) {
    	String tweet1 = "This is a tweet.";
    	String tweet2 = "This is a second good tweet.";
    	String tweet3 = "This is a second bad tweet.";
    	
    	System.out.println("NON_ANNOTATED_TWEET = 1, NEGATIVE_TWEET = 0, NEUTRAL_TWEET = 2, POSITIVE_TWEET = 4");
    	System.out.println("tweet1: \"" + tweet1 + "\" = " + new BayesienneClassifier().classify(tweet1));
    	System.out.println("tweet2: \"" + tweet2 + "\" = " + new BayesienneClassifier().classify(tweet2));
    	System.out.println("tweet3: \"" + tweet3 + "\" = " + new BayesienneClassifier().classify(tweet3));
    }
	
	private double getNbTweets() {
		return this.positiveTweets.size() + this.negativeTweets.size() + this.neutralTweets.size();
	}
	
	private double getNbWords() {
		return this.positiveWords.size() + this.negativeWords.size() + this.neutralWords.size();
	}
}
