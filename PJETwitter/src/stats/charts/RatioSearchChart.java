package stats.charts;

import helper.Globals;
import helper.Utils;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RefineryUtilities;

import pjetwitter.TweetInfo;

public class RatioSearchChart extends JFrame
{
	boolean inPercentages = true;

	public RatioSearchChart(List<TweetInfo> tweets, String search)
	{
		super("Search stats");
		JPanel localJPanel = createDemoPanel(tweets, search);
		localJPanel.setPreferredSize(new Dimension(500, 500));
		setContentPane(localJPanel);
		this.pack();
		RefineryUtilities.centerFrameOnScreen(this);
		this.setVisible(true);
	}

	private PieDataset createDatasetFrom(List<TweetInfo> tweets)
	{
		int nbPositifs = 0, nbNegatifs = 0, nbNeutres = 0;

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



	private JFreeChart createChart(String name, PieDataset paramPieDataset)
	{
		JFreeChart localJFreeChart = ChartFactory.createPieChart(name, paramPieDataset, true, true, false);
		PiePlot localPiePlot = (PiePlot) localJFreeChart.getPlot();
		localPiePlot.setLabelGenerator(new StandardPieSectionLabelGenerator(inPercentages ? "{0} = {1}%" : "{0} = {1}"));
		return localJFreeChart;
	}

	public JPanel createDemoPanel(List<TweetInfo> tweets, String search)
	{
		BaseChartPanel localBaseChartPanel = new BaseChartPanel(new GridLayout());

		JFreeChart referenceCsvChart = createChart("Tendance pour \"" + search + "\"", createDatasetFrom(tweets));
		PiePlot referenceCsvPiePlot = (PiePlot) referenceCsvChart.getPlot();
		referenceCsvPiePlot.setIgnoreNullValues(true);
		referenceCsvPiePlot.setIgnoreZeroValues(false);

		localBaseChartPanel.add(new ChartPanel(referenceCsvChart));
		localBaseChartPanel.addChart(referenceCsvChart);
		return localBaseChartPanel;
	}
}
