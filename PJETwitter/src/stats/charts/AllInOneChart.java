package stats.charts;

import helper.Globals;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.RefineryUtilities;

public class AllInOneChart extends JFrame
{	
	int[] _csvReferenceValues = new int[3];
	int[] _knnValues = new int[3];
	int[] _keywordValues = new int[3];
	int[] _bayesUniValues = new int[3];
	int[] _bayesUniFreqValues = new int[3];
	int[] _bayesBiValues = new int[3];
	int[] _bayesBiFreqValues = new int[3];
	int[] _bayesUniBiValues = new int[3];
	int[] _bayesUniBiFreqValues = new int[3];

	int[] _knnTrueValues = new int[3];
	int[] _keywordTrueValues = new int[3];
	int[] _bayesUniTrueValues = new int[3];
	int[] _bayesUniFreqTrueValues = new int[3];
	int[] _bayesBiTrueValues = new int[3];
	int[] _bayesBiFreqTrueValues = new int[3];
	int[] _bayesUniBiTrueValues = new int[3];
	int[] _bayesUniBiFreqTrueValues = new int[3];

	double _ratioTotalKeywords;
	double _ratioTotalKNN;
	double _ratioTotalBayesienneUni;
	double _ratioTotalBayesienneUniFreq;
	double _ratioTotalBayesienneBi;
	double _ratioTotalBayesienneBiFreq;
	double _ratioTotalBayesienneUniBi;
	double _ratioTotalBayesienneUniBiFreq;


	public AllInOneChart(int[] csvReferenceValues, int[] knnValues, int[] keywordValues, 
			int[] bayesUniValues, int[] bayesUniFreqValues, int[] bayesBiValues, int[] bayesBiFreqValues, 
			int[] bayesUniBiValues, int[] bayesUniBiFreqValues,
			double ratioTotalKeywords, double ratioTotalKNN, double ratioTotalBayesienneUni, 
			double ratioTotalBayesienneUniFreq, double ratioTotalBayesienneBi, double ratioTotalBayesienneBiFreq,
			double ratioTotalBayesienneUniBi, double ratioTotalBayesienneUniBiFreq,
			int nbTruePositiveBayesienneUni, int nbTruePositiveBayesienneUniFreq,
			int nbTruePositiveBayesienneBi, int nbTruePositiveBayesienneBiFreq, int nbTruePositiveBayesienneUniBi,
			int nbTruePositiveBayesienneUniBiFreq, int nbTruePositiveKNN, int nbTruePositiveKeywords,
			int nbTrueNeutralBayesienneUni, int nbTrueNeutralBayesienneUniFreq, int nbTrueNeutralBayesienneBi,
			int nbTrueNeutralBayesienneBiFreq, int nbTrueNeutralBayesienneUniBi, int nbTrueNeutralBayesienneUniBiFreq,
			int nbTrueNeutralKNN, int nbTrueNeutralKeywords, int nbTrueNegativeBayesienneUni, int nbTrueNegativeBayesienneUniFreq,
			int nbTrueNegativeBayesienneBi, int nbTrueNegativeBayesienneBiFreq, int nbTrueNegativeBayesienneUniBi,
			int nbTrueNegativeBayesienneUniBiFreq, int nbTrueNegativeKNN, int nbTrueNegativeKeywords)
	{
		super("Classifier stats");
        
        
		for (int i = 0; i < 3; i++)
		{
			_csvReferenceValues[i] = csvReferenceValues[i];
			_knnValues[i] = knnValues[i];
			_keywordValues[i] = keywordValues[i];
			_bayesUniValues[i] = bayesUniValues[i];
			_bayesUniFreqValues[i] = bayesUniFreqValues[i];
			_bayesBiValues[i] = bayesBiValues[i];
			_bayesBiFreqValues[i] = bayesBiFreqValues[i];
			_bayesUniBiValues[i] = bayesUniBiValues[i];
			_bayesUniBiFreqValues[i] = bayesUniBiFreqValues[i];
		}

		_knnTrueValues[0] = nbTrueNegativeKNN;
		_knnTrueValues[1] = nbTrueNeutralKNN;
		_knnTrueValues[2] = nbTruePositiveKNN;
		
		_keywordTrueValues[0] = nbTrueNegativeKeywords;
		_keywordTrueValues[1] = nbTrueNeutralKeywords;
		_keywordTrueValues[2] = nbTruePositiveKeywords;
		
		_bayesUniTrueValues[0] = nbTrueNegativeBayesienneUni;
		_bayesUniTrueValues[1] = nbTrueNeutralBayesienneUni;
		_bayesUniTrueValues[2] = nbTruePositiveBayesienneUni;
		
		_bayesUniFreqTrueValues[0] = nbTrueNegativeBayesienneUniFreq;
		_bayesUniFreqTrueValues[1] = nbTrueNeutralBayesienneUniFreq;
		_bayesUniFreqTrueValues[2] = nbTruePositiveBayesienneUniFreq;

		_bayesBiTrueValues[0] = nbTrueNegativeBayesienneBi;
		_bayesBiTrueValues[1] = nbTrueNeutralBayesienneBi;
		_bayesBiTrueValues[2] = nbTruePositiveBayesienneBi;

		_bayesBiFreqTrueValues[0] = nbTrueNegativeBayesienneBiFreq;
		_bayesBiFreqTrueValues[1] = nbTrueNeutralBayesienneBiFreq;
		_bayesBiFreqTrueValues[2] = nbTruePositiveBayesienneBiFreq;

		_bayesUniBiTrueValues[0] = nbTrueNegativeBayesienneUniBi;
		_bayesUniBiTrueValues[1] = nbTrueNeutralBayesienneUniBi;
		_bayesUniBiTrueValues[2] = nbTruePositiveBayesienneUniBi;

		_bayesUniBiFreqTrueValues[0] = nbTrueNegativeBayesienneUniBiFreq;
		_bayesUniBiFreqTrueValues[1] = nbTrueNeutralBayesienneUniBiFreq;
		_bayesUniBiFreqTrueValues[2] = nbTruePositiveBayesienneUniBiFreq;
		
		_ratioTotalKeywords = ratioTotalKeywords;
		_ratioTotalKNN = ratioTotalKNN;
		_ratioTotalBayesienneUni = ratioTotalBayesienneUni;
		_ratioTotalBayesienneUniFreq = ratioTotalBayesienneUniFreq;
		_ratioTotalBayesienneBi = ratioTotalBayesienneBi;
		_ratioTotalBayesienneBiFreq = ratioTotalBayesienneBiFreq;
		_ratioTotalBayesienneUniBi = ratioTotalBayesienneUniBi;
		_ratioTotalBayesienneUniBiFreq = ratioTotalBayesienneUniBiFreq;
		
		setContentPane(createDemoPanel());
	}



