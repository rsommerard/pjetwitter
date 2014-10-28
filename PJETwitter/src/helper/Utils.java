package helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

	public static List<TweetInfo> removeAlreadyAnnotatedTweetsFrom(List<TweetInfo> liste)
	{
		CsvHelper finalCsv = new CsvHelper(Constants.CSV_FINAL_LOCATION, Constants.CSV_DELIMITER);

		List<TweetInfo> retour = new ArrayList<TweetInfo>();
		Collections.copy(retour, liste);


		for (int i = 0; i < liste.size(); i++)
		{
			TweetInfo tweet = liste.get(i);
			if (finalCsv.idExist(tweet.getTweetID()))
				retour.remove(i);
		}
		
		return retour;

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
