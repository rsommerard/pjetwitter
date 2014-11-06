package helper.csv;

import helper.Constants;
import helper.Utils;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import pjetwitter.TweetInfo;
import com.csvreader.CsvWriter;

public class CsvWriterHelper extends AbstractCsvHelper
{
	public CsvWriterHelper(String csv_location)
	{
		super(csv_location);
	}

	private void write(long id, String user, String text, Date date, String usedRequest, int tweetPolarity, boolean clean) throws Exception
	{
		String outputFile = csv_location;

		// before we open the file check to see if it already exists
		boolean alreadyExists = new File(outputFile).exists();

		if (!alreadyExists)
		{
			File file = new File(outputFile);
			file.createNewFile();
		}

		// use FileWriter constructor that specifies open for appending
		CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), Constants.CSV_DELIMITER);

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
		try
		{
			write(tweet.getTweetID(), tweet.getTweetPublisher(), tweet.getTweetText(), tweet.getTweetDate(), tweet.getUsedRequest(),
					tweet.getTweetPolarity(), clean);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}



}
