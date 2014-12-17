package pjetwitter.classifier;

import helper.Globals;
import helper.Utils;

import java.util.ArrayList;
import java.util.List;

import pjetwitter.TweetInfo;

public class BayesienneUniBiPresenceClassifier {
	private ArrayList<TweetInfo> uniPositiveTweets;
	private ArrayList<TweetInfo> uniNegativeTweets;
	private ArrayList<TweetInfo> uniNeutralTweets;
	private ArrayList<TweetInfo> biPositiveTweets;
	private ArrayList<TweetInfo> biNegativeTweets;
	private ArrayList<TweetInfo> biNeutralTweets;
	
	private ArrayList<String> uniPositiveWords;
	private ArrayList<String> uniNegativeWords;
	private ArrayList<String> uniNeutralWords;
	private ArrayList<String> biPositiveWords;
	private ArrayList<String> biNegativeWords;
	private ArrayList<String> biNeutralWords;
	
	public BayesienneUniBiPresenceClassifier(List<TweetInfo> references) {
		List<TweetInfo> listTweets = references;
		
		this.uniPositiveTweets = new ArrayList<TweetInfo>();
		this.uniNegativeTweets = new ArrayList<TweetInfo>();
		this.uniNeutralTweets = new ArrayList<TweetInfo>();
		this.biPositiveTweets = new ArrayList<TweetInfo>();
		this.biNegativeTweets = new ArrayList<TweetInfo>();
		this.biNeutralTweets = new ArrayList<TweetInfo>();
		
		this.uniPositiveWords = new ArrayList<String>();
		this.uniNegativeWords = new ArrayList<String>();
		this.uniNeutralWords = new ArrayList<String>();
		this.biPositiveWords = new ArrayList<String>();
		this.biNegativeWords = new ArrayList<String>();
		this.biNeutralWords = new ArrayList<String>();
		
		for(TweetInfo tweet : listTweets) {
			switch(tweet.getTweetPolarity()) {
				case Globals.NEUTRAL_TWEET:
					this.uniNeutralTweets.add(tweet);
					this.biNeutralTweets.add(tweet);
					this.addWordsToNeutralUniList(tweet.getTweetText());
					this.addWordsToNeutralBiList(tweet.getTweetText());
					break;
				case Globals.POSITIVE_TWEET:
					this.uniPositiveTweets.add(tweet);
					this.biPositiveTweets.add(tweet);
					this.addWordsToPositiveUniList(tweet.getTweetText());
					this.addWordsToPositiveBiList(tweet.getTweetText());
					break;
				case Globals.NEGATIVE_TWEET:
					this.uniNegativeTweets.add(tweet);
					this.biNegativeTweets.add(tweet);
					this.addWordsToNegativeUniList(tweet.getTweetText());
					this.addWordsToNegativeBiList(tweet.getTweetText());
					break;
				default:
					break;
			}
		}
	}
	
	private void addWordsToNeutralUniList(String tweet) {
		String[] words = tweet.split(" ");
		
		ArrayList<String> wordsRefreshed = new ArrayList<String>();
		
		for(String str : words) {
			if(str.length() >= 3) {
				wordsRefreshed.add(str);
			}
		}
		
		for(String word : wordsRefreshed) {
			this.uniNeutralWords.add(word);
		}
	}
	
	private void addWordsToNegativeUniList(String tweet) {
		String[] words = tweet.split(" ");
		
		ArrayList<String> wordsRefreshed = new ArrayList<String>();
		
		for(String str : words) {
			if(str.length() >= 3) {
				wordsRefreshed.add(str);
			}
		}
		
		for(String word : wordsRefreshed) {
			this.uniNegativeWords.add(word);
		}
	}
	
	private void addWordsToPositiveUniList(String tweet) {
		String[] words = tweet.split(" ");
		
		ArrayList<String> wordsRefreshed = new ArrayList<String>();
		
		for(String str : words) {
			if(str.length() >= 3) {
				wordsRefreshed.add(str);
			}
		}
		
		for(String word : wordsRefreshed) {
			this.uniPositiveWords.add(word);
		}
	}
	
