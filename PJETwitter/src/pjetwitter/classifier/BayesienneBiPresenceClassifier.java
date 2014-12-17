package pjetwitter.classifier;

import helper.Globals;
import helper.Utils;
import helper.csv.CsvHelper;

import java.util.ArrayList;
import java.util.List;

import pjetwitter.TweetInfo;

public class BayesienneBiPresenceClassifier {
	private ArrayList<TweetInfo> positiveTweets;
	private ArrayList<TweetInfo> negativeTweets;
	private ArrayList<TweetInfo> neutralTweets;
	
	private ArrayList<String> positiveWords;
	private ArrayList<String> negativeWords;
	private ArrayList<String> neutralWords;
	
	public BayesienneBiPresenceClassifier(List<TweetInfo> references) {
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
		
		/*System.out.println("-----------------");
		System.out.println(tweet);
		System.out.println(wordsRefreshed.size());*/
		
		for(int i = 1; i < wordsRefreshed.size(); i += 2) {
			this.neutralWords.add(wordsRefreshed.get(i-1) + " " + wordsRefreshed.get(i));
			//System.out.println(wordsRefreshed.get(i-1) + " " + wordsRefreshed.get(i));
		}
		
		if(wordsRefreshed.size() % 2 != 0) {
			if(wordsRefreshed.size() > 2) {
				this.neutralWords.add(wordsRefreshed.get(wordsRefreshed.size()-2) + " " + wordsRefreshed.get(wordsRefreshed.size()-1));
				//System.out.println(wordsRefreshed.get(wordsRefreshed.size()-2) + " " + wordsRefreshed.get(wordsRefreshed.size()-1));
			}
			else {
				this.neutralWords.add(wordsRefreshed.get(0));
				//System.out.println(wordsRefreshed.get(0));
			}
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
		
		for(int i = 1; i < wordsRefreshed.size(); i += 2) {
			this.negativeWords.add(wordsRefreshed.get(i-1) + " " + wordsRefreshed.get(i));
		}
		
		if(wordsRefreshed.size() % 2 != 0) {
			if(wordsRefreshed.size() > 2)
				this.negativeWords.add(wordsRefreshed.get(wordsRefreshed.size()-2) + " " + wordsRefreshed.get(wordsRefreshed.size()-1));
			else
				this.negativeWords.add(wordsRefreshed.get(0));
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
		
		for(int i = 1; i < wordsRefreshed.size(); i += 2) {
			this.positiveWords.add(wordsRefreshed.get(i-1) + " " + wordsRefreshed.get(i));
		}
		
		if(wordsRefreshed.size() % 2 != 0) {
			if(wordsRefreshed.size() > 2)
				this.positiveWords.add(wordsRefreshed.get(wordsRefreshed.size()-2) + " " + wordsRefreshed.get(wordsRefreshed.size()-1));
			else
				this.positiveWords.add(wordsRefreshed.get(0));
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
		
    	double probNegative = 0;
    	double probNeutral = 0;
    	double probPositive = 0;
    	
    	
    	//Negative
    	probNegative = (this.negativeTweets.size() / this.getNbTweets());
    	for(String word : words) {
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
    	for(String word : words) {
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
    	for(String word : words) {
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
	
	public static void main(String[] args) {
		CsvHelper csv = helper.csv.CsvSingletons.getInstance().referenceCsv;

		List<TweetInfo> references = csv.readAll();
		
		new BayesienneBiPresenceClassifier(references);
	}
	
	private double getNbTweets() {
		return this.positiveTweets.size() + this.negativeTweets.size() + this.neutralTweets.size();
	}
	
	private double getNbWords() {
		return this.positiveWords.size() + this.negativeWords.size() + this.neutralWords.size();
	}
}