	public JPanel createDemoPanel()
	{
		BaseChartPanel localBaseChartPanel = new BaseChartPanel(new GridLayout(2, 2));

		ClassifiersComparatorBarChart ccbcTrue = new ClassifiersComparatorBarChart(
				_csvReferenceValues, // csvReference (nbNegatifs, nbNeutres, nbPositifs)
				_knnValues, // KNN
				_keywordValues, // keywords
				_bayesUniValues, // BayesUni
				_bayesUniFreqValues, // BayesUniFreq
				_bayesBiValues, // BayesBi
				_bayesBiFreqValues, // BayesBiFreq
				_bayesUniBiValues, // BayesUniBi
				_bayesUniBiFreqValues, // BayesUniBiFreq
				true
		);
		JFreeChart ccbcChartPercentage = ccbcTrue.createJFreeChartPanel("Ecart par rapport à la base");

		ClassifiersComparatorBarChart ccbcFalse = new ClassifiersComparatorBarChart(
				_csvReferenceValues, // csvReference (nbNegatifs, nbNeutres, nbPositifs)
				_knnTrueValues, // KNN
				_keywordTrueValues, // keywords
				_bayesUniTrueValues, // BayesUni
				_bayesUniFreqTrueValues, // BayesUniFreq
				_bayesBiTrueValues, // BayesBi
				_bayesBiFreqTrueValues, // BayesBiFreq
				_bayesUniBiTrueValues, // BayesUniBi
				_bayesUniBiFreqTrueValues, // BayesUniBiFreq
				false
		);
		
		int total = _csvReferenceValues[0] * 3;
		JFreeChart ccbcChartNormal = ccbcFalse.createJFreeChartPanel("True tweets (" + total + " tweets) | " + _csvReferenceValues[0] + " par classe)");
		

		ClassifiersErrorsComparatorBarChart cecbc = new ClassifiersErrorsComparatorBarChart(_ratioTotalKeywords, _ratioTotalKNN, _ratioTotalBayesienneUni, _ratioTotalBayesienneUniFreq,
				_ratioTotalBayesienneBi, _ratioTotalBayesienneBiFreq, _ratioTotalBayesienneUniBi, _ratioTotalBayesienneUniBiFreq);
		JFreeChart cecbcNormal = cecbc.createJFreeChartPanel();
		
		
		RatioReferenceChart rrs = new RatioReferenceChart(Globals.CSV_REFERENCE_LOCATION);
		JFreeChart rrsChart = rrs.createJFreeChartPanel(Globals.CSV_REFERENCE_LOCATION);

		
		localBaseChartPanel.add(new ChartPanel(rrsChart));
		localBaseChartPanel.add(new ChartPanel(cecbcNormal));
		localBaseChartPanel.add(new ChartPanel(ccbcChartNormal));
		localBaseChartPanel.add(new ChartPanel(ccbcChartPercentage));
		localBaseChartPanel.addChart(rrsChart);
		localBaseChartPanel.addChart(cecbcNormal);
		localBaseChartPanel.addChart(ccbcChartNormal);
		localBaseChartPanel.addChart(ccbcChartPercentage);
	    localBaseChartPanel.setPreferredSize(new Dimension(1152, 768));
		return localBaseChartPanel;
	}

