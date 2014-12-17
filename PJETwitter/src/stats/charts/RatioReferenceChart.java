package stats.charts;

import helper.Globals;
import helper.Utils;
import helper.csv.CsvHelper;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import pjetwitter.TweetInfo;

public class RatioReferenceChart extends ApplicationFrame
{
	boolean inPercentages = true;

	public RatioReferenceChart(String csvPath)
	{
		super("PJE Twitter");
		JPanel localJPanel = createDemoPanel(csvPath);
		localJPanel.setPreferredSize(new Dimension(500, 500));
		setContentPane(localJPanel);
	}

	private PieDataset createDatasetFrom(String csvPath)
	{
		int nbPositifs = 0, nbNegatifs = 0, nbNeutres = 0;
		CsvHelper csv;

		if (csvPath.equals(Globals.CSV_REFERENCE_LOCATION))
			csv = helper.csv.CsvSingletons.getInstance().referenceCsv;
		else
			return new DefaultPieDataset();

		List<TweetInfo> tweets = csv.readAll();

		for (TweetInfo tweet : tweets)
		{
			if (tweet.getTweetPolarity() == Globals.NEGATIVE_TWEET)
				nbNegatifs++;
			if (tweet.getTweetPolarity() == Globals.POSITIVE_TWEET)
				nbPositifs++;
			if (tweet.getTweetPolarity() == Globals.NEUTRAL_TWEET)
				nbNeutres++;
		}
		
		System.out.println("Nb Négatifs: " + nbNegatifs);
		System.out.println("Nb Neutres: " + nbNeutres);
		System.out.println("Nb Positifs: " + nbPositifs);

		DefaultPieDataset localDefaultPieDataset = new DefaultPieDataset();
		if (inPercentages)
		{
			int nbTweets = tweets.size();


			localDefaultPieDataset.setValue("Positifs", Utils.round(((nbPositifs * 100f)) / nbTweets, 2));
			localDefaultPieDataset.setValue("Négatifs", Utils.round(((nbNegatifs * 100f)) / nbTweets, 2));
			localDefaultPieDataset.setValue("Neutres", Utils.round(((nbNeutres * 100f)) / nbTweets, 2));
		}
		else
		{
			localDefaultPieDataset.setValue("Positifs", nbPositifs);
			localDefaultPieDataset.setValue("Négatifs", nbNegatifs);
			localDefaultPieDataset.setValue("Neutres", nbNeutres);
		}

		return localDefaultPieDataset;
	}



	private JFreeChart createChart(PieDataset paramPieDataset)
	{
		JFreeChart localJFreeChart = ChartFactory.createPieChart("Ratio CSV Référence", paramPieDataset, true, true, false);
		PiePlot localPiePlot = (PiePlot) localJFreeChart.getPlot();
		localPiePlot.setLabelGenerator(new StandardPieSectionLabelGenerator(inPercentages ? "{0} = {1}%" : "{0} = {1}"));
		return localJFreeChart;
	}

	private JPanel createDemoPanel(String csvPath)
	{
		return new ChartPanel(createJFreeChartPanel(csvPath));
	}

	public JFreeChart createJFreeChartPanel(String csvPath)
	{
		return createChart(createDatasetFrom(csvPath));
	}
	
	
	
	public JPanel createDemoPanel()
	{
		BaseChartPanel localBaseChartPanel = new BaseChartPanel(new GridLayout());

		JFreeChart referenceCsvChart = createChart(createDatasetFrom(Globals.CSV_REFERENCE_LOCATION));
		PiePlot referenceCsvPiePlot = (PiePlot) referenceCsvChart.getPlot();
		referenceCsvPiePlot.setIgnoreNullValues(true);
		referenceCsvPiePlot.setIgnoreZeroValues(false);

		localBaseChartPanel.add(new ChartPanel(referenceCsvChart));
		localBaseChartPanel.addChart(referenceCsvChart);
		return localBaseChartPanel;
	}

	public static void main(String[] paramArrayOfString)
	{
		RatioReferenceChart ratioReferenceChart = new RatioReferenceChart(Globals.CSV_REFERENCE_LOCATION);
		ratioReferenceChart.pack();
		RefineryUtilities.centerFrameOnScreen(ratioReferenceChart);
		ratioReferenceChart.setVisible(true);
	}
}
