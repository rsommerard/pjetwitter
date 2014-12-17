package stats.charts;

import helper.Globals;
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

public class TwitterPieChart extends ApplicationFrame
{
	boolean inPercentages = true;

	public TwitterPieChart()
	{
		super("PJE Twitter");
		JPanel localJPanel = createDemoPanel();
		localJPanel.setPreferredSize(new Dimension(800, 600));
		setContentPane(localJPanel);
	}

	private PieDataset createDatasetFrom(String csvPath)
	{
		int nbPositifs = 0, nbNegatifs = 0, nbNeutres = 0;
		CsvHelper csv;

		if (csvPath.equals(Globals.CSV_REFERENCE_LOCATION))
			csv = helper.csv.CsvSingletons.getInstance().referenceCsv;
		else if (csvPath.equals(Globals.CSV_FINAL_LOCATION))
			csv = helper.csv.CsvSingletons.getInstance().finalCsv;
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

		DefaultPieDataset localDefaultPieDataset = new DefaultPieDataset();
		if (inPercentages)
		{
			int nbTweets = tweets.size();


			localDefaultPieDataset.setValue("Positifs", round(((float) (nbPositifs * 100)) / nbTweets, 1));
			localDefaultPieDataset.setValue("N�gatifs", round(((float) (nbNegatifs * 100)) / nbTweets, 2));
			localDefaultPieDataset.setValue("Neutres", round(((float) (nbNeutres * 100)) / nbTweets, 2));
		}
		else
		{
			localDefaultPieDataset.setValue("Positifs", nbPositifs);
			localDefaultPieDataset.setValue("N�gatifs", nbNegatifs);
			localDefaultPieDataset.setValue("Neutres", nbNeutres);
		}

		return localDefaultPieDataset;
	}

	private double round(double a, int nbDecimals)
	{
		int val = (int) Math.pow(10, nbDecimals);
		return (double) Math.round(a * val) / val;
	}

	private JFreeChart createChart(String name, PieDataset paramPieDataset)
	{
		JFreeChart localJFreeChart = ChartFactory.createPieChart(name, paramPieDataset, true, true, false);
		PiePlot localPiePlot = (PiePlot) localJFreeChart.getPlot();
		localPiePlot.setLabelGenerator(new StandardPieSectionLabelGenerator(inPercentages ? "{0} = {1}%" : "{0} = {1}"));
		return localJFreeChart;
	}

	public JPanel createDemoPanel()
	{
		BaseChartPanel localBaseChartPanel = new BaseChartPanel(new GridLayout(2, 2));

		JFreeChart referenceCsvChart = createChart("CSV r�f�rence", createDatasetFrom(Globals.CSV_REFERENCE_LOCATION));
		PiePlot referenceCsvPiePlot = (PiePlot) referenceCsvChart.getPlot();
		referenceCsvPiePlot.setIgnoreNullValues(true);
		referenceCsvPiePlot.setIgnoreZeroValues(false);

		JFreeChart finalCsvChart = createChart("CSV final", createDatasetFrom(Globals.CSV_FINAL_LOCATION));
		PiePlot finalCsvPiePlot = (PiePlot) finalCsvChart.getPlot();
		finalCsvPiePlot.setIgnoreNullValues(true);
		finalCsvPiePlot.setIgnoreZeroValues(false);

		localBaseChartPanel.add(new ChartPanel(referenceCsvChart));
		localBaseChartPanel.add(new ChartPanel(finalCsvChart));
		localBaseChartPanel.addChart(referenceCsvChart);
		localBaseChartPanel.addChart(finalCsvChart);
		return localBaseChartPanel;
	}

	public static void main(String[] paramArrayOfString)
	{
		TwitterPieChart twitterPieChart = new TwitterPieChart();
		twitterPieChart.pack();
		RefineryUtilities.centerFrameOnScreen(twitterPieChart);
		twitterPieChart.setVisible(true);
	}
}
