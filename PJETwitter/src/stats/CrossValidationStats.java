package stats;

import helper.Globals;
import helper.Utils;
import helper.csv.CsvHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import stats.charts.AllInOneChart;
import pjetwitter.TweetInfo;
import pjetwitter.classifier.BayesienneBiFrequenceClassifier;
import pjetwitter.classifier.BayesienneBiPresenceClassifier;
import pjetwitter.classifier.BayesienneUniBiFrequenceClassifier;
import pjetwitter.classifier.BayesienneUniBiPresenceClassifier;
import pjetwitter.classifier.BayesienneUniFrequenceClassifier;
import pjetwitter.classifier.BayesienneUniPresenceClassifier;
import pjetwitter.classifier.KNNClassifier;
import pjetwitter.classifier.KeywordsClassifier;

public class CrossValidationStats
{

	public static void main(String[] args)
	{
		final int NB_TRANCHES = 10;

		CsvHelper csv = helper.csv.CsvSingletons.getInstance().referenceCsv;

		List<TweetInfo> tweetsBase = csv.readAll();

		ArrayList<TweetInfo> negativeTweets = Utils.getTweetsWithPolarity(tweetsBase, Globals.NEGATIVE_TWEET);
		ArrayList<TweetInfo> neutralTweets = Utils.getTweetsWithPolarity(tweetsBase, Globals.NEUTRAL_TWEET);
		ArrayList<TweetInfo> positiveTweets = Utils.getTweetsWithPolarity(tweetsBase, Globals.POSITIVE_TWEET);
		System.out.println("Nb tweet pour dans CSV reference: " + tweetsBase.size());
		System.out.println("  -> negatifs: " + negativeTweets.size());
		System.out.println("  -> neutres: " + neutralTweets.size());
		System.out.println("  -> positifs: " + positiveTweets.size());

		Collections.shuffle(negativeTweets);
		Collections.shuffle(neutralTweets);
		Collections.shuffle(positiveTweets);

		int minNumberTweet = Math.min(Math.min(negativeTweets.size(), neutralTweets.size()), positiveTweets.size());

		int tweetPolarityByTranche = minNumberTweet / NB_TRANCHES;
		int tweetByTranche = tweetPolarityByTranche * 3;
		//int nbTweets = tweetByTranche * NB_TRANCHES;

		while (negativeTweets.size() != tweetPolarityByTranche * NB_TRANCHES)
		{
			negativeTweets.remove(0);
		}

		while (neutralTweets.size() != tweetPolarityByTranche * NB_TRANCHES)
		{
			neutralTweets.remove(0);
		}

		while (positiveTweets.size() != tweetPolarityByTranche * NB_TRANCHES)
		{
			positiveTweets.remove(0);
		}

		ArrayList<TweetInfo> tweets = new ArrayList<TweetInfo>();

		for (int i = 0; i < NB_TRANCHES; i++)
		{
			for (int j = 0; j < tweetPolarityByTranche; j++)
			{
				tweets.add(negativeTweets.remove(0));
				tweets.add(neutralTweets.remove(0));
				tweets.add(positiveTweets.remove(0));
			}
		}

		ArrayList<TweetInfo> tweetsNonAnnotedBayesienneUni = new ArrayList<TweetInfo>();
		for (TweetInfo tweet : tweets)
		{
			TweetInfo tweetNonAnnoted = (TweetInfo) tweet.clone();
			tweetNonAnnoted.setTweetPolarity(Globals.NON_ANNOTATED_TWEET);
			tweetsNonAnnotedBayesienneUni.add(tweetNonAnnoted);
		}
		
		ArrayList<TweetInfo> tweetsNonAnnotedBayesienneUniFreq = new ArrayList<TweetInfo>();
		for (TweetInfo tweet : tweets)
		{
			TweetInfo tweetNonAnnoted = (TweetInfo) tweet.clone();
			tweetNonAnnoted.setTweetPolarity(Globals.NON_ANNOTATED_TWEET);
			tweetsNonAnnotedBayesienneUniFreq.add(tweetNonAnnoted);
		}
		
		ArrayList<TweetInfo> tweetsNonAnnotedBayesienneBi = new ArrayList<TweetInfo>();
		for (TweetInfo tweet : tweets)
		{
			TweetInfo tweetNonAnnoted = (TweetInfo) tweet.clone();
			tweetNonAnnoted.setTweetPolarity(Globals.NON_ANNOTATED_TWEET);
			tweetsNonAnnotedBayesienneBi.add(tweetNonAnnoted);
		}
		
		ArrayList<TweetInfo> tweetsNonAnnotedBayesienneBiFreq = new ArrayList<TweetInfo>();
		for (TweetInfo tweet : tweets)
		{
			TweetInfo tweetNonAnnoted = (TweetInfo) tweet.clone();
			tweetNonAnnoted.setTweetPolarity(Globals.NON_ANNOTATED_TWEET);
			tweetsNonAnnotedBayesienneBiFreq.add(tweetNonAnnoted);
		}
		
		ArrayList<TweetInfo> tweetsNonAnnotedBayesienneUniBi = new ArrayList<TweetInfo>();
		for (TweetInfo tweet : tweets)
		{
			TweetInfo tweetNonAnnoted = (TweetInfo) tweet.clone();
			tweetNonAnnoted.setTweetPolarity(Globals.NON_ANNOTATED_TWEET);
			tweetsNonAnnotedBayesienneUniBi.add(tweetNonAnnoted);
		}
		
		ArrayList<TweetInfo> tweetsNonAnnotedBayesienneUniBiFreq = new ArrayList<TweetInfo>();
		for (TweetInfo tweet : tweets)
		{
			TweetInfo tweetNonAnnoted = (TweetInfo) tweet.clone();
			tweetNonAnnoted.setTweetPolarity(Globals.NON_ANNOTATED_TWEET);
			tweetsNonAnnotedBayesienneUniBiFreq.add(tweetNonAnnoted);
		}

		ArrayList<TweetInfo> tweetsNonAnnotedKeywords = new ArrayList<TweetInfo>();
		for (TweetInfo tweet : tweets)
		{
			TweetInfo tweetNonAnnoted = (TweetInfo) tweet.clone();
			tweetNonAnnoted.setTweetPolarity(Globals.NON_ANNOTATED_TWEET);
			tweetsNonAnnotedKeywords.add(tweetNonAnnoted);
		}

		ArrayList<TweetInfo> tweetsNonAnnotedKNN = new ArrayList<TweetInfo>();
		for (TweetInfo tweet : tweets)
		{
			TweetInfo tweetNonAnnoted = (TweetInfo) tweet.clone();
			tweetNonAnnoted.setTweetPolarity(Globals.NON_ANNOTATED_TWEET);
			tweetsNonAnnotedKNN.add(tweetNonAnnoted);
		}

		System.out.println("Nb tweet pour références: " + tweets.size());

		for (int i = 0; i < NB_TRANCHES; i++)
		{
			ArrayList<TweetInfo> tweetsCopy = new ArrayList<TweetInfo>();
			for (TweetInfo tweet : tweets)
			{
				tweetsCopy.add(tweet);
			}

			for (int j = 0; j < tweetByTranche; j++)
			{
				tweetsCopy.remove(i * tweetByTranche);
			}

			BayesienneUniPresenceClassifier bayesienneUniClassifier = new BayesienneUniPresenceClassifier(tweetsCopy);
			BayesienneUniFrequenceClassifier bayesienneUniFreqClassifier = new BayesienneUniFrequenceClassifier(tweetsCopy);
			BayesienneBiPresenceClassifier bayesienneBiClassifier = new BayesienneBiPresenceClassifier(tweetsCopy);
			BayesienneBiFrequenceClassifier bayesienneBiFreqClassifier = new BayesienneBiFrequenceClassifier(tweetsCopy);
			BayesienneUniBiPresenceClassifier bayesienneUniBiClassifier = new BayesienneUniBiPresenceClassifier(tweetsCopy);
			BayesienneUniBiFrequenceClassifier bayesienneUniBiFreqClassifier = new BayesienneUniBiFrequenceClassifier(tweetsCopy);
			KNNClassifier knnClassifier = new KNNClassifier(tweetsCopy);
			KeywordsClassifier keywordsClassifier = new KeywordsClassifier();

			for (int j = 0; j < tweetByTranche; j++)
			{
				TweetInfo tweetToAnnotateBayesienneUni = tweetsNonAnnotedBayesienneUni.get(i * tweetByTranche + j);
				tweetToAnnotateBayesienneUni.setTweetPolarity(bayesienneUniClassifier.classify(tweetToAnnotateBayesienneUni.getTweetText()));
				
				TweetInfo tweetToAnnotateBayesienneUniFreq = tweetsNonAnnotedBayesienneUniFreq.get(i * tweetByTranche + j);
				tweetToAnnotateBayesienneUniFreq.setTweetPolarity(bayesienneUniFreqClassifier.classify(tweetToAnnotateBayesienneUniFreq.getTweetText()));

				TweetInfo tweetToAnnotateBayesienneBi = tweetsNonAnnotedBayesienneBi.get(i * tweetByTranche + j);
				tweetToAnnotateBayesienneBi.setTweetPolarity(bayesienneBiClassifier.classify(tweetToAnnotateBayesienneBi.getTweetText()));
				
				TweetInfo tweetToAnnotateBayesienneBiFreq = tweetsNonAnnotedBayesienneBiFreq.get(i * tweetByTranche + j);
				tweetToAnnotateBayesienneBiFreq.setTweetPolarity(bayesienneBiFreqClassifier.classify(tweetToAnnotateBayesienneBiFreq.getTweetText()));
				
				TweetInfo tweetToAnnotateBayesienneUniBi = tweetsNonAnnotedBayesienneUniBi.get(i * tweetByTranche + j);
				tweetToAnnotateBayesienneUniBi.setTweetPolarity(bayesienneUniBiClassifier.classify(tweetToAnnotateBayesienneUniBi.getTweetText()));
				
				TweetInfo tweetToAnnotateBayesienneUniBiFreq = tweetsNonAnnotedBayesienneUniBiFreq.get(i * tweetByTranche + j);
				tweetToAnnotateBayesienneUniBiFreq.setTweetPolarity(bayesienneUniBiFreqClassifier.classify(tweetToAnnotateBayesienneUniBiFreq.getTweetText()));
				
				TweetInfo tweetToAnnotateKNN = tweetsNonAnnotedKNN.get(i * tweetByTranche + j);
				tweetToAnnotateKNN.setTweetPolarity(knnClassifier.classify(tweetToAnnotateKNN.getTweetText()));

				TweetInfo tweetToAnnotateKeywords = tweetsNonAnnotedKeywords.get(i * tweetByTranche + j);
				tweetToAnnotateKeywords.setTweetPolarity(keywordsClassifier.classify(tweetToAnnotateKeywords.getTweetText()));
			}
		}
		
		
		double ratioBayesienneUni;
		double ratioBayesienneUniFreq;
		double ratioBayesienneBi;
		double ratioBayesienneBiFreq;
		double ratioBayesienneUniBi;
		double ratioBayesienneUniBiFreq;
		double ratioKNN;
		double ratioKeywords;
		
		double nbErreursBayesienneUni = 0;
		double nbErreursBayesienneUniFreq = 0;
		double nbErreursBayesienneBi = 0;
		double nbErreursBayesienneBiFreq = 0;
		double nbErreursBayesienneUniBi = 0;
		double nbErreursBayesienneUniBiFreq = 0;
		double nbErreursKNN = 0;
		double nbErreursKeywords = 0;
		
		int nbTruePositiveBayesienneUni = 0;
		int nbFalsePositiveBayesienneUni = 0;
		int nbTrueNeutralBayesienneUni = 0;
		int nbFalseNeutralBayesienneUni = 0;
		int nbTrueNegativeBayesienneUni = 0;
		int nbFalseNegativeBayesienneUni = 0;
		
		int nbTruePositiveBayesienneUniFreq = 0;
		int nbFalsePositiveBayesienneUniFreq = 0;
		int nbTrueNeutralBayesienneUniFreq = 0;
		int nbFalseNeutralBayesienneUniFreq = 0;
		int nbTrueNegativeBayesienneUniFreq = 0;
		int nbFalseNegativeBayesienneUniFreq = 0;
		
		int nbTruePositiveBayesienneBi = 0;
		int nbFalsePositiveBayesienneBi = 0;
		int nbTrueNeutralBayesienneBi = 0;
		int nbFalseNeutralBayesienneBi = 0;
		int nbTrueNegativeBayesienneBi = 0;
		int nbFalseNegativeBayesienneBi = 0;
		
		int nbTruePositiveBayesienneBiFreq = 0;
		int nbFalsePositiveBayesienneBiFreq = 0;
		int nbTrueNeutralBayesienneBiFreq = 0;
		int nbFalseNeutralBayesienneBiFreq = 0;
		int nbTrueNegativeBayesienneBiFreq = 0;
		int nbFalseNegativeBayesienneBiFreq = 0;
		
		int nbTruePositiveBayesienneUniBi = 0;
		int nbFalsePositiveBayesienneUniBi = 0;
		int nbTrueNeutralBayesienneUniBi = 0;
		int nbFalseNeutralBayesienneUniBi = 0;
		int nbTrueNegativeBayesienneUniBi = 0;
		int nbFalseNegativeBayesienneUniBi = 0;
		
		int nbTruePositiveBayesienneUniBiFreq = 0;
		int nbFalsePositiveBayesienneUniBiFreq = 0;
		int nbTrueNeutralBayesienneUniBiFreq = 0;
		int nbFalseNeutralBayesienneUniBiFreq = 0;
		int nbTrueNegativeBayesienneUniBiFreq = 0;
		int nbFalseNegativeBayesienneUniBiFreq = 0;
		
		int nbTruePositiveKNN = 0;
		int nbFalsePositiveKNN = 0;
		int nbTrueNeutralKNN = 0;
		int nbFalseNeutralKNN = 0;
		int nbTrueNegativeKNN = 0;
		int nbFalseNegativeKNN = 0;
		
		int nbTruePositiveKeywords = 0;
		int nbFalsePositiveKeywords = 0;
		int nbTrueNeutralKeywords = 0;
		int nbFalseNeutralKeywords = 0;
		int nbTrueNegativeKeywords = 0;
		int nbFalseNegativeKeywords = 0;
		
		
		
		int i = 0;
		for(TweetInfo tweet : tweets) {
			System.out.println("----------------");
			System.out.println(tweet.toString());
			System.out.println(tweetsNonAnnotedBayesienneUni.get(i).toString());
			
			if (tweet.getTweetPolarity() != tweetsNonAnnotedBayesienneUni.get(i).getTweetPolarity())
			{
				nbErreursBayesienneUni++;
			}
			
			if (tweetsNonAnnotedBayesienneUni.get(i).getTweetPolarity() == Globals.POSITIVE_TWEET)
			{
				if(tweet.getTweetPolarity() == Globals.POSITIVE_TWEET) {
					nbTruePositiveBayesienneUni++;
				}
				else {
					nbFalsePositiveBayesienneUni++;
				}
			}
			else if (tweetsNonAnnotedBayesienneUni.get(i).getTweetPolarity() == Globals.NEUTRAL_TWEET)
			{
				if(tweet.getTweetPolarity() == Globals.NEUTRAL_TWEET) {
					nbTrueNeutralBayesienneUni++;
				}
				else {
					nbFalseNeutralBayesienneUni++;
				}
			}
			else if (tweetsNonAnnotedBayesienneUni.get(i).getTweetPolarity() == Globals.NEGATIVE_TWEET)
			{
				if(tweet.getTweetPolarity() == Globals.NEGATIVE_TWEET) {
					nbTrueNegativeBayesienneUni++;
				}
				else {
					nbFalseNegativeBayesienneUni++;
				}
			}
			
			//------------------
			
			if (tweet.getTweetPolarity() != tweetsNonAnnotedBayesienneUniFreq.get(i).getTweetPolarity())
			{
				nbErreursBayesienneUniFreq++;
			}
			
			if (tweetsNonAnnotedBayesienneUniFreq.get(i).getTweetPolarity() == Globals.POSITIVE_TWEET)
			{
				if(tweet.getTweetPolarity() == Globals.POSITIVE_TWEET) {
					nbTruePositiveBayesienneUniFreq++;
				}
				else {
					nbFalsePositiveBayesienneUniFreq++;
				}
			}
			else if (tweetsNonAnnotedBayesienneUniFreq.get(i).getTweetPolarity() == Globals.NEUTRAL_TWEET)
			{
				if(tweet.getTweetPolarity() == Globals.NEUTRAL_TWEET) {
					nbTrueNeutralBayesienneUniFreq++;
				}
				else {
					nbFalseNeutralBayesienneUniFreq++;
				}
			}
			else if (tweetsNonAnnotedBayesienneUniFreq.get(i).getTweetPolarity() == Globals.NEGATIVE_TWEET)
			{
				if(tweet.getTweetPolarity() == Globals.NEGATIVE_TWEET) {
					nbTrueNegativeBayesienneUniFreq++;
				}
				else {
					nbFalseNegativeBayesienneUniFreq++;
				}
			}
			
			//--------------------
			
			if (tweet.getTweetPolarity() != tweetsNonAnnotedBayesienneBi.get(i).getTweetPolarity())
			{
				nbErreursBayesienneBi++;
			}
			
			if (tweetsNonAnnotedBayesienneBi.get(i).getTweetPolarity() == Globals.POSITIVE_TWEET)
			{
				if(tweet.getTweetPolarity() == Globals.POSITIVE_TWEET) {
					nbTruePositiveBayesienneBi++;
				}
				else {
					nbFalsePositiveBayesienneBi++;
				}
			}
			else if (tweetsNonAnnotedBayesienneBi.get(i).getTweetPolarity() == Globals.NEUTRAL_TWEET)
			{
				if(tweet.getTweetPolarity() == Globals.NEUTRAL_TWEET) {
					nbTrueNeutralBayesienneBi++;
				}
				else {
					nbFalseNeutralBayesienneBi++;
				}
			}
			else if (tweetsNonAnnotedBayesienneBi.get(i).getTweetPolarity() == Globals.NEGATIVE_TWEET)
			{
				if(tweet.getTweetPolarity() == Globals.NEGATIVE_TWEET) {
					nbTrueNegativeBayesienneBi++;
				}
				else {
					nbFalseNegativeBayesienneBi++;
				}
			}
			
			//---------------------
			
			if (tweet.getTweetPolarity() != tweetsNonAnnotedBayesienneBiFreq.get(i).getTweetPolarity())
			{
				nbErreursBayesienneBiFreq++;
			}
			
			if (tweetsNonAnnotedBayesienneBiFreq.get(i).getTweetPolarity() == Globals.POSITIVE_TWEET)
			{
				if(tweet.getTweetPolarity() == Globals.POSITIVE_TWEET) {
					nbTruePositiveBayesienneBiFreq++;
				}
				else {
					nbFalsePositiveBayesienneBiFreq++;
				}
			}
			else if (tweetsNonAnnotedBayesienneBiFreq.get(i).getTweetPolarity() == Globals.NEUTRAL_TWEET)
			{
				if(tweet.getTweetPolarity() == Globals.NEUTRAL_TWEET) {
					nbTrueNeutralBayesienneBiFreq++;
				}
				else {
					nbFalseNeutralBayesienneBiFreq++;
				}
			}
			else if (tweetsNonAnnotedBayesienneBiFreq.get(i).getTweetPolarity() == Globals.NEGATIVE_TWEET)
			{
				if(tweet.getTweetPolarity() == Globals.NEGATIVE_TWEET) {
					nbTrueNegativeBayesienneBiFreq++;
				}
				else {
					nbFalseNegativeBayesienneBiFreq++;
				}
			}
			
			//-----------
			
			if (tweet.getTweetPolarity() != tweetsNonAnnotedBayesienneUniBi.get(i).getTweetPolarity())
			{
				nbErreursBayesienneUniBi++;
			}
			
			if (tweetsNonAnnotedBayesienneUniBi.get(i).getTweetPolarity() == Globals.POSITIVE_TWEET)
			{
				if(tweet.getTweetPolarity() == Globals.POSITIVE_TWEET) {
					nbTruePositiveBayesienneUniBi++;
				}
				else {
					nbFalsePositiveBayesienneUniBi++;
				}
			}
			else if (tweetsNonAnnotedBayesienneUniBi.get(i).getTweetPolarity() == Globals.NEUTRAL_TWEET)
			{
				if(tweet.getTweetPolarity() == Globals.NEUTRAL_TWEET) {
					nbTrueNeutralBayesienneUniBi++;
				}
				else {
					nbFalseNeutralBayesienneUniBi++;
				}
			}
			else if (tweetsNonAnnotedBayesienneUniBi.get(i).getTweetPolarity() == Globals.NEGATIVE_TWEET)
			{
				if(tweet.getTweetPolarity() == Globals.NEGATIVE_TWEET) {
					nbTrueNegativeBayesienneUniBi++;
				}
				else {
					nbFalseNegativeBayesienneUniBi++;
				}
			}
			
			//------------------
			
			if (tweet.getTweetPolarity() != tweetsNonAnnotedBayesienneUniBiFreq.get(i).getTweetPolarity())
			{
				nbErreursBayesienneUniBiFreq++;
			}
			
			if (tweetsNonAnnotedBayesienneUniBiFreq.get(i).getTweetPolarity() == Globals.POSITIVE_TWEET)
			{
				if(tweet.getTweetPolarity() == Globals.POSITIVE_TWEET) {
					nbTruePositiveBayesienneUniBiFreq++;
				}
				else {
					nbFalsePositiveBayesienneUniBiFreq++;
				}
			}
			else if (tweetsNonAnnotedBayesienneUniBiFreq.get(i).getTweetPolarity() == Globals.NEUTRAL_TWEET)
			{
				if(tweet.getTweetPolarity() == Globals.NEUTRAL_TWEET) {
					nbTrueNeutralBayesienneUniBiFreq++;
				}
				else {
					nbFalseNeutralBayesienneUniBiFreq++;
				}
			}
			else if (tweetsNonAnnotedBayesienneUniBiFreq.get(i).getTweetPolarity() == Globals.NEGATIVE_TWEET)
			{
				if(tweet.getTweetPolarity() == Globals.NEGATIVE_TWEET) {
					nbTrueNegativeBayesienneUniBiFreq++;
				}
				else {
					nbFalseNegativeBayesienneUniBiFreq++;
				}
			}
			
			//-----------------------
			
			if (tweet.getTweetPolarity() != tweetsNonAnnotedKNN.get(i).getTweetPolarity())
			{
				nbErreursKNN++;
			}
			
			if (tweetsNonAnnotedKNN.get(i).getTweetPolarity() == Globals.POSITIVE_TWEET)
			{
				if(tweet.getTweetPolarity() == Globals.POSITIVE_TWEET) {
					nbTruePositiveKNN++;
				}
				else {
					nbFalsePositiveKNN++;
				}
			}
			else if (tweetsNonAnnotedKNN.get(i).getTweetPolarity() == Globals.NEUTRAL_TWEET)
			{
				if(tweet.getTweetPolarity() == Globals.NEUTRAL_TWEET) {
					nbTrueNeutralKNN++;
				}
				else {
					nbFalseNeutralKNN++;
				}
			}
			else if (tweetsNonAnnotedKNN.get(i).getTweetPolarity() == Globals.NEGATIVE_TWEET)
			{
				if(tweet.getTweetPolarity() == Globals.NEGATIVE_TWEET) {
					nbTrueNegativeKNN++;
				}
				else {
					nbFalseNegativeKNN++;
				}
			}
			
			//-------------------
			
			if (tweet.getTweetPolarity() != tweetsNonAnnotedKeywords.get(i).getTweetPolarity())
			{
				nbErreursKeywords++;
			}
			
			if (tweetsNonAnnotedKeywords.get(i).getTweetPolarity() == Globals.POSITIVE_TWEET)
			{
				if(tweet.getTweetPolarity() == Globals.POSITIVE_TWEET) {
					nbTruePositiveKeywords++;
				}
				else {
					nbFalsePositiveKeywords++;
				}
			}
			else if (tweetsNonAnnotedKeywords.get(i).getTweetPolarity() == Globals.NEUTRAL_TWEET)
			{
				if(tweet.getTweetPolarity() == Globals.NEUTRAL_TWEET) {
					nbTrueNeutralKeywords++;
				}
				else {
					nbFalseNeutralKeywords++;
				}
			}
			else if (tweetsNonAnnotedKeywords.get(i).getTweetPolarity() == Globals.NEGATIVE_TWEET)
			{
				if(tweet.getTweetPolarity() == Globals.NEGATIVE_TWEET) {
					nbTrueNegativeKeywords++;
				}
				else {
					nbFalseNegativeKeywords++;
				}
			}
			
			//------------------
			
			i++;
		}

		
		System.out.println("Tweets Size: " + tweets.size());
		System.out.println("nbErreursBayesienneUni: " + nbErreursBayesienneUni);
		System.out.println("nbErreursBayesienneUniFreq: " + nbErreursBayesienneUniFreq);
		System.out.println("nbErreursBayesienneBi: " + nbErreursBayesienneBi);
		System.out.println("nbErreursBayesienneBiFreq: " + nbErreursBayesienneBiFreq);
		System.out.println("nbErreursBayesienneUniBi: " + nbErreursBayesienneUniBi);
		System.out.println("nbErreursBayesienneUniBiFreq: " + nbErreursBayesienneUniBiFreq);
		System.out.println("nbErreursKNN: " + nbErreursKNN);
		System.out.println("nbErreursKeywords: " + nbErreursKeywords);
		System.out.println("---------------------------");
		System.out.println("nbTruePositiveBayesienneUni: " + nbTruePositiveBayesienneUni);
		System.out.println("nbFalsePositiveBayesienneUni: " + nbFalsePositiveBayesienneUni);
		System.out.println("nbTrueNeutralBayesienneUni: " + nbTrueNeutralBayesienneUni);
		System.out.println("nbFalseNeutralBayesienneUni: " + nbFalseNeutralBayesienneUni);
		System.out.println("nbTrueNegativeBayesienneUni: " + nbTrueNegativeBayesienneUni);
		System.out.println("nbFalseNegativeBayesienneUni: " + nbFalseNegativeBayesienneUni);
		System.out.println("---------------------------");
		System.out.println("nbTruePositiveBayesienneUniFreq: " + nbTruePositiveBayesienneUniFreq);
		System.out.println("nbFalsePositiveBayesienneUniFreq: " + nbFalsePositiveBayesienneUniFreq);
		System.out.println("nbTrueNeutralBayesienneUniFreq: " + nbTrueNeutralBayesienneUniFreq);
		System.out.println("nbFalseNeutralBayesienneUniFreq: " + nbFalseNeutralBayesienneUniFreq);
		System.out.println("nbTrueNegativeBayesienneUniFreq: " + nbTrueNegativeBayesienneUniFreq);
		System.out.println("nbFalseNegativeBayesienneUniFreq: " + nbFalseNegativeBayesienneUniFreq);
		System.out.println("---------------------------");
		System.out.println("nbTruePositiveBayesienneBi: " + nbTruePositiveBayesienneBi);
		System.out.println("nbFalsePositiveBayesienneBi: " + nbFalsePositiveBayesienneBi);
		System.out.println("nbTrueNeutralBayesienneBi: " + nbTrueNeutralBayesienneBi);
		System.out.println("nbFalseNeutralBayesienneBi: " + nbFalseNeutralBayesienneBi);
		System.out.println("nbTrueNegativeBayesienneBi: " + nbTrueNegativeBayesienneBi);
		System.out.println("nbFalseNegativeBayesienneBi: " + nbFalseNegativeBayesienneBi);
		System.out.println("---------------------------");
		System.out.println("nbTruePositiveBayesienneBiFreq: " + nbTruePositiveBayesienneBiFreq);
		System.out.println("nbFalsePositiveBayesienneBiFreq: " + nbFalsePositiveBayesienneBiFreq);
		System.out.println("nbTrueNeutralBayesienneBiFreq: " + nbTrueNeutralBayesienneBiFreq);
		System.out.println("nbFalseNeutralBayesienneBiFreq: " + nbFalseNeutralBayesienneBiFreq);
		System.out.println("nbTrueNegativeBayesienneBiFreq: " + nbTrueNegativeBayesienneBiFreq);
		System.out.println("nbFalseNegativeBayesienneBiFreq: " + nbFalseNegativeBayesienneBiFreq);
		System.out.println("---------------------------");
		System.out.println("nbTruePositiveBayesienneUniBi: " + nbTruePositiveBayesienneUniBi);
		System.out.println("nbFalsePositiveBayesienneUniBi: " + nbFalsePositiveBayesienneUniBi);
		System.out.println("nbTrueNeutralBayesienneUniBi: " + nbTrueNeutralBayesienneUniBi);
		System.out.println("nbFalseNeutralBayesienneUniBi: " + nbFalseNeutralBayesienneUniBi);
		System.out.println("nbTrueNegativeBayesienneUniBi: " + nbTrueNegativeBayesienneUniBi);
		System.out.println("nbFalseNegativeBayesienneUniBi: " + nbFalseNegativeBayesienneUniBi);
		System.out.println("---------------------------");
		System.out.println("nbTruePositiveBayesienneUniBiFreq: " + nbTruePositiveBayesienneUniBiFreq);
		System.out.println("nbFalsePositiveBayesienneUniBiFreq: " + nbFalsePositiveBayesienneUniBiFreq);
		System.out.println("nbTrueNeutralBayesienneUniBiFreq: " + nbTrueNeutralBayesienneUniBiFreq);
		System.out.println("nbFalseNeutralBayesienneUniBiFreq: " + nbFalseNeutralBayesienneUniBiFreq);
		System.out.println("nbTrueNegativeBayesienneUniBiFreq: " + nbTrueNegativeBayesienneUniBiFreq);
		System.out.println("nbFalseNegativeBayesienneUniBiFreq: " + nbFalseNegativeBayesienneUniBiFreq);
		System.out.println("---------------------------");
		System.out.println("nbTruePositiveKNN: " + nbTruePositiveKNN);
		System.out.println("nbFalsePositiveKNN: " + nbFalsePositiveKNN);
		System.out.println("nbTrueNeutralKNN: " + nbTrueNeutralKNN);
		System.out.println("nbFalseNeutralKNN: " + nbFalseNeutralKNN);
		System.out.println("nbTrueNegativeKNN: " + nbTrueNegativeKNN);
		System.out.println("nbFalseNegativeKNN: " + nbFalseNegativeKNN);
		System.out.println("---------------------------");
		System.out.println("nbTruePositiveKeywords: " + nbTruePositiveKeywords);
		System.out.println("nbFalsePositiveKeywords: " + nbFalsePositiveKeywords);
		System.out.println("nbTrueNeutralKeywords: " + nbTrueNeutralKeywords);
		System.out.println("nbFalseNeutralKeywords: " + nbFalseNeutralKeywords);
		System.out.println("nbTrueNegativeKeywords: " + nbTrueNegativeKeywords);
		System.out.println("nbFalseNegativeKeywords: " + nbFalseNegativeKeywords);
		System.out.println("---------------------------");
		
		ratioBayesienneUni = nbErreursBayesienneUni / tweets.size();
		ratioBayesienneUniFreq = nbErreursBayesienneUniFreq / tweets.size();
		ratioBayesienneBi = nbErreursBayesienneBi / tweets.size();
		ratioBayesienneBiFreq = nbErreursBayesienneBiFreq / tweets.size();
		ratioBayesienneUniBi = nbErreursBayesienneUniBi / tweets.size();
		ratioBayesienneUniBiFreq = nbErreursBayesienneUniBiFreq / tweets.size();
		ratioKNN = nbErreursKNN / tweets.size();
		ratioKeywords = nbErreursKeywords / tweets.size();

		System.out.println("Pourcentage erreurs BayesienneUni: " + Utils.round(100 * ratioBayesienneUni, 2) + "%");
		System.out.println("Pourcentage erreurs BayesienneUniFreq: " + Utils.round(100 * ratioBayesienneUniFreq, 2) + "%");
		System.out.println("Pourcentage erreurs BayesienneBi: " + Utils.round(100 * ratioBayesienneBi, 2) + "%");
		System.out.println("Pourcentage erreurs BayesienneBiFreq: " + Utils.round(100 * ratioBayesienneBiFreq, 2) + "%");
		System.out.println("Pourcentage erreurs BayesienneUniBi: " + Utils.round(100 * ratioBayesienneUniBi, 2) + "%");
		System.out.println("Pourcentage erreurs BayesienneUniBiFreq: " + Utils.round(100 * ratioBayesienneUniBiFreq, 2) + "%");
		System.out.println("Pourcentage erreurs KNN: " + Utils.round(100 * ratioKNN, 2) + "%");
		System.out.println("Pourcentage erreurs Keywords: " + Utils.round(100 * ratioKeywords, 2) + "%");


		//
		// affichage du graphique
		//
		showAllInOneBarChart(tweets, tweetsNonAnnotedBayesienneUni, tweetsNonAnnotedBayesienneUniFreq, 
				tweetsNonAnnotedBayesienneBi, tweetsNonAnnotedBayesienneBiFreq, tweetsNonAnnotedBayesienneUniBi, 
				tweetsNonAnnotedBayesienneUniBiFreq, tweetsNonAnnotedKeywords, tweetsNonAnnotedKNN,
				
				ratioKeywords, ratioKNN, ratioBayesienneUni,
				ratioBayesienneUniFreq, ratioBayesienneBi, ratioBayesienneBiFreq,
				ratioBayesienneUniBi, ratioBayesienneUniBiFreq,
		 nbTruePositiveBayesienneUni,  nbTruePositiveBayesienneUniFreq,
		 nbTruePositiveBayesienneBi,  nbTruePositiveBayesienneBiFreq,  nbTruePositiveBayesienneUniBi,
		 nbTruePositiveBayesienneUniBiFreq,  nbTruePositiveKNN,  nbTruePositiveKeywords,
		 nbTrueNeutralBayesienneUni,  nbTrueNeutralBayesienneUniFreq,  nbTrueNeutralBayesienneBi,
		 nbTrueNeutralBayesienneBiFreq,  nbTrueNeutralBayesienneUniBi,  nbTrueNeutralBayesienneUniBiFreq,
		 nbTrueNeutralKNN,  nbTrueNeutralKeywords,  nbTrueNegativeBayesienneUni,  nbTrueNegativeBayesienneUniFreq,
		 nbTrueNegativeBayesienneBi,  nbTrueNegativeBayesienneBiFreq,  nbTrueNegativeBayesienneUniBi,
		 nbTrueNegativeBayesienneUniBiFreq,  nbTrueNegativeKNN,  nbTrueNegativeKeywords);
		

	}
	private static void showAllInOneBarChart(ArrayList<TweetInfo> tweets, ArrayList<TweetInfo> tweetsNonAnnotedBayesienneUni, 
			ArrayList<TweetInfo> tweetsNonAnnotedBayesienneUniFreq, ArrayList<TweetInfo> tweetsNonAnnotedBayesienneBi, 
			ArrayList<TweetInfo> tweetsNonAnnotedBayesienneBiFreq, ArrayList<TweetInfo> tweetsNonAnnotedBayesienneUniBi, 
			ArrayList<TweetInfo> tweetsNonAnnotedBayesienneUniBiFreq, ArrayList<TweetInfo> tweetsNonAnnotedKeywords, 
			ArrayList<TweetInfo> tweetsNonAnnotedKNN,
			double ratioTotalKeywords, double ratioTotalKNN, double ratioTotalBayesienneUni, 
			double ratioTotalBayesienneUniFreq, double ratioTotalBayesienneBi, double ratioTotalBayesienneBiFreq,
			double ratioTotalBayesienneUniBi, double ratioTotalBayesienneUniBiFreq,
			int nbTruePositiveBayesienneUni, int nbTruePositiveBayesienneUniFreq,
			int nbTruePositiveBayesienneBi, int nbTruePositiveBayesienneBiFreq, int nbTruePositiveBayesienneUniBi,
			int nbTruePositiveBayesienneUniBiFreq, int nbTruePositiveKNN, int nbTruePositiveKeywords,
			int nbTrueNeutralBayesienneUni, int nbTrueNeutralBayesienneUniFreq, int nbTrueNeutralBayesienneBi,
			int nbTrueNeutralBayesienneBiFreq, int nbTrueNeutralBayesienneUniBi, int nbTrueNeutralBayesienneUniBiFreq,
			int nbTrueNeutralKNN, int nbTrueNeutralKeywords, int nbTrueNegativeBayesienneUni, int nbTrueNegativeBayesienneUniFreq,
			int nbTrueNegativeBayesienneBi, int nbTrueNegativeBayesienneBiFreq, int nbTrueNegativeBayesienneUniBi,
			int nbTrueNegativeBayesienneUniBiFreq, int nbTrueNegativeKNN, int nbTrueNegativeKeywords)
	{
		// j'avoue, c'est un peu degueu et je le vis mal..., mais bon, on va faire avec =D
		// Pis meme, en fait je pense que ca veut rien dire puisque ca compare les totaux...
		int csvNeg = Utils.getTweetsWithPolarity(tweets, Globals.NEGATIVE_TWEET).size();
		int csvNeutr = Utils.getTweetsWithPolarity(tweets, Globals.NEUTRAL_TWEET).size();
		int csvPos = Utils.getTweetsWithPolarity(tweets, Globals.POSITIVE_TWEET).size();
		System.out.println("CSVNEG: " + csvNeg + " | CSVNEUTR: " + csvNeutr + " | CSVPOS: " + csvPos);
		int knnNeg = Utils.getTweetsWithPolarity(tweetsNonAnnotedKNN, Globals.NEGATIVE_TWEET).size();
		int knnNeutr = Utils.getTweetsWithPolarity(tweetsNonAnnotedKNN, Globals.NEUTRAL_TWEET).size();
		int knnPos = Utils.getTweetsWithPolarity(tweetsNonAnnotedKNN, Globals.POSITIVE_TWEET).size();
		System.out.println("KNNNEG: " + knnNeg + " | KNNNEUTR: " + knnNeutr + " | KNNPOS: " + knnPos);
		int wordNeg = Utils.getTweetsWithPolarity(tweetsNonAnnotedKeywords, Globals.NEGATIVE_TWEET).size();
		int wordNeutr = Utils.getTweetsWithPolarity(tweetsNonAnnotedKeywords, Globals.NEUTRAL_TWEET).size();
		int wordPos = Utils.getTweetsWithPolarity(tweetsNonAnnotedKeywords, Globals.POSITIVE_TWEET).size();
		System.out.println("WORDNEG: " + wordNeg + " | WORDNEUTR: " + wordNeutr + " | WORDPOS: " + wordPos);
		int bayeUniNeg = Utils.getTweetsWithPolarity(tweetsNonAnnotedBayesienneUni, Globals.NEGATIVE_TWEET).size();
		int bayeUniNeutr = Utils.getTweetsWithPolarity(tweetsNonAnnotedBayesienneUni, Globals.NEUTRAL_TWEET).size();
		int bayeUniPos = Utils.getTweetsWithPolarity(tweetsNonAnnotedBayesienneUni, Globals.POSITIVE_TWEET).size();
		System.out.println("BAYEUNINEG: " + bayeUniNeg + " | BAYEUNINEUTR: " + bayeUniNeutr + " | BAYEUNIPOS: " + bayeUniPos);
		int bayeUniFreqNeg = Utils.getTweetsWithPolarity(tweetsNonAnnotedBayesienneUniFreq, Globals.NEGATIVE_TWEET).size();
		int bayeUniFreqNeutr = Utils.getTweetsWithPolarity(tweetsNonAnnotedBayesienneUniFreq, Globals.NEUTRAL_TWEET).size();
		int bayeUniFreqPos = Utils.getTweetsWithPolarity(tweetsNonAnnotedBayesienneUniFreq, Globals.POSITIVE_TWEET).size();
		System.out.println("BAYEUNIFREQNEG: " + bayeUniFreqNeg + " | BAYEUNIFREQNEUTR: " + bayeUniFreqNeutr + " | BAYEUNIFREQPOS: " + bayeUniFreqPos);
		int bayeBiNeg = Utils.getTweetsWithPolarity(tweetsNonAnnotedBayesienneBi, Globals.NEGATIVE_TWEET).size();
		int bayeBiNeutr = Utils.getTweetsWithPolarity(tweetsNonAnnotedBayesienneBi, Globals.NEUTRAL_TWEET).size();
		int bayeBiPos = Utils.getTweetsWithPolarity(tweetsNonAnnotedBayesienneBi, Globals.POSITIVE_TWEET).size();
		System.out.println("BAYEBINEG: " + bayeBiNeg + " | BAYEBINEUTR: " + bayeBiNeutr + " | BAYEBIPOS: " + bayeBiPos);
		int bayeBiFreqNeg = Utils.getTweetsWithPolarity(tweetsNonAnnotedBayesienneBiFreq, Globals.NEGATIVE_TWEET).size();
		int bayeBiFreqNeutr = Utils.getTweetsWithPolarity(tweetsNonAnnotedBayesienneBiFreq, Globals.NEUTRAL_TWEET).size();
		int bayeBiFreqPos = Utils.getTweetsWithPolarity(tweetsNonAnnotedBayesienneBiFreq, Globals.POSITIVE_TWEET).size();
		System.out.println("BAYEBIFREQNEG: " + bayeBiFreqNeg + " | BAYEBIFREQNEUTR: " + bayeBiFreqNeutr + " | BAYEBIFREQPOS: " + bayeBiFreqPos);
		int bayeUniBiNeg = Utils.getTweetsWithPolarity(tweetsNonAnnotedBayesienneUniBi, Globals.NEGATIVE_TWEET).size();
		int bayeUniBiNeutr = Utils.getTweetsWithPolarity(tweetsNonAnnotedBayesienneUniBi, Globals.NEUTRAL_TWEET).size();
		int bayeUniBiPos = Utils.getTweetsWithPolarity(tweetsNonAnnotedBayesienneUniBi, Globals.POSITIVE_TWEET).size();
		System.out.println("BAYEUNIBINEG: " + bayeUniBiNeg + " | BAYEUNIBINEUTR: " + bayeUniBiNeutr + " | BAYEUNIBIPOS: " + bayeUniBiPos);
		int bayeUniBiFreqNeg = Utils.getTweetsWithPolarity(tweetsNonAnnotedBayesienneUniBiFreq, Globals.NEGATIVE_TWEET).size();
		int bayeUniBiFreqNeutr = Utils.getTweetsWithPolarity(tweetsNonAnnotedBayesienneUniBiFreq, Globals.NEUTRAL_TWEET).size();
		int bayeUniBiFreqPos = Utils.getTweetsWithPolarity(tweetsNonAnnotedBayesienneUniBiFreq, Globals.POSITIVE_TWEET).size();
		System.out.println("BAYEUNIBIFREQNEG: " + bayeUniBiFreqNeg + " | BAYEUNIBIFREQNEUTR: " + bayeUniBiFreqNeutr + " | BAYEUNIBIFREQPOS: " + bayeUniBiFreqPos);

		AllInOneChart.launchWithData(
				new int[] { csvNeg, csvNeutr, csvPos }, // csvReference (nbNegatifs, nbNeutres, nbPositifs) 
				new int[] { knnNeg, knnNeutr, knnPos }, // KNN
				new int[] { wordNeg, wordNeutr, wordPos }, // keywords
				new int[] { bayeUniNeg, bayeUniNeutr, bayeUniPos }, // BayesUni
				new int[] { bayeUniFreqNeg, bayeUniFreqNeutr, bayeUniFreqPos }, // BayesUni
				new int[] { bayeBiNeg, bayeBiNeutr, bayeBiPos }, // BayesBi
				new int[] { bayeBiFreqNeg, bayeBiFreqNeutr, bayeBiFreqPos }, // BayesBiFreq
				new int[] { bayeUniBiNeg, bayeUniBiNeutr, bayeUniBiPos }, // BayesUniBi
				new int[] { bayeUniBiFreqNeg, bayeUniBiFreqNeutr, bayeUniBiFreqPos }, // BayesUniBiFreq
				
				ratioTotalKeywords, ratioTotalKNN, ratioTotalBayesienneUni, ratioTotalBayesienneUniFreq,
				ratioTotalBayesienneBi, ratioTotalBayesienneBiFreq, ratioTotalBayesienneUniBi, ratioTotalBayesienneUniBiFreq,

				nbTruePositiveBayesienneUni,  nbTruePositiveBayesienneUniFreq,
				nbTruePositiveBayesienneBi,  nbTruePositiveBayesienneBiFreq,  nbTruePositiveBayesienneUniBi,
				nbTruePositiveBayesienneUniBiFreq,  nbTruePositiveKNN,  nbTruePositiveKeywords,
				nbTrueNeutralBayesienneUni,  nbTrueNeutralBayesienneUniFreq,  nbTrueNeutralBayesienneBi,
				nbTrueNeutralBayesienneBiFreq,  nbTrueNeutralBayesienneUniBi,  nbTrueNeutralBayesienneUniBiFreq,
				nbTrueNeutralKNN,  nbTrueNeutralKeywords,  nbTrueNegativeBayesienneUni,  nbTrueNegativeBayesienneUniFreq,
				nbTrueNegativeBayesienneBi,  nbTrueNegativeBayesienneBiFreq,  nbTrueNegativeBayesienneUniBi,
				nbTrueNegativeBayesienneUniBiFreq,  nbTrueNegativeKNN,  nbTrueNegativeKeywords
				);
	}

}
