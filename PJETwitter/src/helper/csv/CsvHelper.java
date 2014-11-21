package helper.csv;

import helper.Globals;
import helper.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import pjetwitter.TweetInfo;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;



@SuppressWarnings("unused")
public class CsvHelper
{
	// ex.: CsvHelper csvBase = new CsvHelper("chemin");
	private List<TweetInfo> csvCache;
	private CsvReaderHelper reader;
	private CsvWriterHelper writer;

	private String csv_location;


	public CsvHelper(String csv_location)
	{
		super();
		this.csv_location = csv_location;

		this.reader = new CsvReaderHelper(csv_location);
		this.writer = new CsvWriterHelper(csv_location);
		this.csvCache = new ArrayList<TweetInfo>();
	}

	public void write(TweetInfo tweet, boolean clean)
	{
		if (idExist(tweet.getTweetID()))
			return;

		writer.write(tweet, clean);
		csvCache.add(tweet);
	}

	/**
	 * Lit tout le csv et le place en cache
	 */
	public List<TweetInfo> readAll()
	{
		List<TweetInfo> readerResult;

		if (!fileExists())
			readerResult = new ArrayList<TweetInfo>();
		else
			readerResult = reader.readAll();

		csvCache = new ArrayList<TweetInfo>(readerResult);
		return readerResult;
	}

	public boolean idExist(long id)
	{
		if (!fileExists())
			return false;

		for (TweetInfo t : csvCache)
		{
			if (t.getTweetID() == id)
				return true;
		}

		return false;
	}


	public List<TweetInfo> getTweetsWithSameRequest(String usedRequest) throws IOException
	{
		List<TweetInfo> tweets = new ArrayList<TweetInfo>();

		for (TweetInfo t : csvCache)
		{
			if (t.getUsedRequest().equals(usedRequest))
				tweets.add(t);
		}

		return tweets;
	}


	public boolean fileExists()
	{
		File file = new File(csv_location);
		return file.exists();
	}

	/**
	 * Détruit le fichier et le cache
	 */
	public void deleteIfExists()
	{
		File file = new File(csv_location);
		if (file.exists())
		{
			file.delete();
			this.csvCache = new ArrayList<TweetInfo>();
		}
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

