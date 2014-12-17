package helper;

import helper.csv.CsvHelper;
import helper.csv.CsvSingletons;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pjetwitter.TweetInfo;

public class Utils
{
	public static TweetInfo getById(List<TweetInfo> liste, long id)
	{
		for (TweetInfo t : liste)
			if (t.getTweetID() == id)
				return t;


		return null;
	}

	public static ArrayList<TweetInfo> getTweetsWithPolarity(List<TweetInfo> tweetsParam, int polarity)
	{
		ArrayList<TweetInfo> tweets = new ArrayList<TweetInfo>();

		for (TweetInfo tweet : tweetsParam)
		{
			if (tweet.getTweetPolarity() == polarity)
			{
				tweets.add((TweetInfo) tweet.clone());
			}
		}

		return tweets;
	}

	public static void removeAlreadyAnnotatedTweetsFrom(List<TweetInfo> liste)
	{
		CsvHelper csvReference = CsvSingletons.getInstance().referenceCsv;

		for (Iterator<TweetInfo> iterator = liste.iterator(); iterator.hasNext();)
		{
			TweetInfo tweet = iterator.next();

			if (csvReference.idExist(tweet.getTweetID()))
				iterator.remove();
		}
	}


	public static void createNewBaseFrom(List<TweetInfo> oldBase, List<TweetInfo> api)
	{
		List<TweetInfo> newTweets;

		if (oldBase.size() == 0)
		{
			newTweets = new ArrayList<TweetInfo>();
			for (TweetInfo apiTweet : api)
			{
				// déréférence le tweet qui provient de l'api.
				// On peut ainsi modifier sa polarité sans le modifier par référence dans api
				// (newTweets va être écrit dans csvBase, toutes les polaritées doivent être à NON_ANNOTATED)
				TweetInfo clone = (TweetInfo) apiTweet.clone();
				newTweets.add(clone);
			}

		}
		else
		{
			newTweets = new ArrayList<TweetInfo>(oldBase);
			List<TweetInfo> tmpNewTweets = new ArrayList<TweetInfo>(newTweets);


			// /!\ O(n²)
			for (TweetInfo apiTweet : api)
			{
				for (TweetInfo newTweet : tmpNewTweets)
				{
					if (apiTweet.compareTo(newTweet) != 0)
					{
						TweetInfo clone = (TweetInfo) apiTweet.clone();
						newTweets.add(clone);
					}
				}
			}
		}


		CsvHelper csv = CsvSingletons.getInstance().baseCsv;
		// suppression de base.csv avant la réecriture (éviter le appendFile)
		csv.deleteIfExists();

		for (TweetInfo tweet : newTweets)
		{
			csv.write(tweet, false);
		}

	}


	public static Date stringToDate(String sDate) throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat(Globals.DATE_FORMAT);
		return sdf.parse(sDate);
	}

	public static String DateToString(Date sDate)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(Globals.DATE_FORMAT);
		return sdf.format(sDate);
	}

	public static String cleanTweet(String tweetText)
	{
		tweetText = regexRemoveMatched(tweetText, Globals.TWITTER_USERNAME_REGEX);
		tweetText = regexRemoveMatched(tweetText, Globals.TWITTER_HASH_REGEX);
		tweetText = regexRemoveMatched(tweetText, Globals.TWITTER_EMOTICON_REGEX);
		tweetText = regexRemoveMatched(tweetText, Globals.TWITTER_URL_REGEX);

		tweetText = tweetText.replace("\n", " ");


		tweetText = Normalizer.normalize(tweetText, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		tweetText = tweetText.toLowerCase();


		// Suppression des caractères spéciaux (voir ascii)
		for (int i = 33; i <= 38; i++)
			tweetText = tweetText.replace(String.valueOf((char) i), "");
		for (int i = 40; i <= 47; i++)
			tweetText = tweetText.replace(String.valueOf((char) i), "");
		for (int i = 58; i <= 64; i++)
			tweetText = tweetText.replace(String.valueOf((char) i), "");
		for (int i = 91; i <= 96; i++)
			tweetText = tweetText.replace(String.valueOf((char) i), "");
		for (int i = 123; i <= 126; i++)
			tweetText = tweetText.replace(String.valueOf((char) i), "");

		String[] tweetTextSplitted = tweetText.split(" ");
		tweetText = "";
		for (String str : tweetTextSplitted)
		{
			if (!str.equals(" ") && !str.equals(""))
				tweetText += " " + str;
		}

		tweetText = tweetText.trim();

		return tweetText;
	}

	public static String regexRemoveMatched(String text, String regex)
	{
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);

		while (matcher.find())
		{
			text = matcher.replaceAll("");
		}

		return text;
	}

	public static int probabilitiesToTweetPolarity(double probNegative, double probNeutral, double probPositive, boolean invertResult)
	{
		if (invertResult)
		{
			double min = Math.min(probNegative, Math.min(probNeutral, probPositive));

			if (probNegative == probNeutral && probNeutral == probPositive)
				return Globals.NEUTRAL_TWEET;
			else if (min == probNegative)
				return Globals.NEGATIVE_TWEET;
			else if (min == probPositive)
				return Globals.POSITIVE_TWEET;
			else
				return Globals.NEUTRAL_TWEET;

		}
		else
		{
			double max = Math.max(probNegative, Math.max(probNeutral, probPositive));

			if (probNegative == probNeutral && probNeutral == probPositive)
				return Globals.NEUTRAL_TWEET;
			else if (max == probNegative)
				return Globals.NEGATIVE_TWEET;
			else if (max == probPositive)
				return Globals.POSITIVE_TWEET;
			else
				return Globals.NEUTRAL_TWEET;

		}

	}


	public static String polarityToString(int polarity)
	{
		if (polarity == Globals.NEGATIVE_TWEET)
			return "NegativeTweet";
		if (polarity == Globals.POSITIVE_TWEET)
			return "PositiveTweet";
		if (polarity == Globals.NEUTRAL_TWEET)
			return "NeutralTweet";
		if (polarity == Globals.NON_ANNOTATED_TWEET)
			return "NonAnnotatedTweet";
		else
			return "<UnknownPolarity>";
	}

	public static double round(double a, int nbDecimals)
	{
		int val = (int) Math.pow(10, nbDecimals);
		return (double) Math.round(a * val) / val;
	}

}
