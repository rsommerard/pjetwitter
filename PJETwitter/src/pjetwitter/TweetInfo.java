package pjetwitter;

import helper.Constants;

import java.util.Date;

import twitter4j.Status;

public class TweetInfo implements Comparable<TweetInfo>, Cloneable
{
	public void setTweetPolarity(int tweetPolarity)
	{
		this.tweetPolarity = tweetPolarity;
	}

	long tweetID;
	String tweetPublisher;
	String tweetText;
	Date tweetDate;
	String usedRequest;
	int tweetPolarity;

	public TweetInfo(long tweetID, String tweetPublisher, String tweetText, Date tweetDate, String usedRequest, int tweetPolarity)
	{
		this.tweetID = tweetID;
		this.tweetPublisher = tweetPublisher;
		this.tweetText = tweetText;
		this.tweetDate = tweetDate;
		this.usedRequest = usedRequest;
		this.tweetPolarity = tweetPolarity;
	}


	public TweetInfo(Status status, String usedRequest)
	{
		this(status.getId(), status.getUser().getScreenName(), status.getText(), status.getCreatedAt(), usedRequest, Constants.NON_ANNOTATED_TWEET);
	}

	public long getTweetID()
	{
		return tweetID;
	}

	public String getTweetPublisher()
	{
		return tweetPublisher;
	}

	public String getTweetText()
	{
		return tweetText;
	}

	public Date getTweetDate()
	{
		return tweetDate;
	}

	public String getUsedRequest()
	{
		return usedRequest;
	}

	public int getTweetPolarity()
	{
		return tweetPolarity;
	}

	@Override
	public String toString()
	{
		return "[" + tweetID + " - " + tweetPublisher + " - " + tweetText + " - " + tweetDate + " - " + usedRequest + " - " + tweetPolarity + "]";
	}


	@Override
	/**
	 * Retourne 0 si les tweets ont les mêmes IDs
	 */
	public int compareTo(TweetInfo o)
	{
		if (this.tweetID < o.tweetID)
			return -1;
		else if (this.tweetID > o.tweetID)
			return 1;
		else
			return 0;
	}


	@Override
	public Object clone()
	{
		TweetInfo jouet = null;
		try
		{
			jouet = (TweetInfo) super.clone();
		}
		catch (CloneNotSupportedException cnse)
		{
			cnse.printStackTrace(System.err);
		}
		return jouet;
	}

}
