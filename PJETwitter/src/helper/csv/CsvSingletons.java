package helper.csv;

import helper.Globals;

public class CsvSingletons
{
	private static CsvSingletons instance;

	public CsvHelper baseCsv;
	public CsvHelper referenceCsv;



	private CsvSingletons()
	{
		baseCsv = new CsvHelper(Globals.CSV_BASE_LOCATION);
		referenceCsv = new CsvHelper(Globals.CSV_REFERENCE_LOCATION);
	}



	public static CsvSingletons getInstance()
	{
		if (null == instance)
		{
			instance = new CsvSingletons();
		}
		return instance;
	}


}
