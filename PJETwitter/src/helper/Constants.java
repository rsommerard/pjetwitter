package helper;

public class Constants
{
	public final static String DATE_FORMAT = "dd-MM-yy hh:mm:ss";

	public static final String TWITTER_URL_REGEX = "https?://t.co/\\w+";
	public static final String TWITTER_USERNAME_REGEX = "(^|[^@\\w])@(\\w{1,15})\\b";
	public static final String TWITTER_HASH_REGEX = "(?:(?<=\\s)|^)#(\\w*[A-Za-z_]+\\w*)";
	public static final String TWITTER_EMOTICON_REGEX = "((?::|;|=)(?:-)?(?:\\)|D|P|d|p))";


	public final static String CSV_BASE_LOCATION = "data/base.csv";
	public final static String CSV_FINAL_LOCATION = "data/final.csv";
	public final static char CSV_DELIMITER = ';';
	

	public final static int NON_ANNOTATED_TWEET = 1;
	public final static int NEGATIVE_TWEET = 0;
	public final static int NEUTRAL_TWEET = 2;
	public final static int POSITIVE_TWEET = 4;

	public final static String NON_ANNOTATED_TWEET_STR = "Not Annotated";
	public final static String NEGATIVE_TWEET_STR = "Negative";
	public final static String NEUTRAL_TWEET_STR = "Neutral";
	public final static String POSITIVE_TWEET_STR = "Positive";
	

}
