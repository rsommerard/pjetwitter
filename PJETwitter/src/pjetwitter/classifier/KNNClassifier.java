package pjetwitter.classifier;

import helper.Constants;

import java.util.ArrayList;
import java.util.List;

import pjetwitter.TweetInfo;

public class KNNClassifier {
	
	private List<TweetInfo> referenceTweets;
	
	public KNNClassifier(List<TweetInfo> tweets) {
		this.referenceTweets = tweets;
	}

	private double distance(String tweet1, String tweet2) {
		String[] tweet1Splited = tweet1.split("\\s");
		String[] tweet2Splited = tweet2.split("\\s");
		
		List<String> tweet2List = new ArrayList<String>();
		for(String str : tweet2Splited) {
			tweet2List.add(str);
		}
		
		double count = 0;
		for(String str : tweet1Splited) {
			if(tweet2List.contains(str)) {
				count++;
			}
		}
		
		double nbElement = tweet1Splited.length + tweet2Splited.length;
		
		return ((nbElement - count*2) / nbElement);
	}
	
	public int classify(String tweetToClassify) {
    	double negativeDistance = 0;
    	double positiveDistance = 0;
    	double neutralDistance = 0;
    	
    	for(TweetInfo tweet : this.referenceTweets) {
    		if(tweet.getTweetPolarity() == Constants.NEGATIVE_TWEET) {
    			negativeDistance += distance(tweetToClassify, tweet.getTweetText());
    		}
    		if(tweet.getTweetPolarity() == Constants.NEUTRAL_TWEET) {
    			neutralDistance += distance(tweetToClassify, tweet.getTweetText());
    		}
    		if(tweet.getTweetPolarity() == Constants.POSITIVE_TWEET) {
    			positiveDistance += distance(tweetToClassify, tweet.getTweetText());
    		}
    	}
    	
    	System.out.println("Positive distance: " + positiveDistance);
    	System.out.println("Neutral distance: " + neutralDistance);
    	System.out.println("Negative distance: " + negativeDistance);
    	
    	if(positiveDistance < neutralDistance) {
    		if(positiveDistance < negativeDistance) {
    			return Constants.POSITIVE_TWEET;
    		}
    		else {
    			if(negativeDistance < neutralDistance) {
    				return Constants.NEGATIVE_TWEET;
    			}
    			else {
    				return Constants.NEUTRAL_TWEET;
    			}
    		}
    	}
    	
    	if(neutralDistance < negativeDistance) {
    		return Constants.NEUTRAL_TWEET;
    	}
    	
    	return Constants.NEGATIVE_TWEET;
    }
	
	public static void main(String[] args) {
		TweetInfo positiveTweet = new TweetInfo(1, "toto", "It's a good day.", null, "test", Constants.POSITIVE_TWEET);
		TweetInfo negativeTweet = new TweetInfo(2, "toto", "This day is a bad day.", null, "test", Constants.NEGATIVE_TWEET);
		TweetInfo neutralTweet = new TweetInfo(3, "toto", "This is a day like the others.", null, "test", Constants.NEUTRAL_TWEET);
		
		List<TweetInfo> testList = new ArrayList<TweetInfo>();
		testList.add(positiveTweet);
		testList.add(negativeTweet);
		testList.add(neutralTweet);
		
    	String tweet1 = "This is a tweet.";
    	String tweet2 = "This is a second good tweet.";
    	String tweet3 = "This is a second bad tweet.";
    	
    	System.out.println("NON_ANNOTATED_TWEET = 1, NEGATIVE_TWEET = 0, NEUTRAL_TWEET = 2, POSITIVE_TWEET = 4");
    	System.out.println("tweet1: \"" + tweet1 + "\" = " + new KNNClassifier(testList).classify(tweet1));
    	System.out.println("tweet2: \"" + tweet2 + "\" = " + new KNNClassifier(testList).classify(tweet2));
    	System.out.println("tweet3: \"" + tweet3 + "\" = " + new KNNClassifier(testList).classify(tweet3));
    }
}
