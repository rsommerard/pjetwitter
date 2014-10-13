package pjetwitter;

import java.util.Date;

import twitter4j.Status;

public class TweetInfo
{
	long tweetID;
	String tweetPublisher;
	String tweetText;
	Date tweetDate;
	String usedRequest;
	int tweetPolarity;


	/**
	 * @param tweetID
	 * @param tweetPublisher
	 * @param tweetText
	 * @param tweetDate
	 * @param usedRequest
	 * @param tweetPolarity
	 */
	public TweetInfo(long tweetID, String tweetPublisher, String tweetText, Date tweetDate, String usedRequest, int tweetPolarity)
	{
		this.tweetID = tweetID;
		this.tweetPublisher = tweetPublisher;
		this.tweetText = tweetText;
		this.tweetDate = tweetDate;
		this.usedRequest = usedRequest;
		this.tweetPolarity = tweetPolarity;
	}
	
	public TweetInfo(Status status, String usedRequest) {
		this(status.getId(), status.getUser().getScreenName(), status.getText(), status.getCreatedAt(), usedRequest, -1);
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


}