	public static void launchWithData(final int[] csvReferenceValues, final int[] knnValues, final int[] keywordValues, final int[] bayesUniValues, 
			final int[] bayesUniFreqValues, final int[] bayesBiValues, final int[] bayesBiFreqValues, final int[] bayesUniBiValues, final int[] bayesUniBiFreqValues,
			
			final double ratioTotalKeywords, final double ratioTotalKNN, final double ratioTotalBayesienneUni, 
			final double ratioTotalBayesienneUniFreq, final double ratioTotalBayesienneBi, final double ratioTotalBayesienneBiFreq,
			final double ratioTotalBayesienneUniBi, final double ratioTotalBayesienneUniBiFreq,
			int nbTruePositiveBayesienneUni, int nbTruePositiveBayesienneUniFreq,
			int nbTruePositiveBayesienneBi, int nbTruePositiveBayesienneBiFreq, int nbTruePositiveBayesienneUniBi,
			int nbTruePositiveBayesienneUniBiFreq, int nbTruePositiveKNN, int nbTruePositiveKeywords,
			int nbTrueNeutralBayesienneUni, int nbTrueNeutralBayesienneUniFreq, int nbTrueNeutralBayesienneBi,
			int nbTrueNeutralBayesienneBiFreq, int nbTrueNeutralBayesienneUniBi, int nbTrueNeutralBayesienneUniBiFreq,
			int nbTrueNeutralKNN, int nbTrueNeutralKeywords, int nbTrueNegativeBayesienneUni, int nbTrueNegativeBayesienneUniFreq,
			int nbTrueNegativeBayesienneBi, int nbTrueNegativeBayesienneBiFreq, int nbTrueNegativeBayesienneUniBi,
			int nbTrueNegativeBayesienneUniBiFreq, int nbTrueNegativeKNN, int nbTrueNegativeKeywords
			)
	{

		JFrame frame = new AllInOneChart(csvReferenceValues, knnValues, keywordValues, bayesUniValues,
				bayesUniFreqValues, bayesBiValues, bayesBiFreqValues, bayesUniBiValues, bayesUniBiFreqValues,
				ratioTotalKeywords, ratioTotalKNN, ratioTotalBayesienneUni, ratioTotalBayesienneUniFreq,
				ratioTotalBayesienneBi, ratioTotalBayesienneBiFreq, ratioTotalBayesienneUniBi, ratioTotalBayesienneUniBiFreq,
				nbTruePositiveBayesienneUni,  nbTruePositiveBayesienneUniFreq,
				nbTruePositiveBayesienneBi,  nbTruePositiveBayesienneBiFreq,  nbTruePositiveBayesienneUniBi,
				nbTruePositiveBayesienneUniBiFreq,  nbTruePositiveKNN,  nbTruePositiveKeywords,
				nbTrueNeutralBayesienneUni,  nbTrueNeutralBayesienneUniFreq,  nbTrueNeutralBayesienneBi,
				nbTrueNeutralBayesienneBiFreq,  nbTrueNeutralBayesienneUniBi,  nbTrueNeutralBayesienneUniBiFreq,
				nbTrueNeutralKNN,  nbTrueNeutralKeywords,  nbTrueNegativeBayesienneUni,  nbTrueNegativeBayesienneUniFreq,
				nbTrueNegativeBayesienneBi,  nbTrueNegativeBayesienneBiFreq,  nbTrueNegativeBayesienneUniBi,
				nbTrueNegativeBayesienneUniBiFreq,  nbTrueNegativeKNN,  nbTrueNegativeKeywords);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		RefineryUtilities.centerFrameOnScreen(frame);
		frame.setVisible(true);

	}


}