	private void addWordsToNeutralBiList(String tweet) {
		String[] words = tweet.split(" ");
		
		ArrayList<String> wordsRefreshed = new ArrayList<String>();
		
		for(String str : words) {
			if(str.length() >= 3) {
				wordsRefreshed.add(str);
			}
		}
		
		/*System.out.println("-----------------");
		System.out.println(tweet);
		System.out.println(wordsRefreshed.size());*/
		
		for(int i = 1; i < wordsRefreshed.size(); i += 2) {
			this.biNeutralWords.add(wordsRefreshed.get(i-1) + " " + wordsRefreshed.get(i));
			//System.out.println(wordsRefreshed.get(i-1) + " " + wordsRefreshed.get(i));
		}
		
		if(wordsRefreshed.size() % 2 != 0) {
			if(wordsRefreshed.size() > 2) {
				this.biNeutralWords.add(wordsRefreshed.get(wordsRefreshed.size()-2) + " " + wordsRefreshed.get(wordsRefreshed.size()-1));
				//System.out.println(wordsRefreshed.get(wordsRefreshed.size()-2) + " " + wordsRefreshed.get(wordsRefreshed.size()-1));
			}
			else {
				this.biNeutralWords.add(wordsRefreshed.get(0));
				//System.out.println(wordsRefreshed.get(0));
			}
		}
	}
	
	private void addWordsToNegativeBiList(String tweet) {
		String[] words = tweet.split(" ");
		
		ArrayList<String> wordsRefreshed = new ArrayList<String>();
		
		for(String str : words) {
			if(str.length() >= 3) {
				wordsRefreshed.add(str);
			}
		}
		
		for(int i = 1; i < wordsRefreshed.size(); i += 2) {
			this.biNegativeWords.add(wordsRefreshed.get(i-1) + " " + wordsRefreshed.get(i));
		}
		
		if(wordsRefreshed.size() % 2 != 0) {
			if(wordsRefreshed.size() > 2)
				this.biNegativeWords.add(wordsRefreshed.get(wordsRefreshed.size()-2) + " " + wordsRefreshed.get(wordsRefreshed.size()-1));
			else
				this.biNegativeWords.add(wordsRefreshed.get(0));
		}
	}
	
	private void addWordsToPositiveBiList(String tweet) {
		String[] words = tweet.split(" ");
		
		ArrayList<String> wordsRefreshed = new ArrayList<String>();
		
		for(String str : words) {
			if(str.length() >= 3) {
				wordsRefreshed.add(str);
			}
		}
		
		for(int i = 1; i < wordsRefreshed.size(); i += 2) {
			this.biPositiveWords.add(wordsRefreshed.get(i-1) + " " + wordsRefreshed.get(i));
		}
		
		if(wordsRefreshed.size() % 2 != 0) {
			if(wordsRefreshed.size() > 2)
				this.biPositiveWords.add(wordsRefreshed.get(wordsRefreshed.size()-2) + " " + wordsRefreshed.get(wordsRefreshed.size()-1));
			else
				this.biPositiveWords.add(wordsRefreshed.get(0));
		}
	}

