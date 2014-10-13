package pjetwitter;

import java.util.List;
import java.util.Map;

import twitter4j.Query;
import twitter4j.Query.ResultType;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class PJETwitter {
	
	private String proxyHost;
	private int proxyPort;
	private String proxyUser;
	private String proxyPassword;
	private boolean proxyUsed;
	
	private int countResult;
	private ResultType resultType;
	
	public PJETwitter() {
		this.proxyUsed = false;
		this.proxyHost = "cache-etu.univ-lille1.fr";
		this.proxyPort = 3128;
		
		this.countResult = 100;
		
		this.resultType = ResultType.mixed;
	}

	private ConfigurationBuilder getConfigurationBuilder() {
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setDebugEnabled(true);
		configurationBuilder.setOAuthConsumerKey(PersonalTokens.CONSUMER_KEY);
		configurationBuilder.setOAuthConsumerSecret(PersonalTokens.CONSUMER_SECRET);
		configurationBuilder.setOAuthAccessToken(PersonalTokens.ACCESS_TOKEN);
		configurationBuilder.setOAuthAccessTokenSecret(PersonalTokens.ACCESS_TOKEN_SECRET);
		
		if(this.proxyUsed) {
			configurationBuilder.setHttpProxyHost(this.proxyHost);
			configurationBuilder.setHttpProxyPort(this.proxyPort);
			configurationBuilder.setHttpProxyUser(this.proxyUser);
			configurationBuilder.setHttpProxyPassword(this.proxyPassword);
		}
		
		return configurationBuilder;
	}
	
	public boolean enableProxy() {
		if(this.isValidHost() && this.isValidPort() && this.isValidUser() && this.isValidPassword()) {
			this.proxyUsed = true;
			return true;
		}
		
		return false;
	}
	
	public void disableProxy() {
		this.proxyUsed = false;
	}
	
	public boolean isProxyUsed() {
		return this.proxyUsed;
	}
	
	private boolean isValidHost() {
		return this.proxyHost != null && !this.proxyHost.isEmpty();
	}
	
	private boolean isValidPort() {		
		return (this.proxyPort >= 0) && (this.proxyPort <= 65535);
	}
	
	private boolean isValidUser() {
		return this.proxyUser != null && !this.proxyUser.isEmpty();
	}
	
	private boolean isValidPassword() {
		return this.proxyPassword != null && !this.proxyPassword.isEmpty();
	}
	
	public String getProxyHost() {
		return this.proxyHost;
	}
	
	public int getProxyPort() {
		return this.proxyPort;
	}
	
	public String getProxyUser() {
		return this.proxyUser;
	}
	
	public String getProxyPassword() {
		return this.proxyPassword;
	}
	
	public int getCountResult() {
		return this.countResult;
	}
	
	public void setCountResult(int countResult) {
		if(countResult > 0) {
			this.countResult = countResult;
		}
		
		if(countResult > 100) {
			this.countResult = 100;
		}
	}
	
	public ResultType getResultType() {
		return this.resultType;
	}
	
	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}
	
	public boolean addProxyConfig(String host, int port, String user, String password) {
		this.proxyHost = host;
		this.proxyPort = port;
		this.proxyUser = user;
		this.proxyPassword = password;
		
		return false;
	}

	public QueryResult search(String requestedWord) {
		try {
			TwitterFactory twitterFactory = new TwitterFactory(this.getConfigurationBuilder().build());
			Twitter twitter = twitterFactory.getInstance();
			
			Query query = new Query(requestedWord);
			
			query.setResultType(this.resultType);
			
			query.setCount(this.countResult);

			return twitter.search(query);
		} catch (TwitterException ex) {
			return null;
		}
	}
	
	public int getRateLimit() {
		try {
			TwitterFactory twitterFactory = new TwitterFactory(this.getConfigurationBuilder().build());
			Twitter twitter = twitterFactory.getInstance();

			Map<String, RateLimitStatus> rateLimitStatusMap = twitter.getRateLimitStatus();

			RateLimitStatus rateLimitStatus = rateLimitStatusMap.get("/search/tweets");
			
			return rateLimitStatus.getRemaining();
		} catch (TwitterException ex) {
			return 180;
		}
	}
}
