package pjetwitter.classifier;

import pjetwitter.TweetInfo;

public class Voisin {
	private TweetInfo tweetInstance;
	private double distance;

	
	public Voisin(TweetInfo tweetInstance, double distance) {
		this.setInstance(tweetInstance);
		this.setDistance(distance);
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getDistance() {
		return distance;
	}

	public void setInstance(TweetInfo instance) {
		this.tweetInstance = instance;
	}

	public TweetInfo getInstance() {
		return tweetInstance;
	}
}
