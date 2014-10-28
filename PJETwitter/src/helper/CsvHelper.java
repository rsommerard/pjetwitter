package helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import pjetwitter.TweetInfo;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;



public class CsvHelper
{
	private String csv_location;
	private char csv_delimiter;

	/**
	 * @param csv_location
	 * @param csv_delimiter
	 */
	public CsvHelper(String csv_location, char csv_delimiter)
	{
		super();
		this.csv_location = csv_location;
		this.csv_delimiter = csv_delimiter;
	}

	public void write(long id, String user, String text, Date date, String usedRequest, int tweetPolarity, boolean clean) throws Exception
	{
		String outputFile = csv_location;

		// before we open the file check to see if it already exists
		boolean alreadyExists = new File(outputFile).exists();
		
		if(!alreadyExists) {
			File file = new File(outputFile);
			file.createNewFile();
		}
		
		if(idExist(id))
			return;
		
			// use FileWriter constructor that specifies open for appending
			
			CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), csv_delimiter);

			// if the file didn't already exist then we need to write out the
			// header line
			if (!alreadyExists)
			{
				csvOutput.write("TweetID");
				csvOutput.write("TweetPublisher");
				csvOutput.write("TweetText");
				csvOutput.write("TweetDate");
				csvOutput.write("UsedRequest");
				csvOutput.write("TweetPolarity");
				csvOutput.endRecord();
			}
			// else assume that the file already has the correct header line
			String dateFormatee = Utils.DateToString(date);

			csvOutput.write(String.valueOf(id));
			csvOutput.write(user);

			if (clean)
				csvOutput.write(Utils.cleanTweet(text));
			else
				csvOutput.write(text);

			csvOutput.write(dateFormatee);
			csvOutput.write(usedRequest);
			csvOutput.write(String.valueOf(tweetPolarity));
			csvOutput.endRecord();

			csvOutput.close();
	}

	public void write(TweetInfo tweet, boolean clean)
	{
		try {
			write(tweet.getTweetID(), tweet.getTweetPublisher(), tweet.getTweetText(), tweet.getTweetDate(), tweet.getUsedRequest(),
					tweet.getTweetPolarity(), clean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<TweetInfo> readAll()
	{
		List<TweetInfo> listeTweets = new LinkedList<TweetInfo>();

		try
		{
			CsvReader products = new CsvReader(csv_location, csv_delimiter);

			products.readHeaders();

			while (products.readRecord())
			{
				long tweetID = Long.valueOf(products.get("TweetID"));
				String tweetPublisher = products.get("TweetPublisher");
				String tweetText = products.get("TweetText");
				Date tweetDate = Utils.stringToDate(products.get("TweetDate"));
				String usedRequest = products.get("UsedRequest");
				int tweetPolarity = Integer.valueOf(products.get("TweetPolarity"));

				TweetInfo tweet = new TweetInfo(tweetID, tweetPublisher, tweetText, tweetDate, usedRequest, tweetPolarity);
				listeTweets.add(tweet);
			}

			products.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return listeTweets;
	}
		

	public boolean idExist(long id)
	{
		try
		{
			CsvReader products = new CsvReader(csv_location, csv_delimiter);

			products.readHeaders();

			while (products.readRecord())
			{
				long tweetID = Long.valueOf(products.get("TweetID"));
				if (tweetID == id)
					return true;
			}

			products.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}








}

//

//

//

//

//

//

//

//

//

//

//

//

//

//

//

//

//

