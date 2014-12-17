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
public class ClassifiersComparatorBarChart extends ApplicationFrame
{
	double[] _csvReferenceValues = new double[3];
	double[] _knnValues = new double[3];
	double[] _keywordValues = new double[3];
	double[] _bayesUniValues = new double[3];
	double[] _bayesUniFreqValues = new double[3];
	double[] _bayesBiValues = new double[3];
	double[] _bayesBiFreqValues = new double[3];
	double[] _bayesUniBiValues = new double[3];
	double[] _bayesUniBiFreqValues = new double[3];

	boolean _inPercentages;
	int _nbReferences;

	public ClassifiersComparatorBarChart(int[] csvReferenceValues, int[] knnValues, int[] keywordValues, 
			int[] bayesUniValues, int[] bayesUniFreqValues, int[] bayesBiValues, int[] bayesBiFreqValues, 
			int[] bayesUniBiValues, int[] bayesUniBiFreqValues, boolean inPercentages)
	{
		super("Comparatif classifieurs (%:" + inPercentages + ")");
		_inPercentages = inPercentages;

		if (inPercentages)
		{
			// 0:negatifs, 1:positifs, 2:neutres
			for (int i = 0; i < 3; i++)
			{
				_csvReferenceValues[i] = 100;
				_knnValues[i] = ((double) (100 * knnValues[i]) / csvReferenceValues[i])-100;
				_keywordValues[i] = ((double) (100 * keywordValues[i]) / csvReferenceValues[i])-100;
				_bayesUniValues[i] = ((double) (100 * bayesUniValues[i]) / csvReferenceValues[i])-100;
				_bayesUniFreqValues[i] = ((double) (100 * bayesUniFreqValues[i]) / csvReferenceValues[i])-100;
				_bayesBiValues[i] = ((double) (100 * bayesBiValues[i]) / csvReferenceValues[i])-100;
				_bayesBiFreqValues[i] = ((double) (100 * bayesBiFreqValues[i]) / csvReferenceValues[i])-100;
				_bayesUniBiValues[i] = ((double) (100 * bayesUniBiValues[i]) / csvReferenceValues[i])-100;
				_bayesUniBiFreqValues[i] = ((double) (100 * bayesUniBiFreqValues[i]) / csvReferenceValues[i])-100;
			}
		}
		else
		{
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
		}


		JPanel jpanel = createDemoPanel();
		jpanel.setPreferredSize(new Dimension(800, 400));
		setContentPane(jpanel);
	}

	private CategoryDataset createDataset()
	{
		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

		String[] tmpStr = new String[] { "Négatifs", "Neutres", "Positifs" };
		for (int i = 0; i < 3; i++)
		{
			//defaultcategorydataset.addValue(_csvReferenceValues[i], "CSV reference", tmpStr[i]);
			defaultcategorydataset.addValue(_knnValues[i], "KNN", tmpStr[i]);
			defaultcategorydataset.addValue(_keywordValues[i], "Keywords", tmpStr[i]);
			defaultcategorydataset.addValue(_bayesUniValues[i], "Uni-P", tmpStr[i]);
			defaultcategorydataset.addValue(_bayesUniFreqValues[i], "Uni-F", tmpStr[i]);
			defaultcategorydataset.addValue(_bayesBiValues[i], "Bi-P", tmpStr[i]);
			defaultcategorydataset.addValue(_bayesBiFreqValues[i], "Bi-F", tmpStr[i]);
			defaultcategorydataset.addValue(_bayesUniBiValues[i], "Uni+Bi-P", tmpStr[i]);
			defaultcategorydataset.addValue(_bayesUniBiFreqValues[i], "Uni+Bi-F", tmpStr[i]);
		}
		return defaultcategorydataset;
	}

	private JFreeChart createChart(CategoryDataset categorydataset, String title)
	{
		JFreeChart jfreechart = ChartFactory.createBarChart(title, null, "Nombre de tweets", categorydataset, PlotOrientation.VERTICAL, true, true, false);
		CategoryPlot categoryplot = (CategoryPlot) jfreechart.getPlot();
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		BarRenderer barrenderer = (BarRenderer) categoryplot.getRenderer();
		barrenderer.setDrawBarOutline(false);
		barrenderer.setMaximumBarWidth(0.1);
		barrenderer.setLegendItemLabelGenerator(new StandardCategorySeriesLabelGenerator("{0}"));
		return jfreechart;
	}

	private JPanel createDemoPanel()
	{
		return new ChartPanel(createJFreeChartPanel(""));
	}

	public JFreeChart createJFreeChartPanel(String title)
	{
		return createChart(createDataset(), title);
	}

	/**
	 * Static method to dynamically launch the Frame The constructor is voluntarily set private.
	 * 
	 * @param csvReferenceValues
	 *            [0:nbNegatifs, 1:nbNeutres, 2:nbPositifs]
	 * @param knnValues
	 *            [0..2]
	 * @param keywordValues
	 *            [0..2]
	 * @param bayesUniValues
	 *            [0..2]
	 * @param bayesBiValues
	 *            [0..2]
	 * @param inPercentages
	 *            ?
	 */
	public static void launchWithData(int[] csvReferenceValues, int[] knnValues, int[] keywordValues, int[] bayesUniValues, 
			int[] bayesUniFreqValues, int[] bayesBiValues, int[] bayesBiFreqValues, int[] bayesUniBiValues, int[] bayesUniBiFreqValues, boolean inPercentages)
	{
		ClassifiersComparatorBarChart barchartdemo4 = new ClassifiersComparatorBarChart(csvReferenceValues, knnValues, keywordValues, bayesUniValues,
				bayesUniFreqValues, bayesBiValues, bayesBiFreqValues, bayesUniBiValues, bayesUniBiFreqValues, inPercentages);
		barchartdemo4.pack();
		RefineryUtilities.centerFrameOnScreen(barchartdemo4);
		barchartdemo4.setVisible(true);
	}


}
