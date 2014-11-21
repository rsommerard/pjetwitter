package helper.csv;

import helper.Globals;
import helper.Utils;
import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import pjetwitter.TweetInfo;
import com.csvreader.CsvReader;

public class CsvReaderHelper extends AbstractCsvHelper
{
	public CsvReaderHelper(String csv_location)
	{
		super(csv_location);
	}

	public List<TweetInfo> readAll()
	{
		List<TweetInfo> listeTweets = new LinkedList<TweetInfo>();

		File file = new File(csv_location);
		if (!file.exists())
			return listeTweets;

		try
		{
			CsvReader products = new CsvReader(csv_location, Globals.CSV_DELIMITER);

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



}
