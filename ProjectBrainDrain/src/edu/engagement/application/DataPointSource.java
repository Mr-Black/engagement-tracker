package edu.engagement.application;

import java.util.ArrayList;
import java.util.List;

import edu.engagement.thrift.EegPower;
import edu.engagement.thrift.HeartRate;
import edu.engagement.thrift.EegAttention;
import edu.engagement.thrift.EegRaw;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataPointSource
{

	// Database fields
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private String[] allColumns =
	{ DatabaseHelper.COLUMN_TIMESTAMP, DatabaseHelper.COLUMN_HEARTRATE, DatabaseHelper.COLUMN_ALPHA,
			DatabaseHelper.COLUMN_BETA, DatabaseHelper.COLUMN_THETA, DatabaseHelper.COLUMN_ATTENTION,
			DatabaseHelper.COLUMN_CH1, DatabaseHelper.COLUMN_CH2, DatabaseHelper.COLUMN_CH3, DatabaseHelper.COLUMN_CH4,
			DatabaseHelper.COLUMN_CH5, DatabaseHelper.COLUMN_CH6, DatabaseHelper.COLUMN_CH7, DatabaseHelper.COLUMN_CH8 };

	public DataPointSource(Context context)
	{
		dbHelper = new DatabaseHelper(context);
	}

	public void open() throws SQLException
	{
		database = dbHelper.getWritableDatabase();
	}

	public void close()
	{
		dbHelper.close();
	}

	public DataPoint createDataPointEEG(long timeStamp, double alpha, double beta, double theta)
	{
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_TIMESTAMP, timeStamp);
		values.put(DatabaseHelper.COLUMN_ALPHA, alpha);
		values.put(DatabaseHelper.COLUMN_BETA, beta);
		values.put(DatabaseHelper.COLUMN_THETA, theta);

		database.insert(DatabaseHelper.TABLE_EEG, null, values);

		return new DataPoint(timeStamp, 0, alpha, beta, theta, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	public DataPoint createDataPointHR(long timeStamp, double hr)
	{
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_TIMESTAMP, timeStamp);
		values.put(DatabaseHelper.COLUMN_HEARTRATE, hr);

		database.insert(DatabaseHelper.TABLE_HR, null, values);

		return new DataPoint(timeStamp, hr, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	public DataPoint createDataPointAttention(long timeStamp, double attention)
	{
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_TIMESTAMP, timeStamp);
		values.put(DatabaseHelper.COLUMN_ATTENTION, attention);

		database.insert(DatabaseHelper.TABLE_ATTENTION, null, values);

		return new DataPoint(timeStamp, 0, 0, 0, 0, attention, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	public DataPoint createDataPointRaw(long timeStamp, double ch1, double ch2, double ch3, double ch4, double ch5,
			double ch6, double ch7, double ch8)
	{
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.COLUMN_TIMESTAMP, timeStamp);
		values.put(DatabaseHelper.COLUMN_CH1, ch1);
		values.put(DatabaseHelper.COLUMN_CH2, ch2);
		values.put(DatabaseHelper.COLUMN_CH3, ch3);
		values.put(DatabaseHelper.COLUMN_CH4, ch4);
		values.put(DatabaseHelper.COLUMN_CH5, ch5);
		values.put(DatabaseHelper.COLUMN_CH6, ch6);
		values.put(DatabaseHelper.COLUMN_CH7, ch7);
		values.put(DatabaseHelper.COLUMN_CH8, ch8);

		database.insert(DatabaseHelper.TABLE_ATTENTION, null, values);

		return new DataPoint(timeStamp, 0, 0, 0, 0, 0, ch1, ch2, ch3, ch4, ch5, ch6, ch7, ch8);
	}

	public void deleteDataPoint(DataPoint point)
	{
		long timestamp = point.getTimeStamp();
		if (point.getAlpha() != 0)
		{
			database.delete(DatabaseHelper.TABLE_EEG, DatabaseHelper.COLUMN_TIMESTAMP + " = " + timestamp, null);
			System.out.println("DataPoint deleted with timestamp: " + timestamp);
		} else if (point.getHeartRate() != 0)
		{
			database.delete(DatabaseHelper.TABLE_HR, DatabaseHelper.COLUMN_TIMESTAMP + " = " + timestamp, null);
			System.out.println("DataPoint deleted with timestamp: " + timestamp);
		} else if (point.getAttention() != 0)
		{
			database.delete(DatabaseHelper.TABLE_ATTENTION, DatabaseHelper.COLUMN_TIMESTAMP + " = " + timestamp, null);
			System.out.println("DataPoint deleted with timestamp: " + timestamp);
		} else if (point.getCh1() != 0)
		{
			database.delete(DatabaseHelper.TABLE_RAW, DatabaseHelper.COLUMN_TIMESTAMP + " = " + timestamp, null);
			System.out.println("DataPoint deleted with timestamp: " + timestamp);
		}

	}

	public List<EegPower> getAllDataPointsEEG()
	{
		List<EegPower> points = new ArrayList<EegPower>();

		Cursor cursor = database.query(DatabaseHelper.TABLE_EEG, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			EegPower point = new EegPower();
			point.setMillisecondTimeStamp(String.valueOf(cursor.getLong(0)));
			point.setAlpha(cursor.getInt(1));
			point.setBeta(cursor.getInt(2));
			point.setTheta(cursor.getInt(3));
			points.add(point);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return points;
	}

	public List<HeartRate> getAllDataPointsHR()
	{
		List<HeartRate> points = new ArrayList<HeartRate>();

		Cursor cursor = database.query(DatabaseHelper.TABLE_HR, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			HeartRate point = new HeartRate();
			point.setMillsecondTimeStamp(String.valueOf(cursor.getLong(0)));
			point.setBpm(cursor.getInt(1));
			points.add(point);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return points;
	}
	
	public List<EegAttention> getAllDataPointsAttention()
	{
		List<EegAttention> points = new ArrayList<EegAttention>();

		Cursor cursor = database.query(DatabaseHelper.TABLE_ATTENTION, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			EegAttention point = new EegAttention();
			point.setMillisecondTimeStamp(String.valueOf(cursor.getLong(0)));
			point.setAttention(cursor.getInt(1));
			points.add(point);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return points;
	}
	
	public List<EegRaw> getAllDataPointsRaw()
	{
		List<EegRaw> points = new ArrayList<EegRaw>();

		Cursor cursor = database.query(DatabaseHelper.TABLE_RAW, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			EegRaw point = new EegRaw();
			point.setMillisecondTimeStamp(String.valueOf(cursor.getLong(0)));
			point.setRaw(cursor.getInt(1));//TODO (mbarnes) EegRaw needs to store 8 values, ch1 - ch8
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return points;
	}

	public void clearDatabse()
	{
		database.delete(DatabaseHelper.TABLE_EEG, null, null);
		database.delete(DatabaseHelper.TABLE_HR, null, null);
		database.delete(DatabaseHelper.TABLE_ATTENTION, null, null);
		database.delete(DatabaseHelper.TABLE_RAW, null, null);
	}
}