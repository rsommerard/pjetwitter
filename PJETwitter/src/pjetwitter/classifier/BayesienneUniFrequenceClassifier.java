package pjetwitter.classifier;

import helper.Globals;
import helper.Utils;

import java.util.ArrayList;
import java.util.List;

import pjetwitter.TweetInfo;

public class BayesienneUniFrequenceClassifier {
	private ArrayList<TweetInfo> positiveTweets;
	private ArrayList<TweetInfo> negativeTweets;
	private ArrayList<TweetInfo> neutralTweets;
	
	private ArrayList<String> positiveWords;
	private ArrayList<String> negativeWords;
	private ArrayList<String> neutralWords;
	
	public BayesienneUniFrequenceClassifier(List<TweetInfo> references) {
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
		
		ArrayList<String> wordsRefreshed = new ArrayList<String>();
		
		for(String str : words) {
			if(str.length() >= 3) {
				wordsRefreshed.add(str);
			}
		}
		
		for(String word : wordsRefreshed) {
			this.neutralWords.add(word);
		}
	}
	
	private void addWordsToNegativeList(String tweet) {
		String[] words = tweet.split(" ");
		
		ArrayList<String> wordsRefreshed = new ArrayList<String>();
		
		for(String str : words) {
			if(str.length() >= 3) {
				wordsRefreshed.add(str);
			}
		}
		
		for(String word : wordsRefreshed) {
			this.negativeWords.add(word);
		}
	}
	
	private void addWordsToPositiveList(String tweet) {
		String[] words = tweet.split(" ");
		
		ArrayList<String> wordsRefreshed = new ArrayList<String>();
		
		for(String str : words) {
			if(str.length() >= 3) {
				wordsRefreshed.add(str);
			}
		}
		
		for(String word : wordsRefreshed) {
			this.positiveWords.add(word);
		}
	}

	public int classify(String tweet) {
    	String[] words = tweet.split(" ");
    	
    	ArrayList<String> wordsRefreshed = new ArrayList<String>();
		
		for(String str : words) {
			if(str.length() >= 3) {
				wordsRefreshed.add(str);
			}
		}
    	
    	double probNegative = 0;
    	double probNeutral = 0;
    	double probPositive = 0;
    	double exp = 0;
    	
    	//Negative
    	probNegative = (this.negativeTweets.size() / this.getNbTweets());
    	for(String word : wordsRefreshed) {
    		exp = 0;
    		double num = 1;
    		double den = this.negativeWords.size() + this.getNbWords();
    		for(String negativeWord : this.negativeWords) {
    			if(word.equals(negativeWord)) {
    				for(String wordBis : wordsRefreshed) {
    					if(wordBis.equals(word)) {
    						exp++;
    					}
    				}
    				num++;
    			}
    		}
    		if(exp != 0) {
    			probNegative *= Math.pow((num/den), exp);
    		}
    		else {
    			probNegative *= (num/den);
    		}
    	}
    	
    	
    	//Neutral
    	probNeutral = (this.neutralTweets.size() / this.getNbTweets());
    	for(String word : wordsRefreshed) {
    		exp = 0;
    		double num = 1;
    		double den = this.neutralWords.size() + this.getNbWords();
    		for(String neutralWord : this.neutralWords) {
    			if(word.equals(neutralWord)) {
    				for(String wordBis : wordsRefreshed) {
    					if(wordBis.equals(word)) {
    						exp++;
    					}
    				}
    				num++;
    			}
    		}
    		if(exp != 0) {
    			probNeutral *= Math.pow((num/den), exp);
    		}
    		else {
    			probNeutral *= (num/den);
    		}
    	}

    	//Positive
    	probPositive = (this.positiveTweets.size() / this.getNbTweets());
    	for(String word : wordsRefreshed) {
    		exp = 0;
    		double num = 1;
    		double den = this.positiveWords.size() + this.getNbWords();
    		for(String positiveWord : this.positiveWords) {
    			if(word.equals(positiveWord)) {
    				for(String wordBis : wordsRefreshed) {
    					if(wordBis.equals(word)) {
    						exp++;
    					}
    				}
    				num++;
    			}
    		}
    		if(exp != 0) {
    			probPositive *= Math.pow((num/den), exp);
    		}
    		else {
    			probPositive *= (num/den);
    		}
    	}
    	
    	return Utils.probabilitiesToTweetPolarity(probNegative, probNeutral, probPositive, true);
    }
	
	private double getNbTweets() {
		return this.positiveTweets.size() + this.negativeTweets.size() + this.neutralTweets.size();
	}
	
	private double getNbWords() {
		return this.positiveWords.size() + this.negativeWords.size() + this.neutralWords.size();
	}
}
