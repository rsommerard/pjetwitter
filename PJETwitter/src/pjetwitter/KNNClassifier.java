package pjetwitter;

import java.util.ArrayList;
import java.util.List;

public class KNNClassifier {

	private static double distance(String tweet1, String tweet2) {
		String[] tweet1Splited = tweet1.split("\\s");
		String[] tweet2Splited = tweet2.split("\\s");
		
		List<String> tweet2List = new ArrayList<String>();
		for(String str : tweet2Splited) {
			tweet2List.add(str);
		}
		
		double count = 0;
		for(String str : tweet1Splited) {
			if(tweet2List.contains(str)) {
				count++;
			}
		}
		
		double nbElement = tweet1Splited.length + tweet2Splited.length;
		
		return ((nbElement - count*2) / nbElement);
	}
}
