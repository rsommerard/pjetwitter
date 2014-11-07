package helper;

import helper.csv.CsvHelper;
import helper.csv.CsvSingletons;

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
				clone.setTweetPolarity(Constants.NON_ANNOTATED_TWEET);
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
						clone.setTweetPolarity(Constants.NON_ANNOTATED_TWEET);
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
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		return sdf.parse(sDate);
	}

	public static String DateToString(Date sDate)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		return sdf.format(sDate);
	}

	public static String cleanTweet(String tweetText)
	{
		tweetText = regexRemoveMatched(tweetText, Constants.TWITTER_USERNAME_REGEX);
		tweetText = regexRemoveMatched(tweetText, Constants.TWITTER_HASH_REGEX);
		tweetText = regexRemoveMatched(tweetText, Constants.TWITTER_EMOTICON_REGEX);
		tweetText = regexRemoveMatched(tweetText, Constants.TWITTER_URL_REGEX);

		tweetText = tweetText.replace(";", "");

		return tweetText;
	}

	public static String regexRemoveMatched(String text, String regex)
	{
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		if (matcher.find())
		{
			text = matcher.replaceAll("");
		}

		return text;
	}

}
