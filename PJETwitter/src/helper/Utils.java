package helper;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pjetwitter.TweetInfo;

public class Utils
{
	public static TweetInfo getById(List<TweetInfo> liste, int id)
	{
		for (TweetInfo t : liste)
			if (t.getTweetID() == id)
				return t;


		return null;
	}



	public static Date stringToDate(String sDate) throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		return sdf.parse(sDate);
	}

	public static String cleanTweet(String tweetText)
	{
		tweetText = regexRemoveMatched(tweetText, Constants.TWITTER_USERNAME_REGEX);
		tweetText = regexRemoveMatched(tweetText, Constants.TWITTER_HASH_REGEX);
		tweetText = regexRemoveMatched(tweetText, Constants.TWITTER_EMOTICON_REGEX);
		tweetText = regexRemoveMatched(tweetText, Constants.TWITTER_URL_REGEX);

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
