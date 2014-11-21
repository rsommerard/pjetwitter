package pjetwitter.classifier;

import helper.Globals;
import helper.Utils;

import java.util.*;

import pjetwitter.TweetInfo;

public class KNNClassifier
{
	private int nbVoisin = 5;
	
	private ArrayList<TweetInfo> listTweets;
	
	public KNNClassifier() {
		this.listTweets = new ArrayList<TweetInfo>(helper.csv.CsvSingletons.getInstance().referenceCsv.readAll());
	}
	
	public int classify(String tweet) {
		ArrayList<Voisin> distances = calculateDistances(listTweets, tweet);
		ArrayList<Voisin> voisins = new ArrayList<Voisin>();

		for (int i = 0; i < this.nbVoisin; i++)
		{
			int index = this.minDistanceVoisin(distances);
			voisins.add(distances.get(index));
			distances.remove(index);
		}
		
		int classification = determineMajority(voisins);
		
		System.out.println("Recherche des " + this.nbVoisin + " plus proches voisins de:");
		System.out.println("\"" + tweet + "\"");
		this.printNeighbors(voisins);
		System.out.println("\nPolarité pour le tweet: " + Utils.polarityToString(classification));
		
		return classification;
	}

	private int minDistanceVoisin(ArrayList<Voisin> distances) {
		int indexMin = 0;
		double distanceMin = distances.get(0).getDistance();
		
		for(int i = 1; i < distances.size(); i++) {
			if(distances.get(i).getDistance() < distanceMin) {
				indexMin = i;
				distanceMin = distances.get(i).getDistance();
			}
		}
		
		return indexMin;
	}

	private void printNeighbors(ArrayList<Voisin> voisins)
	{
		int i = 0;
		for (Voisin voisin : voisins)
		{
			TweetInfo tweet = voisin.getInstance();

			System.out.println("\nVoisin " + (i + 1) + ", ID=" + tweet.getTweetID() + " (" + tweet.getTweetPublisher() + ")");
			System.out.println("  distance: " + voisin.getDistance());
			i++;
		}
	}

	private int determineMajority(ArrayList<Voisin> voisins)
	{
		int pos = 0, neg = 0, neutr = 0;

		for (int i = 0; i < voisins.size(); i++)
		{
			TweetInfo tweet = voisins.get(i).getInstance();
			if (tweet.getTweetPolarity() == Globals.POSITIVE_TWEET)
			{
				pos++;
			}
			if (tweet.getTweetPolarity() == Globals.NEGATIVE_TWEET)
			{
				neg++;
			}
			if (tweet.getTweetPolarity() == Globals.NEUTRAL_TWEET)
			{
				neutr++;
			}
		}


		if (pos > neg)
		{
			if (neutr > pos)
				return Globals.NEUTRAL_TWEET;
			else
				return Globals.POSITIVE_TWEET;
		}
		else
		{
			if (neutr > neg)
				return Globals.NEUTRAL_TWEET;
			else
				return Globals.NEGATIVE_TWEET;
		}
	}

	private ArrayList<Voisin> calculateDistances(ArrayList<TweetInfo> instances, String tweet)
	{
		ArrayList<Voisin> distances = new ArrayList<Voisin>();
		double distance = 0;

		for (int i = 0; i < instances.size(); i++)
		{
			TweetInfo instance = instances.get(i);
			distance = distance(instance.getTweetText(), tweet);


			Voisin voisin = new Voisin(instance, distance);
			distances.add(voisin);
		}

		for (int i = 0; i < distances.size(); i++)
		{
			for (int j = 0; j < distances.size() - i - 1; j++)
			{
				if (distances.get(j).getDistance() > distances.get(j + 1).getDistance())
				{
					Voisin tempNeighbor = distances.get(j);
					distances.set(j, distances.get(j + 1));
					distances.set(j + 1, tempNeighbor);
				}
			}
		}

		return distances;
	}

	private double distance(String tweet1, String tweet2)
	{
		String[] tweet1Splited = tweet1.split("\\s");
		String[] tweet2Splited = tweet2.split("\\s");

		List<String> tweet2List = new ArrayList<String>();
		for (String str : tweet2Splited)
		{
			tweet2List.add(str);
		}

		double count = 0;
		for (String str : tweet1Splited)
		{
			if (tweet2List.contains(str))
			{
				count++;
			}
		}

		double nbElement = tweet1Splited.length + tweet2Splited.length;

		return ((nbElement - count * 2) / nbElement);
	}

}
