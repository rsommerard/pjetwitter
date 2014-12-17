package stats.charts;

import java.awt.Dimension;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategorySeriesLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

@SuppressWarnings("serial")
public class ClassifiersErrorsComparatorBarChart extends ApplicationFrame
{
	double percentageErrorsKeywords;
	double percentageErrorsKNN;
	double percentageErrorsBayesienneUni;
	double percentageErrorsBayesienneUniFreq;
	double percentageErrorsBayesienneBi;
	double percentageErrorsBayesienneBiFreq;
	double percentageErrorsBayesienneUniBi;
	double percentageErrorsBayesienneUniBiFreq;

	public ClassifiersErrorsComparatorBarChart(double ratioTotalKeywords, double ratioTotalKNN, double ratioTotalBayesienneUni, 
			double ratioTotalBayesienneUniFreq, double ratioTotalBayesienneBi, double ratioTotalBayesienneBiFreq, 
			double ratioTotalBayesienneUniBi, double ratioTotalBayesienneUniBiFreq)
	{
		super("Taux d'erreurs");
		this.percentageErrorsKeywords = 100 * ratioTotalKeywords;
		this.percentageErrorsKNN = 100 * ratioTotalKNN;
		this.percentageErrorsBayesienneUni = 100 * ratioTotalBayesienneUni;
		this.percentageErrorsBayesienneUniFreq = 100 * ratioTotalBayesienneUniFreq;
		this.percentageErrorsBayesienneBi = 100 * ratioTotalBayesienneBi;
		this.percentageErrorsBayesienneBiFreq = 100 * ratioTotalBayesienneBiFreq;
		this.percentageErrorsBayesienneUniBi = 100 * ratioTotalBayesienneUniBi;
		this.percentageErrorsBayesienneUniBiFreq = 100 * ratioTotalBayesienneUniBiFreq;
		
		JPanel jpanel = createDemoPanel();
		jpanel.setPreferredSize(new Dimension(800, 400));
		setContentPane(jpanel);
	}

	private CategoryDataset createDataset()
	{
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		
		defaultcategorydataset.addValue(percentageErrorsKeywords, "Keywords", "");
		defaultcategorydataset.addValue(percentageErrorsKNN, "KNN", "");
		defaultcategorydataset.addValue(percentageErrorsBayesienneUni, "Uni-P", "");
		defaultcategorydataset.addValue(percentageErrorsBayesienneUniFreq, "Uni-F", "");
		defaultcategorydataset.addValue(percentageErrorsBayesienneBi, "Bi-P", "");
		defaultcategorydataset.addValue(percentageErrorsBayesienneBiFreq, "Bi-F", "");
		defaultcategorydataset.addValue(percentageErrorsBayesienneUniBi, "Uni+Bi-P", "");
		defaultcategorydataset.addValue(percentageErrorsBayesienneUniBiFreq, "Uni+Bi-F", "");

		return defaultcategorydataset;
	}

	private JFreeChart createChart(CategoryDataset categorydataset)
	{
		JFreeChart jfreechart = ChartFactory.createBarChart("Taux d'erreurs", null, "Pourcentage d'erreurs", categorydataset,
				PlotOrientation.VERTICAL, true, true, false);
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setRange(0, 100);
		BarRenderer barrenderer = (BarRenderer) categoryplot.getRenderer();
		barrenderer.setDrawBarOutline(false);
		barrenderer.setMaximumBarWidth(0.1);
		barrenderer.setLegendItemLabelGenerator(new StandardCategorySeriesLabelGenerator("{0}"));
		return jfreechart;
	}

	private JPanel createDemoPanel()
	{
		return new ChartPanel(createJFreeChartPanel());
	}

	public JFreeChart createJFreeChartPanel()
	{
		return createChart(createDataset());
	}
	/**
	 * Static method to dynamically launch the Frame The constructor is voluntarily set private.
	 */
	public static void launchWithData(double ratioTotalKeywords, double ratioTotalKNN, double ratioTotalBayesienneUni, 
			double ratioTotalBayesienneUniFreq, double ratioTotalBayesienneBi, double ratioTotalBayesienneBiFreq,
			double ratioTotalBayesienneUniBi, double ratioTotalBayesienneUniBiFreq, double ratioTotalTest)
	{
		ClassifiersErrorsComparatorBarChart barchartdemo4 = new ClassifiersErrorsComparatorBarChart(ratioTotalKeywords, 
				ratioTotalKNN, ratioTotalBayesienneUni, ratioTotalBayesienneUniFreq, ratioTotalBayesienneBi,
				ratioTotalBayesienneBiFreq, ratioTotalBayesienneUniBi, ratioTotalBayesienneUniBiFreq);
		barchartdemo4.pack();
		RefineryUtilities.centerFrameOnScreen(barchartdemo4);
		barchartdemo4.setVisible(true);
	}

	// main
	public static void main(String args[])
	{
		ClassifiersErrorsComparatorBarChart.launchWithData(0.60, 0.50, 0.35, 0.20, 0.45, 0.40, 0.30, 0.25, 0.10);
	}


}
