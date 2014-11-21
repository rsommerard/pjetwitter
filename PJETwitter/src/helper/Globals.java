package helper;

public class Globals
{
	public final static String DATE_FORMAT = "dd-MM-yy hh:mm:ss";

	public static final String TWITTER_URL_REGEX = "https?://t.co/\\w+";
	public static final String TWITTER_USERNAME_REGEX = "(^|[^@\\w])@(\\w{1,15})\\b";
	public static final String TWITTER_HASH_REGEX = "(?:(?<=\\s)|\'|^)#(\\w*[A-Za-z_#]+\\w*)";
	public static final String TWITTER_EMOTICON_REGEX = "((?::|;|=)(?:-)?(?:\\)|D|P|d|p))";
	public static final String US_ASCII_ONLY_REGEX = "[^\\p{ASCII}]";
	public static final String US_ALPHANUM_ONLY_REGEX = "[^\\P{Alnum}]";


	public final static String CSV_BASE_LOCATION = "data/base.csv";
	public final static String CSV_REFERENCE_LOCATION = "data/reference.csv";
	public final static String CSV_FINAL_LOCATION = "data/final.csv";
	public final static char CSV_DELIMITER = ';';
	
	

	public final static String POSITIVE_FILE_PATH = "data/positive.txt";
	public final static String NEGATIVE_FILE_PATH = "data/negative.txt";
	

	public final static int NON_ANNOTATED_TWEET = 1;
	public final static int NEGATIVE_TWEET = 0;
	public final static int NEUTRAL_TWEET = 2;
	public final static int POSITIVE_TWEET = 4;

	public final static String NON_ANNOTATED_TWEET_STR = "Not Annotated";
	public final static String NEGATIVE_TWEET_STR = "Negative";
	public final static String NEUTRAL_TWEET_STR = "Neutral";
	public final static String POSITIVE_TWEET_STR = "Positive";
	
	public final static String LANG_ALL = "All";
	public final static String LANG_EN = "English";
	public final static String LANG_FR = "French";
	
	public final static int KNN_NB_PROCHES_VOISINS = 10;
}