	public int classify(String tweet) {
		String[] singleWords = tweet.split(" ");
		ArrayList<String> words = new ArrayList<String>();
		
		ArrayList<String> wordsRefreshed = new ArrayList<String>();
		
		for(String str : singleWords) {
			if(str.length() >= 3) {
				wordsRefreshed.add(str);
			}
		}
		
		for(int i = 1; i < wordsRefreshed.size(); i += 2) {
			words.add(wordsRefreshed.get(i-1) + " " + wordsRefreshed.get(i));
		}
		
		if(wordsRefreshed.size() % 2 != 0) {
			if(wordsRefreshed.size() > 2)
				words.add(wordsRefreshed.get(wordsRefreshed.size()-2) + " " + wordsRefreshed.get(wordsRefreshed.size()-1));
			else
				words.add(wordsRefreshed.get(0));
		}
		
    	double probBiNegative = 0;
    	double probBiNeutral = 0;
    	double probBiPositive = 0;
    	
    	//Negative
    	probBiNegative = (this.biNegativeTweets.size() / this.getBiNbTweets());
    	for(String word : words) {
    		double num = 1;
    		double den = this.biNegativeWords.size() + this.getBiNbWords();
    		for(String negativeWord : this.biNegativeWords) {
    			if(word.equals(negativeWord)) {
    				num++;
    			}
    		}
    		probBiNegative *= (num/den);
    	}
    	
    	//Neutral
    	probBiNeutral = (this.biNeutralTweets.size() / this.getBiNbTweets());
    	for(String word : words) {
    		double num = 1;
    		double den = this.biNeutralWords.size() + this.getBiNbWords();
    		for(String neutralWord : this.biNeutralWords) {
    			if(word.equals(neutralWord)) {
    				num++;
    			}
    		}
    		probBiNeutral *= (num/den);
    	}
    	
    	//Positive
    	probBiPositive = (this.biPositiveTweets.size() / this.getBiNbTweets());
    	for(String word : words) {
    		double num = 1;
    		double den = this.biPositiveWords.size() + this.getBiNbWords();
    		for(String positiveWord : this.biPositiveWords) {
    			if(word.equals(positiveWord)) {
    				num++;
    			}
    		}
    		probBiPositive *= (num/den);
    	}
    	
    	singleWords = tweet.split(" ");
    	
    	wordsRefreshed = new ArrayList<String>();
		
		for(String str : singleWords) {
			if(str.length() >= 3) {
				wordsRefreshed.add(str);
			}
		}
    	
    	double probUniNegative = 0;
    	double probUniNeutral = 0;
    	double probUniPositive = 0;
    	
    	//Negative
    	probUniNegative = (this.uniNegativeTweets.size() / this.getUniNbTweets());
    	for(String word : wordsRefreshed) {
    		double num = 1;
    		double den = this.uniNegativeWords.size() + this.getUniNbWords();
    		for(String negativeWord : this.uniNegativeWords) {
    			if(word.equals(negativeWord)) {
    				num++;
    			}
    		}
    		probUniNegative *= (num/den);
    	}
    	
    	//Neutral
    	probUniNeutral = (this.uniNeutralTweets.size() / this.getUniNbTweets());
    	for(String word : wordsRefreshed) {
    		double num = 1;
    		double den = this.uniNeutralWords.size() + this.getUniNbWords();
    		for(String neutralWord : this.uniNeutralWords) {
    			if(word.equals(neutralWord)) {
    				num++;
    			}
    		}
    		probUniNeutral *= (num/den);
    	}
    	
    	//Positive
    	probUniPositive = (this.uniPositiveTweets.size() / this.getUniNbTweets());
    	for(String word : wordsRefreshed) {
    		double num = 1;
    		double den = this.uniPositiveWords.size() + this.getUniNbWords();
    		for(String positiveWord : this.uniPositiveWords) {
    			if(word.equals(positiveWord)) {
    				num++;
    			}
    		}
    		probUniPositive *= (num/den);
    	}
    	
    	double probNegative = probBiNegative*probUniNegative;
    	double probNeutral = probBiNeutral*probUniNeutral;
    	double probPositive = probBiPositive*probUniPositive;
    	
    	
    	
    	return Utils.probabilitiesToTweetPolarity(probNegative, probNeutral, probPositive, false);
    }
	
	private double getBiNbTweets() {
		return this.biPositiveTweets.size() + this.biNegativeTweets.size() + this.biNeutralTweets.size();
	}
	
	private double getUniNbTweets() {
		return this.uniPositiveTweets.size() + this.uniNegativeTweets.size() + this.uniNeutralTweets.size();
	}
	
	private double getBiNbWords() {
		return this.biPositiveWords.size() + this.biNegativeWords.size() + this.biNeutralWords.size();
	}
	
	private double getUniNbWords() {
		return this.uniPositiveWords.size() + this.uniNegativeWords.size() + this.uniNeutralWords.size();
	}
}
